import {request} from "../../api/httpClient";

export const postInCartToggle = async (gameId: number) => {
    return request<{isInCart: boolean}>(`/api/user/inCart/toggle/${gameId}`, {
        method: 'POST',
    })
}