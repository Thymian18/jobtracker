import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../services/application.service';
import { Application, ApplicationStatus, ChangeStatusRequest } from '../../models/application.model';

@Component({
    selector: 'app-application-detail',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './application-detail.html',
    styleUrl: './application-detail.css'
})
export class ApplicationDetailComponent implements OnInit {
    application: Application | null = null;
    errorMessage: string = '';
    selectedStatus: ApplicationStatus = 'APPLIED';
    statusOptions: ApplicationStatus[] = ['APPLIED', 'INTERVIEW', 'OFFER', 'REJECTED'];

    constructor(
        private applicationService: ApplicationService,
        private route: ActivatedRoute,
        private router: Router,
        private cdr: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.loadApplication(+id);
        }
    }

    loadApplication(id: number): void {
        this.applicationService.getById(id).subscribe({
            next: (data) => {
                this.application = data;
                this.selectedStatus = data.status;
                this.cdr.detectChanges();
            },
            error: () => this.errorMessage = 'Fehler beim Laden der Bewerbung'
        });
    }

    changeStatus(): void {
        if (!this.application) return;

        const request: ChangeStatusRequest = { status: this.selectedStatus };
        this.applicationService.changeStatus(this.application.id, request).subscribe({
            next: (data) => {
                this.application = data;
                this.cdr.detectChanges();
            },
            error: () => this.errorMessage = 'Fehler beim Ändern des Status'
        });
    }

    editApplication(): void {
        if (!this.application) return;
        this.router.navigate(['/applications', this.application.id, 'edit']);
    }

    deleteApplication(): void {
        if (!this.application) return;
        if (confirm('Bewerbung wirklich löschen?')) {
            this.applicationService.delete(this.application.id).subscribe({
                next: () => this.router.navigate(['/dashboard']),
                error: () => this.errorMessage = 'Fehler beim Löschen'
            });
        }
    }

    goBack(): void {
        this.router.navigate(['/dashboard']);
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