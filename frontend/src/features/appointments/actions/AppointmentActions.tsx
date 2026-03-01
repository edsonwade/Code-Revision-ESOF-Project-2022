import React from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { appointmentApi } from '../../../entities/appointment/api/appointmentApi';
import { AppointmentStatus, canBeCancelled, canBeRescheduled } from '../../../entities/appointment/model/appointment.types';

interface AppointmentActionsProps {
  appointmentId: number;
  status: AppointmentStatus;
}

export const AppointmentActions: React.FC<AppointmentActionsProps> = ({ appointmentId, status }) => {
  const queryClient = useQueryClient();

  const cancelMutation = useMutation({
    mutationFn: () => appointmentApi.deleteAppointment(appointmentId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['appointments.all'] });
    },
  });

  if (!canBeCancelled(status) && !canBeRescheduled(status)) {
    return null;
  }

  return (
    <div className="flex space-x-2">
      {canBeRescheduled(status) && (
        <button 
          className="text-primary-600 hover:text-primary-700 text-xs font-bold px-2 py-1 rounded hover:bg-primary-50 transition-colors"
          onClick={() => {/* Open reschedule modal */}}
        >
          Reschedule
        </button>
      )}
      {canBeCancelled(status) && (
        <button 
          className="text-red-600 hover:text-red-700 text-xs font-bold px-2 py-1 rounded hover:bg-red-50 transition-colors"
          onClick={() => {
            if (window.confirm('Are you sure you want to cancel this appointment?')) {
              cancelMutation.mutate();
            }
          }}
          disabled={cancelMutation.isPending}
        >
          {cancelMutation.isPending ? 'Cancelling...' : 'Cancel'}
        </button>
      )}
    </div>
  );
};
