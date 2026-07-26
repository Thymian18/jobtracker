import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { ApplicationService } from '../../services/application.service';
import { AuthService } from '../../services/auth.service';
import { Application } from '../../models/application.model';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit {
  applications: Application[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(
    private applicationService: ApplicationService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications(): void {
    console.log('loadApplications wird aufgerufen');
    this.applicationService.getAll().subscribe({
        next: (data) => {
            console.log('Daten erhalten:', data);
            this.applications = data;
            this.isLoading = false;
            this.cdr.detectChanges();
            console.log('isLoading ist jetzt:', this.isLoading);
        },
        error: (error) => {
            console.log('Fehler:', error);
            this.errorMessage = 'Fehler beim Laden der Bewerbungen';
            this.isLoading = false;
            this.cdr.detectChanges();
        }
    });
}

  navigateToDetail(id: number): void {
        this.router.navigate(['/applications', id]);
    }

    navigateToNew(): void {
        this.router.navigate(['/applications/new']);
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

    getStatusClass(status: string): string {
        switch (status) {
            case 'APPLIED': return 'status-applied';
            case 'INTERVIEW': return 'status-interview';
            case 'OFFER': return 'status-offer';
            case 'REJECTED': return 'status-rejected';
            default: return '';
        }
    }
}
