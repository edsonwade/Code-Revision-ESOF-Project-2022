import { useState } from 'react';
import { type ColumnDef } from '@tanstack/react-table';
import { Plus, Building2 } from 'lucide-react';
import { useColleges } from '@entities/college/hooks/useColleges';
import { type College } from '@entities/college/model/college.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Badge } from '@shared/components/ui/badge';

export function CollegesPage() {
  const { data, isLoading } = useColleges();

  const columns: ColumnDef<College>[] = [
    {
      id: 'college',
      header: 'College',
      accessorFn: (row) => row.name,
      cell: ({ row }) => (
        <div className="flex items-center gap-2.5">
          <div className="flex h-7 w-7 items-center justify-center rounded-lg bg-[#22D3EE]/10">
            <Building2 className="h-3.5 w-3.5 text-[#22D3EE]" />
          </div>
          <span className="text-sm font-medium text-[#F1F5F9]">{row.original.name}</span>
        </div>
      ),
    },
    { accessorKey: 'code', header: 'Code', cell: ({ getValue }) => <Badge variant="secondary">{getValue<string>()}</Badge> },
  ];

  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader title="Colleges" description={`${data?.length ?? 0} colleges`} action={<Button><Plus className="h-4 w-4" />Add College</Button>} />
      <DataTable data={data ?? []} columns={columns} isLoading={isLoading} emptyMessage="No colleges found." />
    </div>
  );
}
