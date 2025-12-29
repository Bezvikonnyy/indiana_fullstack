import {request} from "../../api/httpClient";

export const editGame = async (formData: FormData, id: string) => {
    return request<void>(`/api/game/edit/${id}`, {
        method: 'POST',
        body: formData,
    })
}
