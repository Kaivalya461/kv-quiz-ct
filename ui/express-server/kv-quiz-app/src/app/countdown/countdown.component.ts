import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-countdown',
  imports: [
  ],
  templateUrl: './countdown.component.html',
  styleUrl: './countdown.component.css'
})
export class CountdownComponent {
  @Input() futureDate!: Date | undefined;
  remainingTime: string = '';
  interval!: any;
  @Output() timesUpEvent = new EventEmitter<boolean>(false);

  ngOnInit(): void {
    this.startCountdown();
  }

  startCountdown() {
    if (!this.futureDate) {
      this.remainingTime = "No scheduled test.";
      return;
    }

    this.interval = setInterval(() => {
      const now = new Date().getTime();
      const future = new Date(this.futureDate!).getTime();
      const timeLeft = future - now;

      if (timeLeft <= 0) {
        this.remainingTime = "Time's up!";
        this.timesUpEvent.emit(true);
        clearInterval(this.interval);
      } else {
        const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
        const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

        // Constructing the time display dynamically
        let timeParts: string[] = [];
        if (days > 0) timeParts.push(`${days}d`);
        if (hours > 0) timeParts.push(`${hours}h`);
        if (minutes > 0) timeParts.push(`${minutes}m`);
        if (seconds > 0) timeParts.push(`${seconds}s`);

        this.remainingTime = timeParts.join(' ');
      }
    }, 1000);
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }

}
