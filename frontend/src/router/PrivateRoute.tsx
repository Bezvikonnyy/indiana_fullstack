import {FC, PropsWithChildren} from "react";
import {Navigate} from "react-router-dom";
import { getUserFromToken, hasRole } from '../utils/auth';


export interface PrivateRouteProps extends PropsWithChildren{roles:string[]}
export const PrivateRoute:FC<PrivateRouteProps> = ({children, roles}) => {
    const user = getUserFromToken();
    if (!user) return <Navigate to="/login" />;
    if (roles && !roles.some(role => hasRole(role))) {
        return <Navigate to="/home" />;
    }
    return children;
}