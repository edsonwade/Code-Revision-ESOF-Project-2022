import { AuditableEntity } from '../../../shared/types/common.types';

export interface Explainer extends AuditableEntity {
  id: number;
  name: string;
  email: string;
  language?: string; // Optional since not all explainers might have it yet
}

export interface ExplainerDto extends Explainer {}
