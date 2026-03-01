import React from 'react';
import {createBrowserRouter, redirect} from 'react-router-dom';
import {ROUTES} from './routes';
import {AppShell} from '../../widgets/layout/AppShell';
import AuthGuard from './guards/AuthGuard';

// Lazy loading pages
const Dashboard = React.lazy(() => import('../../pages/dashboard'));
const LoginPage = React.lazy(() => import('../../pages/auth/LoginPage'));
const RegisterPage = React.lazy(() => import('../../pages/auth/RegisterPage'));
const StudentListPage = React.lazy(() => import('../../pages/students/StudentListPage'));
const StudentDetailPage = React.lazy(() => import('../../pages/students/StudentDetailPage'));
const AppointmentListPage = React.lazy(() => import('../../pages/appointments'));
const ExplainerListPage = React.lazy(() => import('../../pages/explainers'));
const CollegeListPage = React.lazy(() => import('../../pages/colleges/CollegeListPage'));
const CourseListPage = React.lazy(() => import('../../pages/courses/CourseListPage'));
const DegreeListPage = React.lazy(() => import('../../pages/degrees/DegreeListPage'));
const ReviewListPage = React.lazy(() => import('../../pages/reviews/ReviewListPage'));

export const router = createBrowserRouter([
    {
        path: ROUTES.HOME,
        element: <AppShell/>,
        ErrorBoundary: () => <div>Something went wrong</div>,
        children: [
            {
                index: true,
                loader: () => redirect(ROUTES.DASHBOARD),
            },
            {
                path: ROUTES.DASHBOARD,
                element: (
                    <AuthGuard>
                        <Dashboard/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.STUDENTS,
                element: (
                    <AuthGuard>
                        <StudentListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.STUDENTS + '/:id',
                element: (
                    <AuthGuard>
                        <StudentDetailPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.APPOINTMENTS,
                element: (
                    <AuthGuard>
                        <AppointmentListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.EXPLAINERS,
                element: (
                    <AuthGuard>
                        <ExplainerListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.COLLEGES,
                element: (
                    <AuthGuard>
                        <CollegeListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.COURSES,
                element: (
                    <AuthGuard>
                        <CourseListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.DEGREES,
                element: (
                    <AuthGuard>
                        <DegreeListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.REVIEWS,
                element: (
                    <AuthGuard>
                        <ReviewListPage/>
                    </AuthGuard>
                ),
            },
            {
                path: ROUTES.FORBIDDEN,
                element: <div>403 - Forbidden</div>, // Or create a proper ForbiddenPage
            }
        ],
    },
    {
        path: ROUTES.LOGIN,
        element: <LoginPage/>,
    },
    {
        path: ROUTES.REGISTER,
        element: <RegisterPage/>,
    }
]);
