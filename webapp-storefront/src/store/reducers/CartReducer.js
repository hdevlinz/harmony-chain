import cookie from 'react-cookies';

export const UPDATE_CART = 'UPDATE_CART';

const cartReducer = (state, action) => {
   if (action.type === UPDATE_CART) {
      cookie.save('cart', action.payload);
      return action.payload;
   }

   return state;
};

export default cartReducer;
