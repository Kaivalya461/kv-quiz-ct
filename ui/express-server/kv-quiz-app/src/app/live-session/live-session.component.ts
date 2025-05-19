import { Component } from '@angular/core';
import { McqTestComponent } from '../mcq-test/mcq-test.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProgressComponent } from '../progress/progress.component';
import { ActivatedRoute } from '@angular/router';
import { ProgressService } from '../service/progress.service';

@Component({
  selector: 'app-live-session',
  imports: [
    McqTestComponent,
    CommonModule,
    ProgressComponent,
    FormsModule
  ],
  templateUrl: './live-session.component.html',
  styleUrl: './live-session.component.css'
})
export class LiveSessionComponent {
  showLeaderboard = false;
  mcqTestId: string = '';

  constructor(
    private progressService: ProgressService,
    private route: ActivatedRoute
  ) {
    const mcqTestId = this.route.snapshot.queryParamMap.get('mcq-test-id');
    if (mcqTestId != null) {
      this.mcqTestId = mcqTestId;
    }
  }

  // Test is finished, show the leaderboard.
  updateTestDetails() {
    console.log("Inside updateTestDetails");
    this.showLeaderboard = true;
  }
}
