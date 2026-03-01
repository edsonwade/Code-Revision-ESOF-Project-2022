import React from 'react';
import { useStudents } from '../../entities/student/hooks/useStudents';
import { useAppointments } from '../../entities/appointment/hooks/useAppointments';
import { useExplainers } from '../../entities/explainer/hooks/useExplainers';
import { useReviews } from '../../entities/review/hooks/useReviews';
import { AppointmentTable } from '../../widgets/AppointmentTable/AppointmentTable';
import { PageHeader } from '../../shared/components/PageHeader';
import { Button } from '../../shared/components/ui/button';
import { StatCard } from './StatCard';
import {
    Users,
    Calendar,
    UserCheck,
    Star,
    Plus
} from 'lucide-react';
import { Link } from 'react-router-dom';
import { ROUTES } from '../../app/router/routes';

const Dashboard: React.FC = () => {
    const { data: students = [] } = useStudents();
    const { data: appointments = [] } = useAppointments();
    const { data: explainers = [] } = useExplainers();
    const { data: reviews = [] } = useReviews();

    const stats = [
        {
            title: 'Total Students',
            value: students.length.toString(),
            icon: <Users className="w-5 h-5" />,
            trend: { value: 12, isPositive: true }
        },
        {
            title: 'Appointments',
            value: appointments.length.toString(),
            icon: <Calendar className="w-5 h-5" />,
            trend: { value: 8, isPositive: true }
        },
        {
            title: 'Active Explainers',
            value: explainers.length.toString(),
            icon: <UserCheck className="w-5 h-5" />,
            trend: { value: 4, isPositive: true }
        },
        {
            title: 'Average Rating',
            value: reviews.length > 0
                ? (reviews.reduce((acc, rev) => acc + rev.rating, 0) / reviews.length).toFixed(1)
                : '0.0',
            icon: <Star className="w-5 h-5" />,
            trend: { value: 2, isPositive: true }
        }
    ];

    return (
        <div className="space-y-8 animate-in fade-in duration-700">
            <PageHeader
                title="Dashboard"
                description="Welcome back! Here's what's happening in your university system today."
                actions={
                    <Button className="bg-indigo-600 hover:bg-indigo-700 text-white shadow-lg shadow-indigo-200 transition-all duration-300 hover:scale-105 active:scale-95 group">
                        <Plus className="w-4 h-4 mr-2 transition-transform group-hover:rotate-90" />
                        Quick Action
                    </Button>
                }
            />

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {stats.map((stat, index) => (
                    <StatCard key={index} {...stat} />
                ))}
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                <div className="lg:col-span-2 space-y-6">
                    <div className="flex items-center justify-between">
                        <h3 className="text-xl font-bold text-slate-800 flex items-center">
                            <Calendar className="w-6 h-6 mr-3 text-primary-500" />
                            Recent Appointments
                        </h3>
                        <Link to={ROUTES.APPOINTMENTS} className="text-primary-600 font-bold text-sm hover:underline">View all</Link>
                    </div>
                    <AppointmentTable appointments={appointments.slice(0, 5)} isLoading={false} />
                </div>

                <div className="space-y-6">
                    <h3 className="text-xl font-bold text-slate-800 flex items-center">
                        <Users className="w-6 h-6 mr-3 text-primary-500" />
                        Quick Actions
                    </h3>
                    <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-4">
                        <button className="w-full text-left p-4 rounded-xl border border-slate-100 hover:border-primary-200 hover:bg-primary-50 transition-all group">
                            <p className="font-bold text-slate-800 group-hover:text-primary-700">Add New Student</p>
                            <p className="text-xs text-slate-500 mt-1">Register a new student in the system</p>
                        </button>
                        <button className="w-full text-left p-4 rounded-xl border border-slate-100 hover:border-primary-200 hover:bg-primary-50 transition-all group">
                            <p className="font-bold text-slate-800 group-hover:text-primary-700">Schedule Session</p>
                            <p className="text-xs text-slate-500 mt-1">Book an appointment with an explainer</p>
                        </button>
                        <button className="w-full text-left p-4 rounded-xl border border-slate-100 hover:border-primary-200 hover:bg-primary-50 transition-all group">
                            <p className="font-bold text-slate-800 group-hover:text-primary-700">Add Explainer</p>
                            <p className="text-xs text-slate-500 mt-1">Onboard a new educational staff member</p>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
