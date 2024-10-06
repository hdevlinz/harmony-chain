import { useCallback, useEffect, useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routeUrl } from '../../App';
import APIs, { endpoints } from '../../configs/APIConfigs';
import './Supplier.css';

const Supplier = () => {
   const [suppliers, setSuppliers] = useState([]);

   const [page, setPage] = useState(1);
   const [size] = useState(12);

   const navigate = useNavigate();

   const loadSuppliers = useCallback(async () => {
      try {
         const res = await APIs.get(endpoints.suppliers, { params: { page, size } });

         setSuppliers(res.data);
      } catch (error) {
         console.error(error);
      }
   }, [page, size]);

   useEffect(() => {
      loadSuppliers();
   }, [loadSuppliers]);

   const handleNextPage = () => setPage((prevPage) => prevPage + 1);

   const handlePrevPage = () => setPage((prevPage) => (prevPage > 1 ? prevPage - 1 : 1));

   return (
      <Container className="rating-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <h1 className="rating-container__title">Danh sách nhà cung cấp</h1>
               {suppliers.length > 0 ? (
                  <>
                     {suppliers.map((supplier) => (
                        <Col sm={12} md={4} lg={3} key={supplier.id} className="mb-3">
                           <div
                              className="supplier-card shadow-sm p-3 bg-body rounded"
                              onClick={() => navigate(routeUrl.SUPPLIER_DETAILS(supplier.id))}
                           >
                              <div className="supplier-card__item">
                                 <i className="bx bxs-id-card"></i>
                                 <span>Tên: {supplier.name}</span>
                              </div>

                              <div className="supplier-card__item">
                                 <i class="bx bx-current-location"></i>
                                 <span>Địa chỉ: {supplier.address}</span>
                              </div>

                              <div className="supplier-card__item">
                                 <i class="bx bxs-phone"></i>
                                 <span>SĐT: {supplier.phone}</span>
                              </div>

                              <div className="supplier-card__item">
                                 <i class="bx bxs-contact"></i>
                                 <span>Thông tin liên hệ: {supplier.contactInfo}</span>
                              </div>
                           </div>
                        </Col>
                     ))}
                     <div className="text-center mt-4">
                        <button className="btn-page me-2 me-3" onClick={handlePrevPage} disabled={page === 1}>
                           <i className="bx bxs-left-arrow"></i>
                        </button>
                        <button className="btn-page" onClick={handleNextPage} disabled={suppliers.length < size}>
                           <i className="bx bxs-right-arrow"></i>
                        </button>
                     </div>
                  </>
               ) : (
                  <div className="text-center mt-4">
                     <p>Không có nhà cung cấp nào</p>
                  </div>
               )}
            </Row>
         </div>
      </Container>
   );
};

export default Supplier;
