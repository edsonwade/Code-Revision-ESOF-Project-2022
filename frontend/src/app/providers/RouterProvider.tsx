import React from 'react';
import { RouterProvider as ReactRouterProvider } from 'react-router-dom';
import { router } from '../router';

export const RouterProvider: React.FC = () => {
    return <ReactRouterProvider router={router} />;
};
