import { Users, BookOpen, Calendar, Star, TrendingUp, Clock, AlertCircle } from 'lucide-react';
import { useStudents } from '@entities/student/hooks/useStudents';
import { useExplainers } from '@entities/explainer/hooks/useExplainers';
import { useAppointments } from '@entities/appointment/hooks/useAppointments';
import { useReviews } from '@entities/review/hooks/useReviews';
import { APPOINTMENT_STATUS } from '@entities/appointment/model/appointment.types';
import { formatDate } from '@shared/lib/dateFormatter';
import { Badge } from '@shared/components/ui/badge';
import { Card, CardContent } from '@shared/components/ui/card';
import { StatusBadge } from '@shared/components/StatusBadge';

function StatCard({
  icon: Icon,
  label,
  value,
  sub,
  color,
  isLoading,
}: {
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  value: number | string;
  sub?: string;
  color: string;
  isLoading?: boolean;
}) {
  return (
    <Card className="relative overflow-hidden">
      <CardContent className="p-5">
        <div className="flex items-start justify-between">
          <div>
            <p className="text-xs font-medium text-[#94A3B8] uppercase tracking-wider">{label}</p>
            {isLoading ? (
              <div className="skeleton h-8 w-16 mt-2 rounded" />
            ) : (
              <p className="font-display font-bold text-3xl text-[#F1F5F9] mt-1">{value}</p>
            )}
            {sub && <p className="text-xs text-[#475569] mt-1">{sub}</p>}
          </div>
          <div
            className="flex h-10 w-10 items-center justify-center rounded-xl flex-shrink-0"
            style={{ background: `${color}18` }}
          >
            <Icon className="h-5 w-5" style={{ color }} />
          </div>
        </div>
        {/* Subtle gradient accent bottom */}
        <div
          className="absolute bottom-0 left-0 right-0 h-0.5 opacity-40"
          style={{ background: `linear-gradient(to right, ${color}, transparent)` }}
        />
      </CardContent>
    </Card>
  );
}

