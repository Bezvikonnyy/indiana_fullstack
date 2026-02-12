import {request} from "../../api/httpClient";

export const postCreateNews = async (formData: FormData) => {
    return request<NewsDto>(`/api/news/newNews`, {
        method: 'POST',
        body: formData,
    });
}