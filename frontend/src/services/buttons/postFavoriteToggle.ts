export const postFavoriteToggle = (gameId, active, setActive) => {
    setActive(!active);

    fetch(`http://localhost:8080/api/user/favorite/toggle/${gameId}`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
            'Content-Type': 'application/json'
        }

    })
        .then(res => res.json())
        .then(data => setActive(data.isFavorite))
        .catch(err => {
            setActive(!active);
            console.error('Ошибка при изменении избранного', err);
        });
}