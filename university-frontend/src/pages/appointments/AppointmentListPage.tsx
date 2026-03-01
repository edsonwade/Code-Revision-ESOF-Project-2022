import { useState } from 'react';
import { type ColumnDef } from '@tanstack/react-table';
import { Plus, Trash2, Search, Calendar } from 'lucide-react';
import { useAppointments } from '@entities/appointment/hooks/useAppointments';
import { useDeleteAppointment } from '@features/appointments/create/useDeleteAppointment';
import { type AppointmentDto, isFinalState } from '@entities/appointment/model/appointment.types';
import { StatusBadge } from '@shared/components/StatusBadge';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogCloseButton } from '@shared/components/ui/dialog';
import { formatDateTime } from '@shared/lib/dateFormatter';
import { useDebounce } from '@shared/hooks/useDebounce';

export function AppointmentListPage() {
  const [search, setSearch] = useState('');
  const [deleteId, setDeleteId] = useState<number | null>(null);
  const debouncedSearch = useDebounce(search, 300);

  const { data: appointments, isLoading } = useAppointments();
  const deleteMutation = useDeleteAppointment();

  const filtered = appointments?.filter((a) =>
    debouncedSearch
      ? String(a.id).includes(debouncedSearch) ||
        String(a.studentId).includes(debouncedSearch)
      : true
  ) ?? [];

  const columns: ColumnDef<AppointmentDto>[] = [
    {
      accessorKey: 'id',
      header: '#',
      cell: ({ getValue }) => (
        <span className="font-mono text-xs text-[#475569]">#{getValue<number>()}</span>
      ),
    },
    {
      id: 'participants',
      header: 'Participants',
      cell: ({ row }) => (
        <div className="space-y-0.5">
          <div className="text-sm text-[#F1F5F9]">Student #{row.original.studentId}</div>
          <div className="text-xs text-[#475569]">Explainer #{row.original.explainerId}</div>
        </div>
      ),
    },
    {
      accessorKey: 'scheduledAt',
      header: 'Scheduled',
      cell: ({ getValue }) => (
        <div className="flex items-center gap-1.5">
          <Calendar className="h-3.5 w-3.5 text-[#475569]" />
          <span className="text-sm text-[#94A3B8]">{formatDateTime(getValue<string>())}</span>
        </div>
      ),
    },
    {
      accessorKey: 'status',
      header: 'Status',
      cell: ({ getValue }) => <StatusBadge status={getValue<AppointmentDto['status']>()} />,
    },
    {
      id: 'actions',
      header: '',
      cell: ({ row }) => (
        <div className="flex items-center justify-end gap-1">
          <Button
            variant="ghost"
            size="icon-sm"
            className="text-[#F87171] hover:bg-[#F87171]/10"
            title="Delete"
            onClick={() => setDeleteId(row.original.id)}
            disabled={!isFinalState(row.original.status) && row.original.status === 'SCHEDULED'}
          >
            <Trash2 className="h-3.5 w-3.5" />
          </Button>
        </div>
      ),
    },
  ];

  const featureFlag = import.meta.env['VITE_FEATURE_APPOINTMENT_MANAGEMENT'] === 'true';

  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader
        title="Appointments"
        description={`${appointments?.length ?? 0} total appointments`}
        action={
          featureFlag ? (
            <Button>
              <Plus className="h-4 w-4" />
              New Appointment
            </Button>
          ) : undefined
        }
      />

      {!featureFlag && (
        <div className="rounded-lg bg-[#5B8AF5]/10 border border-[#5B8AF5]/20 px-4 py-3 text-sm text-[#94A3B8]">
          <span className="font-medium text-[#5B8AF5]">Note:</span> Appointment creation and management operations are pending backend activation.
        </div>
      )}

      <div className="relative max-w-xs">
        <Search className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-3.5 w-3.5 text-[#475569]" />
        <Input placeholder="Search appointments..." className="pl-8" value={search} onChange={(e) => setSearch(e.target.value)} />
      </div>

      <DataTable
        data={filtered}
        columns={columns}
        isLoading={isLoading}
        emptyMessage="No appointments found."
      />

      <Dialog open={deleteId !== null} onClose={() => setDeleteId(null)}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Delete Appointment</DialogTitle>
            <DialogCloseButton onClose={() => setDeleteId(null)} />
          </DialogHeader>
          <div className="p-5 space-y-4">
            <p className="text-sm text-[#94A3B8]">
              Are you sure you want to delete appointment <span className="font-mono text-[#F1F5F9]">#{deleteId}</span>?
            </p>
            <div className="flex justify-end gap-3">
              <Button variant="secondary" onClick={() => setDeleteId(null)}>Cancel</Button>
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
