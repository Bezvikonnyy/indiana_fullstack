import { request } from "../../api/httpClient";
import { CategoryPageDto } from "../../types/CategoryPageDto";

export const getCategory = async (categoryId: number, page: number, size: number) => {
    return request<CategoryPageDto>(
        `/api/categories/${categoryId}?page=${page}&size=${size}`
    );
};
