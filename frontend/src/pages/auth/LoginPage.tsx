import React, { useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { useAuthStore } from '../../shared/store/authStore';
import { ROUTES } from '../../app/router/routes';
import { authApi } from '../../shared/api/authApi';

const LoginPage: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const setAuth = useAuthStore((state) => state.setAuth);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const state = location.state as { from?: { pathname: string } } | null;
    const from = state?.from?.pathname || ROUTES.DASHBOARD;

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        setIsLoading(true);

        try {
            // High-grade metadata capture Paco style
            const ipAddress = '127.0.0.1';
            const userAgent = navigator.userAgent;

            const response = await authApi.login({
                email,
                password,
                ipAddress,
                userAgent
            });

            setAuth(response.accessToken, {
                id: response.userId,
                name: (response.email || '').split('@')[0] || 'User',
                role: 'USER',
                email: response.email
            });

            navigate(from, { replace: true });
        } catch (err: any) {
            setError(err.response?.data?.error || 'Invalid credentials');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-slate-50 p-6">
            <div className="max-w-md w-full space-y-8 bg-white p-10 rounded-3xl shadow-xl border border-slate-100 animate-in fade-in slide-in-from-bottom-4 duration-700">
                <div className="text-center">
                    <div className="w-16 h-16 bg-indigo-600 rounded-2xl flex items-center justify-center mx-auto mb-6 shadow-indigo-100 shadow-2xl rotate-3 hover:rotate-0 transition-transform duration-500">
                        <span className="text-white text-3xl font-black">U</span>
                    </div>
                    <h2 className="text-3xl font-black text-slate-900 tracking-tight">University Hub</h2>
                    <p className="mt-2 text-sm text-slate-500 font-medium">Welcome back! Please enter your details.</p>
                </div>

                <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
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

                    <div>
                        <button
                            type="submit"
                            disabled={isLoading}
                            className="group relative w-full flex justify-center py-4 px-4 border border-transparent text-sm font-black rounded-xl text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 shadow-lg shadow-indigo-200 transition-all duration-300 active:scale-[0.98] disabled:opacity-50"
                        >
                            {isLoading ? 'Authenticating...' : 'Sign In'}
                        </button>
                    </div>
                </form>

                <div className="text-center space-y-4">
                    <p className="text-xs text-slate-400 font-medium lowercase">
                        demo credentials: <span className="font-bold text-slate-600">demo@unimanage.edu / PacoStyle2024</span>
                    </p>
                    <div className="pt-4 border-t border-slate-100 flex justify-center gap-6">
                        <a href="#" className="text-xs font-bold text-indigo-600 hover:text-indigo-800 transition-colors">Forgot Password?</a>
                        <Link to={ROUTES.REGISTER} className="text-xs font-bold text-slate-600 hover:text-slate-800 transition-colors">Create Account</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
