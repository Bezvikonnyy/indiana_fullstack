import {request} from "../../api/httpClient";

export const putClearCart = async () => {
    return request<CartDto>(`/api/cart/clear`, {
        method: 'PUT',
    });
}