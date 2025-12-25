export type GameFullDto = {
    id: number;
    title: string;
    details: string;
    imageUrl: string;
    gameFileUrl: string;
    authorId: number;
    categories: Category[];
    price: number;
    isFavorite: boolean;
    isInCart: boolean;
    isPurchased: boolean;
}
