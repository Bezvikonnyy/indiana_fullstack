import React, { useEffect, useState } from 'react';
import './CommentSection.css';
import { getUserId } from '../utils/auth';

function CommentSection({ gameId }) {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem('token');
    const currentUserId = getUserId();

    const fetchComments = () => {
        fetch(`http://localhost:8080/api/comment/${gameId}/comments`)
            .then(res => res.json())
            .then(data => {
                setComments(data);
                setLoading(false);
            })
            .catch(err => {
                console.error('Ошибка загрузки комментариев:', err);
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchComments();
    }, [gameId]); // Удалено fetchComments из зависимостей, чтобы избежать бесконечных вызовов

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!newComment.trim()) return;

        try {
            const res = await fetch(`http://localhost:8080/api/comment/create_comment`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({
                    text: newComment,
                    gameId: Number(gameId),
                }),
            });

            if (res.ok) {
                setNewComment('');
                fetchComments();
            } else {
                const err = await res.text();
                alert('Ошибка: ' + err);
            }
        } catch (err) {
            alert('Сетевая ошибка: ' + err.message);
        }
    };

    const handleEdit = (comment) => {
        const newText = prompt('Редактировать комментарий:', comment.text);
        if (!newText || newText === comment.text) return;

        fetch(`http://localhost:8080/api/comment/edit_comment/${comment.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify({
                text: newText,
                gameId: comment.gameId,
            }),
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка обновления комментария');
                fetchComments();
            })
            .catch(err => alert(err.message));
    };

    const handleDelete = (commentId) => {
        if (!window.confirm('Удалить комментарий?')) return;

        fetch(`http://localhost:8080/api/comment/delete_comment/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        })
            .then(res => {
                if (!res.ok) throw new Error('Ошибка удаления комментария');
                fetchComments();
            })
            .catch(err => alert(err.message));
    };

    if (loading) return <p>Загрузка комментариев...</p>;

    return (
        <div className="comment-section">
            <h3>Комментарии</h3>

            {token && (
                <form onSubmit={handleSubmit} className="comment-form">
                    <textarea
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                        placeholder="Оставьте комментарий..."
                        rows={3}
                        required
                    />
                    <button type="submit">Отправить</button>
                </form>
            )}

            <ul className="comment-list">
                {comments.length === 0 ? (
                    <li>Комментариев пока нет</li>
                ) : (
                    comments.map(comment => {
                        const isAuthor = currentUserId === comment.authorId;
                        return (
                            <li key={comment.id}>
                                <strong>Пользователь: {comment.authorName}</strong>{' '}
                                <span className="date">
                                    {new Date(comment.time).toLocaleString()}
                                </span>
                                <p>{comment.text}</p>
                                {isAuthor && (
                                    <div className="comment-actions">
                                        <button onClick={() => handleEdit(comment)}>Редактировать</button>
                                        <button onClick={() => handleDelete(comment.id)}>Удалить</button>
                                    </div>
                                )}
                            </li>
                        );
                    })
                )}
            </ul>
        </div>
    );
}

export default CommentSection;
