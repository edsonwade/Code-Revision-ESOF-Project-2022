import { Search, Bell, User } from 'lucide-react';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { useAuthStore } from '@shared/store/authStore';

interface TopBarProps {
  onSearch?: (q: string) => void;
}

export function TopBar({ onSearch }: TopBarProps) {
  const user = useAuthStore((s) => s.user);
  const skipAuth = import.meta.env['VITE_SKIP_AUTH'] === 'true';

  return (
    <header className="flex h-14 items-center justify-between border-b border-[#1E2438] bg-[#0D0F18]/80 backdrop-blur-sm px-4 gap-4 flex-shrink-0">
      {/* Search */}
      <div className="relative max-w-xs w-full hidden sm:block">
        <Search className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-3.5 w-3.5 text-[#475569]" />
        <Input
          placeholder="Search..."
          className="pl-8 h-8 bg-[#131621] text-sm"
          onChange={(e) => onSearch?.(e.target.value)}
        />
      </div>

      <div className="flex items-center gap-2 ml-auto">
        {/* Notifications */}
        <Button variant="ghost" size="icon" className="relative h-8 w-8">
          <Bell className="h-4 w-4" />
          <span className="absolute right-1.5 top-1.5 h-1.5 w-1.5 rounded-full bg-[#5B8AF5]" />
        </Button>

        {/* User */}
        <div className="flex items-center gap-2 rounded-lg bg-[#131621] border border-[#1E2438] px-2.5 py-1.5">
          <div className="flex h-6 w-6 items-center justify-center rounded-full bg-gradient-to-br from-[#5B8AF5] to-[#A78BFA]">
            <User className="h-3.5 w-3.5 text-white" />
          </div>
          {(user || skipAuth) && (
            <div className="hidden sm:block">
              <div className="text-xs font-medium text-[#F1F5F9] leading-tight">
                {user?.name ?? 'Developer'}
              </div>
              <div className="text-[10px] text-[#475569] leading-tight">
                {user?.role ?? 'DEV MODE'}
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
