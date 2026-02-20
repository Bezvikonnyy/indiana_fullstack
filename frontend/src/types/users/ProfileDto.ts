import {RoleForProfileDto} from "./RoleForProfileDto";

export type ProfileDto = {
    id: number;
    username: string;
    role: RoleForProfileDto;
    createdAt: number;
}