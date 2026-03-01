import React from 'react';
import { NavLink } from 'react-router-dom';
import {
    LayoutDashboard,
    Users,
    Calendar,
    BookOpen,
    GraduationCap,
    School,
    Award,
    Star,
    LogOut,
    ChevronRight
} from 'lucide-react';
import { ROUTES } from '../../app/router/routes';
import { cn } from '../../shared/lib/utils';

const navItems = [
    { label: 'Dashboard', icon: LayoutDashboard, path: ROUTES.DASHBOARD },
    { label: 'Students', icon: Users, path: ROUTES.STUDENTS },
    { label: 'Explainers', icon: GraduationCap, path: ROUTES.EXPLAINERS },
    { label: 'Colleges', icon: School, path: ROUTES.COLLEGES },
    { label: 'Courses', icon: BookOpen, path: ROUTES.COURSES },
    { label: 'Degrees', icon: Award, path: ROUTES.DEGREES },
    { label: 'Appointments', icon: Calendar, path: ROUTES.APPOINTMENTS },
    { label: 'Reviews', icon: Star, path: ROUTES.REVIEWS },
];

export const Sidebar: React.FC = () => {
    return (
        <aside className="w-64 h-full bg-slate-900 text-slate-300 flex flex-col border-r border-slate-800 transition-all duration-300">
            <div className="p-6 flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-indigo-500 flex items-center justify-center shadow-lg shadow-indigo-500/20">
                    <GraduationCap className="text-white w-5 h-5" />
                </div>
                <span className="font-bold text-lg text-white tracking-tight">University Hub</span>
            </div>

            <nav className="flex-1 px-4 space-y-1 mt-4 overflow-y-auto">
                {navItems.map((item) => (
                    <NavLink
                        key={item.path}
                        to={item.path}
                        className={({ isActive }) =>
                            cn(
                                'flex items-center justify-between px-3 py-2.5 rounded-xl transition-all duration-200 group relative',
                                isActive
                                    ? 'bg-indigo-500/10 text-indigo-400 ring-1 ring-indigo-500/20'
                                    : 'hover:bg-slate-800/50 hover:text-white'
                            )
                        }
                    >
                        <div className="flex items-center gap-3">
                            <item.icon className="w-5 h-5 opacity-70 group-hover:opacity-100 transition-opacity" />
                            <span className="font-medium text-[14px]">{item.label}</span>
                        </div>
                        <ChevronRight className="w-4 h-4 opacity-0 group-hover:opacity-40 transition-all -translate-x-2 group-hover:translate-x-0" />
                    </NavLink>
                ))}
            </nav>

            <div className="p-4 mt-auto">
                <div className="p-4 rounded-2xl bg-slate-800/40 border border-slate-800/60 mb-4">
                    <div className="flex items-center gap-2 mb-1">
                        <div className="w-2 h-2 rounded-full bg-green-500 animate-pulse" />
                        <span className="text-[10px] font-semibold text-slate-400 uppercase tracking-widest">System Online</span>
                    </div>
                    <p className="text-[10px] text-slate-500">University Portal v1.0.4</p>
                </div>

                <button className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-red-500/10 hover:text-red-400 text-slate-400 transition-all duration-200 group">
                    <LogOut className="w-5 h-5" />
                    <span className="font-medium text-sm">Logout</span>
                </button>
            </div>
        </aside>
    );
};
