import { useCallback, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { CSSTransition } from 'react-transition-group';
import Swal from 'sweetalert2';
import APIs, { authAPI, endpoints } from '../../configs/APIConfigs';
import Loading from '../../layout/loading/Loading';
import { useUser } from '../../store/contexts/UserContext';
import { criteriaTypesName, defaultImage, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Supplier.css';

const SupplierDetails = () => {
   const { supplierId } = useParams();
   const [user] = useUser();

   const [supplierDetails, setSupplierDetails] = useState(null);
   const [ratings, setRatings] = useState([]);
   const [selectedRating, setSelectedRating] = useState(null);
   const [rating, setRating] = useState({
      rating: 1,
      content: '',
      criteria: Object.keys(criteriaTypesName)[0],
   });

   const [loading, setLoading] = useState(false);
   const [visibleRatingsCount, setVisibleRatingsCount] = useState(10);
   const [isRatingDetailsModalVisible, setIsRatingDetailsModalVisible] = useState(false);
   const [isAddRatingModalVisible, setIsAddRatingModalVisible] = useState(false);
   const [isEditRatingModalVisible, setIsEditRatingModalVisible] = useState(false);

   const loadSupplierDetails = useCallback(async () => {
      setLoading(true);
      try {
         const res = await APIs.get(endpoints.getSupplier(supplierId));
         setSupplierDetails(res.data);
      } catch (error) {
         console.error(error);
      } finally {
         setLoading(false);
      }
   }, [supplierId]);

   const loadRatings = useCallback(async () => {
      setLoading(true);
      try {
         const res = await APIs.get(endpoints.getRatingsOfSupplier(supplierId));

         const sortedRatings = res.data.sort((a, b) => b.id - a.id);
         setRatings(sortedRatings);
      } catch (error) {
         console.error(error);
      } finally {
         setLoading(false);
      }
   }, [supplierId]);

   useEffect(() => {
      loadSupplierDetails();
      loadRatings();
   }, [loadSupplierDetails, loadRatings]);

   const handleAddRating = async (e) => {
      e.preventDefault();
      setIsAddRatingModalVisible(false);
      resetRating();

      const formData = new FormData();
      Object.entries(rating).forEach(([key, value]) => {
         formData.append(key.toString(), value);
      });

      try {
         let res = await authAPI().post(endpoints.addRatingForSupplier(supplierId), formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
         });

         if (res.status === statusCode.HTTP_200_OK) {
            setRatings((prevRatings) => [res.data, ...prevRatings]);

            Toast.fire({
               icon: 'success',
               title: 'Đánh giá thành công',
               text: 'Bạn đã gửi đánh giá thành công',
            });
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Gửi đánh giá thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
      }
   };

   const handleUpdateRating = async (e) => {
      e.preventDefault();
      handleCloseEditRatingModal();

      const formData = new FormData();
      Object.entries(rating).forEach(([key, value]) => {
         formData.append(key.toString(), value);
      });

      try {
         const res = await authAPI().post(endpoints.detailsRating(rating.id), formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
         });

         if (res.status === statusCode.HTTP_200_OK) {
            setRatings((prevRatings) => prevRatings.map((r) => (r.id === rating.id ? res.data : r)));

            Toast.fire({
               icon: 'success',
               title: 'Cập nhật đánh giá thành công',
               text: 'Bạn đã cập nhật đánh giá thành công',
            });
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Cập nhật đánh giá thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
      }
   };

   const handleDeleteRating = async (e, ratingId) => {
      e.stopPropagation();
      e.preventDefault();

      Swal.fire({
         title: 'Xác nhận xóa đánh giá',
         text: 'Bạn chắc chắn muốn xóa đánh giá?',
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
               const res = await authAPI().delete(endpoints.detailsRating(ratingId));

               if (res.status === statusCode.HTTP_204_NO_CONTENT) {
                  setRatings((prevRatings) => prevRatings.filter((rating) => rating.id !== ratingId));

                  Toast.fire({
                     icon: 'success',
                     title: 'Xóa đánh giá thành công',
                     text: 'Bạn đã xóa đánh giá thành công',
                  });
               }
            } catch (error) {
               Toast.fire({
                  icon: 'error',
                  title: 'Xóa đánh giá thất bại',
                  text:
                     error?.response?.data.map((data) => data.message).join('\n') ||
                     'Hệ thống đang bận, vui lòng thử lại sau',
               });
            }
         }
      });
   };

   const updateRating = (field, value) => {
      setRating({ ...rating, [field]: value });
   };

   const resetRating = () => {
      setRating({
         rating: 1,
         content: '',
         criteria: Object.keys(criteriaTypesName)[0],
      });
   };

   const handleShowMoreRating = () => {
      setVisibleRatingsCount((prevCount) => {
         const newCount = prevCount + 10;
         return newCount >= ratings.length ? ratings.length : newCount;
      });
   };

   const handleShowRatingDetailsModal = (rating) => {
      setSelectedRating(rating);
      setIsRatingDetailsModalVisible(true);
   };

   const handleCloseRatingDetailsModal = () => {
      setSelectedRating(null);
      setIsRatingDetailsModalVisible(false);
   };

   const handleShowEditRatingModal = (e, rating) => {
      e.stopPropagation();
      setRating({
         ...rating,
         id: rating.id,
         rating: rating.rating,
         content: rating.content,
         criteria: rating.criteria,
      });
      setIsEditRatingModalVisible(true);
   };

   const handleCloseEditRatingModal = () => {
      setIsEditRatingModalVisible(false);
      resetRating();
   };

   const renderStars = (rating) => {
      const stars = [];
      for (let i = 0; i < 5; i++) {
         stars.push(
            <i
               key={i}
               className={`bx bxs-star ${i < rating ? 'filled' : ''}`}
               style={{ color: i < rating ? 'var(--primary-color)' : '#ddd' }}
            />,
         );
      }
      return stars;
   };

   if (!supplierDetails) return <Loading />;

   return (
      <Container className="rating-details-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={12}>
                  <h1 className="rating-details-title mb-4 mt-3">CHI TIẾT NHÀ CUNG CẤP</h1>
                  <CSSTransition in={!loading} timeout={1000} classNames="fade" unmountOnExit>
                     <Container className="container-rating">
                        <div className="rating-details-card">
                           <div className="rating-details-card__content">
                              <i className="bx bxs-id-card"></i>
                              <h2 className="rating-details-card__name h1">Tên nhà cung cấp: {supplierDetails.name}</h2>
                           </div>

                           <div className="rating-details-card__content">
                              <i className="bx bxs-phone"></i>
                              <h2 className="rating-details-card__name h1">Số điện thoại: {supplierDetails.phone}</h2>
                           </div>

                           <div className="rating-details-card__content">
                              <i className="bx bx-current-location"></i>
                              <h2 className="rating-details-card__name h1">Địa chỉ: {supplierDetails.address}</h2>
                           </div>

                           <div className="rating-details-card__content">
                              <i className="bx bxs-contact"></i>
                              <h2 className="rating-details-card__name h1">
                                 Thông tin liên hệ: {supplierDetails.contactInfo}
                              </h2>
                           </div>
                        </div>
                     </Container>
                  </CSSTransition>

                  <div className="rating-user">
                     <div className="add-rating-user">
                        <h1 className="rating-user__total">Các đánh giá - {ratings.length} đánh giá</h1>
                        <i className="bx bxs-message-add" onClick={() => setIsAddRatingModalVisible(true)}></i>
                     </div>

                     <div className="rating-user__list">
                        {ratings.slice(0, visibleRatingsCount).map((rating, index) => (
                           <div
                              key={index}
                              className="rating-user__item"
                              onClick={() => handleShowRatingDetailsModal(rating)}
                           >
                              <div className="rating-user__image">
                                 <img src={rating.user.avatar || defaultImage.USER_AVATAR} alt="User Avatar" />
                              </div>

                              <div className="rating-user__content">
                                 <p>Người đánh giá: {rating.user.username}</p>
                                 <p style={{ fontStyle: 'italic' }}>Ngày đánh giá: {rating.createdAt}</p>
                                 <p>Tiêu chí: {criteriaTypesName[rating.criteria]}</p>
                                 <p>Đánh giá: {renderStars(rating.rating)}</p>
                                 <p>Nội dung: {rating.content}</p>
                              </div>

                              {user?.data?.username === rating.user.username && (
                                 <div className="rating-user__dots">
                                    <div className="rating-user__dots--hover">...</div>
                                    <div className="rating-user__dots--box">
                                       <span
                                          style={{ padding: '12px 12px 4px 12px' }}
                                          onClick={(e) => {
                                             handleShowEditRatingModal(e, rating);
                                          }}
                                       >
                                          Chỉnh sửa
                                       </span>
                                       <span
                                          style={{ padding: '4px 12px 12px 12px' }}
                                          onClick={(e) => handleDeleteRating(e, rating.id)}
                                       >
                                          Xóa
                                       </span>
                                    </div>
                                 </div>
                              )}
                           </div>
                        ))}
                     </div>

                     <div className="rating-user__actions d-flex justify-content-center">
                        {ratings.length > 10 && (
                           <>
                              {visibleRatingsCount < ratings.length && (
                                 <Button
                                    className="btn-rating-details"
                                    style={{
                                       backgroundColor: 'var(--primary-color)',
                                       border: 'none',
                                       fontWeight: 500,
                                       width: '100px',
                                    }}
                                    onClick={handleShowMoreRating}
                                 >
                                    Xem thêm
                                 </Button>
                              )}
                              {visibleRatingsCount > 10 && (
                                 <Button
                                    className="btn-rating-details"
                                    style={{
                                       backgroundColor: 'var(--primary-color)',
                                       border: 'none',
                                       fontWeight: 500,
                                       width: '100px',
                                       marginLeft: '20px',
                                    }}
                                    onClick={() => setVisibleRatingsCount(10)}
                                 >
                                    Thu gọn
                                 </Button>
                              )}
                           </>
                        )}
                     </div>
                  </div>
               </Col>
            </Row>
         </div>

         {/* Modal chi tiết đánh giá */}
         <Modal show={isRatingDetailsModalVisible} onHide={handleCloseRatingDetailsModal} centered>
            <Modal.Header closeButton>
               <Modal.Title>Chi tiết đánh giá</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               {selectedRating && (
                  <Form>
                     <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                        <img
                           alt="User Avatar"
                           style={{ width: '100px', height: '100px' }}
                           src={selectedRating.user.avatar || defaultImage.USER_AVATAR}
                        />
                     </div>
                     <Form.Group className="mb-3">
                        <Form.Label>Người đánh giá</Form.Label>
                        <Form.Control type="text" value={selectedRating.user.username || ''} readOnly />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="email" value={selectedRating.user.email || ''} readOnly />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Nội dung</Form.Label>
                        <Form.Control as="textarea" rows={3} value={selectedRating.content || ''} readOnly />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Đánh giá</Form.Label>
                        <Form.Control type="text" value={selectedRating.rating} readOnly />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Tiêu chí</Form.Label>
                        <Form.Control
                           type="text"
                           value={criteriaTypesName[selectedRating.criteria] || selectedRating.criteria || ''}
                           readOnly
                        />
                     </Form.Group>
                  </Form>
               )}
            </Modal.Body>
         </Modal>

         {/* Modal thêm đánh giá */}
         <Modal
            centered
            scrollable
            show={isAddRatingModalVisible}
            style={{ height: '520px', marginTop: '100px' }}
            onHide={() => setIsAddRatingModalVisible(false)}
         >
            <Modal.Header closeButton>
               <Modal.Title>Thêm đánh giá</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <Form>
                  <Form.Group className="mb-3">
                     <Form.Label>Tiêu chí</Form.Label>
                     <Form.Control
                        as="select"
                        value={rating.criteria}
                        onChange={(e) => updateRating('criteria', e.target.value)}
                     >
                        {Object.entries(criteriaTypesName).map(([key, value]) => (
                           <option key={key} value={key}>
                              {value}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>

                  <Form.Group className="mb-3">
                     <Form.Label>Nội dung</Form.Label>
                     <Form.Control
                        as="textarea"
                        rows={3}
                        value={rating.content}
                        onChange={(e) => updateRating('content', e.target.value)}
                     />
                  </Form.Group>

                  <Form.Group className="mb-3">
                     <Form.Label>Đánh giá</Form.Label>
                     <Form.Control
                        type="number"
                        min="1"
                        value={rating.rating}
                        onChange={(e) => updateRating('rating', e.target.value)}
                     />
                  </Form.Group>

                  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                     <Button
                        style={{
                           width: '120px',
                           border: 'none',
                           fontWeight: 500,
                           backgroundColor: 'var(--primary-color)',
                        }}
                        onClick={handleAddRating}
                        variant="primary"
                     >
                        Gửi đánh giá
                     </Button>
                  </div>
               </Form>
            </Modal.Body>
         </Modal>

         {/* Modal chỉnh sửa đánh giá */}
         <Modal
            centered
            scrollable
            show={isEditRatingModalVisible}
            onHide={handleCloseEditRatingModal}
            style={{ height: '520px', marginTop: '100px' }}
         >
            <Modal.Header closeButton>
               <Modal.Title>Chỉnh sửa đánh giá</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <Form>
                  <Form.Group className="mb-3">
                     <Form.Label>Tiêu chí</Form.Label>
                     <Form.Control
                        as="select"
                        value={rating.criteria}
                        onChange={(e) => updateRating('criteria', e.target.value)}
                     >
                        {Object.entries(criteriaTypesName).map(([key, value]) => (
                           <option key={key} value={key}>
                              {value}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>

                  <Form.Group className="mb-3">
                     <Form.Label>Nội dung</Form.Label>
                     <Form.Control
                        as="textarea"
                        rows={3}
                        value={rating.content}
                        onChange={(e) => updateRating('content', e.target.value)}
                     />
                  </Form.Group>

                  <Form.Group className="mb-3">
                     <Form.Label>Đánh giá</Form.Label>
                     <Form.Control
                        type="number"
                        min="1"
                        max="5"
                        value={rating.rating}
                        onChange={(e) => updateRating('rating', Number(e.target.value))}
                     />
                  </Form.Group>

                  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                     <Button
                        onClick={handleUpdateRating}
                        variant="primary"
                        style={{
                           width: '120px',
                           border: 'none',
                           fontWeight: 500,
                           backgroundColor: 'var(--primary-color)',
                        }}
                     >
                        Cập nhật
                     </Button>
                  </div>
               </Form>
            </Modal.Body>
         </Modal>
      </Container>
   );
};

export default SupplierDetails;
