import React, { useState, useRef, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import hatIcon from '../assets/hat.png';

function Header() {
    const navigate = useNavigate();
    const isLoggedIn = !!localStorage.getItem('token');
    const [menuOpen, setMenuOpen] = useState(false);
    const menuRef = useRef(null);

    const toggleMenu = () => {
        setMenuOpen(prev => !prev);
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        setMenuOpen(false);
        navigate('/login');
    };

    const handleProfile = () => {
        setMenuOpen(false);
        navigate('/profile');
    };

    const handleLoginRedirect = () => navigate('/login');
    const handleRegisterRedirect = () => navigate('/register');

    // Закрытие меню при клике вне
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
        <header style={styles.header}>
            <div style={styles.titleContainer}>
                <Link to="/home" style={styles.link}>
                    <span style={styles.titleText}>Indian</span>
                    <span style={styles.lastLetterWrapper}>
                        <span style={styles.lastLetter}>a</span>
                        <img src={hatIcon} alt="Hat" style={styles.hatIcon} />
                    </span>
                </Link>
            </div>

            <div style={styles.buttonsContainer}>
                {isLoggedIn ? (
                    <div style={{ position: 'relative' }} ref={menuRef}>
                        <button onClick={toggleMenu} style={styles.burgerButton}>
                            ☰
                        </button>
                        {menuOpen && (
                            <div style={styles.dropdownMenu}>
                                <button onClick={handleProfile} style={styles.menuItem}>Профиль</button>
                                <button onClick={handleLogout} style={styles.menuItem}>Выйти</button>
                            </div>
                        )}
                    </div>
                ) : (
                    <>
                        <button style={styles.button} onClick={handleLoginRedirect}>Войти</button>
                        <button style={{ ...styles.button, ...styles.secondaryButton }} onClick={handleRegisterRedirect}>Регистрация</button>
                    </>
                )}
            </div>
        </header>
    );
}

const styles = {
    header: {
        position: 'relative',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 20px',
        height: '4rem',
        backgroundColor: '#2e7d32',
        boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
    },

    titleContainer: {
        position: 'absolute',
        left: '50%',
        transform: 'translateX(-50%)',
        fontSize: '1.8rem',
        fontFamily: 'Georgia, serif',
        color: 'white',
        display: 'flex',
        alignItems: 'baseline',
    },

    link: {
        color: 'inherit',
        textDecoration: 'none',
        display: 'flex',
        alignItems: 'baseline',
        cursor: 'pointer',
    },
    titleText: {
        fontWeight: 'bold',
    },
    lastLetterWrapper: {
        position: 'relative',
        display: 'inline-block',
        width: '1.8rem',
        height: '2rem',
    },
    lastLetter: {
        fontWeight: 'bold',
    },
    hatIcon: {
        position: 'absolute',
        top: '0.4rem',
        left: '-0.28rem',
        width: '1.6rem',
        transform: 'rotate(-15deg)',
        pointerEvents: 'none',
    },

    buttonsContainer: {
        display: 'flex',
        alignItems: 'center',
        gap: '12px',
    },

    button: {
        backgroundColor: '#4caf50',
        color: 'white',
        border: 'none',
        padding: '8px 16px',
        fontSize: '1rem',
        borderRadius: '6px',
        cursor: 'pointer',
        transition: 'background-color 0.3s ease',
    },

    secondaryButton: {
        backgroundColor: '#81c784',
    },

    burgerButton: {
        backgroundColor: '#4caf50',
        color: 'white',
        border: 'none',
        padding: '6px 12px',
        fontSize: '1.5rem',
        borderRadius: '6px',
        cursor: 'pointer',
        userSelect: 'none',
    },

    dropdownMenu: {
        position: 'absolute',
        right: 0,
        top: '110%',
        backgroundColor: '#fff',
        border: '1px solid #ccc',
        borderRadius: '5px',
        boxShadow: '0 4px 10px rgba(0,0,0,0.2)',
        display: 'flex',
        flexDirection: 'column',
        minWidth: '150px',
        zIndex: 1000,
    },

    menuItem: {
        padding: '10px 16px',
        border: 'none',
        background: 'none',
        textAlign: 'left',
        cursor: 'pointer',
        fontSize: '1rem',
        color: '#333',
    },
};

export default Header;
