import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { FormsModule, FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { JoinLiveTestPopupComponent } from '../join-live-test-popup/join-live-test-popup.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [
    FormsModule,
    CommonModule,
    JoinLiveTestPopupComponent,
    ReactiveFormsModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  myForm = new FormGroup({
    textInput: new FormControl('', [Validators.required])
  });

  username: string = '';
  userLoggedIn: boolean = false;
  showJoinLivePopup = false;

  constructor(private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const savedUsername = sessionStorage.getItem('username');
    if (savedUsername != null || savedUsername != undefined) {
      //already logged-in
      this.username = savedUsername;
      this.onSubmit();
    }
  }

  onSubmit() {
    this.userService.setUsername(this.username);
    this.userService.userLoggedIn$.subscribe(response => {
      this.userLoggedIn = response;
    })

    // Reset 'Enter your name' Form Fields
    this.username = '';
  }

  joinLiveTest() {
    this.showJoinLivePopup = true;
  }

  navigateToEditor() {
    this.router.navigate(['/editor'], {});
  }
}
