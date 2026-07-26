import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    register(request: RegisterRequest): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/register`, request);
    }

    login(request: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
            tap(response => localStorage.setItem('token', response.token))
        );
    }

    logout(): void {
        localStorage.removeItem('token');
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    isLoggedIn(): boolean {
        return this.getToken() !== null;
    }
}