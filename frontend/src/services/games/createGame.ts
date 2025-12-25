export  const createGame = async  (formData, navigate) => {
    try {
        const res = await fetch('http://localhost:8080/api/game/new_game', {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            },
            body: formData,
        });

        if (res.ok) {
            alert('Игра успешно создана');
            navigate('/home');
        } else {
            const errorText = await res.text();
            alert('Ошибка: ' + errorText);
        }
    } catch (err) {
        alert('Ошибка сети: ' + err.message);
    }
}