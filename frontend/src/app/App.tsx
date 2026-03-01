import { Suspense } from 'react';
import { QueryProvider, RouterProvider } from './providers';

function App() {
    return (
        <Suspense fallback={
            <div className="h-screen w-screen flex items-center justify-center bg-slate-50">
                <div className="w-8 h-8 border-4 border-indigo-500 border-t-transparent rounded-full animate-spin"></div>
            </div>
        }>
            <QueryProvider>
                <RouterProvider />
            </QueryProvider>
        </Suspense>
    );
}

export default App;
