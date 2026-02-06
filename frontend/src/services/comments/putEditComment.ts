import {request} from "../../api/httpClient";

export const putEditComment = async (
    data: { text: string;}, commentId: number) => {
    return request<CommentDto>(`/api/comment/edit_comment/${commentId}`, {
        method: 'PUT',
        body: JSON.stringify(data),
    });
}