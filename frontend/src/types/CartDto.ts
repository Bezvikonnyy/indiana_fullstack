interface CartDto {
    id: number;
    userId: number;
    items: CartItemDto[];
    totalItems: number;
    totalPrice: number;
}