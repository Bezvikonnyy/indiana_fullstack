// import React, { useEffect, useState } from 'react';
//
// function UserRequests() {
//     const [users, setUsers] = useState([]);
//     const token = localStorage.getItem('token');
//
//     const fetchUsers = () => {
//         fetch('http://localhost:8080/api/admin/users', {
//             headers: {
//                 Authorization: `Bearer ${token}`
//             }
//         })
//             .then(res => res.json())
//             .then(setUsers)
//             .catch(console.error);
//     };
//
//     useEffect(() => {
//         fetchUsers();
//     }, []);
//
//     const handleApprove = (id) => {
//         fetch(`http://localhost:8080/api/admin/approve/${id}`, {
//             method: 'PUT',
//             headers: { Authorization: `Bearer ${token}` }
//         })
//             .then(res => {
//                 if (!res.ok) throw new Error('Ошибка одобрения заявки');
//                 fetchUsers();
//             })
//             .catch(err => alert(err.message));
//     };
//
//     const handleReject = (id) => {
//         if (!window.confirm('Удалить заявку?')) return;
//         fetch(`http://localhost:8080/api/admin/delete/request/${id}`, {
//             method: 'DELETE',
//             headers: { Authorization: `Bearer ${token}` }
//         })
//             .then(res => {
//                 if (!res.ok) throw new Error('Ошибка удаления заявки');
//                 fetchUsers();
//             })
//             .catch(err => alert(err.message));
//     };
//
//     const deleteUser = (id) => {
//         if (!window.confirm('Удалить пользователя? Это действие необратимо.')) return;
//         fetch(`http://localhost:8080/api/admin/delete/user/${id}`, {
//             method: 'DELETE',
//             headers: { Authorization: `Bearer ${token}` }
//         })
//             .then(res => {
//                 if (!res.ok) throw new Error('Ошибка удаления пользователя');
//                 fetchUsers();
//             })
//             .catch(err => alert(err.message));
//     };
//
//     return (
//         <div className="user-requests">
//             <h3>Пользователи и заявки на авторов</h3>
//             {users.length === 0 ? (
//                 <p>Пользователей нет</p>
//             ) : (
//                 <table className="user-requests-table">
//                     <thead>
//                     <tr>
//                         <th>Имя пользователя</th>
//                         <th>Роли</th>
//                         <th style={{ width: '120px' }}>Заявка</th>
//                         <th style={{ width: '160px' }}>Действия</th>
//                     </tr>
//                     </thead>
//                     <tbody>
//                     {users.map(user => {
//                         const hasRequest = user.requestUsers && user.requestUsers.trim() !== '';
//                         return (
//                             <tr key={user.id}>
//                                 <td>{user.username}</td>
//                                 <td>{user.roles.map(r => r.title).join(', ')}</td>
//                                 <td style={{ textAlign: 'center' }}>
//                                     {hasRequest ? (
//                                         <div className="action-buttons">
//                                             <button className="approve-btn" onClick={() => handleApprove(user.id)}>✔</button>
//                                             <button className="reject-btn" onClick={() => handleReject(user.id)}>✖</button>
//                                         </div>
//                                     ) : (
//                                         '–'
//                                     )}
//                                 </td>
//                                 <td>
//                                     <button
//                                         onClick={() => deleteUser(user.id)}
//                                         style={{ color: 'red' }}
//                                     >
//                                         Удалить пользователя
//                                     </button>
//                                 </td>
//                             </tr>
//                         );
//                     })}
//                     </tbody>
//                 </table>
//             )}
//         </div>
//     );
// }
//
// export default UserRequests;
