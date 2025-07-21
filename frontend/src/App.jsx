// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { getUserFromToken, hasRole } from './utils/auth';

import Header from './components/Header';
import HomePage from './pages/HomePage';
import LoginPage from './pages/auth/LoginPage';
import RegisterPage from './pages/auth/RegisterPage';
import CreateGamePage from './pages/games/CreateGamePage';
import EditGamePage from './pages/games/EditGamePage';
import GameDetailsPage from './pages/games/GameDetailsPage';
import ProfilePage from './pages/ProfilePage';
import AdminPanelPage from './pages/AdminPanelPage'; // <== Импортируем страницу админки

function PrivateRoute({ children, roles }) {
    const user = getUserFromToken();
    if (!user) return <Navigate to="/login" />;
    if (roles && !roles.some(role => hasRole(role))) {
        return <Navigate to="/home" />;
    }
    return children;
}

function App() {
    return (
        <Router>
            <Header />
            <Routes>
                <Route path="/home" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/games/:id" element={<GameDetailsPage />} />
                <Route path="/profile" element={<ProfilePage />} />

                <Route
                    path="/games/create"
                    element={
                        <PrivateRoute roles={['AUTHOR', 'ADMIN']}>
                            <CreateGamePage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/games/edit/:id"
                    element={
                        <PrivateRoute roles={['AUTHOR', 'ADMIN']}>
                            <EditGamePage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin"
                    element={
                        <PrivateRoute roles={['ADMIN']}>
                            <AdminPanelPage />
                        </PrivateRoute>
                    }
                />

                <Route path="*" element={<Navigate to="/home" />} />
            </Routes>
        </Router>
    );
}

export default App;
