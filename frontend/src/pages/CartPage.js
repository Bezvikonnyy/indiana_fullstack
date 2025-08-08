import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./CartPage.css";

// Иконка корзины в SVG
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

const CartPage = () => {
    const navigate = useNavigate();
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem("token");

    // Загружаем корзину
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

    // Удаление игры
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

    // Очистка корзины
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

    // Оформление заказа
    const toOrder = () => {
        if (!cart) return;

        fetch(`/api/cart/order`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({ note: "Оплата заказа" }),
        })
            .then((res) => {
                if (!res.ok) throw new Error(`Ошибка оформления заказа: ${res.status}`);
                return res.json();
            })
            .then((order) => navigate(`/orders/${order.id}`))
            .catch((err) => alert("Ошибка оформления заказа: " + err.message));
    };

    // Состояние загрузки
    if (loading) return <p>Загрузка...</p>;

    // Если корзина пустая
    if (!cart || !cart.items || cart.items.length === 0)
        return (
            <div className="cart-page">
                <h2>
                    <CartIcon style={{ marginRight: 8, verticalAlign: "middle" }} />
                    Ваша корзина пуста
                </h2>
            </div>
        );

    // Если корзина не пуста
    return (
        <div className="cart-page">
            <h2>
                <CartIcon style={{ marginRight: 8, verticalAlign: "middle" }} />
                Моя корзина
            </h2>
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
                <button onClick={toOrder} className="btn-order">
                    Оформить заказ
                </button>
            </div>
        </div>
    );
};

export default CartPage;
