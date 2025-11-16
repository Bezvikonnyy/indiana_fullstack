export interface GameCard {
    id: number;
    title: string;
    imageUrl: string;
    price: number;
    isFavorite: boolean;
    inCart: boolean;
    isPurchased: boolean;
}