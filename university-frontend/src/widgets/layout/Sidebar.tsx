import { NavLink, useLocation } from 'react-router-dom';
import {
  LayoutDashboard, Users, BookOpen, Calendar, Clock,
  GraduationCap, School, Building2, Star, Settings, ChevronLeft, ChevronRight
} from 'lucide-react';
import { cn } from '@shared/lib/utils';
import { ROUTES } from '@app/router/routes';
import { useState } from 'react';

const navItems = [
  { icon: LayoutDashboard, label: 'Dashboard', path: ROUTES.DASHBOARD, exact: true },
  { icon: Users, label: 'Students', path: ROUTES.STUDENTS },
  { icon: BookOpen, label: 'Explainers', path: ROUTES.EXPLAINERS },
  { icon: Calendar, label: 'Appointments', path: ROUTES.APPOINTMENTS },
  { icon: Clock, label: 'Availability', path: ROUTES.AVAILABILITY },
  { icon: GraduationCap, label: 'Courses', path: ROUTES.COURSES },
  { icon: School, label: 'Degrees', path: ROUTES.DEGREES },
  { icon: Building2, label: 'Colleges', path: ROUTES.COLLEGES },
  { icon: Star, label: 'Reviews', path: ROUTES.REVIEWS },
];

interface SidebarProps {
  collapsed: boolean;
  onToggle: () => void;
}

export function Sidebar({ collapsed, onToggle }: SidebarProps) {
  const location = useLocation();

  return (
    <aside
      className={cn(
        'flex flex-col h-screen bg-[#0D0F18] border-r border-[#1E2438] transition-all duration-300 ease-in-out flex-shrink-0',
        collapsed ? 'w-14' : 'w-56'
      )}
    >
      {/* Logo */}
      <div className={cn(
        'flex items-center h-14 border-b border-[#1E2438] px-3 flex-shrink-0',
        collapsed ? 'justify-center' : 'gap-2.5'
      )}>
        <div className="flex h-7 w-7 flex-shrink-0 items-center justify-center rounded-lg bg-gradient-to-br from-[#5B8AF5] to-[#A78BFA] shadow-glow-sm">
          <GraduationCap className="h-4 w-4 text-white" />
        </div>
        {!collapsed && (
          <span className="font-display font-bold text-sm text-[#F1F5F9] truncate">
            UniManage
          </span>
        )}
      </div>

      {/* Nav */}
      <nav className="flex-1 py-3 overflow-y-auto overflow-x-hidden">
        <div className="space-y-0.5 px-2">
          {navItems.map(({ icon: Icon, label, path, exact }) => {
            const isActive = exact
              ? location.pathname === path
              : location.pathname.startsWith(path);

            return (
              <NavLink
                key={path}
                to={path}
                title={collapsed ? label : undefined}
                className={cn(
                  'flex items-center gap-2.5 rounded-lg px-2.5 py-2 text-sm font-medium transition-all duration-150 group',
                  isActive
                    ? 'bg-[#5B8AF5]/15 text-[#5B8AF5]'
                    : 'text-[#94A3B8] hover:bg-[#1A1E2E] hover:text-[#F1F5F9]',
                  collapsed && 'justify-center px-2'
                )}
              >
                <Icon className={cn(
                  'h-4 w-4 flex-shrink-0 transition-colors',
                  isActive ? 'text-[#5B8AF5]' : 'text-[#475569] group-hover:text-[#94A3B8]'
                )} />
                {!collapsed && <span className="truncate">{label}</span>}
                {isActive && !collapsed && (
                  <div className="ml-auto h-1.5 w-1.5 rounded-full bg-[#5B8AF5]" />
                )}
              </NavLink>
            );
          })}
        </div>
      </nav>

      {/* Settings + Collapse */}
      <div className="border-t border-[#1E2438] py-2 px-2 space-y-0.5">
        <NavLink
          to={ROUTES.SETTINGS}
          title={collapsed ? 'Settings' : undefined}
          className={({ isActive }) => cn(
            'flex items-center gap-2.5 rounded-lg px-2.5 py-2 text-sm font-medium transition-all duration-150',
            isActive
              ? 'bg-[#5B8AF5]/15 text-[#5B8AF5]'
              : 'text-[#94A3B8] hover:bg-[#1A1E2E] hover:text-[#F1F5F9]',
            collapsed && 'justify-center px-2'
          )}
        >
          <Settings className="h-4 w-4 flex-shrink-0 text-[#475569]" />
          {!collapsed && <span>Settings</span>}
        </NavLink>

        <button
          onClick={onToggle}
          className="flex w-full items-center gap-2.5 rounded-lg px-2.5 py-2 text-sm text-[#475569] hover:bg-[#1A1E2E] hover:text-[#94A3B8] transition-all duration-150"
          title={collapsed ? 'Expand sidebar' : 'Collapse sidebar'}
        >
          {collapsed
            ? <ChevronRight className="h-4 w-4 mx-auto" />
            : (
              <>
                <ChevronLeft className="h-4 w-4 flex-shrink-0" />
                <span>Collapse</span>
              </>
            )
          }
        </button>
      </div>
    </aside>
  );
}
