import { type ColumnDef } from '@tanstack/react-table';
import { Star } from 'lucide-react';
import { useReviews } from '@entities/review/hooks/useReviews';
import { type ReviewResponseDTO } from '@entities/review/model/review.types';
import { DataTable } from '@shared/components/DataTable';
import { PageHeader } from '@shared/components/PageHeader';
import { formatDate } from '@shared/lib/dateFormatter';

function StarRating({ rating }: { rating: number }) {
  return (
    <div className="flex items-center gap-0.5">
      {Array.from({ length: 5 }).map((_, i) => (
        <Star key={i} className={`h-3.5 w-3.5 ${i < rating ? 'text-[#FBBF24] fill-[#FBBF24]' : 'text-[#1E2438]'}`} />
      ))}
    </div>
  );
}

export function ReviewsPage() {
  const { data, isLoading } = useReviews();
  const columns: ColumnDef<ReviewResponseDTO>[] = [
    { accessorKey: 'rating', header: 'Rating', cell: ({ getValue }) => <StarRating rating={getValue<number>()} /> },
    { accessorKey: 'comment', header: 'Comment', cell: ({ getValue }) => <span className="text-sm text-[#94A3B8] line-clamp-1">{getValue<string>() ?? '—'}</span> },
    { accessorKey: 'studentId', header: 'Student', cell: ({ getValue }) => <span className="font-mono text-xs text-[#475569]">#{getValue<number>()}</span> },
    { accessorKey: 'explainerId', header: 'Explainer', cell: ({ getValue }) => <span className="font-mono text-xs text-[#475569]">#{getValue<number>()}</span> },
    { accessorKey: 'createdAt', header: 'Date', cell: ({ getValue }) => <span className="text-sm text-[#475569]">{formatDate(getValue<string>())}</span> },
  ];
  return (
    <div className="animate-fade-in space-y-4">
      <PageHeader title="Reviews" description={`${data?.length ?? 0} reviews submitted`} />
      <DataTable data={data ?? []} columns={columns} isLoading={isLoading} emptyMessage="No reviews yet." />
    </div>
  );
}
