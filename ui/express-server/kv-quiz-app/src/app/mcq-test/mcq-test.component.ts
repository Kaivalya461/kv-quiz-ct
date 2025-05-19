import { Component, Output, EventEmitter } from '@angular/core';
import { McqTestDto } from '../dto/McqTestDto';
import { Question } from '../dto/QuestionDto';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LeaderboardService } from '../service/leaderboard.service';
import { UserService } from '../service/user.service';
import { TestQuestionService } from '../service/test-question.service';
import { McqTestService } from '../service/mcq-test.service';
import { TestQuestionDto } from '../dto/TestQuestionDto';
import { CountdownComponent } from '../countdown/countdown.component';
import { ProgressService } from '../service/progress.service';
import { UserProgressDto } from '../dto/UserProgressDto';

@Component({
  selector: 'app-mcq-test',
  imports: [
    CommonModule,
    FormsModule,
    CountdownComponent
  ],
  templateUrl: './mcq-test.component.html',
  styleUrl: './mcq-test.component.css'
})
export class McqTestComponent {
  test: McqTestDto = new McqTestDto();
  testQuestionDto: TestQuestionDto = new TestQuestionDto();
  totalQuestionsCount = 0;
  mcqTestId: any;

  pageSize = 5; // Show 5 questions per page
  currentPage = 1;
  paginatedQuestions: Question[] = [];

  userAnswers: Record<number, string> = {};
  @Output() isTestFinishedEvent = new EventEmitter<boolean>();
  isTestFinished: boolean = false;
  correctAnswers: number = 0;
  totalQuestions: number = 0;
  userFinalScore: number = 0;

  username: string = '';
  testCategory: string | null = '';

  testFinishDateTime: Date | undefined;
  showTimeRemaining: boolean = false;

  constructor(
    private mcqTestDetailService: McqTestService,
    private testQuestionService: TestQuestionService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService,
    private userService: UserService,
    private progressService: ProgressService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    this.mcqTestId = this.route.snapshot.queryParamMap.get('mcq-test-id');
    const mcqTestId = this.mcqTestId;
    this.testCategory = this.route.snapshot.queryParamMap.get('test-category');
    if (this.test.id == null && mcqTestId != null && this.testCategory != null) {
      this.testQuestionService.getQuestionsForTest(mcqTestId, this.testCategory).subscribe(data => {
        this.testQuestionDto = data;
        this.totalQuestionsCount = data.questions.length;
        this.updatePagination();
      });
    }

    if (this.test.id == null && mcqTestId != null) {
      this.mcqTestDetailService.getMcqTestDetails(mcqTestId).subscribe(response => {
        this.test = response;

        // Test Time remaining countdown
        this.testFinishDateTime = this.getTestFinishTime(this.test.durationInMinutes);
        this.showTimeRemaining = true;
      });

      // Update testInProgress flag as true
      this.progressService.testStart(mcqTestId);
    }

    this.userService.username$.subscribe(name => this.username = name);


  }

  updatePagination() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.paginatedQuestions = this.testQuestionDto.questions.slice(startIndex, endIndex);
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
      this.sendTestUpdateEvent();
    }
  }

  nextPage() {
    if (this.currentPage * this.pageSize < this.testQuestionDto.questions.length) {
      this.currentPage++;
      this.updatePagination();
      this.sendTestUpdateEvent();
    }
  }

  submitTest() {
    this.testQuestionDto.questions.forEach((question) => {
      if (this.userAnswers[question.id] === question.correctAnswer) {
        this.correctAnswers++;
      }
    });

    this.totalQuestions = this.testQuestionDto.questions.length;
    this.userFinalScore = (this.correctAnswers / this.totalQuestions) * 100;;

    // Update leaderboard
    // this.leaderboardService.addUserResult(this.username, this.userFinalScore);
    // Disable questions section
    this.isTestFinished = true;
    this.isTestFinishedEvent.emit(true); // Emit new value to parent

    // Time Remaining
    this.showTimeRemaining = false;

    // Update for Real-Time events
    this.sendTestCompletionEvent();

    // Update testInProgress flag as false
    this.progressService.testFinish();

    // Wait for few seconds, and redirect to /leaderboard
    setTimeout(() => {
      this.navigateToLeaderboardPage(this.mcqTestId);
    }, 4000);

    // alert(`Test Submitted! 🎉\nYour Score: ${this.correctAnswers}/${this.totalQuestions} (${this.userFinalScore.toFixed(2)}%)`);
  }

  getTestFinishTime(minutesAhead: number) {
    const currentDateTime = new Date();
    currentDateTime.setMinutes(currentDateTime.getMinutes() + minutesAhead);
    return currentDateTime;
  }

  getCurrentUserProgress(): number {
    const answeredQuestionsCount = Object.keys(this.userAnswers).length;
    return ( answeredQuestionsCount / this.totalQuestionsCount ) * 100;
  }

  sendTestUpdateEvent() {
    const userProgressDto = new UserProgressDto(
      this.username,
      this.getCurrentUserProgress(),
      false,
      Object.keys(this.userAnswers).length,
      this.correctAnswers,
      this.totalQuestions
    );
    this.progressService.sendMessage(this.test.id!, userProgressDto);
  }

  sendTestCompletionEvent() {
    const userProgressDto = new UserProgressDto(
      this.username,
      this.getCurrentUserProgress(),
      true,
      Object.keys(this.userAnswers).length,
      this.correctAnswers,
      this.totalQuestions
    );
    this.progressService.sendMessage(this.test.id!, userProgressDto);
  }

  navigateToLeaderboardPage(mcqTestId: string): void {
    this.router.navigate(['/leaderboard'], { queryParams: { 'mcq-test-id': mcqTestId, 'test-type': 'live-mcq'} });
  }
}
