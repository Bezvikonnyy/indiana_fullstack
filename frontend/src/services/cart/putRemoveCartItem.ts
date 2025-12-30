import {request} from "../../api/httpClient";

export const putRemoveCartItem = async (gameId) => {
    return request<CartDto>(`/api/cart/remove`, {
       method: 'PUT',
       body: JSON.stringify({ gameId }),
    });
}