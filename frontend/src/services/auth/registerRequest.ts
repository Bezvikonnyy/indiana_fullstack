import {request} from "../../api/httpClient";

export const registerRequest = async (PayloadAuth) => {
    return  request<UserDto>(`/api/user/registration`, {
        method: "POST",
        body: JSON.stringify(PayloadAuth),
    });
}
