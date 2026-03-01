import React from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { studentApi } from '../../../entities/student/api/studentApi';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { User, Mail, Sparkles, Loader2 } from 'lucide-react';

const studentSchema = z.object({
    name: z.string().min(2, 'Name must be at least 2 characters'),
    email: z.string().email('Invalid email address'),
});

type StudentFormValues = z.infer<typeof studentSchema>;

interface CreateStudentFormProps {
    onSuccess?: () => void;
    onCancel?: () => void;
}

export const CreateStudentForm: React.FC<CreateStudentFormProps> = ({ onSuccess, onCancel }) => {
    const queryClient = useQueryClient();
    const { register, handleSubmit, formState: { errors } } = useForm<StudentFormValues>({
        resolver: zodResolver(studentSchema),
    });

    const mutation = useMutation({
        mutationFn: studentApi.createStudent,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['students'] });
            onSuccess?.();
        },
    });

    const onSubmit = (data: StudentFormValues) => {
        mutation.mutate(data);
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div className="space-y-4">
                <div className="relative group">
                    <label className="text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1 mb-1.5 block">
                        Full Name
                    </label>
                    <div className="relative">
                        <User className="absolute left-4 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400 group-focus-within:text-indigo-500 transition-colors" />
                        <input
                            {...register('name')}
                            className="w-full pl-11 pr-4 py-3 bg-slate-50 border border-slate-200 rounded-2xl text-sm font-semibold text-slate-700 outline-none focus:ring-4 focus:ring-indigo-500/10 focus:border-indigo-500 transition-all placeholder:text-slate-300"
                            placeholder="e.g. Wayne Test"
                        />
                    </div>
                    {errors.name && <p className="mt-1.5 ml-1 text-xs font-bold text-rose-500 animate-in slide-in-from-left-2">{errors.name.message}</p>}
                </div>

                <div className="relative group">
                    <label className="text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1 mb-1.5 block">
                        Email Address
                    </label>
                    <div className="relative">
                        <Mail className="absolute left-4 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400 group-focus-within:text-indigo-500 transition-colors" />
                        <input
                            {...register('email')}
                            type="email"
                            className="w-full pl-11 pr-4 py-3 bg-slate-50 border border-slate-200 rounded-2xl text-sm font-semibold text-slate-700 outline-none focus:ring-4 focus:ring-indigo-500/10 focus:border-indigo-500 transition-all placeholder:text-slate-300"
                            placeholder="wayne@university.hub"
                        />
                    </div>
                    {errors.email && <p className="mt-1.5 ml-1 text-xs font-bold text-rose-500 animate-in slide-in-from-left-2">{errors.email.message}</p>}
                </div>
            </div>

            <div className="flex items-center gap-3 pt-4 border-t border-slate-100">
                <button
                    type="button"
                    onClick={onCancel}
                    className="flex-1 px-4 py-3 border border-slate-200 rounded-2xl text-sm font-bold text-slate-500 hover:bg-slate-50 hover:text-slate-700 transition-all active:scale-95"
                >
                    Discard
                </button>
                <button
                    type="submit"
                    disabled={mutation.isPending}
                    className="flex-[2] flex items-center justify-center gap-2 px-4 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-2xl text-sm font-bold shadow-lg shadow-indigo-500/30 transition-all hover:scale-[1.02] active:scale-[0.98] disabled:opacity-50"
                >
                    {mutation.isPending ? (
                        <Loader2 className="w-4 h-4 animate-spin" />
                    ) : (
                        <>
                            <Sparkles className="w-4 h-4" />
                            Enroll Student
                        </>
                    )}
                </button>
            </div>
        </form>
    );
};
