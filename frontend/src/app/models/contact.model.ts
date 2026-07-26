export interface Contact {
    id: number;
    name: string;
    role: string;
    email: string;
    companyId: number;
    companyName: string;
}

export interface ContactRequest {
    name: string;
    role?: string;
    email: string;
    companyId: number;
}