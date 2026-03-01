import { type HTMLAttributes } from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@shared/lib/utils';

const badgeVariants = cva(
  'inline-flex items-center gap-1 rounded-md px-2 py-0.5 text-xs font-medium transition-colors',
  {
    variants: {
      variant: {
        default: 'bg-[#5B8AF5]/15 text-[#5B8AF5] border border-[#5B8AF5]/20',
        success: 'bg-[#34D399]/10 text-[#34D399] border border-[#34D399]/20',
        warning: 'bg-[#FBBF24]/10 text-[#FBBF24] border border-[#FBBF24]/20',
        danger: 'bg-[#F87171]/10 text-[#F87171] border border-[#F87171]/20',
        secondary: 'bg-[#1A1E2E] text-[#94A3B8] border border-[#1E2438]',
        violet: 'bg-[#A78BFA]/10 text-[#A78BFA] border border-[#A78BFA]/20',
        cyan: 'bg-[#22D3EE]/10 text-[#22D3EE] border border-[#22D3EE]/20',
      },
    },
    defaultVariants: { variant: 'default' },
  }
);

export interface BadgeProps
  extends HTMLAttributes<HTMLSpanElement>,
    VariantProps<typeof badgeVariants> {}

function Badge({ className, variant, ...props }: BadgeProps) {
  return (
    <span className={cn(badgeVariants({ variant }), className)} {...props} />
  );
}

export { Badge, badgeVariants };
