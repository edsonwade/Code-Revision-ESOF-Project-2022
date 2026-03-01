import React from 'react';
import { Explainer } from '../../entities/explainer/model/explainer.types';
import { Globe, User } from 'lucide-react';

interface ExplainerCardProps {
    explainer: Explainer;
    onClick?: () => void;
}

export const ExplainerCard: React.FC<ExplainerCardProps> = ({ explainer, onClick }) => {
    return (
        <div
            onClick={onClick}
            className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm hover:shadow-md transition-shadow cursor-pointer group"
        >
            <div className="flex items-start justify-between mb-4">
                <div className="w-12 h-12 rounded-full bg-primary-100 text-primary-700 flex items-center justify-center">
                    <User className="w-6 h-6" />
                </div>
                <div className="flex items-center space-x-1 px-2 py-1 bg-slate-100 rounded text-xs font-semibold text-slate-600">
                    <Globe className="w-3 h-3" />
                    <span>{explainer.language}</span>
                </div>
            </div>
            <div>
                <h4 className="text-lg font-bold text-slate-900 group-hover:text-primary-600 transition-colors uppercase tracking-tight">
                    {explainer.name}
                </h4>
                <p className="text-sm text-slate-500 mt-1">Experienced Tutor • Applied Mathematics</p>
            </div>
            <div className="mt-4 pt-4 border-t border-slate-100 flex justify-between items-center text-xs">
                <span className="text-green-600 font-medium">Available Today</span>
                <button className="text-primary-600 font-bold hover:underline">View Schedule</button>
            </div>
        </div>
    );
};
