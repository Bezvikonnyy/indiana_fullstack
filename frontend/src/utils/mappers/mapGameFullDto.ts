import {GameFullDto} from "../../types/GameFullDto";

export const mapGameFullDto = (data: GameFullDto) => ({
    id: data.id,
    title: data.title,
    details: data.details,
    imageUrl: data.imageUrl,
    gameFileUrl: data.gameFileUrl,
    authorId: data.authorId,
    categories: data.categories,
    price: data.price,
    isFavorite: data.isFavorite,
    isInCart: data.isInCart,
    isPurchased: data.isPurchased
});
