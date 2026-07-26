import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../services/application.service';
import { CompanyService } from '../../services/company.service';
import { ApplicationRequest } from '../../models/application.model';
import { Company } from '../../models/company.model';

@Component({
    selector: 'app-application-form',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './application-form.html',
    styleUrl: './application-form.css'
})
export class ApplicationFormComponent implements OnInit {
    isEditMode: boolean = false;
    applicationId: number | null = null;
    companies: Company[] = [];
    errorMessage: string = '';

    formData: ApplicationRequest = {
        position: '',
        appliedDate: '',
        jobPostingText: '',
        companyId: 0
    };

    constructor(
        private applicationService: ApplicationService,
        private companyService: CompanyService,
        private router: Router,
        private route: ActivatedRoute,
        private cdr: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.loadCompanies();

        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.isEditMode = true;
            this.applicationId = +id;
            this.loadApplication(this.applicationId);
        }
    }

    loadCompanies(): void {
        this.companyService.getAll().subscribe({
            next: (data) => {
                this.companies = data;
                this.cdr.detectChanges();
            },
            error: () => this.errorMessage = 'Fehler beim Laden der Firmen'
        });
    }

    loadApplication(id: number): void {
        this.applicationService.getById(id).subscribe({
            next: (data) => {
                this.formData = {
                    position: data.position,
                    appliedDate: data.appliedDate,
                    jobPostingText: data.jobPostingText,
                    companyId: data.companyId
                };
                this.cdr.detectChanges();
            },
            error: () => this.errorMessage = 'Fehler beim Laden der Bewerbung'
        });
    }

    onSubmit(): void {
        if (this.isEditMode && this.applicationId) {
            this.applicationService.update(this.applicationId, this.formData).subscribe({
                next: () => this.router.navigate(['/dashboard']),
                error: () => this.errorMessage = 'Fehler beim Aktualisieren'
            });
        } else {
            this.applicationService.create(this.formData).subscribe({
                next: () => this.router.navigate(['/dashboard']),
                error: () => this.errorMessage = 'Fehler beim Erstellen'
            });
        }
    }

    onCancel(): void {
        this.router.navigate(['/dashboard']);
    }
}