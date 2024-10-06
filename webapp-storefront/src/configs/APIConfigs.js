import axios from 'axios';
import cookie from 'react-cookies';
import { statusCode } from '../utils/Constatns';

const CONTEXT_PATH = '/harmony';
const BASE_URL = 'http://localhost:8080';

export const endpoints = {
   login: `${CONTEXT_PATH}/api/users/login`,
   register: `${CONTEXT_PATH}/api/users/register`,
   confirm: `${CONTEXT_PATH}/api/users/confirm`,
   profileUser: `${CONTEXT_PATH}/api/users/profile`,
   updateProfileUser: `${CONTEXT_PATH}/api/users/profile/update`,
   deleteProfileUser: `${CONTEXT_PATH}/api/users/profile/delete`,

   getCart: `${CONTEXT_PATH}/api/cart`,
   addProductToCart: `${CONTEXT_PATH}/api/cart/product/add`,
   updateProductInCart: (productId) => `${CONTEXT_PATH}/api/cart/product/${productId}/update`,
   removeProductFromCart: (productId) => `${CONTEXT_PATH}/api/cart/product/${productId}/remove`,
   clearCart: `${CONTEXT_PATH}/api/cart/product/clear`,

   customers: `${CONTEXT_PATH}/api/customers`,
   getCustomer: (customerId) => `${CONTEXT_PATH}/api/customers/${customerId}`,
   profileCustomer: `${CONTEXT_PATH}/api/customers/profile`,
   updateProfileCustomer: `${CONTEXT_PATH}/api/customers/profile/update`,

   suppliers: `${CONTEXT_PATH}/api/suppliers`,
   getSupplier: (supplierId) => `${CONTEXT_PATH}/api/suppliers/${supplierId}`,
   profileSupplier: `${CONTEXT_PATH}/api/suppliers/profile`,
   updateProfileSupplier: `${CONTEXT_PATH}/api/suppliers/profile/update`,
   getRatingsOfSupplier: (supplierId) => `${CONTEXT_PATH}/api/suppliers/${supplierId}/ratings`,
   addRatingForSupplier: (supplierId) => `${CONTEXT_PATH}/api/suppliers/${supplierId}/rating/add`,
   getProductsOfSupplier: (supplierId) => `${CONTEXT_PATH}/api/suppliers/${supplierId}/products`,
   publishProduct: `${CONTEXT_PATH}/api/suppliers/product/publish`,
   unpublishProduct: (productId) => `${CONTEXT_PATH}/api/suppliers/product/${productId}/unpublish`,
   getOrdersOfSupplier: (supplierId) => `${CONTEXT_PATH}/api/suppliers/${supplierId}/orders`,

   shippers: `${CONTEXT_PATH}/api/shippers`,
   getShipper: (shipperId) => `${CONTEXT_PATH}/api/shippers/${shipperId}`,
   profileShipper: `${CONTEXT_PATH}/api/shippers/profile`,
   updateProfileShipper: `${CONTEXT_PATH}/api/shippers/profile/update`,

   products: `${CONTEXT_PATH}/api/products`,
   getProduct: (productId) => `${CONTEXT_PATH}/api/products/${productId}`,
   categories: `${CONTEXT_PATH}/api/categories`,
   tags: `${CONTEXT_PATH}/api/tags`,
   taxes: `${CONTEXT_PATH}/api/taxes`,
   units: `${CONTEXT_PATH}/api/units`,
   ratings: `${CONTEXT_PATH}/api/ratings`,
   rankedSuppliers: `${CONTEXT_PATH}/api/ratings/ranked-suppliers`,
   detailsRating: (ratingId) => `${CONTEXT_PATH}/api/ratings/${ratingId}`,

   invoices: `${CONTEXT_PATH}/api/invoices`,
   getInvoiceByInvoiceNumber: (invoiceNumber) => `${CONTEXT_PATH}/api/invoices/${invoiceNumber}`,
   charge: `${CONTEXT_PATH}/api/invoices/charge`,
   orders: `${CONTEXT_PATH}/api/orders`,
   getOrderByOrderNumber: (orderNumber) => `${CONTEXT_PATH}/api/orders/${orderNumber.toString()}`,
   checkout: `${CONTEXT_PATH}/api/orders/checkout`,
   checkin: `${CONTEXT_PATH}/api/orders/checkin`,
   cancelOrder: (orderId) => `${CONTEXT_PATH}/api/orders/${orderId}/cancel`,
   updateStatusOrder: (orderId) => `${CONTEXT_PATH}/api/orders/${orderId}/status`,
};

export default axios.create({
   baseURL: BASE_URL,
});

export const authAPI = () => {
   return axios.create({
      baseURL: BASE_URL,
      headers: {
         Authorization: cookie.load('token'),
      },
   });
};

export const findOrderByOrderNumber = async (orderNumber) => {
   try {
      const res = await authAPI().get(endpoints.getOrderByOrderNumber(orderNumber));

      if (res.status === statusCode.HTTP_200_OK) {
         return res.data;
      }

      return null;
   } catch (error) {
      console.log(error);
      if (error.response.status === statusCode.HTTP_401_UNAUTHORIZED) {
         return 'Vui lòng đăng nhập để tìm kiếm';
      }

      return 'Không tìm thấy đơn hàng. Vui lòng thử lại!';
   }
};

export const findInvoiceByInvoiceNumber = async (invoiceNumber) => {
   try {
      const res = await authAPI().get(endpoints.getInvoiceByInvoiceNumber(invoiceNumber));

      if (res.status === statusCode.HTTP_200_OK) {
         return res.data;
      }

      return null;
   } catch (error) {
      if (error.response.status === statusCode.HTTP_401_UNAUTHORIZED) {
         return 'Vui lòng đăng nhập để tìm kiếm';
      }

      return 'Không tìm thấy hóa đơn. Vui lòng thử lại!';
   }
};
