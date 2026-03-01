import { User } from 'lucide-react';

interface StudentBadgeProps {
  name: string;
  studentNumber?: string;
}

export function StudentBadge({ name, studentNumber }: StudentBadgeProps) {
  const initials = name.split(' ').map((n) => n[0]).slice(0, 2).join('').toUpperCase();
  return (
    <div className="flex items-center gap-2.5">
      <div className="flex h-7 w-7 items-center justify-center rounded-full bg-[#5B8AF5]/15 text-[#5B8AF5] text-xs font-semibold flex-shrink-0">
        {initials || <User className="h-3.5 w-3.5" />}
      </div>
      <div className="min-w-0">
        <div className="text-sm font-medium text-[#F1F5F9] truncate">{name}</div>
        {studentNumber && (
          <div className="text-xs text-[#475569] font-mono">{studentNumber}</div>
        )}
      </div>
    </div>
  );
}
