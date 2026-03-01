export const ROUTES = {
  DASHBOARD: '/',
  LOGIN: '/login',
  FORBIDDEN: '/forbidden',
  STUDENTS: '/students',
  STUDENT_DETAIL: '/students/:id',
  EXPLAINERS: '/explainers',
  APPOINTMENTS: '/appointments',
  AVAILABILITY: '/availability',
  COURSES: '/courses',
  DEGREES: '/degrees',
  COLLEGES: '/colleges',
  REVIEWS: '/reviews',
  SETTINGS: '/settings',
} as const;

export function buildStudentDetailPath(id: number): string {
  return `/students/${id}`;
}
