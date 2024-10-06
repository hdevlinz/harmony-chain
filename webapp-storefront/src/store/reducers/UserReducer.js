import cookie from 'react-cookies';
import { initialUser } from '../contexts/UserContext';

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';
export const UPDATE_USER = 'UPDATE_USER';

const userReducer = (state, action) => {
   switch (action.type) {
      case LOGIN:
         return action.payload;
      case LOGOUT:
         cookie.remove('token');
         cookie.remove('user');
         cookie.remove('cart');
         return initialUser;
      case UPDATE_USER:
         const newState = {...state, ...action.payload};
         cookie.save('user', newState);
         return newState;
      default:
         return state;
   }
};

export default userReducer;
