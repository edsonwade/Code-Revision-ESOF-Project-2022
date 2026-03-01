import { forwardRef, type SelectHTMLAttributes } from 'react';
import { cn } from '@shared/lib/utils';
import { ChevronDown } from 'lucide-react';

export interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
  placeholder?: string;
}

const Select = forwardRef<HTMLSelectElement, SelectProps>(
  ({ className, children, placeholder, ...props }, ref) => {
    return (
      <div className="relative">
        <select
          className={cn(
            'flex h-9 w-full appearance-none rounded-lg border border-[#1E2438] bg-[#131621] px-3 py-2 pr-8 text-sm text-[#F1F5F9] transition-colors',
            'focus:outline-none focus:ring-2 focus:ring-[#5B8AF5] focus:border-transparent',
            'disabled:cursor-not-allowed disabled:opacity-50',
            !props.value && 'text-[#475569]',
            className
          )}
          ref={ref}
          {...props}
        >
          {placeholder && (
            <option value="" disabled>
              {placeholder}
            </option>
          )}
          {children}
        </select>
        <ChevronDown className="pointer-events-none absolute right-2.5 top-1/2 -translate-y-1/2 h-4 w-4 text-[#475569]" />
      </div>
    );
  }
);
Select.displayName = 'Select';

export { Select };
