export const queryKeys = {
  students: {
    all: ['students'] as const,
    detail: (id: number) => ['students', id] as const,
    byName: (name: string) => ['students', 'search', name] as const,
  },
  explainers: {
    all: ['explainers'] as const,
    detail: (id: number) => ['explainers', id] as const,
  },
  appointments: {
    all: ['appointments'] as const,
    detail: (id: number) => ['appointments', id] as const,
  },
  availability: {
    all: ['availability'] as const,
    detail: (id: number) => ['availability', id] as const,
  },
  colleges: {
    all: ['colleges'] as const,
    detail: (id: number) => ['colleges', id] as const,
  },
  courses: {
    all: ['courses'] as const,
    detail: (id: number) => ['courses', id] as const,
  },
  degrees: {
    all: ['degrees'] as const,
    detail: (id: number) => ['degrees', id] as const,
  },
  reviews: {
    all: ['reviews'] as const,
    byExplainer: (explainerId: number) => ['reviews', 'explainer', explainerId] as const,
  },
} as const;
