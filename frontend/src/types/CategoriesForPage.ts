import {CategoryWithGames} from "./CategoryWithGames";

export interface CategoriesForPage{
    page: number;
    totalPages: number;
    totalElements: number;
    categories: CategoryWithGames[];
}