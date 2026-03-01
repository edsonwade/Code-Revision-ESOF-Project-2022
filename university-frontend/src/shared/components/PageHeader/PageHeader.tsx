import type { ReactNode } from 'react';
import { cn } from '@shared/lib/cn';

interface PageHeaderProps {
  title: string;
  subtitle?: string;
  action?: ReactNode;
  className?: string;
}

export function PageHeader({ title, subtitle, action, className }: PageHeaderProps) {
  return (
    <div className={cn('flex items-start justify-between gap-4 mb-8', className)}>
      <div className="min-w-0">
        <h1 className="font-display text-2xl font-semibold text-slate-50 tracking-tight truncate">
          {title}
        </h1>
        {subtitle && (
          <p className="mt-1 text-sm text-slate-400 leading-relaxed">
            {subtitle}
          </p>
        )}
      </div>
      {action && (
        <div className="flex-shrink-0 flex items-center gap-2">{action}</div>
      )}
    </div>
  );
}
