import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useStudent } from '../../entities/student/hooks/useStudents';
import { PageHeader } from '../../shared/components/PageHeader';
import {
    Mail,
    Phone,
    MapPin,
    ArrowLeft,
    Calendar,
    Clock,
    BookOpen,
    GraduationCap,
    School
} from 'lucide-react';

const StudentDetailPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { data: student, isLoading } = useStudent(Number(id));

    if (isLoading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="w-8 h-8 border-4 border-indigo-500 border-t-transparent rounded-full animate-spin"></div>
            </div>
        );
    }

    if (!student) {
        return (
            <div className="text-center py-12">
                <h3 className="text-xl font-bold text-slate-800">Student not found</h3>
                <button
                    onClick={() => navigate(-1)}
                    className="mt-4 text-indigo-600 font-bold hover:underline inline-flex items-center"
                >
                    <ArrowLeft className="w-4 h-4 mr-2" />
                    Go back
                </button>
            </div>
        );
    }

    return (
        <div className="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-700">
            <PageHeader
                title={student.name}
                description={`Student ID: #STU-${student.id.toString().padStart(4, '0')}`}
                actions={
                    <button
                        onClick={() => navigate(-1)}
                        className="flex items-center px-4 py-2 text-sm font-bold text-slate-600 bg-white border border-slate-200 rounded-xl hover:bg-slate-50 transition-all shadow-sm active:scale-95"
                    >
                        <ArrowLeft className="w-4 h-4 mr-2" />
                        Back to List
                    </button>
                }
            />

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                {/* Profile Card */}
                <div className="bg-white rounded-3xl border border-slate-200 shadow-sm overflow-hidden h-fit">
                    <div className="bg-gradient-to-br from-indigo-500 to-purple-600 h-32 relative">
                        <div className="absolute -bottom-12 left-8">
                            <div className="w-24 h-24 rounded-2xl bg-white p-1 shadow-xl">
                                <div className="w-full h-full rounded-xl bg-slate-100 flex items-center justify-center text-4xl font-black text-indigo-600">
                                    {student.name.charAt(0)}
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="pt-16 p-8 space-y-6">
                        <div>
                            <h3 className="text-2xl font-black text-slate-900">{student.name}</h3>
                            <p className="text-slate-500 font-medium">Undergraduate Student</p>
                        </div>

                        <div className="space-y-4 pt-4 border-t border-slate-100">
                            <div className="flex items-center text-slate-600 group">
                                <div className="p-2 rounded-lg bg-slate-50 text-slate-400 group-hover:bg-indigo-50 group-hover:text-indigo-600 transition-colors mr-3">
                                    <Mail className="w-4 h-4" />
                                </div>
                                <span className="text-sm font-bold line-clamp-1">{student.email}</span>
                            </div>
                            <div className="flex items-center text-slate-600 group">
                                <div className="p-2 rounded-lg bg-slate-50 text-slate-400 group-hover:bg-indigo-50 group-hover:text-indigo-600 transition-colors mr-3">
                                    <Phone className="w-4 h-4" />
                                </div>
                                <span className="text-sm font-bold">+351 912 345 678</span>
                            </div>
                            <div className="flex items-center text-slate-600 group">
                                <div className="p-2 rounded-lg bg-slate-50 text-slate-400 group-hover:bg-indigo-50 group-hover:text-indigo-600 transition-colors mr-3">
                                    <MapPin className="w-4 h-4" />
                                </div>
                                <span className="text-sm font-bold">Porto, Portugal</span>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Academic Details */}
                <div className="lg:col-span-2 space-y-8">
                    <div className="bg-white rounded-3xl border border-slate-200 shadow-sm p-8">
                        <h4 className="text-lg font-black text-slate-900 mb-6 flex items-center">
                            <GraduationCap className="w-5 h-5 mr-3 text-indigo-500" />
                            Academic Overview
                        </h4>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div className="p-6 rounded-2xl bg-slate-50 border border-slate-100 hover:border-indigo-100 transition-colors group">
                                <div className="flex items-center gap-3 mb-3">
                                    <div className="p-2 rounded-lg bg-white text-indigo-600 shadow-sm">
                                        <School className="w-4 h-4" />
                                    </div>
                                    <span className="text-xs font-bold text-slate-400 uppercase tracking-widest">Faculty</span>
                                </div>
                                <p className="font-black text-slate-800">Faculty of Engineering</p>
                            </div>
                            <div className="p-6 rounded-2xl bg-slate-50 border border-slate-100 hover:border-emerald-100 transition-colors group">
                                <div className="flex items-center gap-3 mb-3">
                                    <div className="p-2 rounded-lg bg-white text-emerald-600 shadow-sm">
                                        <BookOpen className="w-4 h-4" />
                                    </div>
                                    <span className="text-xs font-bold text-slate-400 uppercase tracking-widest">Course</span>
                                </div>
                                <p className="font-black text-slate-800">Software Engineering</p>
                            </div>
                        </div>
                    </div>

                    {/* Timeline/Schedule */}
                    <div className="bg-white rounded-3xl border border-slate-200 shadow-sm p-8">
                        <h4 className="text-lg font-black text-slate-900 mb-6 flex items-center">
                            <Calendar className="w-5 h-5 mr-3 text-indigo-500" />
                            Upcoming Schedule
                        </h4>
                        <div className="space-y-4">
                            {[1, 2].map((i) => (
                                <div key={i} className="flex items-center gap-4 p-4 rounded-xl border border-slate-100 hover:bg-slate-50 transition-colors cursor-pointer group">
                                    <div className="w-12 h-12 rounded-xl bg-indigo-50 flex flex-col items-center justify-center text-indigo-600 group-hover:bg-indigo-600 group-hover:text-white transition-all">
                                        <span className="text-xs font-black uppercase leading-none">Mar</span>
                                        <span className="text-lg font-black leading-none">{14 + i}</span>
                                    </div>
                                    <div className="flex-1">
                                        <p className="font-bold text-slate-800">Advanced Mathematics Tutoring</p>
                                        <div className="flex items-center text-xs text-slate-500 font-medium mt-1">
                                            <Clock className="w-3 h-3 mr-1.5 text-indigo-400" />
                                            14:00 - 15:30 • Room 204
                                        </div>
                                    </div>
                                    <div className="w-8 h-8 rounded-full bg-slate-100 flex items-center justify-center text-slate-400 opacity-0 group-hover:opacity-100 transition-opacity">
                                        <ArrowLeft className="w-4 h-4 rotate-180" />
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StudentDetailPage;
