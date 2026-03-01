import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../../shared/store/authStore';
import { ROUTES } from '../../app/router/routes';

export const LoginForm: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const setAuth = useAuthStore((state) => state.setAuth);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const state = location.state as { from?: { pathname: string } } | null;
    const from = state?.from?.pathname || ROUTES.DASHBOARD;

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        if (email && password) {
            if (email === 'demo@unimanage.edu' && password === 'PacoStyle2024') {
                setAuth('demo-token', {
                    id: 1,
                    name: 'Demo Admin',
                    role: 'SUPER_ADMIN',
                    email: 'demo@unimanage.edu'
                });
                navigate(from, { replace: true });
            } else {
                setError('Invalid credentials');
            }
        } else {
            setError('Please fill in all fields');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
                <div className="bg-rose-50 border border-rose-100 text-rose-600 px-4 py-3 rounded-xl text-sm font-bold animate-shake">
                    {error}
                </div>
            )}
            <div className="space-y-4">
                <div>
                    <label className="block text-xs font-bold text-slate-400 uppercase tracking-widest mb-2 px-1">Email Address</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="appearance-none rounded-xl relative block w-full px-4 py-3 border border-slate-200 placeholder-slate-400 text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 font-medium transition-all"
                        placeholder="demo@unimanage.edu"
                    />
                </div>
                <div>
                    <label className="block text-xs font-bold text-slate-400 uppercase tracking-widest mb-2 px-1">Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="appearance-none rounded-xl relative block w-full px-4 py-3 border border-slate-200 placeholder-slate-400 text-slate-900 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 font-medium transition-all"
                        placeholder="••••••••"
                    />
                </div>
            </div>

            <button
                type="submit"
                className="group relative w-full flex justify-center py-4 px-4 border border-transparent text-sm font-black rounded-xl text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 shadow-lg shadow-indigo-200 transition-all duration-300 active:scale-[0.98]"
            >
                Sign In
            </button>
        </form>
    );
};
