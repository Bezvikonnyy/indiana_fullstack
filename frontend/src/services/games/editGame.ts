export const editGame = async (formData, navigate, id) => {
    try {
        const token = localStorage.getItem('token');
        const res = await fetch(`http://localhost:8080/api/game/edit/${id}`, {
            method: 'POST',
            headers: { Authorization: `Bearer ${token}` },
            body: formData,
        });

        if (res.ok) {
            alert('Игра обновлена');
            navigate(`/games/${id}`);
        } else {
            const errorText = await res.text();
            alert('Ошибка: ' + errorText);
        }
    } catch (err) {
        alert('Ошибка сети: ' + err.message);
    }
};