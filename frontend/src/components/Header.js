import React, { useState, useRef, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import hatIcon from '../assets/hat.png';
import './Header.css';
import {hasRole} from "../utils/auth";

function Header() {
    const navigate = useNavigate();
    const isLoggedIn = !!localStorage.getItem('token');
    const role = localStorage.getItem('role');
    const [menuOpen, setMenuOpen] = useState(false);
    const menuRef = useRef(null);

    const isAdmin = hasRole(['ADMIN']);

    const toggleMenu = () => setMenuOpen(prev => !prev);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        setMenuOpen(false);
        navigate('/login');
    };

    const handleProfile = () => {
        setMenuOpen(false);
        navigate('/profile');
    };

    const handleLoginRedirect = () => navigate('/login');
    const handleRegisterRedirect = () => navigate('/register');

    useEffect(() => {
        const handleClickOutside = (e) => {
            if (menuRef.current && !menuRef.current.contains(e.target)) {
                setMenuOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <header className="header">
            <div className="title-container">
                <Link to="/home" className="link">
                    <span className="title-text">Indian</span>
                    <span className="last-letter-wrapper">
                        <span className="last-letter">a</span>
                        <img src={hatIcon} alt="Hat" className="hat-icon" />
                    </span>
                </Link>
            </div>

            <div className="buttons-container">
                {isLoggedIn ? (
                    <div className="menu-container" ref={menuRef}>
                        <button onClick={toggleMenu} className="burger-button">☰</button>
                        {menuOpen && (
                            <div className="dropdown-menu">
                                <button onClick={handleProfile} className="menu-item">Профиль</button>
                                {isAdmin && (
                                    <button onClick={() => navigate('/admin')} className="menu-item">
                                        Админ-панель
                                    </button>
                                )}
                                <button onClick={handleLogout} className="menu-item">Выйти</button>
                            </div>
                        )}
                    </div>
                ) : (
                    <>
                        <button className="button" onClick={handleLoginRedirect}>Войти</button>
                        <button className="button secondary-button" onClick={handleRegisterRedirect}>Регистрация</button>
                    </>
                )}
            </div>
        </header>
    );
}

export default Header;
