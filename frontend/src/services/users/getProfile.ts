import {request} from "../../api/httpClient";

export const getProfile = async () => {
    return request<ProfileDto>(`/api/user/profile`);
}