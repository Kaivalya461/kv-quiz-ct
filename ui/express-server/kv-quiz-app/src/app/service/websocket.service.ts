import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private wsBaseUrl = environment.webSocketBaseUrl;

  private socket: WebSocket;
  private subject!: Subject<MessageEvent>;

  constructor() {
    this.socket = new WebSocket(this.wsBaseUrl + '/test-progress');
  }

  connect(): Observable<MessageEvent> {
    if (!this.subject) {
      this.socket = new WebSocket(this.wsBaseUrl + '/test-progress');
      this.subject = this.createObservable();
    }
    return this.subject.asObservable();
  }

  private createObservable(): Subject<MessageEvent> {
    const subject = new Subject<MessageEvent>();
    this.socket.onmessage = (event) => subject.next(event);
    this.socket.onerror = (event) => subject.error(event);
    this.socket.onclose = () => subject.complete();
    return subject;
  }

  sendMessage(message: string): void {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(message);
    }
  }
}
