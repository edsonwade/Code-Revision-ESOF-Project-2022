export const queryKeys = {
  students: {
    all: ['students', 'all'] as const,
    detail: (id: number) => ['students', 'detail', id] as const,
    search: (query: string) => ['students', 'search', query] as const,
  },
  explainers: {
    all: ['explainers', 'all'] as const,
    detail: (id: number) => ['explainers', 'detail', id] as const,
  },
  appointments: {
    all: ['appointments', 'all'] as const,
    detail: (id: number) => ['appointments', 'detail', id] as const,
  },
  availabilities: {
    all: ['availabilities', 'all'] as const,
    detail: (id: number) => ['availabilities', 'detail', id] as const,
  },
  colleges: {
    all: ['colleges', 'all'] as const,
    detail: (id: number) => ['colleges', 'detail', id] as const,
  },
  courses: {
    all: ['courses', 'all'] as const,
    detail: (id: number) => ['courses', 'detail', id] as const,
  },
  degrees: {
    all: ['degrees', 'all'] as const,
    detail: (id: number) => ['degrees', 'detail', id] as const,
  },
  reviews: {
    all: ['reviews', 'all'] as const,
    detail: (id: number) => ['reviews', 'detail', id] as const,
  },
};
