import React, { useEffect, useState } from 'react';

function InviteCodes() {
    const [codes, setCodes] = useState([]);
    const token = localStorage.getItem('token');

    const fetchCodes = () => {
        fetch('http://localhost:8080/api/admin/invite_code', {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => res.json())
            .then(setCodes)
            .catch(console.error);
    };

    useEffect(() => {
        fetchCodes();
    }, []);

    const createCode = () => {
        fetch('http://localhost:8080/api/admin/create_invite', {
            method: 'POST',
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка создания кода');
                return res.json();
            })
            .then(() => fetchCodes())
            .catch(err => alert(err.message));
    };

    const deleteCode = (id) => {
        if (!window.confirm('Удалить инвайт код?')) return;
        fetch(`http://localhost:8080/api/admin/delete/invite/${id}`, {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка удаления кода');
                fetchCodes();
            })
            .catch(err => alert(err.message));
    };

    return (
        <div className="invite-codes">
            <h3>Активные инвайт коды</h3>
            <button onClick={createCode}>Создать новый код</button>
            {codes.length === 0 ? (
                <p>Кодов нет</p>
            ) : (
                <ul className="invite-codes-list">
                    {codes.map(code => (
                        <li key={code.id}>
                            <span>{code.code}</span>
                            <button onClick={() => deleteCode(code.id)}>Удалить</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default InviteCodes;
