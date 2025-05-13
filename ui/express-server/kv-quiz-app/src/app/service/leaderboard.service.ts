import { Injectable } from '@angular/core';

export interface UserResult {
  username: string;
  score: number;
}


@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {

  constructor() { }

  private leaderboard: UserResult[] = [];

  addUserResult(username: string, score: number) {
    this.leaderboard.push({ username, score });
    this.leaderboard.sort((a, b) => b.score - a.score); // Sort by highest score
  }

  getLeaderboard(): UserResult[] {
    return this.leaderboard;
  }

}
