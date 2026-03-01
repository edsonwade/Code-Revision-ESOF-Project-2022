import React from 'react';
import { useReviews } from '../../entities/review/hooks/useReviews';
import { PageHeader } from '../../shared/components/PageHeader';
import { DataTable } from '../../shared/components/DataTable';
import { Star, MoreHorizontal } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table';
import { ReviewResponseDto } from '../../entities/review/model/review.types';

const ReviewListPage: React.FC = () => {
    const { data: reviews, isLoading } = useReviews();

    const columns: ColumnDef<ReviewResponseDto>[] = [
        {
            accessorKey: 'comment',
            header: 'Feedback',
            cell: ({ row }) => (
                <div className="flex flex-col gap-1">
                    <div className="flex items-center gap-1">
                        {[1, 2, 3, 4, 5].map((star) => (
                            <Star
                                key={star}
                                size={12}
                                className={star <= row.original.rating ? 'fill-amber-400 text-amber-400' : 'text-slate-200'}
                            />
                        ))}
                    </div>
                    <p className="text-sm text-slate-700 font-medium line-clamp-2 italic">"{row.original.comment}"</p>
                </div>
            ),
        },
        {
            accessorKey: 'explainerId',
            header: 'Explainer',
            cell: ({ row }) => (
                <span className="text-sm font-semibold text-indigo-600">
                    {`Explainer #${(row.original as any).explainerId}`}
                </span>
            ),
        },
        {
            accessorKey: 'createdAt',
            header: 'Date',
            cell: ({ row }) => (
                <span className="text-xs text-slate-500">
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

    return (
        <div className="space-y-6">
            <PageHeader
                title="Reviews & Feedback"
                description="Monitor student satisfaction and session quality feedback."
            />
            <DataTable
                columns={columns}
                data={reviews || []}
                isLoading={isLoading ?? false}
            />
        </div>
    );
};

export default ReviewListPage;
