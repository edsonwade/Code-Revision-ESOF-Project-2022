export interface AuditableEntity {
  createdAt: string | null;
  updatedAt: string | null;
  deletedAt: string | null;
}

export const USER_ROLE = {
  SUPER_ADMIN: 'SUPER_ADMIN',
  ADMIN: 'ADMIN',
  EXPLAINER: 'EXPLAINER',
  STUDENT: 'STUDENT',
  USER: 'USER',
} as const;

export type Role = typeof USER_ROLE[keyof typeof USER_ROLE];

export interface User {
  id: number;
  name: string;
  role: Role;
  email: string;
}
