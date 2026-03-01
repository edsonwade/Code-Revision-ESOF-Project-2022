import { format, parse, isValid } from 'date-fns';

// Backend uses @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
const BACKEND_DATE_FORMAT = 'yyyy-MM-dd HH:mm:ss';

export function parseBackendDate(dateString: string | null | undefined): Date | null {
  if (!dateString) return null;
  const parsed = parse(dateString, BACKEND_DATE_FORMAT, new Date());
  return isValid(parsed) ? parsed : null;
}

export function formatDate(dateString: string | null | undefined): string {
  const date = parseBackendDate(dateString);
  if (!date) return '—';
  return format(date, 'MMM d, yyyy');
}

export function formatDateTime(dateString: string | null | undefined): string {
  const date = parseBackendDate(dateString);
  if (!date) return '—';
  return format(date, 'MMM d, yyyy · HH:mm');
}

export function formatTime(dateString: string | null | undefined): string {
  const date = parseBackendDate(dateString);
  if (!date) return '—';
  return format(date, 'HH:mm');
}

export function formatRelative(dateString: string | null | undefined): string {
  const date = parseBackendDate(dateString);
  if (!date) return '—';
  const now = new Date();
  const diffMs = now.getTime() - date.getTime();
  const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));
  if (diffDays === 0) return 'Today';
  if (diffDays === 1) return 'Yesterday';
  if (diffDays < 7) return `${diffDays} days ago`;
  return formatDate(dateString);
}
