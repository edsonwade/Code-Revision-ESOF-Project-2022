import { Settings, Server, Shield, Database } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@shared/components/ui/card';
import { Badge } from '@shared/components/ui/badge';

export function SettingsPage() {
  const apiBaseUrl = import.meta.env['VITE_API_BASE_URL'] as string;
  const skipAuth = import.meta.env['VITE_SKIP_AUTH'] === 'true';
  const orgId = import.meta.env['VITE_DEFAULT_ORG_ID'] as string;

  return (
    <div className="animate-fade-in space-y-6 max-w-2xl">
      <div>
        <h1 className="font-display font-bold text-xl text-[#F1F5F9]">Settings</h1>
        <p className="text-sm text-[#94A3B8] mt-0.5">System configuration and environment status</p>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-2">
            <Server className="h-4 w-4 text-[#5B8AF5]" />
            <CardTitle>API Configuration</CardTitle>
          </div>
          <CardDescription>Backend connection settings</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-3">
            {[
              { label: 'API Base URL', value: apiBaseUrl || 'Not set' },
              { label: 'Organization ID', value: orgId || '1' },
              { label: 'Skip Auth', value: skipAuth ? 'true' : 'false' },
            ].map(({ label, value }) => (
              <div key={label} className="flex items-center justify-between py-2 border-b border-[#1E2438] last:border-0">
                <span className="text-sm text-[#94A3B8]">{label}</span>
                <code className="text-xs font-mono text-[#F1F5F9] bg-[#1A1E2E] px-2 py-0.5 rounded">{value}</code>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-2">
            <Shield className="h-4 w-4 text-[#A78BFA]" />
            <CardTitle>Feature Flags</CardTitle>
          </div>
          <CardDescription>Backend feature activation status</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-3">
            {[
              { label: 'Authentication', flag: import.meta.env['VITE_FEATURE_AUTH'] === 'true' },
              { label: 'Appointment Management', flag: import.meta.env['VITE_FEATURE_APPOINTMENT_MANAGEMENT'] === 'true' },
            ].map(({ label, flag }) => (
              <div key={label} className="flex items-center justify-between py-2 border-b border-[#1E2438] last:border-0">
                <span className="text-sm text-[#94A3B8]">{label}</span>
                <Badge variant={flag ? 'success' : 'secondary'}>{flag ? 'Active' : 'Inactive'}</Badge>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
