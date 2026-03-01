import React from 'react';

interface PageHeaderProps {
    title: string;
    description?: string;
    actions?: React.ReactNode;
}

export const PageHeader: React.FC<PageHeaderProps> = ({ title, description, actions }) => {
    return (
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-8">
            <div>
                <h1 className="text-3xl font-bold tracking-tight text-slate-900 drop-shadow-sm">{title}</h1>
                {description && (
                    <p className="mt-1.5 text-slate-500 max-w-2xl text-sm leading-relaxed">
                        {description}
                    </p>
                )}
            </div>
            {actions && (
                <div className="flex items-center gap-3 shrink-0 animate-in fade-in slide-in-from-right-4 duration-500">
                    {actions}
                </div>
            )}
        </div>
    );
};
