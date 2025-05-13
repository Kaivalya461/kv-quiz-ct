import { Component } from '@angular/core';
import { LeaderboardService } from '../service/leaderboard.service';
import { CommonModule } from '@angular/common';

export interface UserResult {
  username: string;
  score: number;
}

@Component({
  selector: 'app-leaderboard',
  imports: [
    CommonModule
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {
  leaderboard: UserResult[] = [];

  constructor(private leaderboardService: LeaderboardService) {}

  ngOnInit(): void {
    this.leaderboard = this.leaderboardService.getLeaderboard();
  }
}
