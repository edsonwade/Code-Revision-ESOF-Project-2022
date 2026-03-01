import { cn } from '@shared/lib/cn';
import type { AppointmentStatus } from '@entities/appointment/model/appointmentStatus.constants';
import { APPOINTMENT_STATUS_LABELS } from '@entities/appointment/model/appointmentStatus.constants';

const statusStyles: Record<AppointmentStatus, string> = {
  SCHEDULED:
    'bg-blue-500/15 text-blue-300 border border-blue-500/30 ring-1 ring-blue-500/20',
  COMPLETED:
    'bg-emerald-500/15 text-emerald-300 border border-emerald-500/30 ring-1 ring-emerald-500/20',
  CANCELLED:
    'bg-red-500/15 text-red-300 border border-red-500/30 ring-1 ring-red-500/20',
  RESCHEDULED:
    'bg-amber-500/15 text-amber-300 border border-amber-500/30 ring-1 ring-amber-500/20',
  NO_SHOW:
    'bg-slate-500/15 text-slate-400 border border-slate-500/30 ring-1 ring-slate-500/20',
};

interface AppointmentStatusBadgeProps {
  status: AppointmentStatus;
  className?: string;
}

export function AppointmentStatusBadge({
  status,
  className,
}: AppointmentStatusBadgeProps) {
  return (
    <span
      className={cn(
        'inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium tracking-wide',
        statusStyles[status],
        className,
      )}
    >
      {APPOINTMENT_STATUS_LABELS[status]}
    </span>
  );
}

interface RoleBadgeProps {
  role: string;
  className?: string;
}

const roleStyles: Record<string, string> = {
  SUPER_ADMIN:
    'bg-purple-500/15 text-purple-300 border border-purple-500/30',
  ADMIN: 'bg-amber-500/15 text-amber-300 border border-amber-500/30',
  EXPLAINER: 'bg-cyan-500/15 text-cyan-300 border border-cyan-500/30',
  STUDENT: 'bg-slate-500/15 text-slate-400 border border-slate-500/30',
};

export function RoleBadge({ role, className }: RoleBadgeProps) {
  return (
    <span
      className={cn(
        'inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium tracking-wide',
        roleStyles[role] ?? 'bg-slate-500/15 text-slate-400',
        className,
      )}
    >
      {role.replace('_', ' ')}
    </span>
  );
}
