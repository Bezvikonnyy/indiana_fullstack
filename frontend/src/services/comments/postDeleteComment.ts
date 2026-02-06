import {request} from "../../api/httpClient";

export const postDeleteComment = async (commentId: number) => {
    return request<void>(`/api/comment/delete_comment/${commentId}`, {
        method: 'DELETE',
    });
}