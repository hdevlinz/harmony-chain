import { Chip } from '@mui/material';
import { useCallback, useEffect, useState } from 'react';
import { Col, Container, Form, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routeUrl } from '../../_app';
import APIs, { authAPI, endpoints } from '../../configs/APIConfigs';
import { useCart } from '../../store/contexts/CartContext';
import { UPDATE_CART } from '../../store/reducers/CartReducer';
import { defaultImage, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Product.css';

const Product = () => {
   const [cart, dispatch] = useCart();

   const [products, setProducts] = useState([]);
   const [categories, setCategories] = useState([]);
   const [units, setUnits] = useState([]);
   const [tags, setTags] = useState([]);

   const [page, setPage] = useState(1);
   const [size] = useState(12);
   const [name, setName] = useState('');
   const [fromPrice, setFromPrice] = useState('');
   const [toPrice, setToPrice] = useState('');
   const [category, setCategory] = useState('');
   const [unit, setUnit] = useState('');
   const [selectedTags, setSelectedTags] = useState([]);

   const navigate = useNavigate();

   const loadProducts = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.products, {
            params: { page, size, name, fromPrice, toPrice, category, unit, tags: selectedTags.join(',') },
         });

         setProducts(res.data);
      } catch (error) {
         console.error('Error loading products:', error);
      }
   }, [page, size, name, fromPrice, toPrice, category, unit, selectedTags]);

   const loadCategories = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.categories);

         setCategories(res.data);
      } catch (error) {
         console.error('Error loading categories:', error);
      }
   }, []);

   const loadUnits = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.units);

         setUnits(res.data);
      } catch (error) {
         console.error('Error loading units:', error);
      }
   }, []);

   const loadTags = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.tags);

         setTags(res.data);
      } catch (error) {
         console.error('Error loading tags:', error);
      }
   }, []);

   useEffect(() => {
      loadProducts();
   }, [loadProducts]);

   useEffect(() => {
      loadCategories();
      loadUnits();
      loadTags();
   }, [loadCategories, loadUnits, loadTags]);

   const handleNextPage = () => setPage((prevPage) => prevPage + 1);

   const handlePrevPage = () => setPage((prevPage) => (prevPage > 1 ? prevPage - 1 : 1));

   const addProductToCart = async (product) => {
      const newCart = { ...cart };

      if (newCart[product.id]) {
         newCart[product.id].quantity++;
      } else {
         newCart[product.id] = {
            quantity: 1,
            unitPrice: product.price,
            product: product,
         };
      }

      dispatch({
         type: UPDATE_CART,
         payload: newCart,
      });

      try {
         const res = await authAPI().post(endpoints.addProductToCart, { productId: product.id, quantity: 1 });

         if (res.status === statusCode.HTTP_200_OK) {
            Toast.fire({
               icon: 'success',
               title: 'Thành công!',
               text: `Thêm sản phẩm ${product.name} vào giỏ hàng thành công.`,
            });
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Thất bại!',
            text: `Thêm sản phẩm ${product.name} vào giỏ hàng thất bại.`,
         });
         console.error('Thêm sản phẩm vào giỏ hàng thất bại:', error);
      }
   };

   const handleEventChange = useCallback((event, callback) => {
      callback(event.target.value);
      setPage(1);
   }, []);

   const handleTagChange = (tagId) => {
      setSelectedTags((prevTags) =>
         prevTags.includes(tagId) ? prevTags.filter((id) => id !== tagId) : [...prevTags, tagId],
      );
      setPage(1);
   };

   return (
      <Container className="product-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={3}>
                  <div className="title-filter">
                     <h3 className="title-filter">Bộ lọc nâng cao</h3>
                  </div>

                  <div className="filter">
                     <div className="filter__title">
                        <i className="bx bxs-category"></i>
                        <h3 className="filter__title--main">Danh mục</h3>
                     </div>

                     <div className="filter-category__dropdown mt-4">
                        <select
                           className="product__search--all"
                           value={category}
                           onChange={(e) => handleEventChange(e, setCategory)}
                        >
                           <option value="">Tất cả danh mục</option>
                           {categories.map((category) => (
                              <option key={category.id} value={category.id} className="short-option">
                                 {category.name}
                              </option>
                           ))}
                        </select>
                     </div>
                  </div>

                  <div className="filter mt-4">
                     <div className="filter__title">
                        <i class="bx bxs-package"></i>
                        <h3 className="filter__title--main">Đơn vị</h3>
                     </div>

                     <div className="filter__dropdown mt-4">
                        <select
                           className="product__search--all"
                           value={unit}
                           onChange={(e) => handleEventChange(e, setUnit)}
                        >
                           <option value="">Tất cả đơn vị</option>
                           {units.map((unit) => (
                              <option key={unit.id} value={unit.id}>
                                 {unit.name}
                              </option>
                           ))}
                        </select>
                     </div>
                  </div>

                  <div className="filter mt-4">
                     <div className="filter__title">
                        <i className="bx bx-money"></i>
                        <h3 className="filter__title--main">Giá</h3>
                     </div>

                     <Form.Group className="mb-3 mt-3">
                        <Form.Label>Từ</Form.Label>
                        <Form.Control
                           type="number"
                           value={fromPrice}
                           placeholder="Nhập giá..."
                           onChange={(e) => handleEventChange(e, setFromPrice)}
                        />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Đến</Form.Label>
                        <Form.Control
                           type="number"
                           value={toPrice}
                           placeholder="Nhập giá..."
                           onChange={(e) => handleEventChange(e, setToPrice)}
                        />
                     </Form.Group>
                  </div>

                  <div className="filter mt-4">
                     <div className="filter__title">
                        <i className="bx bxs-purchase-tag"></i>
                        <h3 className="filter__title--main">Nhãn</h3>
                     </div>

                     <div className="filter__tag--dropdown mt-4">
                        {tags.map((tag) => (
                           <Chip
                              key={tag.id}
                              label={tag.name}
                              color={selectedTags.includes(tag.id) ? 'primary' : 'default'}
                              onClick={() => handleTagChange(tag.id)}
                              onDelete={selectedTags.includes(tag.id) ? () => handleTagChange(tag.id) : undefined}
                              style={{ margin: '4px' }}
                           />
                        ))}
                     </div>
                  </div>
               </Col>

               <Col sm={9}>
                  <Row>
                     <div className="product__search">
                        <input
                           type="text"
                           className="product__search--input"
                           placeholder="Nhập tên sản phẩm..."
                           onChange={(e) => handleEventChange(e, setName)}
                           value={name}
                        />
                     </div>
                     {products.length > 0 ? (
                        <>
                           <div className="row">
                              {products.map((product) => (
                                 <Col sm={3} key={product.id} className="mb-4">
                                    <div className="product-card">
                                       <div
                                          className="product-card__image"
                                          style={{ cursor: 'pointer' }}
                                          onClick={() => navigate(routeUrl.PRODUCT_DETAILS(product.id))}
                                       >
                                          <img
                                             src={product.image ? product.image : defaultImage.PRODUCT_IMAGE}
                                             alt={product.name || 'Ảnh sản phẩm'}
                                          />
                                       </div>

                                       <div
                                          className="product-card__content"
                                          style={{ cursor: 'pointer' }}
                                          onClick={() => navigate(routeUrl.PRODUCT_DETAILS(product.id))}
                                       >
                                          <h2 className="product-card__content--title">{product.name}</h2>
                                          <p className="product-card__content--des">Mô tả: {product.description}</p>
                                          <span>
                                             Giá:{' '}
                                             {product.price.toLocaleString('vi-VN', {
                                                style: 'currency',
                                                currency: 'VND',
                                             })}
                                          </span>
                                       </div>

                                       <div className="product-card__button">
                                          <button
                                             onClick={(e) => {
                                                e.stopPropagation();
                                                addProductToCart(product);
                                             }}
                                          >
                                             <i className="bx bxs-cart-add"></i>
                                          </button>
                                       </div>
                                    </div>
                                 </Col>
                              ))}
                           </div>

                           <div className="text-center mt-4">
                              <button className="btn-page me-2 me-3" onClick={handlePrevPage} disabled={page === 1}>
                                 <i className="bx bxs-left-arrow"></i>
                              </button>
                              <button className="btn-page" onClick={handleNextPage} disabled={products.length < size}>
                                 <i className="bx bxs-right-arrow"></i>
                              </button>
                           </div>
                        </>
                     ) : (
                        <div className="text-center mt-4">
                           <p>Không có sản phẩm nào</p>
                        </div>
                     )}
                  </Row>
               </Col>
            </Row>
         </div>
      </Container>
   );
};

export default Product;
