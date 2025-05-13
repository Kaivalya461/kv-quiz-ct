import { Component, Output, EventEmitter } from '@angular/core';
import { McqTestDto } from '../dto/McqTestDto';
import { Question } from '../dto/QuestionDto';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LeaderboardService } from '../service/leaderboard.service';
import { UserService } from '../service/user.service';
import { TestQuestionService } from '../service/test-question.service';
import { McqTestService } from '../service/mcq-test.service';
import { TestQuestionDto } from '../dto/TestQuestionDto';
import { CountdownComponent } from '../countdown/countdown.component';

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

  pageSize = 5; // Show 5 questions per page
  currentPage = 1;
  paginatedQuestions: Question[] = [];

  userAnswers: Record<number, string> = {};
  @Output() isTestFinishedEvent = new EventEmitter<boolean>();
  isTestFinished: boolean = false;
  username: string = '';
  testCategory: string | null = '';

  testFinishDateTime: Date | undefined;
  showTimeRemaining: boolean = false;

  constructor(
    private mcqTestDetailService: McqTestService,
    private testQuestionService: TestQuestionService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService,
    private userService: UserService
  ) {

  }

  ngOnInit(): void {
    const mcqTestId = this.route.snapshot.queryParamMap.get('mcq-test-id');
    this.testCategory = this.route.snapshot.queryParamMap.get('test-category');
    if (this.test.id == null && mcqTestId != null && this.testCategory != null) {
      this.testQuestionService.getQuestionsForTest(mcqTestId, this.testCategory).subscribe(data => {
        this.testQuestionDto = data;
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
    }
  }

  nextPage() {
    if (this.currentPage * this.pageSize < this.testQuestionDto.questions.length) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  submitTest() {
    let correctAnswers = 0;

    this.testQuestionDto.questions.forEach((question) => {
      if (this.userAnswers[question.id] === question.correctAnswer) {
        correctAnswers++;
      }
    });

    const totalQuestions = this.testQuestionDto.questions.length;
    const scorePercentage = (correctAnswers / totalQuestions) * 100;

    // Update leaderboard
    this.leaderboardService.addUserResult(this.username, scorePercentage);
    // Disable questions section
    this.isTestFinished = true;
    this.isTestFinishedEvent.emit(true); // Emit new value to parent

    // Time Remaining
    this.showTimeRemaining = false;

    alert(`Test Submitted! 🎉\nYour Score: ${correctAnswers}/${totalQuestions} (${scorePercentage.toFixed(2)}%)`);
  }

  getTestFinishTime(minutesAhead: number) {
    const currentDateTime = new Date();
    currentDateTime.setMinutes(currentDateTime.getMinutes() + minutesAhead);
    return currentDateTime;
  }
}
