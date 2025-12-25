export async function loginRequest(username: string, password: string) {
    const response = await fetch('http://localhost:8080/api/user/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        throw new Error("Неверные данные");
    }

    const data = await response.json();
    localStorage.setItem('token', data.token);

    return data;
}
