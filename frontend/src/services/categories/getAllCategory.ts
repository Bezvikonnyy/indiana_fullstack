import {request} from "../../api/httpClient";

export const getAllCategory = async () => {
    return request<CategoryForGameDto[]>(`/api/categories/forGame`);
}