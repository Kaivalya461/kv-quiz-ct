import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { McqTestService } from '../service/mcq-test.service';
import { FormsModule } from '@angular/forms';
import { McqTestDto } from '../dto/McqTestDto';
import { CountdownComponent } from '../countdown/countdown.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-join-live-test-popup',
  imports: [
    CommonModule,
    FormsModule,
    CountdownComponent
  ],
  templateUrl: './join-live-test-popup.component.html',
  styleUrl: './join-live-test-popup.component.css'
})
export class JoinLiveTestPopupComponent {
  @Input() showPopup = false;
  @Output() closePopup = new EventEmitter<void>();

  showSessionDetails: boolean = false;
  showCountdown: boolean = false;
  joinCode: string = '';
  selectedCategory: string = '';
  mcqTestData: McqTestDto = new McqTestDto();
  mcqTestDisplayStatus: string = '';
  

  constructor(private mcqTestService: McqTestService,
    private router: Router
  ) {
  }

  close() {
    this.closePopup.emit(); // Notify the parent to close the popup
  }

  onSubmit() {
    // clear previously fetched Details and Countdown timer.
    this.showSessionDetails = false;
    this.mcqTestData = new McqTestDto();
    this.showCountdown = false;

    this.mcqTestService.getMcqTestDetails(this.joinCode).subscribe(response => {
      if(response.id == null) {
        this.mcqTestDisplayStatus = 'Test Session Not Found';
        return;
      } else if (response.scheduledAt != undefined && this.isPastDateTime(response.scheduledAt)) {
        console.log("Navigating to Live-Session");
        this.navigateToLiveSession(this.joinCode);
      }
  
      this.showSessionDetails = true;
      this.mcqTestDisplayStatus = 'Your session is scheduled at ';
      
      this.showCountdown = true;
      this.mcqTestData = response;
    });
  }

  navigateToLiveSession(mcqTestId: string): void {
    this.router.navigate(['/live-session'], { queryParams: { 'mcq-test-id': mcqTestId, 'test-category': this.selectedCategory} });
  }

  isPastDateTime(inputUtc: Date): boolean {
    // Parse input UTC datetime
    const inputDate = new Date(inputUtc);

    // Get current datetime in host machine's timezone
    const currentDate = new Date();

    // Convert both dates to comparable timestamps
    return inputDate.getTime() < currentDate.getTime();
  }
}