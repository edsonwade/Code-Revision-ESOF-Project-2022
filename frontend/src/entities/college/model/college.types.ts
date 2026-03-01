import { AuditableEntity } from '../../../shared/types/common.types';

export interface College extends AuditableEntity {
  id: number;
  name: string;
}

export interface CollegeDto extends College {}
