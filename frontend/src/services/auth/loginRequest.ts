import {request} from "../../api/httpClient";

export const loginRequest = async (username: string, password: string) => {
    return request<AuthResponseDto>(`/api/user/login`, {
        method: 'POST',
        body: JSON.stringify({username, password}),
    });
}

