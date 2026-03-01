// Matches AuditableEntity base class in backend
export interface AuditableEntity {
  createdAt: string | null;
  updatedAt: string | null;
  deletedAt: string | null; // soft delete marker — never display
}

export type SortDirection = 'asc' | 'desc';

export interface SortState {
  id: string;
  direction: SortDirection;
}
