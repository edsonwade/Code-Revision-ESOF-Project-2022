import React from 'react';
import { useCourses } from '../../entities/course/hooks/useCourses';
import { PageHeader } from '../../shared/components/PageHeader';
import { DataTable } from '../../shared/components/DataTable';
import { Plus, BookOpen, MoreHorizontal } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table';
import { Course } from '../../entities/course/model/course.types';

const CourseListPage: React.FC = () => {
    const { data: courses, isLoading } = useCourses();

    const columns: ColumnDef<Course>[] = [
        {
            accessorKey: 'name',
            header: 'Course Name',
            cell: ({ row }) => (
                <div className="flex items-center gap-3">
                    <div className="w-8 h-8 rounded-lg bg-emerald-50 flex items-center justify-center text-emerald-600">
                        <BookOpen size={16} />
                    </div>
                    <span className="font-semibold text-slate-900">{row.original.name}</span>
                </div>
            ),
        },
        {
            accessorKey: 'createdAt',
            header: 'Created At',
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
                    <button className="p-2 hover:bg-slate-100 rounded-full transition-colors text-slate-400 hover:text-emerald-600">
                        <MoreHorizontal size={20} />
                    </button>
                </div>
            ),
        },
    ];

    const actions = (
        <button className="bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-lg font-semibold transition-all flex items-center shadow-md hover:shadow-lg active:scale-95 text-sm">
            <Plus className="w-4 h-4 mr-2" />
            Add Course
        </button>
    );

    return (
        <div className="space-y-6">
            <PageHeader
                title="Courses"
                description="Browse and manage the academic course catalog and curricula."
                actions={actions}
            />
            <DataTable
                columns={columns}
                data={courses || []}
                isLoading={isLoading ?? false}
            />
        </div>
    );
};

export default CourseListPage;
