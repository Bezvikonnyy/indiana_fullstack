import {request} from "../../api/httpClient";

export const getStatusPayment = async (orderId) => {
    return request<OrderStatusDto>(`/api/cart/order/${orderId}/result`);
}