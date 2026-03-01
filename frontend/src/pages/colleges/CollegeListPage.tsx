import React from 'react';
import { useColleges } from '../../entities/college/hooks/useColleges';
import { PageHeader } from '../../shared/components/PageHeader';
import { DataTable } from '../../shared/components/DataTable';
import { Plus, School, MoreHorizontal } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table';
import { College } from '../../entities/college/model/college.types';

const CollegeListPage: React.FC = () => {
    const { data: colleges, isLoading } = useColleges();

    const columns: ColumnDef<College>[] = [
        {
            accessorKey: 'name',
            header: 'College Name',
            cell: ({ row }) => (
                <div className="flex items-center gap-3">
                    <div className="w-8 h-8 rounded-lg bg-indigo-50 flex items-center justify-center text-indigo-600">
                        <School size={16} />
                    </div>
                    <span className="font-semibold text-slate-900">{row.original.name}</span>
                </div>
            ),
        },
        {
            accessorKey: 'createdAt',
            header: 'Registered At',
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
                    <button className="p-2 hover:bg-slate-100 rounded-full transition-colors text-slate-400 hover:text-indigo-600">
                        <MoreHorizontal size={20} />
                    </button>
                </div>
            ),
        },
    ];

    const actions = (
        <button className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg font-semibold transition-all flex items-center shadow-md hover:shadow-lg active:scale-95 text-sm">
            <Plus className="w-4 h-4 mr-2" />
            Add College
        </button>
    );

    return (
        <div className="space-y-6">
            <PageHeader
                title="Colleges"
                description="Manage institutions and organizational units within the university system."
                actions={actions}
            />
            <DataTable
                columns={columns}
                data={colleges || []}
                isLoading={isLoading ?? false}
            />
        </div>
    );
};

export default CollegeListPage;
