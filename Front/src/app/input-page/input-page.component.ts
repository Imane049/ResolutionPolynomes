import { Component } from '@angular/core';
import { PolynomialService, PolynomialRequest, PolynomialResult } from '../services/polynomial.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-input-page',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './input-page.component.html',
  styleUrls: ['./input-page.component.css']
})
export class InputPageComponent {
  inputValue: string = '';
  domain: string = 'real';
  selectedAnalyticalMethod: string = '';
  showAnalyticalOptions: boolean = false;
  textAreaValue: string = '';
  showDetailedModal: boolean = false;
  tooltipText: string = '';
  tooltipVisible: boolean = false;

  analyticalMethodInfo: { [key: string]: string } = {
    newton: 'Newton-Raphson: Iterative root-finding method for real-valued functions.',
    bisection: 'Bisection: Root-finding method by interval division.',
    bairstow: 'Bairstow: Root-solving method for polynomials with real coefficients.',
    muller: 'Müller: Iterative method for finding polynomial roots.',
    fixedpoints: 'Fixed Points: Root-finding using fixed-point iteration.'
  };

  constructor(private polynomialService: PolynomialService) {}

  selectMethod(method: string) {
    this.showAnalyticalOptions = method === 'analytical';
  }

  showTooltip(method: string) {
    this.tooltipText = this.analyticalMethodInfo[method] || '';
    this.tooltipVisible = true;
  }

  hideTooltip() {
    this.tooltipVisible = false;
  }

  // Standard resolution
  onResolutionClick() {
    if (!this.inputValue) {
      this.textAreaValue = 'Veuillez entrer un polynôme valide.';
      return;
    }

    const method = this.showAnalyticalOptions ? this.selectedAnalyticalMethod : 'numerical';

    const request: PolynomialRequest = {
      polynomial: this.inputValue,
      domain: this.domain,
      method: method
    };

    this.polynomialService.evaluatePolynomial(request).subscribe({
      next: (response) => {
        if (response.roots && response.factorizedForm) {
          const roots = response.roots.join(', ');
          this.textAreaValue = `Racines: ${roots}\nForme Factorisée: ${response.factorizedForm}`;
        } else {
          this.textAreaValue = 'Résultat invalide ou incomplet.';
        }
      },
      error: (err) => {
        console.error('Erreur API:', err);
        this.textAreaValue = '❌ Veuillez fournir un polynôme valide avec une méthode et un ordre supportés.';
      }
    });
  }

  // Detailed resolution with modal
  onDetailedResolutionClick() {
    if (!this.inputValue) {
      this.textAreaValue = 'Veuillez entrer un polynôme valide.';
      return;
    }

    const request: PolynomialRequest = {
      polynomial: this.inputValue,
      domain: this.domain,
      method: 'detailed'
    };

    this.polynomialService.evaluatePolynomial(request).subscribe({
      next: (response) => {
        if (response.text) {
          this.textAreaValue = response.text;
          this.showDetailedModal = true;
        } else {
          this.textAreaValue = 'Aucune explication détaillée disponible.';
        }
      },
      error: (err) => {
        console.error('Erreur API:', err);
        this.textAreaValue = '❌ Veuillez fournir un polynôme valide avec une méthode et un ordre supportés.';
      }
    });
  }

  closeDetailedModal() {
    this.showDetailedModal = false;
  }
}
