import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { App } from './app/App';

function validateEnv(): void {
  const required = ['VITE_API_BASE_URL'];
  const missing = required.filter((key) => !import.meta.env[key]);
  if (missing.length > 0 && import.meta.env.PROD) {
    throw new Error(`Missing required environment variables: ${missing.join(', ')}`);
  }
}

validateEnv();

const rootEl = document.getElementById('root');
if (!rootEl) throw new Error('Root element not found');

createRoot(rootEl).render(
  <StrictMode>
    <App />
  </StrictMode>
);
