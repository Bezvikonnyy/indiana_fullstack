import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export const PaymentResultPage = () => {
    const { orderId } = useParams();
    const [loading, setLoading] = useState(true);
    const [result, setResult] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchResult = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch(`http://localhost:8080/api/cart/order/${orderId}/result`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                });
                if (!response.ok) {
                    throw new Error("Ошибка при получении результата");
                }
                const data = await response.json();
                setResult(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        fetchResult();
    }, [orderId]);

    if (loading) {
        return <p>Загрузка результата...</p>;
    }

    if (error) {
        return (
            <div>
                <h2 style={{ color: "red" }}>Ошибка</h2>
                <p>{error}</p>
            </div>
        );
    }

    return (
        <div>
            <h1>Результат оплаты заказа №{orderId}</h1>
            {result?.status === "PAID" ? (
                <p style={{ color: "green" }}>Оплата прошла успешно ✅</p>
            ) : (
                <p style={{ color: "red" }}>Оплата не прошла ❌</p>
            )}
            {result?.message && <p>{result.message}</p>}
        </div>
    );
}
