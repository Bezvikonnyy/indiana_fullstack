export const getAllCategory = (setCategories, selectedCategory,setSelectedCategory) => {
    fetch('http://localhost:8080/api/categories/forGame', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
        },
    })
        .then(res => res.json())
        .then(data => {
            setCategories(data);
            if (selectedCategory.length === 0 && data.length > 0) {
                setSelectedCategory([data[0]]);
            }
        })
        .catch(err => {
            console.error('Ошибка при загрузке категорий:', err);
        });
}