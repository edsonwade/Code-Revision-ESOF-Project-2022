import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { GraduationCap, Eye, EyeOff, AlertCircle } from 'lucide-react';
import { useRegister } from '@features/auth/useRegister';
import { ROUTES } from '@app/router/routes';
import { parseError } from '@shared/lib/errorParser';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { FormField } from '@shared/components/FormField/FormField';
import { registerSchema, type RegisterFormData } from '@entities/auth/model/auth.schema';

export function RegisterPage() {
    const navigate = useNavigate();
    const registerMutation = useRegister();
    const [showPassword, setShowPassword] = useState(false);

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<RegisterFormData>({
        resolver: zodResolver(registerSchema),
        defaultValues: { firstName: '', lastName: '', email: '', password: '' },
    });

    const onSubmit = (data: RegisterFormData) => {
        registerMutation.mutate(
            {
                email: data.email,
                password: data.password,
                firstName: data.firstName,
                lastName: data.lastName,
                ipAddress: '127.0.0.1',
                userAgent: navigator.userAgent
            },
            { onSuccess: () => navigate(ROUTES.LOGIN) }
        );
    };

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
                            Start Managing<br />
                            <span className="gradient-text">Today</span>
                        </h2>
                        <p className="mt-3 text-sm text-[#94A3B8] leading-relaxed">
                            Create an account to streamline students, explainers, appointments, and academic resources.
                        </p>
                    </div>
                </div>
                <p className="text-xs text-[#475569]">&copy; {new Date().getFullYear()} UniManage</p>
            </div>

            {/* Register form */}
            <div className="flex flex-1 items-center justify-center p-6 h-screen overflow-y-auto">
                <div className="w-full max-w-sm animate-fade-in my-auto py-12">
                    <div className="mb-6">
                        <h1 className="font-display font-bold text-2xl text-[#F1F5F9]">Create an account</h1>
                        <p className="text-sm text-[#94A3B8] mt-1">Enter your details to register</p>
                    </div>

                    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                        <div className="grid grid-cols-2 gap-3">
                            <FormField label="First Name" error={errors.firstName?.message} required>
                                <Input
                                    id="firstName" placeholder="Ana"
                                    className={errors.firstName ? 'border-red-500/50 focus-visible:ring-red-500/50' : ''}
                                    {...register('firstName')}
                                />
                            </FormField>
                            <FormField label="Last Name" error={errors.lastName?.message} required>
                                <Input
                                    id="lastName" placeholder="Silva"
                                    className={errors.lastName ? 'border-red-500/50 focus-visible:ring-red-500/50' : ''}
                                    {...register('lastName')}
                                />
                            </FormField>
                        </div>

                        <FormField label="Email" error={errors.email?.message} required>
                            <Input
                                id="email" type="email" placeholder="you@university.edu" autoComplete="email"
                                className={errors.email ? 'border-red-500/50 focus-visible:ring-red-500/50' : ''}
                                {...register('email')}
                            />
                        </FormField>

                        <FormField label="Password" error={errors.password?.message} required>
                            <div className="relative">
                                <Input
                                    id="password" type={showPassword ? 'text' : 'password'}
                                    placeholder="••••••••"
                                    className={`pr-10 ${errors.password ? 'border-red-500/50 focus-visible:ring-red-500/50' : ''}`}
                                    {...register('password')}
                                />
                                <button
                                    type="button" onClick={() => setShowPassword((s) => !s)}
                                    className="absolute right-3 top-1/2 -translate-y-1/2 text-[#475569] hover:text-[#94A3B8]"
                                >
                                    {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                                </button>
                            </div>
                        </FormField>

                        {registerMutation.isError && (
                            <div className="flex items-center gap-2 rounded-lg bg-[#F87171]/10 border border-[#F87171]/20 px-3 py-2.5 text-sm text-[#F87171]">
                                <AlertCircle className="h-4 w-4 flex-shrink-0" />
                                <span>{parseError(registerMutation.error)}</span>
                            </div>
                        )}

                        <Button type="submit" className="w-full" size="lg" isLoading={registerMutation.isPending}>
                            Register
                        </Button>
                    </form>

                    <p className="mt-6 text-center text-sm text-[#94A3B8]">
                        Already have an account?{' '}
                        <button
                            onClick={() => navigate(ROUTES.LOGIN)}
                            className="text-[#5B8AF5] hover:text-[#7C9FF7] underline"
                        >
                            Sign in
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
}
