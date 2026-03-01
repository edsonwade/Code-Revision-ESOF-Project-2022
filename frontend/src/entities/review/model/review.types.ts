import { AuditableEntity } from '../../../shared/types/common.types';

export interface Review extends AuditableEntity {
  id: number;
  comment: string;
  rating: number;
  studentId: number;
  explainerId: number;
}

export interface ReviewCreateDto {
  comment: string;
  rating: number;
  studentId: number;
  explainerId: number;
}

export interface ReviewResponseDto extends Review {}
