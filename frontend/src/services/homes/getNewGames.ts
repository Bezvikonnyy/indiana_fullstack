export const getNewGames = (setGames) => {
    fetch('http://localhost:8080/api/home/newGame', {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
        },
    })
        .then(res => {
            if (!res.ok) {
                throw new Error('Ошибка при получении новых игр');
            }
            return res.json();
        })
        .then(data => {
            setGames(data);
        })
        .catch(err => {
            console.error('Ошибка загрузки новых игр:', err);
        });
};
