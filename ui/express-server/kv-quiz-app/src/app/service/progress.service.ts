import { Injectable } from '@angular/core';
import { UserProgressDto } from '../dto/UserProgressDto';
import { TestProgressUpdateEventDto } from '../dto/TestProgressUpdateEventDto';
import { WebsocketService } from './websocket.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  mcqTestId: string = '';

  private testInProgressSource = new BehaviorSubject<boolean>(false); // Default empty value
  testInProgress$ = this.testInProgressSource.asObservable(); // Observable for real-time

  constructor(private websocketService: WebsocketService) { }

  sendMessage(testId: string, userProgressDto: UserProgressDto): void {
    const updateEvent = new TestProgressUpdateEventDto(testId, userProgressDto);
    this.websocketService.sendMessage(updateEvent.toJson());
  }

  testStart(mcqTestId: string) {
    this.mcqTestId = mcqTestId;
    this.testInProgressSource.next(true);
  }

  testFinish() {
    this.testInProgressSource.next(false);
  }
}
