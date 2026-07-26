import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () => import('./components/login/login')
            .then(m => m.LoginComponent)
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./components/dashboard/dashboard')
            .then(m => m.DashboardComponent),
        canActivate: [authGuard]
    },
    // {
    //     path: 'applications',
    //     loadComponent: () => import('./components/application-list/application-list.component')
    //         .then(m => m.ApplicationListComponent),
    //     canActivate: [authGuard]
    // },
    {
        path: 'applications/new',
        loadComponent: () => import('./components/application-form/application-form')
            .then(m => m.ApplicationFormComponent),
        canActivate: [authGuard]
    },
    {
        path: 'applications/:id/edit',
        loadComponent: () => import('./components/application-form/application-form')
            .then(m => m.ApplicationFormComponent),
        canActivate: [authGuard]
    },
    {
        path: 'applications/:id',
        loadComponent: () => import('./components/application-detail/application-detail')
            .then(m => m.ApplicationDetailComponent),
        canActivate: [authGuard]
    },
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: 'login'
    }
];
