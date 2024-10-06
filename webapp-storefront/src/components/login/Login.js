import { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import cookie from 'react-cookies';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { routeUrl } from '../../App';
import APIs, { authAPI, endpoints } from '../../configs/APIConfigs';
import { useCart } from '../../store/contexts/CartContext';
import { useUser } from '../../store/contexts/UserContext';
import { UPDATE_CART } from '../../store/reducers/CartReducer';
import { LOGIN } from '../../store/reducers/UserReducer';
import { roles } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Login.css';

const Login = () => {
   const [, userDispatch] = useUser();
   const [, cartDispatch] = useCart();

   const [username, setUsername] = useState('');
   const [password, setPassword] = useState('');
   const [showPassword, setShowPassword] = useState(false);

   const [q] = useSearchParams();
   const navigate = useNavigate();

   const handleLogin = async (e) => {
      e.preventDefault();

      try {
         const token = await APIs.post(endpoints.login, { username, password });
         cookie.save('token', token.data);

         const user = await authAPI().get(endpoints.profileUser);
         let payload = { data: user.data };

         switch (user?.data?.role) {
            case roles.CUSTOMER:
               const profileCustomer = await authAPI().get(endpoints.profileCustomer);
               payload = { ...payload, profile: profileCustomer.data };
               break;
            case roles.SUPPLIER:
               const profileSupplier = await authAPI().get(endpoints.profileSupplier);
               payload = { ...payload, profile: profileSupplier.data };
               break;
            case roles.DISTRIBUTOR:
               break;
            case roles.MANUFACTURER:
               break;
            case roles.SHIPPER:
               const profileShipper = await authAPI().get(endpoints.profileShipper);
               payload = { ...payload, profile: profileShipper.data };
               break;
            default:
               throw new Error('Invalid');
         }

         const cart = await authAPI().get(endpoints.getCart);
         cartDispatch({
            type: UPDATE_CART,
            payload: cart.data,
         });
         cookie.save('cart', cart.data);

         userDispatch({
            type: LOGIN,
            payload: payload,
         });
         cookie.save('user', payload);

         let next = q.get('next') || routeUrl.HOME;
         navigate(next);

         Toast.fire({ icon: 'success', title: 'Đăng nhập thành công' });
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Đăng nhập thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
         console.error(error);
         console.error(error?.response);
      }
   };

   return (
      <div className="login-page">
         <div className="login-container">
            <h2 className="text-center mb-3 login-title">Đăng nhập</h2>
            <Form onSubmit={handleLogin} className="login-form">
               <Form.Group className="mb-3">
                  <Form.Control
                     autoFocus
                     value={username}
                     onChange={(e) => setUsername(e.target.value)}
                     type="text"
                     placeholder="Nhập tên đăng nhập..."
                     required
                  />
               </Form.Group>
               <Form.Group className="mb-3 position-relative">
                  <Form.Control
                     value={password}
                     onChange={(e) => setPassword(e.target.value)}
                     type={showPassword ? 'text' : 'password'}
                     placeholder="Nhập mật khẩu..."
                     required
                  />
                  <Button variant="link" className="password-toggle" onClick={() => setShowPassword(!showPassword)}>
                     {showPassword ? <FaEyeSlash /> : <FaEye />}
                  </Button>
               </Form.Group>
               <Form.Group className="mb-3">
                  <Button
                     variant="primary"
                     type="submit"
                     className="w-100"
                     style={{
                        backgroundColor: 'var(--primary-color)',
                        borderColor: 'var(--primary-color)',
                        fontWeight: '500',
                     }}
                  >
                     Đăng nhập
                  </Button>
               </Form.Group>
            </Form>
         </div>
      </div>
   );
};

export default Login;
