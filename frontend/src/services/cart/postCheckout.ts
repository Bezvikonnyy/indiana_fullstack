import {request} from "../../api/httpClient";

export const postCheckout = async (paymentMethod: string, orderId: number) => {
    return request<PaymentRequestDto>(`/api/cart/checkout/${paymentMethod}/${orderId}`, {
       method: 'POST',
       body: JSON.stringify({paymentMethod, orderId})
    });
}