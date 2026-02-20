import {Category} from "../categories/Category";

export type GameDetailsDto = {
    id: number;
    title: string;
    details: string;
    imageUrl: string;
    gameFileUrl: string;
    authorId: number;
    categories: Category[];
    price: number;
    discountPercent: number;
    finalPrice: number;
    rating: number;
    isFavorite: boolean;
    isInCart: boolean;
    isPurchased: boolean;
}
