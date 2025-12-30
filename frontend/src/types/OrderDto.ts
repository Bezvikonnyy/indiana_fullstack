interface OrderDto{
    id: number;
    userId: number;
    items: OrderItemDto[];
    totalPrice: number;
    status: string;
    createdAt: number;
}