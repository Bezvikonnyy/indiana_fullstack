export type InviteCodeDto = {
    id: number;
    code: string;
    used: boolean;
    createAt: number;
    expiresAt: number;
}