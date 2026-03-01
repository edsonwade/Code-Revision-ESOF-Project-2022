import { useState } from 'react';

interface PaginationState {
  pageIndex: number;
  pageSize: number;
}

interface UsePaginationReturn {
  page: number;
  pageSize: number;
  setPage: (page: number) => void;
  setPageSize: (size: number) => void;
  paginationState: PaginationState;
  reset: () => void;
}

export function usePagination(initialPageSize = 10): UsePaginationReturn {
  const [paginationState, setPaginationState] = useState<PaginationState>({
    pageIndex: 0,
    pageSize: initialPageSize,
  });

  return {
    page: paginationState.pageIndex,
    pageSize: paginationState.pageSize,
    setPage: (page) =>
      setPaginationState((prev) => ({ ...prev, pageIndex: page })),
    setPageSize: (size) =>
      setPaginationState({ pageIndex: 0, pageSize: size }),
    paginationState,
    reset: () =>
      setPaginationState({ pageIndex: 0, pageSize: initialPageSize }),
  };
}
