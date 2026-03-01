import React, { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthStore } from '../../../shared/store/authStore';
import { Role } from '../../../shared/types/common.types';
import { ROUTES } from '../routes';

interface RoleGuardProps {
    children: ReactNode;
    allowedRoles: Role[];
}

const RoleGuard: React.FC<RoleGuardProps> = ({ children, allowedRoles }) => {
    const user = useAuthStore((state) => state.user);

    const skipAuth = import.meta.env.VITE_SKIP_AUTH === 'true';

    if (skipAuth) {
        return <>{children}</>;
    }

    if (!user) {
        return <Navigate to={ROUTES.LOGIN} replace />;
    }

    const userRole = user.role || 'USER';
    const hasAccess = allowedRoles.includes(userRole as Role) || userRole === 'SUPER_ADMIN' || userRole === 'ADMIN';

    if (!hasAccess) {
        return <Navigate to={ROUTES.FORBIDDEN} replace />;
    }

    return <>{children}</>;
};

export default RoleGuard;
