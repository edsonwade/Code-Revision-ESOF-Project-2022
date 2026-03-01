import React from 'react';
import { Outlet } from 'react-router-dom';
import { Sidebar } from './Sidebar';
import { TopBar } from './TopBar';

export const AppShell: React.FC = () => {
    return (
        <div className="flex h-screen bg-[#F8FAFC] overflow-hidden text-slate-900 font-sans antialiased">
            <Sidebar />
            <div className="flex-1 flex flex-col min-w-0">
                <TopBar />
                <main className="flex-1 overflow-y-auto custom-scrollbar p-8">
                    <div className="max-w-7xl mx-auto animate-in fade-in duration-700 slide-in-from-bottom-4">
                        <React.Suspense fallback={
                            <div className="flex items-center justify-center h-64">
                                <div className="w-8 h-8 border-4 border-indigo-500 border-t-transparent rounded-full animate-spin"></div>
                            </div>
                        }>
                            <Outlet />
                        </React.Suspense>
                    </div>
                </main>
            </div>
        </div>
    );
};
