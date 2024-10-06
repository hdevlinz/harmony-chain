import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom';
import './App.css';
import WrappedCart from '../components/cart/Cart';
import Home from '../components/home/Home';
import MyChatBot from '../components/home/MyChatBot';
import Login from '../components/login/Login';
import OrderCustomer from '../components/order/OrderCustomer';
import OrderSupplier from '../components/order/OrderSupplier';
import Product from '../components/product/Product';
import ProductDetails from '../components/product/ProductDetails';
import ProductSupplier from '../components/product/ProductSupplier';
import Account from '../components/profile/Account';
import Profile from '../components/profile/Profile';
import Register from '../components/register/Register';
import Supplier from '../components/supplier/Supplier';
import SupplierDetails from '../components/supplier/SupplierDetails';
import Footer from '../layout/footer/Footer';
import Header from '../layout/header/Header';
import { CartProvider } from '../store/contexts/CartContext';
import { UserProvider } from '../store/contexts/UserContext';

export const routeUrl = {
   HOME: '/',
   LOGIN: '/login',
   REGISTER: '/register',
   ACCOUNT: '/users',
   PROFILE: '/users/profile',
   ORDER_CUSTOMER: '/orders-customer',
   CART: '/cart',

   ORDER_SUPPLIER: '/orders-supplier',

   PRODUCT: '/products',
   PRODUCT_DETAILS: (productId) => `/products/${productId}`,
   SUPPLIER: '/suppliers',
   SUPPLIER_DETAILS: (supplierId) => `/suppliers/${supplierId}`,
   PRODUCT_SUPPLIER: '/products-supplier',
};

function App() {
   return (
      <UserProvider>
         <CartProvider>
            <BrowserRouter>
               <MyChatBot />
               <Header />
               <Routes>
                  <Route path={routeUrl.HOME} element={<Home />} />
                  <Route path={routeUrl.LOGIN} element={<Login />} />
                  <Route path={routeUrl.REGISTER} element={<Register />} />
                  <Route path={routeUrl.ACCOUNT} element={<Account />} />
                  <Route path={routeUrl.PROFILE} element={<Profile />} />
                  <Route path={routeUrl.CART} element={<WrappedCart />} />
                  <Route path={routeUrl.SUPPLIER} element={<Supplier />} />
                  <Route path={routeUrl.SUPPLIER_DETAILS(':supplierId')} element={<SupplierDetails />} />
                  <Route path={routeUrl.PRODUCT} element={<Product />} />
                  <Route path={routeUrl.PRODUCT_DETAILS(':productId')} element={<ProductDetails />} />

                  <Route path={routeUrl.ORDER_CUSTOMER} element={<OrderCustomer />} />
                  <Route path={routeUrl.ORDER_SUPPLIER} element={<OrderSupplier />} />
                  <Route path={routeUrl.PRODUCT_SUPPLIER} element={<ProductSupplier />} />
               </Routes>
               <ConditionalFooter />
            </BrowserRouter>
         </CartProvider>
      </UserProvider>
   );
}

function ConditionalFooter() {
   const location = useLocation();
   const hideFooter =
      location.pathname === routeUrl.LOGIN ||
      location.pathname === routeUrl.REGISTER ||
      location.pathname === routeUrl.CART;

   return hideFooter ? null : <Footer />;
}

export default App;
