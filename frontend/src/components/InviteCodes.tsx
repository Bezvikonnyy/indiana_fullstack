import {useEffect, useState} from "react";
import {getInviteCode} from "../services/admins/getInviteCode";
import {postCreateCode} from "../services/admins/postCreateCode";
import {postDeleteCode} from "../services/admins/postDeleteCode";

export const InviteCodes = () => {
    const [codes, setCodes] = useState<InviteCodeDto[]>([]);

    const fetchCodes = async () => {
        const res = await getInviteCode();
        if (!res.success) {
            console.log(res.error.message);
        } else {
            setCodes(res.data);
        }
    };

    useEffect(() => {
        void fetchCodes();
    }, []);

    const createCode = async () => {
        const res = await postCreateCode();
        if (!res.success) {
            console.log(res.error.message);
        }
        void await fetchCodes();
    };

    const deleteCode = async (inviteCodeId: number) => {
        if (!window.confirm('Удалить инвайт код?')) return;
        const res = await postDeleteCode(inviteCodeId);
        if (!res.success) {
            console.log(res.error.message);
        }
        void await fetchCodes();
    };

    return (
        <div className="invite-codes">
            <h3 className="admin-panel-hover">Активные инвайт коды</h3>
            <button onClick={createCode}>Создать новый код</button>
            {codes.length === 0 ? (
                <p>Кодов нет</p>
            ) : (
                <ul className="invite-codes-list">
                    {codes.map(code => (
                        <li key={code.id}>
                            <span>{code.code}</span>
                            <button onClick={() => deleteCode(code.id)}>Удалить</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
