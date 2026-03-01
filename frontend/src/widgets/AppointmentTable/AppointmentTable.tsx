import React from 'react';
import { ColumnDef } from '@tanstack/react-table';
import { MoreHorizontal, Calendar as CalendarIcon, Clock, User as UserIcon } from 'lucide-react';
import { Appointment } from '../../entities/appointment/model/appointment.types';
import { DataTable } from '../../shared/components/DataTable';
import { StatusBadge } from '../../shared/components/StatusBadge';

interface AppointmentTableProps {
    appointments: Appointment[];
    isLoading?: boolean;
}

export const AppointmentTable: React.FC<AppointmentTableProps> = ({ appointments, isLoading }) => {
    const columns: ColumnDef<Appointment>[] = [
        {
            accessorKey: 'studentId',
            header: 'Student',
            cell: ({ row }) => (
                <div className="flex items-center gap-3">
                    <div className="w-8 h-8 rounded-full bg-slate-100 flex items-center justify-center text-slate-500">
                        <UserIcon size={16} />
                    </div>
                    <span className="font-medium text-slate-900">Student #{row.original.studentId}</span>
                </div>
            ),
        },
        {
            accessorKey: 'startTime',
            header: 'Schedule',
            cell: ({ row }) => (
                <div className="flex flex-col">
                    <div className="flex items-center text-sm font-semibold text-slate-700">
                        <CalendarIcon size={14} className="mr-1.5 text-indigo-500" />
                        {(row.original as any).createdAt ? new Date((row.original as any).createdAt).toLocaleDateString() : 'N/A'}
                    </div>
                    <div className="flex items-center text-xs text-slate-500 mt-0.5">
                        <Clock size={12} className="mr-1.5" />
                        {new Date((row.original as any).startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} -
                        {new Date((row.original as any).endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                    </div>
                </div>
            ),
        },
        {
            accessorKey: 'status',
            header: 'Status',
            cell: ({ row }) => <StatusBadge status={row.original.status} />,
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

    return (
        <DataTable
            columns={columns}
            data={appointments}
            isLoading={isLoading ?? false}
        />
    );
};
