import { type ColumnDef } from '@tanstack/react-table';
import { Plus } from 'lucide-react';
import { useCourses } from '@entities/course/hooks/useCourses';
import { type Course } from '@entities/course/model/course.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Badge } from '@shared/components/ui/badge';

export function CoursesPage() {
  const { data, isLoading } = useCourses();
  const columns: ColumnDef<Course>[] = [
    { accessorKey: 'name', header: 'Course Name', cell: ({ getValue }) => <span className="text-sm font-medium text-[#F1F5F9]">{getValue<string>()}</span> },
    { accessorKey: 'code', header: 'Code', cell: ({ getValue }) => <Badge variant="secondary">{getValue<string>()}</Badge> },
  ];
  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader title="Courses" description={`${data?.length ?? 0} courses`} action={<Button><Plus className="h-4 w-4" />Add Course</Button>} />
      <DataTable data={data ?? []} columns={columns} isLoading={isLoading} emptyMessage="No courses found." />
    </div>
  );
}
