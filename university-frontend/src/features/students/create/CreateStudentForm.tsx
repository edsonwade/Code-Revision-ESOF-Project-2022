import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createStudentSchema, type CreateStudentFormData } from '@shared/validation/schemas/student.schema';
import { useCreateStudent } from './useCreateStudent';
import { parseError, parseValidationErrors } from '@shared/lib/errorParser';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { Label } from '@shared/components/ui/label';
import { AlertCircle } from 'lucide-react';

interface CreateStudentFormProps {
  onSuccess: () => void;
  onCancel: () => void;
}

export function CreateStudentForm({ onSuccess, onCancel }: CreateStudentFormProps) {
  const mutation = useCreateStudent();
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<CreateStudentFormData>({
    resolver: zodResolver(createStudentSchema),
  });

  const onSubmit = (data: CreateStudentFormData) => {
    mutation.mutate(data, {
      onSuccess: () => onSuccess(),
      onError: (err) => {
        const validationErrors = parseValidationErrors(err);
        if (validationErrors) {
          Object.entries(validationErrors).forEach(([field, message]) => {
            setError(field as keyof CreateStudentFormData, { message });
          });
        }
      },
    });
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div className="space-y-1.5">
        <Label htmlFor="name">Full Name</Label>
        <Input id="name" placeholder="Ana Silva" {...register('name')} />
        {errors.name && <p className="text-xs text-[#F87171]">{errors.name.message}</p>}
      </div>

      <div className="space-y-1.5">
        <Label htmlFor="email">Email</Label>
        <Input id="email" type="email" placeholder="ana@university.edu" {...register('email')} />
        {errors.email && <p className="text-xs text-[#F87171]">{errors.email.message}</p>}
      </div>

      <div className="space-y-1.5">
        <Label htmlFor="studentNumber">Student Number</Label>
        <Input id="studentNumber" placeholder="UP123456" {...register('studentNumber')} />
        {errors.studentNumber && <p className="text-xs text-[#F87171]">{errors.studentNumber.message}</p>}
      </div>

      {mutation.isError && !parseValidationErrors(mutation.error) && (
        <div className="flex items-center gap-2 rounded-lg bg-[#F87171]/10 border border-[#F87171]/20 px-3 py-2 text-sm text-[#F87171]">
          <AlertCircle className="h-4 w-4 flex-shrink-0" />
          {parseError(mutation.error)}
        </div>
      )}

      <div className="flex justify-end gap-3 pt-2">
        <Button type="button" variant="secondary" onClick={onCancel}>
          Cancel
        </Button>
        <Button type="submit" isLoading={mutation.isPending}>
          Create Student
        </Button>
      </div>
    </form>
  );
}
