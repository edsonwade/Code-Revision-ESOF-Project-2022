import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.availability.AvailabilityResponseDTO
// Last verified: 2026-03-07
export interface AvailabilityDto extends AuditableEntity {
  id: number;
  explainerId: number | null;
  explainerName: string | null;
  startTime: string;
  endTime: string;
}

// Synchronized with: ufp.esof.project.dto.availability.AvailabilityRequestDTO
// Fields: explainerId (Long), startTime (LocalDateTime), endTime (LocalDateTime)
export interface CreateAvailabilityRequest {
  explainerId: number;
  startTime: string;
  endTime: string;
}
