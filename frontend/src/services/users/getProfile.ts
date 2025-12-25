export const getProfile = async () => {
    const res = await fetch('http://localhost:8080/api/user/profile', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
        },
    });

    if (!res.ok) {
        throw new Error(await res.text());
    }

    return await res.json();
};
