import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { McqTestService } from '../service/mcq-test.service';
import { FormsModule } from '@angular/forms';
import { McqTestDto } from '../dto/McqTestDto';
import { CountdownComponent } from '../countdown/countdown.component';

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
  mcqTestData: McqTestDto = new McqTestDto();
  mcqTestDisplayStatus: string = '';

  constructor(private mcqTestService: McqTestService) {
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
      this.showSessionDetails = true;
      this.mcqTestDisplayStatus = 'Your session is scheduled at ';
      
      if(response.id == null) {
        this.mcqTestDisplayStatus = 'Test Session Not Found';
        return;
      }
      
      this.showCountdown = true;
      this.mcqTestData = response;
    });
  }
}