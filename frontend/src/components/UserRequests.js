import React, { useEffect, useState } from 'react';

function UserRequests() {
    const [users, setUsers] = useState([]);
    const token = localStorage.getItem('token');

    const fetchUsers = () => {
        fetch('http://localhost:8080/api/admin/users', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(res => res.json())
            .then(setUsers)
            .catch(console.error);
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const approveRequest = (id) => {
        fetch(`http://localhost:8080/api/admin/approve/${id}`, {
            method: 'PUT',
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка одобрения заявки');
                fetchUsers();
            })
            .catch(err => alert(err.message));
    };

    const deleteRequest = (id) => {
        if (!window.confirm('Удалить заявку?')) return;
        fetch(`http://localhost:8080/api/admin/delete/request/${id}`, {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка удаления заявки');
                fetchUsers();
            })
            .catch(err => alert(err.message));
    };

    return (
        <div className="user-requests">
            <h3>Пользователи и заявки на авторов</h3>
            {users.length === 0 ? (
                <p>Пользователей нет</p>
            ) : (
                <table className="user-requests-table">
                    <thead>
                    <tr>
                        <th>Имя пользователя</th>
                        <th>Роли</th>
                        <th>Заявка</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map(user => {
                        const hasRequest = !!user.requestUserDto;
                        return (
                            <tr key={user.id}>
                                <td>{user.username}</td>
                                <td>{user.roles.map(r => r.title).join(', ')}</td>
                                <td>{hasRequest ? user.requestUserDto.bodyRequest : '-'}</td>
                                <td>
                                    {hasRequest && (
                                        <>
                                            <button onClick={() => approveRequest(user.id)}>Одобрить</button>
                                            <button onClick={() => deleteRequest(user.id)}>Отклонить</button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default UserRequests;
