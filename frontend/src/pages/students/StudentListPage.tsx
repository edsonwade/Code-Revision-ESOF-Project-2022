import React, { useState } from 'react';
import { ColumnDef } from '@tanstack/react-table';
import { Plus, MoreHorizontal, User as UserIcon, Mail } from 'lucide-react';
import { PageHeader } from '../../shared/components/PageHeader';
import { DataTable } from '../../shared/components/DataTable';
import { Modal } from '../../shared/components/Modal';
import { CreateStudentForm } from '../../features/students/create/CreateStudentForm';
import { useStudents } from '../../entities/student/hooks/useStudents';
import { Student } from '../../entities/student/model/student.types';

const StudentListPage: React.FC = () => {
    const { data: students = [], isLoading } = useStudents();
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);

    const columns: ColumnDef<Student>[] = [
        {
            accessorKey: 'name',
            header: 'Student',
            cell: ({ row }) => (
                <div className="flex items-center gap-3 group">
                    <div className="w-10 h-10 rounded-xl bg-indigo-100 flex items-center justify-center text-indigo-600 shadow-inner ring-2 ring-white">
                        <UserIcon className="w-5 h-5" />
                    </div>
                    <div>
                        <div className="font-semibold text-slate-900 group-hover:text-indigo-600 transition-colors uppercase text-xs tracking-wider">
                            {row.original.name}
                        </div>
                    </div>
                </div>
            ),
        },
        {
            accessorKey: 'email',
            header: 'Email Address',
            cell: ({ row }) => (
                <div className="flex items-center gap-2 text-slate-500">
                    <Mail className="w-4 h-4 opacity-70" />
                    <span className="text-sm font-medium">{row.original.email}</span>
                </div>
            ),
        },
        {
            accessorKey: 'createdAt',
            header: 'Enrollment Date',
            cell: ({ row }) => {
                const date = row.original.createdAt;
                return (
                    <span className="text-sm font-medium text-slate-500">
                        {date ? new Date(date).toLocaleDateString('pt-BR', { day: '2-digit', month: 'short', year: 'numeric' }) : 'N/A'}
                    </span>
                );
            },
        },
        {
            id: 'actions',
            cell: () => (
                <div className="flex justify-end">
                    <button className="p-2 hover:bg-slate-100 rounded-lg text-slate-400 hover:text-slate-600 transition-all active:scale-90">
                        <MoreHorizontal className="w-5 h-5" />
                    </button>
                </div>
            ),
        },
    ];

    return (
        <div className="space-y-8 animate-in fade-in duration-700">
            <PageHeader
                title="Students Hub"
                description="Monitor and manage all university students and their enrollment status. Grade 10 management."
                actions={
                    <button
                        onClick={() => setIsCreateModalOpen(true)}
                        className="flex items-center gap-2 px-5 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-xl font-bold text-sm shadow-md shadow-indigo-500/20 transition-all hover:scale-[1.02] active:scale-[0.98]"
                    >
                        <Plus className="w-4 h-4" />
                        Add New Student
                    </button>
                }
            />

            <DataTable
                columns={columns}
                data={students}
                isLoading={isLoading}
            />

            <Modal
                isOpen={isCreateModalOpen}
                onClose={() => setIsCreateModalOpen(false)}
                title="Enroll New Student"
            >
                <CreateStudentForm
                    onSuccess={() => setIsCreateModalOpen(false)}
                    onCancel={() => setIsCreateModalOpen(false)}
                />
            </Modal>
        </div>
    );
};

export default StudentListPage;
