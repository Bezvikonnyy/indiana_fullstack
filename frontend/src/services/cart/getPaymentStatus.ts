// fetch(`/api/cart/order/${orderIdFromUrl}/status`, {
//     headers: { Authorization: `Bearer ${token}` },
// })
//     .then((res) => {
//         if (!res.ok) throw new Error("Ошибка проверки статуса: " + res.status);
//         return res.json();
//     })
//     .then((data) => {
//         setPaymentStatus(data.status);
//
//         if (data.status === "PAID" || data.status === "CANCELLED") {
//             // Остановить опрос, если получили финальный статус
//             clearInterval(intervalId);
//             setCheckingStatus(false);
//             alert(`Статус оплаты: ${data.status}`);
//         }
//     })
//     .catch((err) => {
//         console.error(err);
//         clearInterval(intervalId);
//         setCheckingStatus(false);
//     });