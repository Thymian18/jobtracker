import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company, CompanyRequest } from '../models/company.model';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {
    private apiUrl = 'http://localhost:8080/companies';

    constructor(private http: HttpClient) {}

    getAll(): Observable<Company[]> {
        return this.http.get<Company[]>(this.apiUrl);
    }

    getById(id: number): Observable<Company> {
        return this.http.get<Company>(`${this.apiUrl}/${id}`);
    }

    create(request: CompanyRequest): Observable<Company> {
        return this.http.post<Company>(this.apiUrl, request);
    }

    update(id: number, request: CompanyRequest): Observable<Company> {
        return this.http.put<Company>(`${this.apiUrl}/${id}`, request);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}