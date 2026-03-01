import { type ColumnDef } from '@tanstack/react-table';
import { Plus, GraduationCap } from 'lucide-react';
import { useDegrees } from '@entities/degree/hooks/useDegrees';
import { type Degree } from '@entities/degree/model/degree.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Badge } from '@shared/components/ui/badge';

export function DegreesPage() {
  const { data, isLoading } = useDegrees();
  const columns: ColumnDef<Degree>[] = [
    {
      id: 'degree',
      header: 'Degree',
      cell: ({ row }) => (
        <div className="flex items-center gap-2.5">
          <div className="flex h-7 w-7 items-center justify-center rounded-lg bg-[#A78BFA]/10">
            <GraduationCap className="h-3.5 w-3.5 text-[#A78BFA]" />
          </div>
          <span className="text-sm font-medium text-[#F1F5F9]">{row.original.name}</span>
        </div>
      ),
    },
    { accessorKey: 'acronym', header: 'Acronym', cell: ({ getValue }) => <Badge variant="violet">{getValue<string>()}</Badge> },
  ];
  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader title="Degrees" description={`${data?.length ?? 0} degrees`} action={<Button><Plus className="h-4 w-4" />Add Degree</Button>} />
      <DataTable data={data ?? []} columns={columns} isLoading={isLoading} emptyMessage="No degrees found." />
    </div>
  );
}
