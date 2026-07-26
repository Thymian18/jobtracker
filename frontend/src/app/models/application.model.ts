export interface StatusHistory {
    id: number;
    status: ApplicationStatus;
    changedAt: string;
}

export interface Application {
    id: number;
    position: string;
    status: ApplicationStatus;
    appliedDate: string;
    jobPostingText: string;
    companyId: number;
    companyName: string;
    statusHistory: StatusHistory[];
}

export interface ApplicationRequest {
    position: string;
    appliedDate: string;
    jobPostingText?: string;
    companyId: number;
}

export interface ChangeStatusRequest {
    status: ApplicationStatus;
}

export type ApplicationStatus = 'APPLIED' | 'INTERVIEW' | 'OFFER' | 'REJECTED';