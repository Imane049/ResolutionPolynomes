import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-info',
  standalone: true,
  imports: [],
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css'],
})
export class InfoComponent {
  polynomials = [
    {
      title: 'What is a Polynomial?',
      description: 'A polynomial is a mathematical expression consisting of variables, coefficients, and exponents.',
    },
    {
      title: 'Types of Polynomials',
      description: 'Polynomials can be classified based on their degree: linear, quadratic, cubic, etc.',
    },
    {
      title: 'Analytical Methods',
      description: 'Techniques like factorization, synthetic division, and quadratic formulas.',
    },
    {
      title: 'Numerical Methods',
      description: 'Methods such as Newton’s, Bisection, and Fixed Points are used for approximation.',
    },
  ];

  constructor(private router: Router) {}

  goToInputPage() {
    this.router.navigate(['/input-page']);
  }
}
