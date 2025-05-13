import { Component } from '@angular/core';
import { McqTestComponent } from '../mcq-test/mcq-test.component';
import { LeaderboardComponent } from '../leaderboard/leaderboard.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-live-session',
  imports: [
    McqTestComponent,
    LeaderboardComponent,
    CommonModule
  ],
  templateUrl: './live-session.component.html',
  styleUrl: './live-session.component.css'
})
export class LiveSessionComponent {
  showLeaderboard = false;

  // Test is finished, show the leaderboard.
  updateTestDetails() {
    console.log("Inside updateTestDetails");
    this.showLeaderboard = true;
  }
}
