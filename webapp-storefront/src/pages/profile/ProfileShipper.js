import { Button, Col, Container, Form, Row } from 'react-bootstrap';

const ProfileShipper = ({ profile, updateFunc, processFunc }) => {
   return (
      <Container className="profile-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={12}>
                  <div className="profile-content">
                     <h3 className="text-white p-3 product-content__title">Thông tin cá nhân</h3>
                  </div>
                  <Row>
                     <Col sm={6}>
                        <Form.Group className="mb-3">
                           <Form.Label>Tên</Form.Label>
                           <Form.Control
                              type="text"
                              name="name"
                              value={profile?.name}
                              onChange={(e) => processFunc('name', e.target.value)}
                           />
                        </Form.Group>
                     </Col>

                     <Col sm={6}>
                        <Form.Group className="mb-3">
                           <Form.Label>Rating</Form.Label>
                           <Form.Control type="tel" name="phone" disabled value={profile?.rating} />
                        </Form.Group>
                     </Col>
                  </Row>

                  <Form.Group className="mb-3">
                     <Form.Label>Thông tin liên hệ</Form.Label>
                     <Form.Control
                        type="tel"
                        name="contactInfo"
                        value={profile?.contactInfo}
                        onChange={(e) => processFunc('contactInfo', e.target.value)}
                     />
                  </Form.Group>

                  <div className="text-center mt-3">
                     <Button
                        variant="primary"
                        style={{
                           backgroundColor: 'var(--primary-color)',
                           border: 'none',
                           color: 'white',
                           fontWeight: 500,
                        }}
                        onClick={updateFunc}
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

export default ProfileShipper;
