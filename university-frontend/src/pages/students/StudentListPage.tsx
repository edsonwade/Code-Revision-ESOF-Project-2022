import { useState } from 'react';
import { type ColumnDef } from '@tanstack/react-table';
import { Plus, Pencil, Trash2, Search } from 'lucide-react';
import { useStudents } from '@entities/student/hooks/useStudents';
import { useDeleteStudent } from '@features/students/delete/useDeleteStudent';
import { type Student } from '@entities/student/model/student.types';
import { StudentBadge } from '@entities/student/ui/StudentBadge';
import { CreateStudentForm } from '@features/students/create/CreateStudentForm';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogCloseButton } from '@shared/components/ui/dialog';
import { Badge } from '@shared/components/ui/badge';
import { formatDate } from '@shared/lib/dateFormatter';
import { useDebounce } from '@shared/hooks/useDebounce';

export function StudentListPage() {
  const [isCreateOpen, setIsCreateOpen] = useState(false);
  const [search, setSearch] = useState('');
  const [deleteId, setDeleteId] = useState<number | null>(null);

  const debouncedSearch = useDebounce(search, 300);
  const { data: students, isLoading } = useStudents();
  const deleteMutation = useDeleteStudent();

  const filtered = students?.filter((s) =>
    debouncedSearch
      ? s.name.toLowerCase().includes(debouncedSearch.toLowerCase()) ||
        s.email.toLowerCase().includes(debouncedSearch.toLowerCase()) ||
        s.studentNumber.toLowerCase().includes(debouncedSearch.toLowerCase())
      : true
  ) ?? [];

  const columns: ColumnDef<Student>[] = [
    {
      id: 'student',
      header: 'Student',
      accessorFn: (row) => row.name,
      cell: ({ row }) => (
        <StudentBadge name={row.original.name} studentNumber={row.original.studentNumber} />
      ),
    },
    {
      accessorKey: 'email',
      header: 'Email',
      cell: ({ getValue }) => (
        <span className="text-sm text-[#94A3B8] font-mono text-xs">{getValue<string>()}</span>
      ),
    },
    {
      accessorKey: 'createdAt',
      header: 'Enrolled',
      cell: ({ getValue }) => (
        <span className="text-sm text-[#475569]">{formatDate(getValue<string>())}</span>
      ),
    },
    {
      id: 'actions',
      header: '',
      cell: ({ row }) => (
        <div className="flex items-center justify-end gap-1">
          <Button
            variant="ghost"
            size="icon-sm"
            className="opacity-0 group-hover:opacity-100"
            title="Edit"
          >
            <Pencil className="h-3.5 w-3.5" />
          </Button>
          <Button
            variant="ghost"
            size="icon-sm"
            className="text-[#F87171] hover:text-[#F87171] hover:bg-[#F87171]/10 opacity-0 group-hover:opacity-100"
            title="Delete"
            onClick={() => setDeleteId(row.original.id)}
          >
            <Trash2 className="h-3.5 w-3.5" />
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader
        title="Students"
        description={`${students?.length ?? 0} students enrolled`}
        action={
          <Button onClick={() => setIsCreateOpen(true)}>
            <Plus className="h-4 w-4" />
            Add Student
          </Button>
        }
      />

      {/* Search */}
      <div className="relative max-w-xs">
        <Search className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-3.5 w-3.5 text-[#475569]" />
        <Input
          placeholder="Search students..."
          className="pl-8"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {/* Table */}
      <DataTable
        data={filtered}
        columns={columns}
        isLoading={isLoading}
        globalFilter={debouncedSearch}
        emptyMessage={
          search ? `No students match "${search}"` : 'No students found. Add your first student.'
        }
      />

      {/* Create modal */}
      <Dialog open={isCreateOpen} onClose={() => setIsCreateOpen(false)}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Add Student</DialogTitle>
            <DialogCloseButton onClose={() => setIsCreateOpen(false)} />
          </DialogHeader>
          <div className="p-5">
            <CreateStudentForm
              onSuccess={() => setIsCreateOpen(false)}
              onCancel={() => setIsCreateOpen(false)}
            />
          </div>
        </DialogContent>
      </Dialog>

      {/* Delete confirm */}
      <Dialog open={deleteId !== null} onClose={() => setDeleteId(null)}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Delete Student</DialogTitle>
            <DialogCloseButton onClose={() => setDeleteId(null)} />
          </DialogHeader>
          <div className="p-5 space-y-4">
            <p className="text-sm text-[#94A3B8]">
              Are you sure you want to delete this student? This action cannot be undone.
            </p>
            <div className="flex justify-end gap-3">
              <Button variant="secondary" onClick={() => setDeleteId(null)}>
                Cancel
              </Button>
              <Button
                variant="destructive"
                isLoading={deleteMutation.isPending}
                onClick={() => {
                  if (deleteId) {
                    deleteMutation.mutate(deleteId, { onSuccess: () => setDeleteId(null) });
                  }
                }}
              >
                <Trash2 className="h-4 w-4" />
                Delete
              </Button>
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
