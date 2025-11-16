import React, { useState } from 'react';
import UserRequests from '../components/UserRequests';
import InviteCodes from '../components/InviteCodes';
import './AdminPanelPage.css';

export const AdminPanelPage = () => {
    const [activeSection, setActiveSection] = useState(null);

    const toggleSection = (section) => {
        setActiveSection(prev => (prev === section ? null : section));
    };

    return (
        <div className="admin-panel">
            <h2>Админ Панель</h2>

            <button
                className={`accordion-btn ${activeSection === 'users' ? 'active' : ''}`}
                onClick={() => toggleSection('users')}
            >
                Пользователи и заявки
            </button>
            {activeSection === 'users' && <UserRequests />}

            <button
                className={`accordion-btn ${activeSection === 'invite' ? 'active' : ''}`}
                onClick={() => toggleSection('invite')}
            >
                Инвайт коды
            </button>
            {activeSection === 'invite' && <InviteCodes />}
        </div>
    );
}
