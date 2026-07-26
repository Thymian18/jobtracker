import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/auth.model';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './login.html',
    styleUrl: './login.css'
})
export class LoginComponent {
    credentials: LoginRequest = {
        username: '',
        password: ''
    };
    errorMessage: string = '';

    constructor(private authService: AuthService, private router: Router) {}

    onLogin(): void {
        this.authService.login(this.credentials).subscribe({
            next: () => this.router.navigate(['/dashboard']),
            error: () => this.errorMessage = 'Ungültige Anmeldedaten'
        });
    }
}