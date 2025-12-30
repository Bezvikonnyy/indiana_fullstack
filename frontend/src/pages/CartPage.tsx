import React, { useEffect, useState } from "react";
import "./CartPage.css";
import {getMyCart} from "../services/cart/getMyCart";
import {CartIcon} from "../assets/CartIcon";
import {putToOrder} from "../services/cart/putToOrder";
import {putRemoveCartItem} from "../services/cart/putRemoveCartItem";
import {putClearCart} from "../services/cart/putClearCart";
import {postCheckout} from "../services/cart/postCheckout";

export const CartPage = () => {
    const [cart, setCart] = useState<CartDto>(null);
    const [loading, setLoading] = useState(true);
    const [isChoosingPayment, setIsChoosingPayment] = useState(false);
    const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<string>(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchMyCart = async () => {
            const res = await getMyCart();
            if(!res.success) {
                console.log(res.error.message);
            } else {
                setCart(res.data);
                setLoading(false);
            }
        }
        void fetchMyCart();
    }, [token]);

    const removeItem = async (gameId) => {
        if (!cart) return;
        const res = await putRemoveCartItem(gameId);
        if(!res.success) {
            alert(res.error.message);
        } else {
            setCart(res.data);
        }
    };

    const clearCart = async () => {
        if (!cart) return;
        const res = await putClearCart();
        if(!res.success) {
            alert(res.error.message);
        } else {
            setCart(res.data);
        }
    };

    const handleToOrder =  async () => {
        if (!cart) return;
        const res = await putToOrder();
        if(!res.success) {
            alert(res.error.message);
        } else {
            return res.data.id;
        }
    };

    const handleCheckout =  async (orderId: number) => {
        if (!cart) return;
        const res = await postCheckout(selectedPaymentMethod, orderId);
        if(!res.success) {
            alert(res.error.message);
        } else {
            const form = document.createElement("form");
            form.method = "POST";
            form.action = "https://www.liqpay.ua/api/3/checkout";

            const dataInput = document.createElement("input");
            dataInput.type = "hidden";
            dataInput.name = "data";
            dataInput.value = res.data.data;
            form.appendChild(dataInput);

            const signatureInput = document.createElement("input");
            signatureInput.type = "hidden";
            signatureInput.name = "signature";
            signatureInput.value = res.data.signature;
            form.appendChild(signatureInput);

            document.body.appendChild(form);

            form.submit();
        }
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
            <div className="cart-items">
                {cart.items.map((item) => (
                    <div className="cart-item" key={item.id}>
                        <img
                            src={`http://localhost:8080${item.imageUrl}`}
                            alt={item.gameTitle}
                            className="cart-item-image"
                        />
                        <div className="cart-info">
                            <h3>{item.gameTitle}</h3>
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
                {isChoosingPayment && (
                    <div className="payment-methods">
                        <label>
                            <input
                                type="radio"
                                name="payment"
                                value="LIQPAY"
                                checked={selectedPaymentMethod === "LIQPAY"}
                                onChange={() => setSelectedPaymentMethod("LIQPAY")}
                            />
                            LiqPay
                        </label>

                        <label>
                            <input
                                type="radio"
                                name="payment"
                                value="CARD"
                                checked={selectedPaymentMethod === "CARD"}
                                onChange={() => setSelectedPaymentMethod("CARD")}
                            />
                            Банковская карта
                        </label>

                        <label>
                            <input
                                type="radio"
                                name="payment"
                                value="PAYPAL"
                                checked={selectedPaymentMethod === "PAYPAL"}
                                onChange={() => setSelectedPaymentMethod("PAYPAL")}
                            />
                            PayPal
                        </label>
                    </div>
                )}
                <button
                    onClick={async () => {
                        if (!isChoosingPayment) {
                            setIsChoosingPayment(true);
                            return;
                        }
                        if (selectedPaymentMethod) {
                            const orderRes = await handleToOrder();
                            await handleCheckout(orderRes);
                        }
                    }}
                    disabled={isChoosingPayment && !selectedPaymentMethod}
                >
                    {selectedPaymentMethod ? "Перейти к оплате" : "Оформить заказ"}
                </button>
            </div>
        </div>
    );
};
