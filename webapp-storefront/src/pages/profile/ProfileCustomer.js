import moment from 'moment';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';

const profile = ({ profile, updateFunc, processFunc }) => {
   return (
      <Container className="profile-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={12}>
                  <div className="profile-content">
                     <h3 className="text-white p-3 product-content__title">Thông tin cá nhân</h3>
                  </div>
                  <Row>
                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Họ</Form.Label>
                           <Form.Control
                              type="text"
                              name="lastName"
                              value={profile?.lastName}
                              placeholder="Nhập họ..."
                              onChange={(e) => processFunc('lastName', e.target.value)}
                           />
                        </Form.Group>
                     </Col>

                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Tên đệm</Form.Label>
                           <Form.Control
                              type="text"
                              name="middleName"
                              value={profile?.middleName}
                              placeholder="Nhập tên đệm..."
                              onChange={(e) => processFunc('middleName', e.target.value)}
                           />
                        </Form.Group>
                     </Col>

                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Tên</Form.Label>
                           <Form.Control
                              type="text"
                              name="firstName"
                              value={profile?.firstName}
                              placeholder="Nhập tên..."
                              onChange={(e) => processFunc('firstName', e.target.value)}
                           />
                        </Form.Group>
                     </Col>
                  </Row>

                  <Form.Group className="mb-3">
                     <Form.Label>Địa chỉ</Form.Label>
                     <Form.Control
                        type="text"
                        name="address"
                        value={profile?.address}
                        placeholder="Nhập địa chỉ..."
                        onChange={(e) => processFunc('address', e.target.value)}
                     />
                  </Form.Group>

                  <Row>
                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Số điện thoại</Form.Label>
                           <Form.Control
                              type="tel"
                              name="phone"
                              value={profile?.phone}
                              placeholder="Nhập số điện thoại..."
                              onChange={(e) => processFunc('phone', e.target.value)}
                           />
                        </Form.Group>
                     </Col>

                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Giới tính</Form.Label>
                           <Form.Select
                              name="gender"
                              value={profile?.gender ? '1' : '0'}
                              onChange={(e) => processFunc('gender', e.target.value === '1')}
                           >
                              <option value="1">Nữ</option>
                              <option value="0">Nam</option>
                           </Form.Select>
                        </Form.Group>
                     </Col>

                     <Col sm={4}>
                        <Form.Group className="mb-3">
                           <Form.Label>Ngày sinh</Form.Label>
                           <Form.Control
                              type="date"
                              value={moment(profile.dateOfBirth, 'DD-MM-YYYY').format('YYYY-MM-DD')}
                              onChange={(e) => {
                                 const selectedDate = moment(e.target.value, 'YYYY-MM-DD').format('DD-MM-YYYY');
                                 processFunc('dateOfBirth', selectedDate);
                              }}
                           />
                        </Form.Group>
                     </Col>
                  </Row>

                  <div className="text-center mt-3">
                     <Button
                        onClick={updateFunc}
                        variant="primary"
                        style={{
                           backgroundColor: 'var(--primary-color)',
                           border: 'none',
                           color: 'white',
                           fontWeight: 500,
                        }}
                     >
                        Cập nhật hồ sơ
                     </Button>
                  </div>
               </Col>
            </Row>
         </div>
      </Container>
   );
};

export default profile;
