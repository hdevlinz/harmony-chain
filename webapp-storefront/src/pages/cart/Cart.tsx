import { CardElement, Elements, useElements, useStripe } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import { useCallback, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { authAPI, endpoints } from '../../lib/http';
import { initialCart, useCart } from '../../store/contexts/CartContext';
import { useUser } from '../../store/contexts/UserContext';
import { UPDATE_CART } from '../../store/reducers/CartReducer';
import { defaultImage, roles, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import { routeUrl } from '../_app';
import './Cart.css';

const stripePromise = loadStripe(
   'pk_test_51O41qGBy1BulLKF8k8qu0rqhO2HoDtOLogY9Yh757QmeFJvjTrj5o96LDJpJ4GWR6CNtEWe6K8aO0SrdV5P5UdfZ00mPyk9MSy',
);

const Cart = () => {
   const [user] = useUser();
   const [cart, cartDispatch] = useCart();

   const [formData, setFormData] = useState({
      customerName: user?.data?.username || '',
      customerEmail: user?.data?.email || '',
      customerPhone: user?.profile?.phone || '',
      customerAddress: user?.profile?.address || '',
   });
   const [quantities, setQuantities] = useState({});

   const [showModal, setShowModal] = useState(false);
   const [selectedPaymentMethod, setSelectedPaymentMethod] = useState('cash');

   const navigate = useNavigate();
   const elements = useElements();
   const stripe = useStripe();

   const tax = 0.01;
   const totalAmount = Object.values(cart).reduce((total, item) => total + item.quantity * item.unitPrice, 0);
   const totalWithFee = Math.round(totalAmount + totalAmount * tax);

   const formattedCurrency = useCallback(
      (data) =>
         data.toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND',
         }),
      [],
   );

   useEffect(() => {
      if (Object.entries(cart).length < 1) {
         Swal.fire({
            title: 'Thông báo',
            text: 'Không có sản phẩm nào trong giỏ hàng.',
            icon: 'info',
            confirmButtonText: 'Đóng',
            customClass: {
               confirmButton: 'swal2-confirm',
            },
         }).then((result) => {
            if (result.isConfirmed) {
               navigate(routeUrl.PRODUCT);
            }
         });
      } else {
         const initialQuantity = Object.keys(cart).reduce((acc, productId) => {
            acc[productId] = cart[productId].quantity;
            return acc;
         }, {});
         setQuantities(initialQuantity);
      }
   }, [cart, navigate]);

   const updateQuantity = async (productId, action) => {
      if (quantities[productId] + action === 0) {
         removeProduct(productId);
         return;
      }

      Swal.fire({
         title: 'Đang cập nhật...',
         text: 'Vui lòng đợi một chút.',
         allowOutsideClick: false,
         showConfirmButton: false,
         didOpen: () => {
            Swal.showLoading();
         },
      });

      try {
         const res = await authAPI().patch(endpoints.updateProductInCart(productId), {
            quantity: quantities[productId] + action,
         });

         if (res.status === statusCode.HTTP_200_OK) {
            const updatedCart = {
               ...cart,
               [productId]: {
                  ...cart[productId],
                  quantity: quantities[productId] + action,
               },
            };

            cartDispatch({
               type: UPDATE_CART,
               payload: updatedCart,
            });

            Swal.close();
         }
      } catch (error) {
         Swal.showValidationMessage(`Cập nhật sản phẩm thất bại: ${error.message}`);
         throw error;
      }
   };

   const removeProduct = (productId) => {
      Swal.fire({
         title: 'Xác nhận xóa sản phẩm',
         text: 'Bạn chắc chắn muốn sản phẩm này ra khỏi giỏ hàng?',
         icon: 'warning',
         showCancelButton: true,
         confirmButtonText: 'Có',
         cancelButtonText: 'Không',
         showLoaderOnConfirm: true,
         allowOutsideClick: () => !Swal.isLoading(),
         customClass: {
            confirmButton: 'swal2-confirm',
         },
         preConfirm: async () => {
            try {
               const res = await authAPI().delete(endpoints.removeProductFromCart(productId));

               if (res.status === statusCode.HTTP_204_NO_CONTENT) {
                  const updatedCart = { ...cart };

                  delete updatedCart[productId];
                  cartDispatch({
                     type: UPDATE_CART,
                     payload: updatedCart,
                  });

                  Toast.fire({
                     icon: 'success',
                     title: 'Thành công!',
                     text: 'Xóa sản phẩm khỏi giỏ hàng thành công.',
                  });
               }
            } catch (error) {
               Swal.showValidationMessage(`Xóa sản phẩm thất bại: ${error.message}`);
               throw error;
            }
         },
      });
   };

   const handleShowModal = () => setShowModal(true);

   const handleCloseModal = () => setShowModal(false);

   const handleFormChange = (e) => {
      const { name, value } = e.target;
      setFormData({ ...formData, [name]: value });
   };

   const handlePaymentMethodChange = (e) => {
      setSelectedPaymentMethod(e.target.value);
   };

   const handleConfirmOrder = async () => {
      Swal.fire({
         title: 'Đang kiểm tra thông tin...',
         text: 'Vui lòng đợi một chút.',
         showConfirmButton: false,
         didOpen: () => {
            Swal.showLoading();
         },
      });

      let order = {
         type: user?.data?.role === roles.SUPPLIER ? 'INBOUND' : 'OUTBOUND',
         orderDetails: Object.values(cart).map((item) => ({
            productId: item.product.id,
            quantity: item.quantity,
            unitPrice: item.unitPrice,
         })),
      };

      let success = false;

      if (selectedPaymentMethod === 'online') {
         if (!stripe || !elements) {
            return;
         }

         try {
            const { error } = await stripe.createPaymentMethod({
               type: 'card',
               card: elements.getElement(CardElement),
            });

            if (error) {
               Swal.hideLoading();
               Swal.showValidationMessage(
                  `Xác thực thông tin thanh toán thất bại: ${
                     error.message || 'Hệ thống đang bận, vui lòng thử lại sau!'
                  }`,
               );
               return;
            }

            const res = await authAPI().post(endpoints.charge, {
               amount: totalWithFee,
               customer: {
                  customerName: formData.customerName,
                  customerEmail: formData.customerEmail,
                  customerPhone: formData.customerPhone,
                  customerAddress: formData.customerAddress,
               },
               products: Object.values(cart).map((item) => ({
                  id: item.product.id,
                  name: item.product.name,
                  price: item.product.price,
               })),
            });
            const { clientSecret, error: serverError } = await res.data;

            if (serverError) {
               Swal.hideLoading();
               Swal.showValidationMessage(
                  `Xác thực thông tin thanh toán thất bại: ${
                     serverError.message || 'Hệ thống đang bận, vui lòng thử lại sau!'
                  }`,
               );
               return;
            }

            const { error: stripeError } = await stripe.confirmCardPayment(clientSecret, {
               payment_method: {
                  card: elements.getElement(CardElement),
                  billing_details: {
                     name: formData.customerName,
                  },
               },
            });

            if (stripeError) {
               Swal.hideLoading();
               const message = stripeError.message.includes('insufficient funds')
                  ? 'Số dư không đủ'
                  : stripeError?.message;
               Swal.showValidationMessage(
                  `Thanh toán thất bại: ${message || 'Hệ thống đang bận, vui lòng thử lại sau!'}`,
               );
               return;
            }

            success = true;
            order = { ...order, paid: true };
         } catch (error) {
            Swal.hideLoading();
            Swal.showValidationMessage(
               `Xác thực thanh toán thất bại: ${
                  error?.response?.data.map((data) => data.message).join('\n') ||
                  'Hệ thống đang bận, vui lòng thử lại sau!'
               }`,
            );
         }
      } else {
         success = true;
      }

      if (success) {
         try {
            const res = await authAPI().post(endpoints.checkout, order);

            if (res.status === statusCode.HTTP_201_CREATED) {
               Swal.fire({
                  title: 'Đặt hàng thành công',
                  text: 'Đơn hàng của bạn đã được đặt.',
                  icon: 'success',
                  confirmButtonText: 'Đóng',
               }).then(async () => {
                  handleCloseModal();
                  cartDispatch({ type: UPDATE_CART, payload: initialCart });
                  await authAPI().delete(endpoints.clearCart);
               });
            }
         } catch (error) {
            Swal.hideLoading();
            Swal.showValidationMessage(
               `Đặt hàng thất bại: ${
                  error?.response?.data.map((data) => data.message).join('\n') ||
                  'Hệ thống đang bận, vui lòng thử lại sau!'
               }`,
            );
         }
      }
   };

   return (
      <Container fluid className="cart-container" style={Object.entries(cart).length < 1 ? { minHeight: '100vh' } : {}}>
         <Row>
            <Col sm={9}>
               <div className="shadow-lg mb-3 bg-body rounded gap-3">
                  <div
                     style={{
                        height: '46px',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        paddingTop: '28px',
                     }}
                  >
                     <h2>Giỏ hàng của bạn - {Object.entries(cart).length} sản phẩm</h2>
                  </div>
                  <hr />
                  {Object.values(cart).map((c, index) => (
                     <>
                        <div key={index} className="cart-card mt-3">
                           <Col sm={3}>
                              <div className="cart-card__image">
                                 <img
                                    className="img-fluid"
                                    src={c?.product?.image ?? defaultImage.PRODUCT_IMAGE}
                                    alt={c?.product?.name || 'Product Image'}
                                 />
                              </div>
                           </Col>

                           <Col sm={9}>
                              <div className="cart-card__content">
                                 <Row className="align-items-end">
                                    <Col sm={4}>
                                       <h1 className="cart-card__content__name">{c?.product?.name}</h1>
                                       <p className="cart-card__content__description">{c?.product?.description}</p>
                                    </Col>
                                    <Col sm={4}>
                                       <h4 style={{ color: 'var(--primary-color)' }}>
                                          {formattedCurrency(c?.quantity * c?.unitPrice)}
                                       </h4>
                                    </Col>
                                    <Col sm={4}>
                                       <div className="d-flex align-items-center">
                                          <Button
                                             style={{
                                                background: 'var(--primary-color)',
                                                border: 'none',
                                             }}
                                             className="fs-5 me-2 btn-cart"
                                             variant="primary"
                                             onClick={() => updateQuantity(c?.product?.id, -1)}
                                          >
                                             <i className="bx bx-minus"></i>
                                          </Button>
                                          <Form.Group style={{ position: 'relative' }} controlId={`quantity-${index}`}>
                                             <Form.Label
                                                style={{
                                                   position: 'absolute',
                                                   left: '50%',
                                                   background: '#fff',
                                                   transform: 'translate(-50%, -50%)',
                                                   fontSize: '0.9rem',
                                                }}
                                             >
                                                Số lượng
                                             </Form.Label>
                                             <Form.Control
                                                disabled
                                                style={{ width: '120px', textAlign: 'center' }}
                                                type="number"
                                                value={quantities[c?.product?.id]}
                                             />
                                          </Form.Group>
                                          <Button
                                             style={{
                                                background: 'var(--primary-color)',
                                                border: 'none',
                                             }}
                                             className="fs-5 ms-2 btn-cart"
                                             variant="primary"
                                             onClick={() => updateQuantity(c?.product?.id, 1)}
                                          >
                                             <i className="bx bx-plus"></i>
                                          </Button>
                                       </div>
                                    </Col>
                                 </Row>
                                 <Button
                                    className="fs-5"
                                    variant="danger"
                                    onClick={() => removeProduct(c?.product?.id)}
                                 >
                                    <i className="bx bxs-trash-alt"></i>
                                 </Button>
                              </div>
                           </Col>
                        </div>
                        {index < Object.entries(cart).length - 1 ? <hr /> : null}
                     </>
                  ))}
               </div>
            </Col>
            <Col sm={3}>
               <Container>
                  <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
                     <div className="sumary-title">Tổng đơn hàng</div>

                     <div className="summary-content">
                        <div className="summary-item ">
                           <h3 className="summary-item__title">Tổng</h3>
                           <span className="summary-item__value">{formattedCurrency(totalAmount) || '0 VNĐ'}</span>
                        </div>
                        <div className="summary-item ">
                           <h3 className="summary-item__title">Số lượng</h3>
                           <span className="summary-item__value">
                              {Object.values(cart).reduce((total, item) => total + item.quantity, 0) || '0'}
                           </span>
                        </div>
                        <div className="summary-item ">
                           <h3 className="summary-item__title">Thuế</h3>
                           <span className="summary-item__value">{tax * 100} %</span>
                        </div>
                     </div>

                     <div className="summary-item ">
                        <h3 className="summary-item__title">Thành tiền</h3>
                        <span className="summary-item__value">{formattedCurrency(totalWithFee) || '0 VNĐ'}</span>
                     </div>

                     <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', height: '100%' }}>
                        <Button onClick={handleShowModal} className="summary-button">
                           Đặt hàng
                        </Button>
                     </div>
                  </div>
               </Container>
            </Col>
         </Row>

         <Modal show={showModal} onHide={handleCloseModal}>
            <Modal.Header closeButton>
               <Modal.Title>Thông tin thanh toán</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <Form>
                  <Form.Group className="mb-3">
                     <Form.Label>Tên</Form.Label>
                     <Form.Control type="text" name="customerName" value={formData.customerName} readOnly />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Email</Form.Label>
                     <Form.Control
                        type="email"
                        name="customerEmail"
                        value={formData.customerEmail}
                        onChange={handleFormChange}
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Số điện thoại</Form.Label>
                     <Form.Control
                        type="text"
                        name="customerPhone"
                        value={formData.customerPhone}
                        onChange={handleFormChange}
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Địa chỉ</Form.Label>
                     <Form.Control
                        type="text"
                        name="customerAddress"
                        value={formData.customerAddress}
                        onChange={handleFormChange}
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Hình thức thanh toán</Form.Label>
                     <div className="custom-radio">
                        <Form.Check
                           type="radio"
                           label="Thanh toán khi nhận hàng"
                           value="cash"
                           checked={selectedPaymentMethod === 'cash'}
                           onChange={handlePaymentMethodChange}
                           custom
                        />
                        <Form.Check
                           type="radio"
                           label="Thanh toán online"
                           value="online"
                           checked={selectedPaymentMethod === 'online'}
                           onChange={handlePaymentMethodChange}
                           custom
                        />
                     </div>
                  </Form.Group>
                  <div
                     className={`payment-method-transition ${
                        selectedPaymentMethod === 'online' ? 'fade-in' : 'fade-out'
                     }`}
                  >
                     {selectedPaymentMethod === 'online' && (
                        <Form.Group className="mb-3">
                           <Form.Label>Thẻ tín dụng</Form.Label>
                           <CardElement className="stripe-card-element" />
                        </Form.Group>
                     )}
                  </div>
               </Form>
            </Modal.Body>
            <Modal.Footer>
               <Button variant="secondary" onClick={handleCloseModal}>
                  Hủy
               </Button>
               <Button className="btn-confirm" onClick={handleConfirmOrder}>
                  Xác nhận
               </Button>
            </Modal.Footer>
         </Modal>
      </Container>
   );
};

const WrappedCart = () => (
   <Elements stripe={stripePromise}>
      <Cart />
   </Elements>
);

export default WrappedCart;
