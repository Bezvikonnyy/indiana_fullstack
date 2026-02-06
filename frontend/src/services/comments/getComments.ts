import {request} from "../../api/httpClient";

export const getComments = async (gameId: number) => {
    return request<CommentDto[]>(`/api/comment/${gameId}/comments`);
}