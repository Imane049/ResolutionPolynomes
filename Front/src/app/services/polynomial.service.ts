import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PolynomialRequest {
  polynomial: string;
  domain: string;
  method: string; // "numerical", "analytical", or "detailed"
}

export interface PolynomialResult {
  roots?: string[]; // For numerical/analytical results
  factorizedForm?: string; // For numerical/analytical results
  text?: string; // For detailed results
}

@Injectable({
  providedIn: 'root'
})
export class PolynomialService {
  private apiUrl = 'http://localhost:8080/api/v1/polynomial/evaluate';

  constructor(private http: HttpClient) {}

  evaluatePolynomial(request: PolynomialRequest): Observable<PolynomialResult> {
    return this.http.post<PolynomialResult>(this.apiUrl, request);
  }
}
