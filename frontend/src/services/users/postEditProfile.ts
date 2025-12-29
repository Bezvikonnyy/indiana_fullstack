import {request} from "../../api/httpClient";

export const postEditProfile = async (username, password) => {
    return request<ProfileDto>(`/api/user/editProfile`, {
        method: 'PUT',
        body: JSON.stringify({username, password: password || null})
    });
}