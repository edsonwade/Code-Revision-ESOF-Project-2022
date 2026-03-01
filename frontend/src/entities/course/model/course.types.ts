import { AuditableEntity } from '../../../shared/types/common.types';

export interface Course extends AuditableEntity {
  id: number;
  name: string;
}

export interface CourseDto extends Course {}
