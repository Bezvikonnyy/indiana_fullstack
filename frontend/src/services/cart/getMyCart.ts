import {request} from "../../api/httpClient";

export const getMyCart = async () => {
    return request<CartDto>(`/api/cart/my`);
}