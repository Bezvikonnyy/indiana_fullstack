import React, {useEffect, useState} from 'react';
import './CommentSection.css';
import {getUserId, hasRole} from '../utils/auth';
import {getComments} from "../services/comments/getComments";
import {postCreateComment} from "../services/comments/postCreateComment";
import {putEditComment} from "../services/comments/putEditComment";
import {postDeleteComment} from "../services/comments/postDeleteComment";

export const CommentSection = ({gameId}) => {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem('token');
    const currentUserId = getUserId();
    const isAdmin = hasRole(['ADMIN']);

    const fetchComments = async () => {
        const res = await getComments(gameId);
        if (!res.success) {
            console.log(res.error.message);
        } else {
            setComments(res.data);
        }
        setLoading(false);
    };

    useEffect(() => {
        void fetchComments();
    }, [gameId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!newComment.trim()) return;
        const res = await postCreateComment({text: newComment, gameId: gameId});
        if (res.success) {
            setNewComment('');
            await fetchComments();
        } else {
            const err = res.error.message;
            alert('Ошибка: ' + err);
        }
    };

    const handleEdit = async (comment) => {
        const newText = prompt('Редактировать комментарий:', comment.text);
        if (!newText || newText === comment.text) return;
        const res = await putEditComment({text:newText}, comment.id);
        if(!res.success) {
            console.log(res.error.message);
        } else {
             await fetchComments();
        }
    };

    const handleDelete = async (commentId) => {
        if (!window.confirm('Удалить комментарий?')) return;

        const res = await postDeleteComment(commentId);
        if(!res.success) {
            console.log(res.error.message);
        }
        await fetchComments();
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
                                <div className="comment-actions">
                                    {isAuthor && <button onClick={() => handleEdit(comment)}>Редактировать</button>}
                                    {(isAuthor || isAdmin) && <button onClick={() => handleDelete(comment.id)}>Удалить</button>}
                                </div>
                            </li>
                        );
                    })
                )}
            </ul>
        </div>
    );
}
