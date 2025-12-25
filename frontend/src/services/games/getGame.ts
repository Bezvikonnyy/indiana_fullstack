import {GameFullDto} from "../../types/GameFullDto";

export const getGame = async (id): Promise<GameFullDto> => {
    // const token = localStorage.getItem('token');
    const res = await fetch(`http://localhost:8080/api/game/${id}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
        },
    });
    if (!res.ok) throw new Error('Ошибка при загрузке данных игры');
    return res.json();
}
