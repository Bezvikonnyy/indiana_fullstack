import {CategoryWithGames} from "./CategoryWithGames";

export type CategoriesForPage = {
    page: number;
    totalPages: number;
    totalElements: number;
    categories: CategoryWithGames[];
}