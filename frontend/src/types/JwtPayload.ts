interface JwtPayload {
    sub: string;
    id: number;
    role: string;
    iat?: number;
    exp?: number;
}
