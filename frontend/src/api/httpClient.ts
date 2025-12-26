const BASE_URL = 'http://localhost:8080';

export async function request<Dto>(url: string, options: RequestInit = {}): Promise<| { success: true; data: Dto }
    | { success: false; error: { status: number; message: string } }> {
    try {
        const token = localStorage.getItem('token');

        const headers = new Headers(options.headers);

        if (!(options.body instanceof FormData)) {
            headers.set('Content-Type', 'application/json');
        }

        if (token) {
            headers.set('Authorization', `Bearer ${token}`);
        }

        const response = await fetch(BASE_URL + url, {
            ...options,
            headers,
        });

        if (!response.ok) {
            let message = 'Ошибка запроса';
            try {
                const errorBody = await response.json();
                message = errorBody.message || message;
            } catch {
            }

            return {
                success: false,
                error: {
                    status: response.status,
                    message,
                },
            };
        }

        const data = await response.json();

        return {
            success: true,
            data,
        };
    } catch (e) {
        return {
            success: false,
            error: {
                status: 0,
                message: 'Сервер недоступен',
            },
        };
    }
}
