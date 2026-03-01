import { forwardRef, type InputHTMLAttributes } from 'react';
import { cn } from '@shared/lib/utils';

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, ...props }, ref) => {
    return (
      <input
        type={type}
        className={cn(
          'flex h-9 w-full rounded-lg border border-[#1E2438] bg-[#131621] px-3 py-2 text-sm text-[#F1F5F9] placeholder:text-[#475569] transition-colors',
          'focus:outline-none focus:ring-2 focus:ring-[#5B8AF5] focus:border-transparent',
          'disabled:cursor-not-allowed disabled:opacity-50',
          'file:border-0 file:bg-transparent file:text-sm file:font-medium',
          className
        )}
        ref={ref}
        {...props}
      />
    );
  }
);
Input.displayName = 'Input';

export { Input };
