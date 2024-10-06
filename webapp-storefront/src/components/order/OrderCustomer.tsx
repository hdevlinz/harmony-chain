import React, { useCallback, useEffect, useState } from 'react';
import { Button, Card, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import { authAPI, endpoints } from '../../configs/APIConfigs';
import { orderStatusName, orderTypesName, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Order.css';

const OrderCustomer = () => {
   const [orders, setOrders] = useState([]);
   const [page, setPage] = useState(1);
   const [size] = useState(9);
   const [showModal, setShowModal] = useState(false);
   const [currentOrder, setCurrentOrder] = useState(null);
   const [type, setType] = useState('');
   const [status, setStatus] = useState('');

   const loadOrders = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.orders, {
            params: { page, size, type, status },
         });

         setOrders(res.data);
      } catch (error) {
         console.error(error);
      }
   }, [page, size, type, status]);

   useEffect(() => {
      loadOrders();
   }, [loadOrders]);

   const handleCardClick = (order) => {
      setCurrentOrder(order);
      setShowModal(true);
   };

   const handleCancelOrder = async (e) => {
      e.preventDefault();

      try {
         const res = await authAPI().patch(endpoints.cancelOrder(currentOrder.id));

         if (res.status === statusCode.HTTP_200_OK) {
            Toast.fire({
               icon: 'success',
               title: 'Hủy đơn hàng thành công',
            });
            loadOrders();
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Hủy đơn hàng thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
      }
   };

   const handleCloseModal = () => setShowModal(false);

   const handleFilterTypeChange = (e) => setType(e.target.value);

   const handleFilterStatusChange = (e) => setStatus(e.target.value);

   const handleNextPage = () => setPage((prevPage) => prevPage + 1);

   const handlePrevPage = () => setPage((prevPage) => (prevPage > 1 ? prevPage - 1 : 1));

   return (
      <Container className="order-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <h1 className="order__title mt-4 mb-4">Đơn hàng của bạn</h1>
            <Row className="mb-3">
               <Col md={6}>
                  <Form.Group controlId="filterType">
                     <Form.Label
                        style={{
                           fontSize: '1.125rem',
                           color: 'var(--text-color)',
                           fontWeight: '500',
                        }}
                     >
                        Lọc theo loại
                     </Form.Label>
                     <Form.Control as="select" value={type} onChange={handleFilterTypeChange}>
                        <option value="">Tất cả loại</option>
                        {Object.entries(orderTypesName).map(([key, value]) => (
                           <option key={key} value={key}>
                              {value}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>
               </Col>
               <Col md={6}>
                  <Form.Group controlId="filterStatus">
                     <Form.Label
                        style={{
                           fontSize: '1.125rem',
                           color: 'var(--text-color)',
                           fontWeight: '500',
                        }}
                     >
                        Lọc theo trạng thái
                     </Form.Label>
                     <Form.Control as="select" value={status} onChange={handleFilterStatusChange}>
                        <option value="">Tất cả trạng thái</option>
                        {Object.entries(orderStatusName).map(([key, value]) => (
                           <option key={key} value={key}>
                              {value}
                           </option>
                        ))}
                     </Form.Control>
                  </Form.Group>
               </Col>
            </Row>
            <Row>
               {orders.map((order) => (
                  <Col sm={12} md={4} key={order.orderNumber} className="mb-3">
                     <Card className="order__card" onClick={() => handleCardClick(order)}>
                        <Card.Body>
                           <Card.Title className="order__title--number text-nowrap">
                              Đơn hàng:
                              <br />
                              {order.orderNumber}
                           </Card.Title>
                           <Card.Text className="order__title--content">Ngày đặt hàng: {order.orderDate}</Card.Text>
                           <Card.Text className="order__title--content">
                              Thời gian giao hàng: {order.expectedDelivery || 'Chưa cập nhật'}
                           </Card.Text>
                           <Card.Text className="order__title--content">
                              Trạng thái: {orderStatusName[order.status] || 'Trạng thái không xác định'}
                           </Card.Text>
                        </Card.Body>
                     </Card>
                  </Col>
               ))}
               <div className="text-center mt-4">
                  <button className="btn-page me-2 me-3" onClick={handlePrevPage} disabled={page === 1}>
                     <i className="bx bxs-left-arrow"></i>
                  </button>
                  <button className="btn-page" onClick={handleNextPage} disabled={orders.length < size}>
                     <i className="bx bxs-right-arrow"></i>
                  </button>
               </div>
            </Row>
         </div>

         <Modal show={showModal} onHide={handleCloseModal} className="custom-modal">
            <Modal.Header closeButton>
               <Modal.Title>Chi tiết đơn hàng</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               {currentOrder && (
                  <>
                     <p>
                        <strong>Mã đơn hàng:</strong> {currentOrder.orderNumber}
                     </p>
                     <p>
                        <strong>Mã hóa đơn:</strong> {currentOrder.invoiceNumber}
                     </p>
                     <p>
                        <strong>Ngày đặt hàng:</strong> {currentOrder.orderDate}
                     </p>
                     <p>
                        <strong>Thời gian giao hàng:</strong> {currentOrder.expectedDelivery || 'Chưa cập nhật'}
                     </p>
                     <p>
                        <strong>Trạng thái:</strong>{' '}
                        {orderStatusName[currentOrder.status] || 'Trạng thái không xác định'}
                     </p>
                     <hr />
                     <h5>Danh sách sản phẩm:</h5>
                     {currentOrder.orderDetailsSet.length > 0 ? (
                        <ul>
                           {currentOrder.orderDetailsSet.map((detail) => (
                              <li key={detail.id}>
                                 <p>
                                    <strong>Sản phẩm:</strong> {detail.product.name}
                                 </p>
                                 <p>
                                    <strong>Mô tả:</strong> {detail.product.description}
                                 </p>
                                 <p>
                                    <strong>Số lượng:</strong> {detail.quantity}
                                 </p>
                                 <p>
                                    <strong>Giá mỗi đơn vị:</strong> {detail.unitPrice.toLocaleString()} VND
                                 </p>
                                 <hr />
                              </li>
                           ))}
                        </ul>
                     ) : (
                        <p>Không có sản phẩm nào.</p>
                     )}
                  </>
               )}
            </Modal.Body>
            <Modal.Footer>
               <Button variant="secondary" onClick={handleCloseModal}>
                  Đóng
               </Button>
               <Button variant="primary" onClick={handleCancelOrder}>
                  Hủy đơn hàng
               </Button>
            </Modal.Footer>
         </Modal>
      </Container>
   );
};

export default OrderCustomer;
