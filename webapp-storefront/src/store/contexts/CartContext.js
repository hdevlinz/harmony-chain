import { createContext, useContext, useReducer } from 'react';
import cookie from 'react-cookies';
import cartReducer from '../reducers/CartReducer';

export const CartContext = createContext(null);

export const initialCart = {};

export const CartProvider = ({ children }) => {
   const [cart, dispatch] = useReducer(cartReducer, cookie.load('cart') || initialCart);

   return <CartContext.Provider value={[cart, dispatch]}>{children}</CartContext.Provider>;
};

export const useCart = () => {
   return useContext(CartContext);
};
