import { useCallback, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import Swal from 'sweetalert2';
import APIs, { authAPI, endpoints } from '../../configs/APIConfigs';
import Loading from '../../layout/loading/Loading';
import { useCart } from '../../store/contexts/CartContext';
import { UPDATE_CART } from '../../store/reducers/CartReducer';
import { defaultImage, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Product.css';

const ProductDetails = () => {
   const [cart, dispatch] = useCart();

   const [product, setProduct] = useState(null);
   const [quantity, setQuantity] = useState(1);

   const { productId } = useParams();

   const loadProductDetails = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.getProduct(productId));

         setProduct(res.data);
      } catch (error) {
         console.error('Error loading product details:', error);
      }
   }, [productId]);

   useEffect(() => {
      loadProductDetails();
   }, [loadProductDetails]);

   const addProductToCart = async () => {
      Swal.fire({
         title: 'Đang thêm sản phẩm...',
         text: 'Vui lòng đợi một chút.',
         showConfirmButton: false,
         didOpen: () => {
            Swal.showLoading();
         },
      });

      try {
         const res = await authAPI().post(endpoints.addProductToCart, { productId: product.id, quantity: quantity });

         if (res.status === statusCode.HTTP_200_OK) {
            const newCart = { ...cart };

            if (newCart[product.id]) {
               newCart[product.id].quantity += quantity;
            } else {
               newCart[product.id] = {
                  quantity: quantity,
                  unitPrice: product.price,
                  product: product,
               };
            }

            dispatch({
               type: UPDATE_CART,
               payload: newCart,
            });

            Toast.fire({
               icon: 'success',
               title: 'Thành công!',
               text: 'Thêm sản phẩm vào giỏ hàng thành công.',
            });
         }
      } catch (error) {
         Swal.showValidationMessage(`Thêm sản phẩm vào giỏ hàng thất bại: ${error.message}`);
         Swal.hideLoading();
         throw error;
      }
   };

   const processChangeQuantity = (action) => setQuantity(quantity + action);

   if (!product) return <Loading />;

   return (
      <Container className="product-detail-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={12}>
                  <div className="product-detail">
                     <Row>
                        <Col sm={5}>
                           <div className="product-detail__image">
                              <img
                                 src={product.image || defaultImage.PRODUCT_IMAGE}
                                 alt={product.name || 'Hình ảnh sản phẩm'}
                                 className="img-fluid"
                              />
                           </div>
                        </Col>

                        <Col sm={7}>
                           <div className="product-detail__content d-flex flex-column justify-content-between h-100">
                              <h2 className="product-detail__content--name">{product.name}</h2>
                              <p className="product-detail__content--description">
                                 Mô tả sản phẩm: {product.description}
                              </p>
                              <p className="product-detail__content--price">
                                 Giá:{' '}
                                 {product.price.toLocaleString('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND',
                                 })}
                              </p>

                              <div className="d-flex align-items-center">
                                 <Button
                                    className="fs-5 me-2"
                                    variant="primary"
                                    disabled={quantity <= 1}
                                    onClick={() => processChangeQuantity(-1)}
                                 >
                                    <i class="bx bx-minus"></i>
                                 </Button>
                                 <Form.Group style={{ position: 'relative' }}>
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
                                    <Form.Control disabled style={{ width: '120px' }} type="number" value={quantity} />
                                 </Form.Group>
                                 <Button
                                    className="fs-5 ms-2"
                                    variant="primary"
                                    onClick={() => processChangeQuantity(1)}
                                 >
                                    <i class="bx bx-plus"></i>
                                 </Button>
                              </div>

                              <div className="product-detail__button mt-3">
                                 <Button onClick={addProductToCart}>Thêm vào giỏ hàng</Button>
                              </div>
                           </div>
                        </Col>
                     </Row>
                  </div>
               </Col>
            </Row>
         </div>
      </Container>
   );
};

export default ProductDetails;
