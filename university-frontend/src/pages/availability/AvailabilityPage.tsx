import { type ColumnDef } from '@tanstack/react-table';
import { Clock } from 'lucide-react';
import { useAvailability } from '@entities/availability/hooks/useAvailability';
import { type AvailabilityDto } from '@entities/availability/model/availability.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';

export function AvailabilityPage() {
  const { data, isLoading } = useAvailability();

  const columns: ColumnDef<AvailabilityDto>[] = [
    { accessorKey: 'explainerId', header: 'Explainer ID', cell: ({ getValue }) => <span className="font-mono text-xs text-[#475569]">#{getValue<number>()}</span> },
    { accessorKey: 'dayOfWeek', header: 'Day', cell: ({ getValue }) => <span className="text-sm text-[#F1F5F9] font-medium">{getValue<string>()}</span> },
    {
      id: 'time',
      header: 'Time Slot',
      cell: ({ row }) => (
        <div className="flex items-center gap-1.5 text-sm text-[#94A3B8]">
          <Clock className="h-3.5 w-3.5 text-[#475569]" />
          {row.original.startTime} — {row.original.endTime}
        </div>
      ),
    },
  ];

  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader title="Availability" description={`${data?.length ?? 0} time slots configured`} />
      <DataTable data={data ?? []} columns={columns} isLoading={isLoading} emptyMessage="No availability slots configured." />
    </div>
  );
}
