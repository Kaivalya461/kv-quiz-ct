import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LiveSessionComponent } from './live-session/live-session.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { KvCodeEditorComponent } from './kv-code-editor/kv-code-editor.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'live-session', component: LiveSessionComponent },
  { path: 'admin', component: AdminPageComponent },
  { path: 'leaderboard', component: LeaderboardComponent },
  { path: 'editor', component: KvCodeEditorComponent }
];
