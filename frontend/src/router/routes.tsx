import {HomePage, LoginPage, RegisterPage, GameDetailsPage, ProfilePage, PaymentResultPage, CartPage, CreateGamePage,
    EditGamePage, AdminPanelPage, PurchasedGamesPage, CreatedGamesPage, FavoritePage} from '../pages';
import { Navigate } from "react-router-dom";
import { PrivateRoute } from "./PrivateRoute";
import {JSX} from "react";

export type AppRoute = {path: string, element: JSX.Element}
export const routes: AppRoute[] = [
    { path: "/home", element: <HomePage /> },
    { path:"/login", element: <LoginPage /> },
    { path:"/register", element:<RegisterPage /> },
    { path:"/games/:id", element:<GameDetailsPage /> },
    { path:"/profile", element:<ProfilePage /> },
    { path:"/order/:orderId/result", element:<PaymentResultPage /> },
    { path:"/cart", element:<PrivateRoute roles={['USER', 'AUTHOR', 'ADMIN']}><CartPage /></PrivateRoute> },
    { path:"/games/create", element:<PrivateRoute roles={['AUTHOR', 'ADMIN']}><CreateGamePage /></PrivateRoute> },
    { path:"/games/edit/:id", element:<PrivateRoute roles={['AUTHOR', 'ADMIN']}><EditGamePage /></PrivateRoute> },
    { path:"/admin", element:<PrivateRoute roles={['ADMIN']}><AdminPanelPage /></PrivateRoute> },
    { path:"/purchased", element:<PrivateRoute roles={['USER', 'AUTHOR','ADMIN']}><PurchasedGamesPage /></PrivateRoute> },
    { path:"/created", element:<PrivateRoute roles={['AUTHOR', 'ADMIN']}><CreatedGamesPage /></PrivateRoute> },
    { path:"/favorite", element:<PrivateRoute roles={['USER', 'AUTHOR', 'ADMIN']}><FavoritePage/></PrivateRoute> },
    { path:"*", element:<Navigate to="/home" /> }
]
