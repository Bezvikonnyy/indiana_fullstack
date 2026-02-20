import {request} from "../../api/httpClient";
import {CategoriesForPage} from "../../types/categories/CategoriesForPage";

export const getCategoriesPage = async (page: number, size: number) => {
    return request<CategoriesForPage>(
        `/api/categories?page=${page}&size=${size}`
    );
};
