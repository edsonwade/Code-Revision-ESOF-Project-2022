import { type HTMLAttributes } from 'react';
import { cn } from '@shared/lib/utils';

interface SeparatorProps extends HTMLAttributes<HTMLDivElement> {
  orientation?: 'horizontal' | 'vertical';
}

function Separator({ className, orientation = 'horizontal', ...props }: SeparatorProps) {
  return (
    <div
      className={cn(
        'bg-[#1E2438]',
        orientation === 'horizontal' ? 'h-px w-full' : 'h-full w-px',
        className
      )}
      {...props}
    />
  );
}

export { Separator };
