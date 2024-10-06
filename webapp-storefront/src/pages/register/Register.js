import _ from 'lodash';
import { useMemo, useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Swal from 'sweetalert2';
import { routeUrl } from '../../App';
import APIs, { endpoints } from '../../configs/APIConfigs';
import { roles, rolesName, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';

const Register = () => {
   const [user, setUser] = useState({ userRole: roles.CUSTOMER });
   const [errors, setErrors] = useState({});

   const [q] = useSearchParams();
   const nav = useNavigate();

   const requiredFields = useMemo(() => {
      return [
         { key: 'userRole', message: 'Vai trò người dùng không được bỏ trống' },
         { key: 'email', message: 'Email không được bỏ trống' },
         { key: 'username', message: 'Tên đăng nhập không được bỏ trống' },
         { key: 'password', message: 'Mật khẩu không được bỏ trống' },
      ];
   }, []);
   const customerFields = useMemo(() => {
      return [
         { key: 'firstName', message: 'Họ không được bỏ trống' },
         { key: 'middleName', message: 'Tên đệm không được bỏ trống' },
         { key: 'lastName', message: 'Tên không được bỏ trống' },
      ];
   }, []);
   const supplierAndShipperFields = useMemo(
      () => (name) => {
         return [
            { key: 'name', message: `Tên ${name} không được bỏ trống` },
            { key: 'contactInfo', message: 'Thông tin liên hệ không được bỏ trống' },
         ];
      },
      [],
   );
   const customerAndSupplierFields = useMemo(() => {
      return [
         { key: 'address', message: 'Địa chỉ không được bỏ trống' },
         { key: 'phone', message: 'Số điện thoại không được bỏ trống' },
      ];
   }, []);

   const handleRegister = async (e) => {
      e.preventDefault();

      const messageError = {};

      validateFields(requiredFields, messageError);

      if (user.password !== user.confirm) {
         messageError.match = 'Mật khẩu không khớp';
      }

      switch (user.userRole) {
         case roles.CUSTOMER:
            validateFields([...customerFields, ...customerAndSupplierFields], messageError);
            break;
         case roles.SUPPLIER:
            validateFields(
               [...customerAndSupplierFields, ...supplierAndShipperFields(rolesName.ROLE_SUPPLIER.toLowerCase())],
               messageError,
            );
            break;
         case roles.SHIPPER:
            validateFields(supplierAndShipperFields(rolesName.ROLE_SHIPPER.toLowerCase()), messageError);
            break;
         default:
            setErrors({ userRole: 'Chưa hỗ trợ vai trò người dùng này!' });
            return;
      }

      if (Object.keys(messageError).length > 0) {
         setErrors(messageError);
         return;
      }

      try {
         const userWithoutConfirm = _.omit(user, ['confirm']);
         const res = await APIs.post(endpoints.register, userWithoutConfirm);

         if (res.status === statusCode.HTTP_201_CREATED) {
            Swal.fire({
               title: 'Đăng ký tài khoản thành công',
               text: 'Chúc mừng bạn đã đăng ký tài khoản thành công.',
               icon: 'success',
               confirmButtonText: 'Xong',
               customClass: {
                  confirmButton: 'swal2-confirm',
               },
            }).then(() => {
               let next = q.get('next') || routeUrl.LOGIN;
               nav(next);
            });
         }
      } catch (error) {
         Toast.fire({
            icon: 'error',
            title: 'Đăng ký tài khoản thất bại',
            text:
               error?.response?.data.map((data) => data.message).join('\n') ||
               'Hệ thống đang bận, vui lòng thử lại sau',
         });
         console.error(error);
         console.error(error?.response);
      }
   };

   const processUpdateUser = (field, value) => setUser({ ...user, [field]: value });

   const validateFields = (fields, messageError) => {
      fields.forEach((field) => {
         if (!user[field.key] || !user[field.key].trim()) {
            messageError[field.key] = field.message;
         }
      });
   };

   return (
      <>
         <h2 style={{ color: 'var(--primary-color)', marginTop: '6rem', marginBottom: '2rem' }} className="text-center">
            ĐĂNG KÝ NGƯỜI DÙNG
         </h2>
         <Container className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Form onSubmit={handleRegister}>
               <Row>
                  <Col md={6}>
                     <Form.Group className="mb-3">
                        <Form.Label>
                           Vai trò (<span className="text-danger">*</span>)
                        </Form.Label>
                        <Form.Select
                           style={{ padding: 12 }}
                           value={user.userRole}
                           onChange={(e) => {
                              setErrors({});
                              processUpdateUser('userRole', e.target.value);
                           }}
                        >
                           <option value="">--- Chọn vai trò ---</option>
                           {Object.entries(rolesName).map(([key, value]) => (
                              <option key={key} value={key} selected={key === roles.CUSTOMER}>
                                 {value}
                              </option>
                           ))}
                        </Form.Select>
                        {errors.userRole && <span className="text-danger">{errors.userRole}</span>}
                     </Form.Group>
                     <Form.Group className="mb-3">
                        <Form.Label>
                           Email (<span className="text-danger">*</span>)
                        </Form.Label>
                        <Form.Control
                           autoFocus
                           value={user.email}
                           onChange={(e) => processUpdateUser('email', e.target.value)}
                           type="email"
                           placeholder="Nhập địa chỉ email..."
                        />
                        {errors.email && <span className="text-danger">{errors.email}</span>}
                     </Form.Group>
                     <Form.Group className="mb-3">
                        <Form.Label>
                           Tên đăng nhập (<span className="text-danger">*</span>)
                        </Form.Label>
                        <Form.Control
                           value={user.username}
                           onChange={(e) => processUpdateUser('username', e.target.value)}
                           type="text"
                           placeholder="Nhập tên đăng nhập..."
                        />
                        {errors.username && <span className="text-danger">{errors.username}</span>}
                     </Form.Group>
                     <Form.Group className="mb-3">
                        <Form.Label>
                           Mật khẩu (<span className="text-danger">*</span>)
                        </Form.Label>
                        <Form.Control
                           value={user.password}
                           onChange={(e) => processUpdateUser('password', e.target.value)}
                           type="password"
                           placeholder="Nhập mật khẩu..."
                        />
                        {errors.password && <span className="text-danger">{errors.password}</span>}
                     </Form.Group>
                     <Form.Group className="mb-3">
                        <Form.Label>
                           Xác nhận mật khẩu(
                           <span className="text-danger">*</span>):
                        </Form.Label>
                        <Form.Control
                           type="password"
                           value={user.confirm}
                           onChange={(e) => processUpdateUser('confirm', e.target.value)}
                           placeholder="Xác nhận mật khẩu"
                        />
                        {errors.match && <span className="text-danger">{errors.match}</span>}
                     </Form.Group>
                  </Col>
                  <Col md={6}>
                     {user.userRole === roles.CUSTOMER && (
                        <>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Họ (<span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.firstName}
                                 onChange={(e) => processUpdateUser('firstName', e.target.value)}
                                 type="text"
                                 placeholder="Nhập họ..."
                              />
                              {errors.firstName && <span className="text-danger">{errors.firstName}</span>}
                           </Form.Group>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Tên đệm (<span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.middleName}
                                 onChange={(e) => processUpdateUser('middleName', e.target.value)}
                                 type="text"
                                 placeholder="Nhập tên đệm..."
                              />
                              {errors.middleName && <span className="text-danger">{errors.middleName}</span>}
                           </Form.Group>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Tên (<span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.lastName}
                                 onChange={(e) => processUpdateUser('lastName', e.target.value)}
                                 type="text"
                                 placeholder="Nhập tên..."
                              />
                              {errors.lastName && <span className="text-danger">{errors.lastName}</span>}
                           </Form.Group>
                        </>
                     )}

                     {(user.userRole === roles.SUPPLIER || user.userRole === roles.SHIPPER) && (
                        <>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 {`Tên ${rolesName[user.userRole].toLowerCase()}`}(
                                 <span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.name}
                                 onChange={(e) => processUpdateUser('name', e.target.value)}
                                 type="text"
                                 placeholder={`Nhập tên ${rolesName[user.userRole].toLowerCase()}...`}
                              />
                              {errors.name && <span className="text-danger">{errors.name}</span>}
                           </Form.Group>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Thông tin liên hệ(
                                 <span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.contactInfo}
                                 onChange={(e) => processUpdateUser('contactInfo', e.target.value)}
                                 type="text"
                                 placeholder="Nhập thông tin liên hệ..."
                              />
                              {errors.contactInfo && <span className="text-danger">{errors.contactInfo}</span>}
                           </Form.Group>
                        </>
                     )}

                     {(user.userRole === roles.CUSTOMER || user.userRole === roles.SUPPLIER) && (
                        <>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Địa chỉ(<span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.address}
                                 onChange={(e) => processUpdateUser('address', e.target.value)}
                                 type="text"
                                 placeholder="Nhập địa chỉ..."
                              />
                              {errors.address && <span className="text-danger">{errors.address}</span>}
                           </Form.Group>
                           <Form.Group className="mb-3">
                              <Form.Label>
                                 Số điện thoại(<span className="text-danger">*</span>)
                              </Form.Label>
                              <Form.Control
                                 value={user.phone}
                                 onChange={(e) => processUpdateUser('phone', e.target.value)}
                                 type="text"
                                 placeholder="Nhập số điện thoại..."
                              />
                              {errors.phone && <span className="text-danger">{errors.phone}</span>}
                           </Form.Group>
                        </>
                     )}
                  </Col>
               </Row>

               <Form.Group className="mb-3 d-flex justify-content-center">
                  <Button
                     style={{ width: '10rem', background: 'var(--primary-color)' }}
                     variant="primary"
                     type="submit"
                  >
                     Đăng ký
                  </Button>
               </Form.Group>
            </Form>
         </Container>
      </>
   );
};

export default Register;
