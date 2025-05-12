import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usernameSource = new BehaviorSubject<string>('No Info Available'); // Default empty value
  username$ = this.usernameSource.asObservable(); // Observable for real-time 

  private userLoggedInSource = new BehaviorSubject<boolean>(false); // Default empty value
  userLoggedIn$ = this.userLoggedInSource.asObservable(); // Observable for real-time 

  constructor() { }

  setUsername(name: string) {
    this.usernameSource.next(name);

    // Mark as LoggedIn as true.
    this.userLoggedInSource.next(true);
  }

}
