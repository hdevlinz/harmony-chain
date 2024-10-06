import { createContext, useContext, useReducer } from 'react';
import cookie from 'react-cookies';
import userReducer from '../reducers/UserReducer';

export const UserContext = createContext(null);

export const initialUser = {
   data: null,
   profile: null,
};

export const UserProvider = ({ children }) => {
   const [user, dispatch] = useReducer(userReducer, cookie.load('user') || initialUser);

   return <UserContext.Provider value={[user, dispatch]}>{children}</UserContext.Provider>;
};

export const useUser = () => {
   return useContext(UserContext);
};
