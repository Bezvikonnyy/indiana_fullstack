export type CardItemDto = {
    id: number;
    title: string;
    imageUrl: string;
    price: number;
    discountPercent: number;
    finalPrice: number;
    rating: number;
    isFavorite: boolean;
    isInCart: boolean;
    isPurchased: boolean;
}
