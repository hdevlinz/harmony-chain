import { Chip } from '@mui/material';
import { useCallback, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import Swal from 'sweetalert2';
import { authAPI, endpoints } from '../../configs/APIConfigs';
import { useUser } from '../../store/contexts/UserContext';
import { defaultImage, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Product.css';

const ProductSupplier = () => {
   const [user] = useUser();

   const [products, setProducts] = useState([]);
   const [categories, setCategories] = useState([]);
   const [units, setUnits] = useState([]);
   const [tags, setTags] = useState([]);

   const [showModal, setShowModal] = useState(false);
   const [newProduct, setNewProduct] = useState({
      name: '',
      description: '',
      price: '',
      expiryDate: '',
      unit: '',
      category: '',
      tags: [],
   });

   const [page, setPage] = useState(1);
   const [size] = useState(12);
   const [name, setName] = useState('');
   const [fromPrice, setFromPrice] = useState('');
   const [toPrice, setToPrice] = useState('');
   const [category, setCategory] = useState('');
   const [unit, setUnit] = useState('');
   const [selectedTags, setSelectedTags] = useState([]);

   const loadProducts = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.getProductsOfSupplier(user?.profile?.id), {
            params: { page, size, name, fromPrice, toPrice, category, unit, tags: selectedTags.join(',') },
         });

         setProducts(res.data);
      } catch (error) {
         console.error('Error loading products:', error);
      }
   }, [user?.profile?.id, page, size, name, fromPrice, toPrice, category, unit, selectedTags]);

   const loadCategories = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.categories);

         setCategories(res.data);
      } catch (error) {
         console.error('Error loading categories:', error);
      }
   }, []);

   const loadUnits = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.units);

         setUnits(res.data);
      } catch (error) {
         console.error('Error loading units:', error);
      }
   }, []);

   const loadTags = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.tags);

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

   const handlePublishProduct = async (e) => {
      e.preventDefault();

      const formData = new FormData();
      Object.entries(newProduct).forEach(([key, value]) => {
         if (key === 'tags') {
            formData.append('tags', newProduct.tags.join(','));
         } else {
            formData.append(key.toString(), value);
         }
      });

      try {
         let res = await authAPI().post(endpoints.publishProduct, formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
         });

         if (res.status === statusCode.HTTP_200_OK) {
            setShowModal(false);
            resetNewProduct();
            loadProducts();

            Swal.fire({
               icon: 'success',
               title: 'Thành công!',
               text: 'Thêm sản phẩm thành công.',
               confirmButtonText: 'OK',
            });
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Thêm sản phẩm thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
      }
   };

   const handleUnpublishProduct = async (productId) => {
      Swal.fire({
         title: 'Xác nhận ẩn sản phẩm',
         text: 'Bạn chắc chắn muốn ẩn sản phẩm này?',
         icon: 'warning',
         showCancelButton: true,
         confirmButtonText: 'Có',
         cancelButtonText: 'Không',
         customClass: {
            confirmButton: 'swal2-confirm',
         },
      }).then(async (result) => {
         if (result.isConfirmed) {
            try {
               const res = await authAPI().delete(endpoints.unpublishProduct(productId));

               if (res.status === statusCode.HTTP_204_NO_CONTENT) {
                  loadProducts();

                  Toast.fire({
                     icon: 'success',
                     title: 'Ẩn sản phẩm thành công',
                     text: 'Bạn đã ẩn sản phẩm thành công',
                  });
               }
            } catch (error) {
               Toast.fire({
                  icon: 'error',
                  title: 'Ẩn sản phẩm thất bại',
                  text:
                     error?.response?.data.map((data) => data.message).join('\n') ||
                     'Hệ thống đang bận, vui lòng thử lại sau',
               });
            }
         }
      });
   };

   const handleNewProductChange = (e) => {
      const { name, value } = e.target;
      setNewProduct((prev) => ({ ...prev, [name]: value }));
   };

   const resetNewProduct = () => {
      setNewProduct({
         name: '',
         description: '',
         price: '',
         expiryDate: '',
         unit: '',
         category: '',
         tags: [],
      });
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

   const handleTagSelection = (tagId) => {
      setNewProduct((prev) => ({
         ...prev,
         tags: prev.tags.includes(tagId) ? prev.tags.filter((id) => id !== tagId) : [...prev.tags, tagId],
      }));
   };

   const handleNextPage = () => setPage((prevPage) => prevPage + 1);

   const handlePrevPage = () => setPage((prevPage) => (prevPage > 1 ? prevPage - 1 : 1));

   return (
      <Container className="product-supplier-container">
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
                        <i className="bx bxs-package"></i>
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
                     <h1 className="product-supplier-container--title mb-3">Sản phẩm của bạn</h1>
                     <div className="product__search">
                        <input
                           type="text"
                           className="product__search--input"
                           placeholder="Nhập tên sản phẩm..."
                           onChange={(e) => handleEventChange(e, setName)}
                           value={name}
                        />
                     </div>
                     <div className="add-product" onClick={() => setShowModal(true)}>
                        <i className="bx bxs-layer-plus"></i>
                     </div>
                     {products.length > 0 ? (
                        <>
                           <div className="row">
                              {products.map((product) => (
                                 <Col sm={3} key={product.id} className="mb-4">
                                    <div className="product-card">
                                       <div className="product-card__image" style={{ cursor: 'pointer' }}>
                                          <img
                                             src={product.image ? product.image : defaultImage.PRODUCT_IMAGE}
                                             alt={product.name || 'Ảnh sản phẩm'}
                                          />
                                       </div>

                                       <div className="product-card__content" style={{ cursor: 'pointer' }}>
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

                                       <div className="product-card-unpublish">
                                          <i
                                             onClick={() => handleUnpublishProduct(product.id)}
                                             class="bx bxs-low-vision"
                                          ></i>
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

         {/* Modal for adding product */}
         <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
               <Modal.Title>Thêm Sản Phẩm Mới</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <Form>
                  <Form.Group className="mb-3">
                     <Form.Label>Tên sản phẩm</Form.Label>
                     <Form.Control
                        type="text"
                        name="name"
                        value={newProduct.name}
                        onChange={handleNewProductChange}
                        placeholder="Nhập tên sản phẩm"
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Mô tả</Form.Label>
                     <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        value={newProduct.description}
                        onChange={handleNewProductChange}
                        placeholder="Nhập mô tả"
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Giá</Form.Label>
                     <Form.Control
                        type="number"
                        name="price"
                        value={newProduct.price}
                        onChange={handleNewProductChange}
                        placeholder="Nhập giá"
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Ngày hết hạn</Form.Label>
                     <Form.Control
                        type="date"
                        name="expiryDate"
                        value={newProduct.expiryDate}
                        onChange={handleNewProductChange}
                     />
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Đơn vị</Form.Label>
                     <Form.Control as="select" name="unit" value={newProduct.unit} onChange={handleNewProductChange}>
                        <option value="">Chọn đơn vị</option>
                        {units.map((unit) => (
                           <option key={unit.id} value={unit.id}>
                              {unit.name}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Danh mục</Form.Label>
                     <Form.Control
                        as="select"
                        name="category"
                        value={newProduct.category}
                        onChange={handleNewProductChange}
                     >
                        <option value="">Chọn danh mục</option>
                        {categories.map((category) => (
                           <option key={category.id} value={category.id}>
                              {category.name}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>
                  <Form.Group className="mb-3">
                     <Form.Label>Nhãn</Form.Label>
                     <div className="filter__tag--dropdown">
                        {tags.map((tag) => (
                           <Chip
                              key={tag.id}
                              label={tag.name}
                              color={newProduct.tags.includes(tag.id) ? 'primary' : 'default'}
                              onClick={() => handleTagSelection(tag.id)}
                              style={{ margin: '4px' }}
                           />
                        ))}
                     </div>
                  </Form.Group>
               </Form>
            </Modal.Body>
            <Modal.Footer>
               <Button variant="secondary" onClick={() => setShowModal(false)}>
                  Đóng
               </Button>
               <Button
                  style={{
                     border: 'none',
                     backgroundColor: 'var(--primary-color)',
                  }}
                  variant="primary"
                  onClick={handlePublishProduct}
               >
                  Thêm sản phẩm
               </Button>
            </Modal.Footer>
         </Modal>
      </Container>
   );
};

export default ProductSupplier;
