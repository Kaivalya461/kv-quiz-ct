import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { JoinLiveTestPopupComponent } from '../join-live-test-popup/join-live-test-popup.component';

@Component({
  selector: 'app-home',
  imports: [
    FormsModule,
    CommonModule,
    JoinLiveTestPopupComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  username: string = '';
  userLoggedIn: boolean = false;
  showJoinLivePopup = false;

  constructor(private userService: UserService) {}

  onSubmit() {
    this.userService.setUsername(this.username);
    this.userService.userLoggedIn$.subscribe(response => {
      this.userLoggedIn = response;
    })
  }

  joinLiveTest() {
    this.showJoinLivePopup = true;
  }
}
