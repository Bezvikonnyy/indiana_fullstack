export const postDeleteGame = async (id, navigate) => {
    const token = localStorage.getItem('token');
    const res = await fetch(`http://localhost:8080/api/game/delete/${id}`, {
        method: 'DELETE',
        headers: {Authorization: `Bearer ${token}`},
    });

    if (res.ok) {
        alert('Игра удалена.');
        navigate('/home');
    } else {
        const text = await res.text();
        alert('Ошибка: ' + text);
    }
}