import {request} from "../../api/httpClient";

export const getNewNews = async () => {
    return request<NewsDto[]>(`/api/home/newNews`);
}