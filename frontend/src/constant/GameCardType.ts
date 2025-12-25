export interface GameCardType {
    id: number;
    title: string;
    imageUrl: string;
    price: number;
    isFavorite: boolean;
    isInCart: boolean;
    isPurchased: boolean;
}