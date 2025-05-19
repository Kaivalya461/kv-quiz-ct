import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProgressDto } from '../dto/UserProgressDto';
import { TestUsersDto } from '../dto/TestUsersDto';
import { WebsocketService } from '../service/websocket.service';

@Component({
  selector: 'app-progress',
  imports: [
    CommonModule
  ],
  templateUrl: './progress.component.html',
  styleUrl: './progress.component.css'
})
export class ProgressComponent {
  @Input() mcqTestId: string = '';

  testUsersDtoArray: TestUsersDto[] = [];
  usersProgress: UserProgressDto[] = [];

  constructor(
    private websocketService: WebsocketService
  ) {

  }

  ngOnInit(): void {
    // const mcqTestId = this.route.snapshot.queryParamMap.get('mcq-test-id');

    if (this.mcqTestId != null && this.mcqTestId != '') {
      // Connect to WebSocket and listen for real-time updates
      this.websocketService.connect().subscribe({
        next: (event) => {
          const data = JSON.parse(event.data); // Convert received message to JSON
          this.testUsersDtoArray = data; // Update the progress tracking object
          const filteredUsers = this.testUsersDtoArray.find(item => item.testId === this.mcqTestId)?.users || [];
          this.usersProgress = filteredUsers;
        },
        error: (err) => console.error("WebSocket error:", err),
        complete: () => console.warn("WebSocket connection closed"),
      });
    } else {
      console.log("MCQ ID Not Found");
    }
  }
  
}
