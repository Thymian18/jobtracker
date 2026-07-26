import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Application, ApplicationRequest, ChangeStatusRequest } from '../models/application.model';

@Injectable({
    providedIn: 'root'
})
export class ApplicationService {
    private apiUrl = 'http://localhost:8080/applications';

    constructor(private http: HttpClient) {}

    getAll(): Observable<Application[]> {
        return this.http.get<Application[]>(this.apiUrl);
    }

    getById(id: number): Observable<Application> {
        return this.http.get<Application>(`${this.apiUrl}/${id}`);
    }

    create(request: ApplicationRequest): Observable<Application> {
        return this.http.post<Application>(this.apiUrl, request);
    }

    update(id: number, request: ApplicationRequest): Observable<Application> {
        return this.http.put<Application>(`${this.apiUrl}/${id}`, request);
    }

    changeStatus(id: number, request: ChangeStatusRequest): Observable<Application> {
        return this.http.patch<Application>(`${this.apiUrl}/${id}/status`, request);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}