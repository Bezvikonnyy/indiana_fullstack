export const postEditProfile = async (username, password) => {
    const res = await fetch('http://localhost:8080/api/user/edit_profile', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
        },
        body: JSON.stringify({
            username,
            password: password || null,
        }),
    });

    if (!res.ok) {
        throw new Error(await res.text());
    }

    return true;
};
