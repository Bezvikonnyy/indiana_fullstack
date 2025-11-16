import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import {Header} from './components/Header';
import { routes } from './router/routes';

interface RouteType {
    path: string;
    element: React.ReactNode;
}

export const App: React.FC = () => {
    return (
        <Router>
            <Header />
            <Routes>
                {routes.map((route: RouteType, index: number) => (
                    <Route key={index} path={route.path} element={route.element} />
                ))}
            </Routes>
        </Router>
    );
};
