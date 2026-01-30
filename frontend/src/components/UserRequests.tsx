import { useEffect, useState } from 'react';
import {getUsers} from "../services/admins/getUsers";
import {putApproveRequest} from "../services/admins/putApproveRequest";
import {postDeleteRequest} from "../services/admins/postDeleteRequest";
import {postDeleteUser} from "../services/admins/postDeleteUser";

export const UserRequests = () => {
    const [users, setUsers] = useState<UserForAdminPanelDto[]>([]);

    const fetchUsers = async () => {
        const res = await getUsers();
        if (!res.success) {
            console.log(res.error.message);
        } else {
            setUsers(res.data.content);
        }
    };

    useEffect(() => {
        void fetchUsers();
    }, []);

    const handleApprove = async (id: number) => {
        const res = await putApproveRequest(id);
        if (!res.success) {
            console.log(res.error.message);
        }
        void await fetchUsers();
    };

    const handleReject = async (id: number) => {
        if (!window.confirm('Удалить заявку?')) return;
        const res = await postDeleteRequest(id);
        if (!res.success) {
            console.log(res.error.message);
        }
        void await fetchUsers();
    };

    const deleteUser = async (id: number) => {
        if (!window.confirm('Удалить пользователя? Это действие необратимо.')) return;
        const res = await postDeleteUser(id);
        if (!res.success) {
            console.log(res.error.message);
        }
        void await fetchUsers();
    };

    return (
        <div className="user-requests">
            <h3 className="admin-panel-hover">Пользователи и заявки на авторов</h3>
            {users.length === 0 ? (
                <p>Пользователей нет</p>
            ) : (
                <table className="user-requests-table">
                    <thead>
                    <tr>
                        <th>Имя пользователя</th>
                        <th>Роли</th>
                        <th style={{ width: '120px' }}>Заявка</th>
                        <th style={{ width: '160px' }}>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map(user => {
                        const hasRequest = user.requestUsers && user.requestUsers.trim() !== '';
                        return (
                            <tr key={user.id}>
                                <td>{user.username}</td>
                                <td>{user.role}</td>
                                <td style={{ textAlign: 'center' }}>
                                    {hasRequest ? (
                                        <div className="action-buttons">
                                            <button className="approve-btn" onClick={() => handleApprove(user.id)}>✔</button>
                                            <button className="reject-btn" onClick={() => handleReject(user.id)}>✖</button>
                                        </div>
                                    ) : (
                                        '–'
                                    )}
                                </td>
                                <td>
                                    <button
                                        className="button-delete-user"
                                        onClick={() => deleteUser(user.id)}
                                    >
                                        Удалить пользователя
                                    </button>
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
