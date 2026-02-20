import {CartItemDto} from "./CartItemDto";

export type CartDto = {
    id: number;
    userId: number;
    items: CartItemDto[];
    totalItems: number;
    totalPrice: number;
}