export function DashboardPage() {
  const students = useStudents();
  const explainers = useExplainers();
  const appointments = useAppointments();
  const reviews = useReviews();

  const scheduledCount = appointments.data?.filter(
    (a) => a.status === APPOINTMENT_STATUS.SCHEDULED
  ).length ?? 0;

  const avgRating =
    reviews.data && reviews.data.length > 0
      ? (reviews.data.reduce((s, r) => s + r.rating, 0) / reviews.data.length).toFixed(1)
      : '—';

  const recentAppointments = appointments.data
    ?.slice()
    .sort((a, b) => b.id - a.id)
    .slice(0, 5);

  return (
    <div className="animate-fade-in space-y-6">
      {/* Header */}
      <div>
        <h1 className="font-display font-bold text-xl text-[#F1F5F9]">Dashboard</h1>
        <p className="text-sm text-[#94A3B8] mt-0.5">Overview of your university management system</p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          icon={Users}
          label="Students"
          value={students.data?.length ?? 0}
          sub="Total enrolled"
          color="#5B8AF5"
          isLoading={students.isLoading}
        />
        <StatCard
          icon={BookOpen}
          label="Explainers"
          value={explainers.data?.length ?? 0}
          sub="Active tutors"
          color="#A78BFA"
          isLoading={explainers.isLoading}
        />
        <StatCard
          icon={Calendar}
          label="Appointments"
          value={scheduledCount}
          sub="Scheduled upcoming"
          color="#22D3EE"
          isLoading={appointments.isLoading}
        />
        <StatCard
          icon={Star}
          label="Avg Rating"
          value={avgRating}
          sub={`from ${reviews.data?.length ?? 0} reviews`}
          color="#FBBF24"
          isLoading={reviews.isLoading}
        />
      </div>

      {/* Recent appointments + quick stats */}
      <div className="grid lg:grid-cols-3 gap-4">
        {/* Recent appointments */}
        <Card className="lg:col-span-2">
          <div className="flex items-center justify-between px-5 pt-5 pb-3">
            <div className="flex items-center gap-2">
              <Calendar className="h-4 w-4 text-[#5B8AF5]" />
              <h3 className="font-display font-semibold text-sm text-[#F1F5F9]">Recent Appointments</h3>
            </div>
            <Badge variant="secondary">{appointments.data?.length ?? 0} total</Badge>
          </div>

          {appointments.isLoading ? (
            <div className="px-5 pb-5 space-y-3">
              {Array.from({ length: 4 }).map((_, i) => (
                <div key={i} className="flex items-center gap-3">
                  <div className="skeleton h-4 w-full rounded" />
                </div>
              ))}
            </div>
          ) : appointments.isError ? (
            <div className="px-5 pb-5 flex items-center gap-2 text-sm text-[#F87171]">
              <AlertCircle className="h-4 w-4" />
              Failed to load appointments
            </div>
          ) : recentAppointments?.length === 0 ? (
            <div className="px-5 pb-5 text-sm text-[#475569]">No appointments yet.</div>
          ) : (
            <div className="divide-y divide-[#1E2438]">
              {recentAppointments?.map((appt) => (
                <div key={appt.id} className="flex items-center justify-between px-5 py-3">
                  <div className="flex items-center gap-3 min-w-0">
                    <div className="flex h-7 w-7 items-center justify-center rounded-full bg-[#5B8AF5]/10 flex-shrink-0">
                      <Calendar className="h-3.5 w-3.5 text-[#5B8AF5]" />
                    </div>
                    <div className="min-w-0">
                      <p className="text-sm text-[#F1F5F9] truncate">
                        Appointment #{appt.id}
                      </p>
                      <p className="text-xs text-[#475569]">{formatDate(appt.scheduledAt)}</p>
                    </div>
                  </div>
                  <StatusBadge status={appt.status} />
                </div>
              ))}
            </div>
          )}
        </Card>

        {/* Status breakdown */}
        <Card>
          <div className="flex items-center gap-2 px-5 pt-5 pb-3">
            <TrendingUp className="h-4 w-4 text-[#A78BFA]" />
            <h3 className="font-display font-semibold text-sm text-[#F1F5F9]">Appointment Status</h3>
          </div>
          <div className="px-5 pb-5 space-y-3">
            {appointments.isLoading ? (
              Array.from({ length: 4 }).map((_, i) => (
                <div key={i} className="skeleton h-6 w-full rounded" />
              ))
            ) : (
              Object.values(APPOINTMENT_STATUS).map((status) => {
                const count = appointments.data?.filter((a) => a.status === status).length ?? 0;
                const total = appointments.data?.length ?? 1;
                const pct = total > 0 ? Math.round((count / total) * 100) : 0;

                const colorMap: Record<string, string> = {
                  SCHEDULED: '#22D3EE',
                  COMPLETED: '#34D399',
                  CANCELLED: '#F87171',
                  RESCHEDULED: '#FBBF24',
                  NO_SHOW: '#475569',
                };
                const color = colorMap[status] ?? '#475569';

                return (
                  <div key={status} className="space-y-1">
                    <div className="flex justify-between text-xs">
                      <span className="text-[#94A3B8]">{status.replace('_', ' ')}</span>
                      <span className="text-[#475569]">{count}</span>
                    </div>
                    <div className="h-1.5 w-full rounded-full bg-[#1A1E2E] overflow-hidden">
                      <div
                        className="h-full rounded-full transition-all duration-500"
                        style={{ width: `${pct}%`, background: color }}
                      />
                    </div>
                  </div>
                );
              })
            )}
          </div>
        </Card>
      </div>

      {/* Latest reviews */}
      {reviews.data && reviews.data.length > 0 && (
        <Card>
          <div className="flex items-center gap-2 px-5 pt-5 pb-3">
            <Star className="h-4 w-4 text-[#FBBF24]" />
            <h3 className="font-display font-semibold text-sm text-[#F1F5F9]">Latest Reviews</h3>
          </div>
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-3 px-5 pb-5">
            {reviews.data.slice(0, 3).map((review) => (
              <div key={review.id} className="rounded-lg bg-[#1A1E2E] p-3 space-y-2">
                <div className="flex items-center gap-1">
                  {Array.from({ length: 5 }).map((_, i) => (
                    <Star
                      key={i}
                      className={`h-3 w-3 ${i < review.rating ? 'text-[#FBBF24] fill-[#FBBF24]' : 'text-[#1E2438]'}`}
                    />
                  ))}
                </div>
                {review.comment && (
                  <p className="text-xs text-[#94A3B8] line-clamp-2">{review.comment}</p>
                )}
                <div className="flex items-center gap-1.5 text-xs text-[#475569]">
                  <Clock className="h-3 w-3" />
                  {formatDate(review.createdAt)}
                </div>
              </div>
            ))}
          </div>
        </Card>
      )}
    </div>
  );
}
