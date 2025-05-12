import { Component } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  username: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.username$.subscribe(name => {
      this.username = name;
    });
  }

}
