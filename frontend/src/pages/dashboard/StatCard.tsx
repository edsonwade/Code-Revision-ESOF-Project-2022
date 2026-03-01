import React, { ReactNode } from 'react';

interface StatCardProps {
    title: string;
    value: string | number;
    icon: ReactNode;
    trend?: {
        value: number;
        isPositive: boolean;
    };
}

export const StatCard: React.FC<StatCardProps> = ({ title, value, icon, trend }) => (
    <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 flex items-center justify-between hover:border-indigo-100 hover:shadow-md transition-all group">
        <div>
            <p className="text-xs font-bold text-slate-400 uppercase tracking-widest mb-1">{title}</p>
            <h3 className="text-3xl font-extrabold text-slate-800 tracking-tight">{value}</h3>
            {trend && (
                <p className={`text-xs mt-3 flex items-center font-bold ${trend.isPositive ? 'text-emerald-600' : 'text-rose-600'}`}>
                    <span className={`inline-block px-1.5 py-0.5 rounded mr-1 ${trend.isPositive ? 'bg-emerald-50' : 'bg-rose-50'}`}>
                        {trend.isPositive ? '↑' : '↓'} {Math.abs(trend.value)}%
                    </span>
                    <span className="text-slate-400 font-medium ml-1">vs last month</span>
                </p>
            )}
        </div>
        <div className="w-14 h-14 rounded-2xl bg-slate-50 text-indigo-600 flex items-center justify-center border border-slate-100 shadow-inner group-hover:bg-indigo-50 group-hover:text-indigo-700 transition-colors">
            {icon}
        </div>
    </div>
);

export default StatCard;
