import { forwardRef, type ButtonHTMLAttributes } from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@shared/lib/utils';

const buttonVariants = cva(
  'inline-flex items-center justify-center gap-2 rounded-lg text-sm font-medium transition-all duration-200 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-[#5B8AF5] focus-visible:ring-offset-2 focus-visible:ring-offset-[#0D0F18] disabled:pointer-events-none disabled:opacity-50 whitespace-nowrap',
  {
    variants: {
      variant: {
        default:
          'bg-[#5B8AF5] text-white hover:bg-[#4A79E4] shadow-sm hover:shadow-[0_0_12px_rgba(91,138,245,0.3)]',
        secondary:
          'bg-[#1A1E2E] text-[#F1F5F9] border border-[#1E2438] hover:bg-[#1E2438] hover:border-[#2A3050]',
        ghost:
          'text-[#94A3B8] hover:text-[#F1F5F9] hover:bg-[#1A1E2E]',
        destructive:
          'bg-[#F87171]/10 text-[#F87171] border border-[#F87171]/20 hover:bg-[#F87171]/20',
        outline:
          'border border-[#1E2438] text-[#F1F5F9] hover:bg-[#1A1E2E]',
        link: 'text-[#5B8AF5] underline-offset-4 hover:underline p-0 h-auto',
      },
      size: {
        default: 'h-9 px-4 py-2',
        sm: 'h-7 px-3 text-xs',
        lg: 'h-11 px-6 text-base',
        icon: 'h-9 w-9',
        'icon-sm': 'h-7 w-7',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
);

export interface ButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  isLoading?: boolean;
}

const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, isLoading, children, ...props }, ref) => {
    return (
      <button
        className={cn(buttonVariants({ variant, size, className }))}
        ref={ref}
        disabled={isLoading ?? props.disabled}
        {...props}
      >
        {isLoading && (
          <span className="h-3.5 w-3.5 rounded-full border-2 border-current border-t-transparent animate-spin" />
        )}
        {children}
      </button>
    );
  }
);
Button.displayName = 'Button';

export { Button, buttonVariants };
