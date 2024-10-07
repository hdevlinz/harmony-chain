import { useCallback, useEffect, useState } from 'react';
import { Button, Card, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import { authAPI, endpoints } from '../../lib/http';
import { useUser } from '../../store/contexts/UserContext';
import { orderStatusName, orderTypes, orderTypesName, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';

const OrderSupplier = () => {
   const [user] = useUser();

   const [orders, setOrders] = useState([]);
   const [selectedOrder, setSelectedOrder] = useState(null);
   const [page, setPage] = useState(1);
   const [size] = useState(9);

   const [showModal, setShowModal] = useState(false);
   const [selectedStatus, setSelectedStatus] = useState('');
   const [type, setType] = useState('');
   const [status, setStatus] = useState('');

   const loadOrdersSupplier = useCallback(async () => {
      try {
         const res = await authAPI().get(endpoints.getOrdersOfSupplier(user?.profile?.id), {
            params: { page, size, type, status },
         });

         setOrders(res.data);
      } catch (error) {
         console.error(error);
      }
   }, [user?.profile?.id, page, size, type, status]);

   useEffect(() => {
      loadOrdersSupplier();
   }, [loadOrdersSupplier]);

   const updateOrderStatus = async () => {
      try {
         const res = await authAPI().patch(endpoints.updateStatusOrder(selectedOrder.id), {
            status: selectedStatus,
         });

         if (res.status === statusCode.HTTP_200_OK) {
            Toast.fire({
               icon: 'success',
               title: 'Cập nhật thành công',
               text: 'Trạng thái đơn hàng đã được cập nhật thành công',
            });

            loadOrdersSupplier();
            setShowModal(false);
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Không thể cập nhật trạng thái đơn hàng',
         });
      }
   };

   const handleCardClick = (order) => {
      setSelectedStatus(order.status);
      setSelectedOrder(order);
      setShowModal(true);
   };

   const handleFilterTypeChange = (e) => {
      setType(e.target.value);
      setPage(1);
   };

   const handleFilterStatusChange = (e) => {
      setStatus(e.target.value);
      setPage(1);
   };

   const handleNextPage = () => setPage((prevPage) => prevPage + 1);

   const handlePrevPage = () => setPage((prevPage) => (prevPage > 1 ? prevPage - 1 : 1));

   return (
      <Container className="order-supplier-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <h1 className="order-supplier__title mb-5 mt-4">Xử lý đơn hàng</h1>
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
                              Loại: {orderTypesName[order.type] || 'Trạng thái không xác định'}
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

         <Modal show={showModal} onHide={() => setShowModal(false)} className="custom-modal">
            <Modal.Header closeButton>
               <Modal.Title>Chi tiết đơn hàng</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               {selectedOrder && (
                  <>
                     <p>
                        <strong>Mã đơn hàng:</strong> {selectedOrder.orderNumber}
                     </p>
                     <p>
                        <strong>Mã hóa đơn:</strong> {selectedOrder.invoiceNumber}
                     </p>
                     <p>
                        <strong>Ngày đặt hàng:</strong> {selectedOrder.orderDate}
                     </p>
                     <p>
                        <strong>Thời gian giao hàng:</strong> {selectedOrder.expectedDelivery || 'Chưa cập nhật'}
                     </p>
                     <p>
                        <strong>Loại: </strong>
                        {orderTypesName[selectedOrder.type] || 'Trạng thái không xác định'}
                     </p>
                     <Form.Group controlId="formOrderStatus">
                        <Form.Label>Trạng thái đơn hàng</Form.Label>
                        <Form.Control
                           disabled={selectedOrder.type === orderTypes.INBOUND}
                           as="select"
                           value={selectedStatus}
                           onChange={(e) => setSelectedStatus(e.target.value)}
                        >
                           {Object.entries(orderStatusName).map(([key, value]) => (
                              <option key={key} value={key}>
                                 {value}
                              </option>
                           ))}
                        </Form.Control>
                     </Form.Group>
                     <hr />
                     <h5>Danh sách sản phẩm:</h5>
                     {selectedOrder.orderDetailsSet.length > 0 ? (
                        <ul>
                           {selectedOrder.orderDetailsSet.map((detail) => (
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
               <Button variant="secondary" onClick={() => setShowModal(false)}>
                  Đóng
               </Button>
               {selectedOrder && selectedOrder.type !== orderTypes.INBOUND && (
                  <Button variant="primary" onClick={updateOrderStatus}>
                     Cập nhật
                  </Button>
               )}
            </Modal.Footer>
         </Modal>
      </Container>
   );
};

export default OrderSupplier;
