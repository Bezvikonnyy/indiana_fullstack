import {request} from "../../api/httpClient";

export const putToOrder = async () => {
    return request<OrderDto>(`/api/cart/order`, {
        method: 'PUT'
    })
}

        // fetch(`/api/cart/checkout`, {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json",
        //         Authorization: `Bearer ${token}`,
        //     },
        //     body: JSON.stringify({ note: "Оплата заказа" }),
        // })
        //     .then((res) => {
        //         if (!res.ok) throw new Error(`Ошибка оплаты: ${res.status}`);
        //         return res.json();
        //     })
        //     .then(({ data, signature }) => {
        //         const form = document.createElement("form");
        //         form.method = "POST";
        //         form.action = "https://www.liqpay.ua/api/3/checkout";
        //
        //         const dataInput = document.createElement("input");
        //         dataInput.type = "hidden";
        //         dataInput.name = "data";
        //         dataInput.value = data;
        //         form.appendChild(dataInput);
        //
        //         const signatureInput = document.createElement("input");
        //         signatureInput.type = "hidden";
        //         signatureInput.name = "signature";
        //         signatureInput.value = signature;
        //         form.appendChild(signatureInput);
        //
        //         document.body.appendChild(form);
        //         form.submit();
        //     })
        //     .catch((err) => alert("Ошибка оплаты: " + err.message));