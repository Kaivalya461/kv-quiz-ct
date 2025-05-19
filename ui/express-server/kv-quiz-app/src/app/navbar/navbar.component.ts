import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { CommonModule } from '@angular/common';
import { ProgressService } from '../service/progress.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  username: string = '';
  userLoggedIn: boolean = false;
  testInProgress: boolean = false;

  constructor(
    private userService: UserService,
    private progressService: ProgressService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService.username$.subscribe(name => {
      this.username = name;
    });

    this.userService.userLoggedIn$.subscribe(response => {
      this.userLoggedIn = response;
    })

    this.progressService.testInProgress$.subscribe(response => {
      this.testInProgress = response;
    })
  }

  changeUser() {
    // Clear existing User Details.
    this.userService.clearUsername();
    this.router.navigate(['/'], { });
  }
}
