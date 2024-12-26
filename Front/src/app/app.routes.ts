import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component'; // Home Component
import { InputPageComponent } from './input-page/input-page.component'; // Input Page
import { InfoComponent } from './info/info.component';

export const routes: Routes = [
  { path: '', component: HomeComponent }, // Homepage is now '/'
  { path: 'input-page', component: InputPageComponent }, // Input page route
  { path: 'info', component: InfoComponent }, // Route for input page
  { path: '**', redirectTo: '' }, // Redirect unknown routes to homepage
];
