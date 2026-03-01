import {
  flexRender,
  getCoreRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  getFilteredRowModel,
  useReactTable,
  type ColumnDef,
  type SortingState,
} from '@tanstack/react-table';
import { useState } from 'react';
import { ChevronUp, ChevronDown, ChevronsUpDown, ChevronLeft, ChevronRight } from 'lucide-react';
import { cn } from '@shared/lib/cn';

interface DataTableProps<TData> {
  data: TData[];
  columns: ColumnDef<TData>[];
  globalFilter?: string;
  pageSize?: number;
  isLoading?: boolean;
  emptyNode?: React.ReactNode;
}

export function DataTable<TData>({
  data, columns, globalFilter, pageSize = 12,
  isLoading, emptyNode,
}: DataTableProps<TData>) {
  const [sorting, setSorting] = useState<SortingState>([]);

  const table = useReactTable({
    data,
    columns,
    state: { sorting, globalFilter: globalFilter ?? '' },
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    initialState: { pagination: { pageSize } },
  });

  const rows = table.getRowModel().rows;
  const totalRows = table.getFilteredRowModel().rows.length;
  const pageCount = table.getPageCount();

  if (isLoading) {
    return (
      <div className="card overflow-hidden">
        <div className="divide-y divide-[#1e2d45]">
          {Array.from({ length: 6 }).map((_, i) => (
            <div key={i} className="flex gap-4 px-5 py-3.5">
              {Array.from({ length: Math.min(columns.length, 5) }).map((_, j) => (
                <div key={j} className={cn('h-3.5 rounded bg-[#1a2540] animate-pulse', j === 0 ? 'w-32' : 'flex-1')} />
              ))}
            </div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            {table.getHeaderGroups().map(hg => (
              <tr key={hg.id} className="border-b border-[#1e2d45] bg-[#0d1424]">
                {hg.headers.map(header => (
                  <th key={header.id}
                    className="px-5 py-3 text-left text-[11px] font-bold uppercase tracking-[0.08em] text-slate-500 whitespace-nowrap"
                    style={{ width: header.getSize() }}
                  >
                    {!header.isPlaceholder && (
                      <div
                        className={cn('flex items-center gap-1.5',
                          header.column.getCanSort() && 'cursor-pointer select-none hover:text-slate-300 transition-colors'
                        )}
                        onClick={header.column.getToggleSortingHandler()}
                      >
                        {flexRender(header.column.columnDef.header, header.getContext())}
                        {header.column.getCanSort() && (
                          <span className="text-slate-700">
                            {header.column.getIsSorted() === 'asc' ? <ChevronUp className="w-3 h-3 text-amber-400" />
                              : header.column.getIsSorted() === 'desc' ? <ChevronDown className="w-3 h-3 text-amber-400" />
                              : <ChevronsUpDown className="w-3 h-3" />}
                          </span>
                        )}
                      </div>
                    )}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody>
            {rows.length === 0 ? (
              <tr>
                <td colSpan={columns.length}>
                  {emptyNode ?? (
                    <div className="text-center py-12 text-slate-600 text-sm">No records found.</div>
                  )}
                </td>
              </tr>
            ) : (
              rows.map(row => (
                <tr key={row.id} className="border-b border-[#172032] last:border-0 hover:bg-white/[0.02] transition-colors group">
                  {row.getVisibleCells().map(cell => (
                    <td key={cell.id} className="px-5 py-3.5 text-slate-300 whitespace-nowrap">
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </td>
                  ))}
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {pageCount > 1 && (
        <div className="flex items-center justify-between px-5 py-3 bg-[#0d1424] border-t border-[#1e2d45]">
          <span className="text-xs text-slate-600">
            {totalRows} record{totalRows !== 1 ? 's' : ''} · Page{' '}
            {table.getState().pagination.pageIndex + 1} / {pageCount}
          </span>
          <div className="flex items-center gap-1">
            {[
              { action: () => table.setPageIndex(0), label: '«', disabled: !table.getCanPreviousPage() },
              { action: () => table.previousPage(), label: '‹', disabled: !table.getCanPreviousPage() },
              { action: () => table.nextPage(), label: '›', disabled: !table.getCanNextPage() },
              { action: () => table.setPageIndex(pageCount - 1), label: '»', disabled: !table.getCanNextPage() },
            ].map(({ action, label, disabled }, i) => (
              <button key={i} onClick={action} disabled={disabled}
                className="w-7 h-7 flex items-center justify-center rounded-lg text-sm text-slate-400
                  hover:text-slate-200 hover:bg-white/5 disabled:opacity-30 disabled:cursor-not-allowed transition-colors font-mono"
              >
                {label}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
