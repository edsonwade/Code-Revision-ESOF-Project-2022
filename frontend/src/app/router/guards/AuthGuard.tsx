import React, { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuthStore } from '../../../shared/store/authStore';
import { ROUTES } from '../routes';

interface AuthGuardProps {
    children: ReactNode;
}

const AuthGuard: React.FC<AuthGuardProps> = ({ children }) => {
    const isAuthenticated = useAuthStore((state) => state.isAuthenticated);
    const location = useLocation();

    const skipAuth = import.meta.env.VITE_SKIP_AUTH === 'true';

    console.log('AuthGuard Check:', { isAuthenticated, skipAuth, path: location.pathname });

    if (skipAuth) {
        return <>{children}</>;
    }

    if (!isAuthenticated) {
        return <Navigate to={ROUTES.LOGIN} state={{ from: location }} replace />;
    }

    return <>{children}</>;
};

export default AuthGuard;
