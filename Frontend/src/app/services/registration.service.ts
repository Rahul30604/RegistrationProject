
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private apiUrl = 'http://localhost:8080/register';

  constructor(private http: HttpClient) {}

  registerUser(userDetails: any): Observable<any> {
    const headers = new HttpHeaders().set('Client_id', '10'); // Example header
    return this.http.post(this.apiUrl, userDetails, { headers });
  }

  getUsers(): Observable<any> {
    return this.http.get('http://localhost:8080/users'); // Endpoint to fetch registered users
  }
}
