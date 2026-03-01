import { createBrowserRouter, Navigate } from 'react-router-dom';
import { AppShell } from '@widgets/layout/AppShell';
import { AuthGuard } from './guards/AuthGuard';
import { ROUTES } from './routes';

import { LoginPage } from '@pages/auth/LoginPage';
import { DashboardPage } from '@pages/dashboard/DashboardPage';
import { StudentListPage } from '@pages/students/StudentListPage';
import { ExplainerListPage } from '@pages/explainers/ExplainerListPage';
import { AppointmentListPage } from '@pages/appointments/AppointmentListPage';
import { AvailabilityPage } from '@pages/availability/AvailabilityPage';
import { CoursesPage } from '@pages/courses/CoursesPage';
import { DegreesPage } from '@pages/degrees/DegreesPage';
import { CollegesPage } from '@pages/colleges/CollegesPage';
import { ReviewsPage } from '@pages/reviews/ReviewsPage';
import { SettingsPage } from '@pages/settings/SettingsPage';

function ForbiddenPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen gap-4">
      <h1 className="font-display font-bold text-2xl text-[#F1F5F9]">403 — Forbidden</h1>
      <p className="text-[#94A3B8]">You do not have permission to access this page.</p>
    </div>
  );
}

function NotFoundPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[60vh] gap-4">
      <h1 className="font-display font-bold text-5xl text-[#1E2438]">404</h1>
      <p className="text-[#94A3B8]">Page not found.</p>
      <a href="/" className="text-sm text-[#5B8AF5] underline">Go home</a>
    </div>
  );
}

export const router = createBrowserRouter([
  { path: ROUTES.LOGIN, element: <LoginPage /> },
  { path: ROUTES.FORBIDDEN, element: <ForbiddenPage /> },
  {
    element: <AuthGuard />,
    children: [
      {
        element: <AppShell />,
        children: [
          { path: ROUTES.DASHBOARD, element: <DashboardPage /> },
          { path: ROUTES.STUDENTS, element: <StudentListPage /> },
          { path: ROUTES.EXPLAINERS, element: <ExplainerListPage /> },
          { path: ROUTES.APPOINTMENTS, element: <AppointmentListPage /> },
          { path: ROUTES.AVAILABILITY, element: <AvailabilityPage /> },
          { path: ROUTES.COURSES, element: <CoursesPage /> },
          { path: ROUTES.DEGREES, element: <DegreesPage /> },
          { path: ROUTES.COLLEGES, element: <CollegesPage /> },
          { path: ROUTES.REVIEWS, element: <ReviewsPage /> },
          { path: ROUTES.SETTINGS, element: <SettingsPage /> },
          { path: '*', element: <NotFoundPage /> },
        ],
      },
    ],
  },
  { path: '*', element: <Navigate to={ROUTES.DASHBOARD} replace /> },
]);
