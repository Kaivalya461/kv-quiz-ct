import { Component } from '@angular/core';
import { LeaderboardService } from '../service/leaderboard.service';
import { CommonModule } from '@angular/common';
import { UserProgressDto } from '../dto/UserProgressDto';
import { ActivatedRoute } from '@angular/router';
import { WebsocketService } from '../service/websocket.service';
import { TestUsersDto } from '../dto/TestUsersDto';
import { ProgressService } from '../service/progress.service';
import { ProgressComponent } from '../progress/progress.component';

export interface UserResult {
  username: string;
  score: number;
}

@Component({
  selector: 'app-leaderboard',
  imports: [
    CommonModule,
    ProgressComponent
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {
  leaderboard: UserProgressDto[] = [];
  mcqTestId: any;
  testUsersDtoArray: TestUsersDto[] = [];

  constructor(private progressService: ProgressService,
    private route: ActivatedRoute,
    private websocketService: WebsocketService
  ) {
      this.mcqTestId = this.route.snapshot.queryParamMap.get('mcq-test-id');
  }

  ngOnInit(): void {
    if (this.mcqTestId != null && this.mcqTestId != '') {
      // Connect to WebSocket and listen for real-time updates
      this.websocketService.connect().subscribe({
        next: (event) => {
          const data = JSON.parse(event.data); // Convert received message to JSON
          this.testUsersDtoArray = data; // Update the progress tracking object
          const filteredUsers = this.testUsersDtoArray.find(item => item.testId === this.mcqTestId)?.users || [];
          this.leaderboard = filteredUsers;
          console.log("Data from WSS: " + filteredUsers);
          this.leaderboard.sort((a, b) => b.correctAnswers - a.correctAnswers);
        },
        error: (err) => console.error("WebSocket error:", err),
        complete: () => console.warn("WebSocket connection closed"),
      });

      setTimeout(() => this.sendDummyEvent(), 1000);
    } else {
      console.log("MCQ ID Not Found");
    }


  }

  sendDummyEvent() {
    // Dummy Event to trigger all session broadcast
    const userProgressDto = new UserProgressDto(
      'DummyUser',
      0,
      false,
      0,
      0,
      0
    );
    this.progressService.sendMessage('DummyTest', userProgressDto);
  }
}
