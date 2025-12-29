interface ProfileDto {
    id: number;
    username: string;
    role: RoleForProfileDto;
    createdAt: number;
    authorsGame: GameForProfileDto[];
    favoriteGame: GameForProfileDto[];
    purchasedGame: GameForProfileDto[];
}