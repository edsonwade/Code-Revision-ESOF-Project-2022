import { AuditableEntity } from '../../../shared/types/common.types';

export interface Degree extends AuditableEntity {
  id: number;
  name: string;
}

export interface DegreeDto extends Degree {}
