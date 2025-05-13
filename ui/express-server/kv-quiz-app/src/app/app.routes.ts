import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LiveSessionComponent } from './live-session/live-session.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
  { path: 'live-session', component: LiveSessionComponent }
];
