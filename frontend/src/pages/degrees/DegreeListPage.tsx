import React from 'react';
import { useDegrees } from '../../entities/degree/hooks/useDegrees';
import { PageHeader } from '../../shared/components/PageHeader';
import { DataTable } from '../../shared/components/DataTable';
import { Plus, Award, MoreHorizontal } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table';
import { Degree } from '../../entities/degree/model/degree.types';

const DegreeListPage: React.FC = () => {
    const { data: degrees, isLoading } = useDegrees();

    const columns: ColumnDef<Degree>[] = [
        {
            accessorKey: 'name',
            header: 'Degree Title',
            cell: ({ row }) => (
                <div className="flex items-center gap-3">
                    <div className="w-8 h-8 rounded-lg bg-amber-50 flex items-center justify-center text-amber-600">
                        <Award size={16} />
                    </div>
                    <span className="font-semibold text-slate-900">{row.original.name}</span>
                </div>
            ),
        },
        {
            accessorKey: 'createdAt',
            header: 'Established At',
            cell: ({ row }) => (
                <span className="text-sm text-slate-500">
                    {(row.original as any).createdAt ? new Date((row.original as any).createdAt).toLocaleDateString() : 'N/A'}
                </span>
            ),
        },
        {
            id: 'actions',
            header: '',
            cell: () => (
                <div className="flex justify-end">
                    <button className="p-2 hover:bg-slate-100 rounded-full transition-colors text-slate-400 hover:text-amber-600">
                        <MoreHorizontal size={20} />
                    </button>
                </div>
            ),
        },
    ];

    const actions = (
        <button className="bg-amber-600 hover:bg-amber-700 text-white px-4 py-2 rounded-lg font-semibold transition-all flex items-center shadow-md hover:shadow-lg active:scale-95 text-sm">
            <Plus className="w-4 h-4 mr-2" />
            Add Degree
        </button>
    );

    return (
        <div className="space-y-6">
            <PageHeader
                title="Degrees"
                description="Configure academic programs, degrees, and graduation requirements."
                actions={actions}
            />
            <DataTable
                columns={columns}
                data={degrees || []}
                isLoading={isLoading ?? false}
            />
        </div>
    );
};

export default DegreeListPage;
