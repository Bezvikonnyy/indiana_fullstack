import { request } from "../../api/httpClient";

export const getAllNews = async (pageId: number) => {
    return request<PageResponse<NewsDto>>(
        `/api/news/allNews?page=0`
    );
};
