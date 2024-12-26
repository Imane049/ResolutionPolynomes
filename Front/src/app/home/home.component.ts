import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private router: Router) {}

  goToInputPage() {
    this.router.navigate(['/input-page']);
  }
  goToInfoPage(){
    this.router.navigate(['/info']);
  }
}
