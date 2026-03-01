import { useState } from 'react';
import { type ColumnDef } from '@tanstack/react-table';
import { Plus, Trash2, Search, Globe } from 'lucide-react';
import { useExplainers } from '@entities/explainer/hooks/useExplainers';
import { type ExplainerDto, LANGUAGE_LABELS } from '@entities/explainer/model/explainer.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { Button } from '@shared/components/ui/button';
import { Input } from '@shared/components/ui/input';
import { Badge } from '@shared/components/ui/badge';
import { formatDate } from '@shared/lib/dateFormatter';
import { useDebounce } from '@shared/hooks/useDebounce';

function ExplainerAvatar({ name }: { name: string }) {
  const initials = name.split(' ').map((n) => n[0]).slice(0, 2).join('').toUpperCase();
  return (
    <div className="flex items-center gap-2.5">
      <div className="flex h-7 w-7 items-center justify-center rounded-full bg-[#A78BFA]/15 text-[#A78BFA] text-xs font-semibold flex-shrink-0">
        {initials}
      </div>
      <span className="text-sm font-medium text-[#F1F5F9] truncate">{name}</span>
    </div>
  );
}

export function ExplainerListPage() {
  const [search, setSearch] = useState('');
  const debouncedSearch = useDebounce(search, 300);
  const { data: explainers, isLoading } = useExplainers();

  const filtered = explainers?.filter((e) =>
    debouncedSearch
      ? e.name.toLowerCase().includes(debouncedSearch.toLowerCase()) ||
        e.email.toLowerCase().includes(debouncedSearch.toLowerCase())
      : true
  ) ?? [];

  const columns: ColumnDef<ExplainerDto>[] = [
    {
      id: 'explainer',
      header: 'Explainer',
      accessorFn: (row) => row.name,
      cell: ({ row }) => <ExplainerAvatar name={row.original.name} />,
    },
    {
      accessorKey: 'email',
      header: 'Email',
      cell: ({ getValue }) => (
        <span className="text-xs text-[#94A3B8] font-mono">{getValue<string>()}</span>
      ),
    },
    {
      accessorKey: 'language',
      header: 'Language',
      cell: ({ getValue }) => {
        const lang = getValue<keyof typeof LANGUAGE_LABELS>();
        return (
          <div className="flex items-center gap-1.5">
            <Globe className="h-3.5 w-3.5 text-[#475569]" />
            <Badge variant="secondary">{LANGUAGE_LABELS[lang] ?? lang}</Badge>
          </div>
        );
      },
    },
    {
      accessorKey: 'createdAt',
      header: 'Joined',
      cell: ({ getValue }) => (
        <span className="text-sm text-[#475569]">{formatDate(getValue<string>())}</span>
      ),
    },
    {
      id: 'actions',
      header: '',
      cell: () => (
        <div className="flex items-center justify-end gap-1">
          <Button variant="ghost" size="icon-sm" className="text-[#F87171] hover:bg-[#F87171]/10">
            <Trash2 className="h-3.5 w-3.5" />
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader
        title="Explainers"
        description={`${explainers?.length ?? 0} active tutors`}
        action={
          <Button>
            <Plus className="h-4 w-4" />
            Add Explainer
          </Button>
        }
      />

      <div className="relative max-w-xs">
        <Search className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-3.5 w-3.5 text-[#475569]" />
        <Input placeholder="Search explainers..." className="pl-8" value={search} onChange={(e) => setSearch(e.target.value)} />
      </div>

      <DataTable
        data={filtered}
        columns={columns}
        isLoading={isLoading}
        emptyMessage="No explainers found."
      />
    </div>
  );
}
