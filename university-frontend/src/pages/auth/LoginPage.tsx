import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GraduationCap, Eye, EyeOff, AlertCircle } from 'lucide-react';
import { useLogin } from '@features/auth/useLogin';
import { ROUTES } from '@app/router/routes';
import { parseError } from '@shared/lib/errorParser';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { Label } from '@shared/components/ui/label';

export function LoginPage() {
  const navigate = useNavigate();
  const loginMutation = useLogin();
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    loginMutation.mutate({ email, password }, { onSuccess: () => navigate(ROUTES.DASHBOARD) });
  };

  const isDevMode = import.meta.env['VITE_SKIP_AUTH'] === 'true';

  return (
    <div className="min-h-screen flex bg-[#0D0F18] dot-grid">
      {/* Left branding panel */}
      <div className="hidden lg:flex flex-col justify-between w-[420px] flex-shrink-0 bg-gradient-to-b from-[#131621] to-[#0D0F18] border-r border-[#1E2438] p-10">
        <div className="flex items-center gap-2.5">
          <div className="flex h-9 w-9 items-center justify-center rounded-xl bg-gradient-to-br from-[#5B8AF5] to-[#A78BFA] shadow-glow">
            <GraduationCap className="h-5 w-5 text-white" />
          </div>
          <span className="font-display font-bold text-lg text-[#F1F5F9]">UniManage</span>
        </div>

        <div className="space-y-6">
          <div>
            <h2 className="font-display font-bold text-3xl text-[#F1F5F9] leading-tight">
              University Management<br />
              <span className="gradient-text">Made Effortless</span>
            </h2>
            <p className="mt-3 text-sm text-[#94A3B8] leading-relaxed">
              Streamline students, explainers, appointments, and academic resources — all in one unified platform.
            </p>
          </div>
          <div className="space-y-3">
            {[
              { label: 'Student & explainer management', color: '#5B8AF5' },
              { label: 'Appointment scheduling', color: '#A78BFA' },
              { label: 'Course & degree structure', color: '#22D3EE' },
            ].map(({ label, color }) => (
              <div key={label} className="flex items-center gap-3">
                <div className="h-1.5 w-1.5 rounded-full flex-shrink-0" style={{ background: color }} />
                <span className="text-sm text-[#94A3B8]">{label}</span>
              </div>
            ))}
          </div>
        </div>
        <p className="text-xs text-[#475569]">&copy; {new Date().getFullYear()} UniManage</p>
      </div>

      {/* Login form */}
      <div className="flex flex-1 items-center justify-center p-6">
        <div className="w-full max-w-sm animate-fade-in">
          <div className="mb-6">
            <h1 className="font-display font-bold text-2xl text-[#F1F5F9]">Sign in</h1>
            <p className="text-sm text-[#94A3B8] mt-1">Enter your credentials to continue</p>
          </div>

          {isDevMode && (
            <div className="mb-4 rounded-lg bg-[#FBBF24]/10 border border-[#FBBF24]/20 px-3 py-2.5">
              <p className="text-xs text-[#FBBF24] font-medium">Development Mode</p>
              <p className="text-xs text-[#94A3B8] mt-0.5">
                Auth bypassed —{' '}
                <button onClick={() => navigate(ROUTES.DASHBOARD)} className="text-[#5B8AF5] underline">
                  Go to dashboard
                </button>
              </p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-1.5">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email" type="email" placeholder="you@university.edu"
                value={email} onChange={(e) => setEmail(e.target.value)} autoComplete="email"
              />
            </div>
            <div className="space-y-1.5">
              <Label htmlFor="password">Password</Label>
              <div className="relative">
                <Input
                  id="password" type={showPassword ? 'text' : 'password'}
                  placeholder="••••••••" value={password}
                  onChange={(e) => setPassword(e.target.value)} className="pr-10"
                />
                <button
                  type="button" onClick={() => setShowPassword((s) => !s)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-[#475569] hover:text-[#94A3B8]"
                >
                  {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                </button>
              </div>
            </div>

            {loginMutation.isError && (
              <div className="flex items-center gap-2 rounded-lg bg-[#F87171]/10 border border-[#F87171]/20 px-3 py-2.5 text-sm text-[#F87171]">
                <AlertCircle className="h-4 w-4 flex-shrink-0" />
                <span>{parseError(loginMutation.error)}</span>
              </div>
            )}

            <Button type="submit" className="w-full" size="lg" isLoading={loginMutation.isPending}>
              Sign in
            </Button>
          </form>

          <p className="mt-6 text-center text-xs text-[#475569]">
            Authentication managed by your administrator
          </p>
        </div>
      </div>
    </div>
  );
}
