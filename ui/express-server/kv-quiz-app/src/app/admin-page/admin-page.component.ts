import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProgressComponent } from '../progress/progress.component';
import { CommonModule } from '@angular/common';
import { ProgressService } from '../service/progress.service';
import { UserProgressDto } from '../dto/UserProgressDto';

@Component({
  selector: 'app-admin-page',
  imports: [
    FormsModule,
    CommonModule,
    ProgressComponent
  ],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css'
})
export class AdminPageComponent {
  testId: string = '';
  isSubmitted = false;

  constructor(private progressService: ProgressService) {

  }

  testProgressFormSubmit() {
    this.isSubmitted = true;

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
