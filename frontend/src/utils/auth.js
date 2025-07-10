import { jwtDecode } from 'jwt-decode';

export function getUserFromToken() {
    const token = localStorage.getItem('token');
    if (!token) return null;

    try {
        const payload = jwtDecode(token);
        const role = payload.role?.startsWith('ROLE_') ? payload.role.replace('ROLE_', '') : payload.role;
        const id = payload.id;
        return { id, username: payload.sub, role };
    } catch (e) {
        console.error('Ошибка при декодировании токена', e);
        return null;
    }
}

export function hasRole(requiredRoles) {
    const user = getUserFromToken();
    if (!user || !user.role) return false;
    return requiredRoles.includes(user.role);
}

export function getUserId() {
    const user = getUserFromToken();
    return user?.id || null;
}
