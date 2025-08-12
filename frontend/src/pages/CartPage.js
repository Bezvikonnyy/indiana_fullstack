import React, { useEffect, useState } from "react";
import "./CartPage.css";

const CartIcon = (props) => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        stroke="currentColor"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
        viewBox="0 0 24 24"
        width={24}
        height={24}
        {...props}
    >
        <circle cx="9" cy="21" r="1" />
        <circle cx="20" cy="21" r="1" />
        <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6" />
    </svg>
);

// Вспомогательная функция для получения query-параметров из URL
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

const CartPage = () => {
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const [paymentStatus, setPaymentStatus] = useState(null);
    const [checkingStatus, setCheckingStatus] = useState(false);
    const token = localStorage.getItem("token");

    // Получаем order_id из URL (если он там есть, например ?order_id=123)
    const orderIdFromUrl = getQueryParam("order_id");

    useEffect(() => {
        fetch(`/api/cart/my`, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                if (!res.ok) throw new Error("Ошибка запроса: " + res.status);
                return res.json();
            })
            .then((data) => {
                setCart(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                alert("Ошибка загрузки корзины: " + err.message);
                setLoading(false);
            });
    }, [token]);

    // Если order_id есть в URL, то начинаем проверять статус оплаты
    useEffect(() => {
        if (!orderIdFromUrl) return;

        setCheckingStatus(true);

        const intervalId = setInterval(() => {
            fetch(`/api/cart/order/${orderIdFromUrl}/status`, {
                headers: { Authorization: `Bearer ${token}` },
            })
                .then((res) => {
                    if (!res.ok) throw new Error("Ошибка проверки статуса: " + res.status);
                    return res.json();
                })
                .then((data) => {
                    setPaymentStatus(data.status);

                    if (data.status === "PAID" || data.status === "CANCELLED") {
                        // Остановить опрос, если получили финальный статус
                        clearInterval(intervalId);
                        setCheckingStatus(false);
                        alert(`Статус оплаты: ${data.status}`);
                    }
                })
                .catch((err) => {
                    console.error(err);
                    clearInterval(intervalId);
                    setCheckingStatus(false);
                });
        }, 5000); // проверяем каждые 5 секунд

        // Очистка таймера при размонтировании компонента
        return () => clearInterval(intervalId);
    }, [orderIdFromUrl, token]);

    const removeItem = (gameId) => {
        if (!cart) return;

        fetch(`/api/cart/remove`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({ gameId }),
        })
            .then((res) => {
                if (!res.ok) throw new Error(`Ошибка удаления: ${res.status}`);
                return res.json();
            })
            .then((data) => setCart(data))
            .catch((err) => alert("Ошибка удаления: " + err.message));
    };

    const clearCart = () => {
        if (!cart) return;

        fetch(`/api/cart/clear`, {
            method: "PUT",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                if (!res.ok) throw new Error(`Ошибка очистки: ${res.status}`);
                return res.json();
            })
            .then((data) => setCart(data))
            .catch((err) => alert("Ошибка очистки: " + err.message));
    };

    const handleCheckout = () => {
        if (!cart) return;

        fetch(`/api/cart/checkout`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({ note: "Оплата заказа" }),
        })
            .then((res) => {
                if (!res.ok) throw new Error(`Ошибка оплаты: ${res.status}`);
                return res.json();
            })
            .then(({ data, signature }) => {
                const form = document.createElement("form");
                form.method = "POST";
                form.action = "https://www.liqpay.ua/api/3/checkout";

                const dataInput = document.createElement("input");
                dataInput.type = "hidden";
                dataInput.name = "data";
                dataInput.value = data;
                form.appendChild(dataInput);

                const signatureInput = document.createElement("input");
                signatureInput.type = "hidden";
                signatureInput.name = "signature";
                signatureInput.value = signature;
                form.appendChild(signatureInput);

                document.body.appendChild(form);
                form.submit();
            })
            .catch((err) => alert("Ошибка оплаты: " + err.message));
    };

    if (loading) return <p>Загрузка...</p>;

    if (!cart || !cart.items || cart.items.length === 0)
        return (
            <div className="cart-page">
                <h2>
                    <CartIcon style={{ marginRight: 8, verticalAlign: "middle" }} />
                    Ваша корзина пуста
                </h2>
            </div>
        );

    return (
        <div className="cart-page">
            <h2>
                <CartIcon style={{ marginRight: 8, verticalAlign: "middle" }} />
                Моя корзина
            </h2>
            {checkingStatus && (
                <div style={{marginBottom: 10, fontWeight: "bold"}}>
                    Проверяем статус оплаты: {paymentStatus || "ожидание..."}
                </div>
            )}
            <div className="cart-items">
                {cart.items.map((item) => (
                    <div className="cart-item" key={item.id}>
                        <img
                            src={item.imageUrl}
                            alt={item.title}
                            className="cart-item-image"
                        />
                        <div className="cart-info">
                            <h3>{item.title}</h3>
                            <p>Цена: {item.price}₴</p>
                        </div>
                        <button
                            className="btn-remove"
                            onClick={() => removeItem(item.gameId)}
                        >
                            Удалить
                        </button>
                    </div>
                ))}
            </div>
            <div className="cart-summary">
                <p>Всего игр: {cart.totalItems}</p>
                <p>Сумма: {cart.totalPrice}₴</p>
                <button onClick={clearCart} className="btn-clear">
                    Очистить корзину
                </button>
                <button onClick={handleCheckout} className="btn-order">
                    Оформить заказ и оплатить
                </button>
            </div>
        </div>
    );
};

export default CartPage;
