import React from 'react';
import {NewsFormPage} from './NewsFormPage';
import {postCreateNews} from '../../services/news/postCreateNews';
import {useNavigate} from "react-router-dom";

export const CreateNewsPage: React.FC = () => {
    const navigate = useNavigate();

    const handleCreate = async (formData: FormData) => {
            const res = await postCreateNews(formData);
            if (!res.success) {
                alert(res.error.message)
            } else {
                alert("Новость создана!");
                navigate('/home');
            }
        }
    ;

    return (
        <div>
            <h2 className="hover-create-news">Создать новость</h2>
            <NewsFormPage onSubmit={handleCreate} submitText="Создать"/>
        </div>
    );
};
