import React from 'react';
import { useAppointments } from '../../entities/appointment/hooks/useAppointments';
import { PageHeader } from '../../shared/components/PageHeader';
import { AppointmentTable } from '../../widgets/AppointmentTable/AppointmentTable';
import {
    Calendar,
    Plus,
    Search
} from 'lucide-react';
import { Button } from '../../shared/components/ui/button';

const AppointmentListPage: React.FC = () => {
    const { data: appointments, isLoading } = useAppointments();

    return (
        <div className="space-y-6">
            <PageHeader
                title="Appointments"
                description="Manage and schedule educational support sessions and tutoring appointments."
                actions={
                    <div className="flex items-center gap-3">
                        <div className="relative group hidden sm:block">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400 group-focus-within:text-indigo-500 transition-colors" />
                            <input
                                type="text"
                                placeholder="Search appointments..."
                                className="bg-white border border-slate-200 rounded-xl py-2 pl-10 pr-4 text-sm focus:ring-2 focus:ring-indigo-500/20 transition-all w-64"
                            />
                        </div>
                        <Button className="bg-indigo-600 hover:bg-indigo-700 text-white shadow-md hover:shadow-lg active:scale-95 transition-all">
                            <Plus className="w-4 h-4 mr-2" />
                            New Appointment
                        </Button>
                    </div>
                }
            />

            <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
                <div className="p-4 border-b border-slate-100 flex items-center justify-between bg-slate-50/50">
                    <div className="flex items-center gap-2">
                        <Calendar className="w-4 h-4 text-indigo-500" />
                        <span className="text-sm font-bold text-slate-700">All Scheduled Sessions</span>
                    </div>
                </div>
                <AppointmentTable appointments={appointments || []} isLoading={isLoading} />
            </div>
        </div>
    );
};

export default AppointmentListPage;
