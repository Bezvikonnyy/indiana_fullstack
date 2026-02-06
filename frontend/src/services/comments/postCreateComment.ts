import {request} from "../../api/httpClient";

export const postCreateComment = async (data: {
    text: string;
    gameId: number;
}) => {
    return request<CommentDto>(`/api/comment/create_comment`, {
        method: 'POST',
        body: JSON.stringify(data),
    });
}