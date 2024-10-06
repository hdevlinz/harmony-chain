import { useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { routeUrl } from '../../App';
import { authAPI, endpoints } from '../../configs/APIConfigs';
import { useUser } from '../../store/contexts/UserContext';
import { LOGOUT, UPDATE_USER } from '../../store/reducers/UserReducer';
import { defaultImage, rolesName, statusCode } from '../../utils/Constatns';
import Toast from '../../utils/Utils';
import './Profile.css';

const Account = () => {
   const [user, dispatch] = useUser();

   const [profile, setProfile] = useState(user?.data);
   const [previewAvatar, setPreviewAvatar] = useState(null);
   const [confirmPassword, setConfirmPassword] = useState('');
   const [loading, setLoading] = useState(false);
   const [showPassword, setShowPassword] = useState(false);

   const navigate = useNavigate();

   const handleConfirmAccount = async (e) => {
      e.preventDefault();

      Swal.fire({
         title: 'Đang xác thực...',
         text: 'Vui lòng đợi một chút.',
         allowOutsideClick: false,
         showConfirmButton: false,
         didOpen: () => {
            Swal.showLoading();
         },
      });

      try {
         const res = await authAPI().post(endpoints.confirm);

         if (res.status === statusCode.HTTP_200_OK) {
            dispatch({
               type: UPDATE_USER,
               payload: {
                  data: {
                     ...user?.data,
                     isConfirm: true,
                  },
                  profile: user?.profile,
               },
            });
            Toast.fire({
               icon: 'success',
               title: 'Xác thực thành công',
               text: 'Tài khoản của bạn đã được xác nhận.',
            });
         }
      } catch (error) {
         Swal.showValidationMessage(
            `Xác thực thất bại: ${
               error?.response?.data.map((data) => data.message).join('\n') || 'Hệ thống đang bận, vui lòng thử lại sau'
            }`,
         );
         console.error(error);
         console.error(error?.response);
      }
   };

   const processUpdateProfile = (field, value) => {
      setProfile({ ...profile, [field]: value });
   };

   const handleUpdateAccount = async (e) => {
      e.preventDefault();

      Swal.fire({
         title: 'Đang cập nhật...',
         text: 'Vui lòng đợi một chút.',
         showConfirmButton: false,
         didOpen: () => {
            Swal.showLoading();
         },
      });

      const formData = new FormData();

      if (profile?.oldPassword && profile?.newPassword) {
         if (profile?.newPassword !== confirmPassword) {
            Swal.showValidationMessage('Mật khẩu xác nhận không khớp!');
            return;
         }

         formData.append('oldPassword', profile?.oldPassword);
         formData.append('newPassword', profile?.newPassword);
      }
      if (profile?.email !== user?.data?.email) {
         formData.append('email', profile?.email);
         console.log(formData);
      }
      if (profile?.username !== user?.data?.username) {
         formData.append('username', profile?.username);
      }
      if (profile?.avatar !== user?.data?.avatar) {
         formData.append('avatar', profile?.avatar);
      }

      setLoading(true);
      try {
         const res = await authAPI().post(endpoints.updateProfileUser, formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
         });

         if (res.status === statusCode.HTTP_200_OK) {
            if (profile?.username !== user?.data?.username || profile?.password) {
               Swal.fire({
                  icon: 'success',
                  title: 'Cập nhật thành công',
                  text: 'Bạn đã cập nhật thông tin nhạy cảm, vui lòng đăng nhập lại!',
                  confirmButtonText: 'Xong',
               }).then((result) => {
                  if (result.isConfirmed) {
                     dispatch({ type: LOGOUT });
                     navigate(routeUrl.LOGIN);
                  }
               });
            } else {
               dispatch({
                  type: UPDATE_USER,
                  payload: {
                     data: res.data,
                     profile: user?.profile,
                  },
               });
               Toast.fire({
                  icon: 'success',
                  title: 'Cập nhật thành công',
                  text: 'Hồ sơ của bạn đã được cập nhật.',
               });
            }
         }
      } catch (error) {
         Swal.showValidationMessage(
            `Cập nhật thất bại: ${
               error?.response?.data.map((data) => data.message).join('\n') || 'Hệ thống đang bận, vui lòng thử lại sau'
            }`,
         );
         Swal.hideLoading();
         console.error(error);
         console.error(error?.response);
      }
   };

   const handleAvatarChange = (e) => {
      const file = e.target.files[0];
      if (file) {
         setPreviewAvatar(URL.createObjectURL(file));
         processUpdateProfile('avatar', file);
      }
   };

   return (
      <Container className="profile-container">
         <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <Row>
               <Col sm={3}>
                  <div className="d-flex flex-column align-items-center justify-content-center">
                     <img
                        className="rounded-circle shadow"
                        src={previewAvatar || user?.data?.avatar || defaultImage.USER_AVATAR}
                        alt="Avatar người dùng"
                        style={{
                           width: 190,
                           height: 190,
                           objectFit: 'cover',
                        }}
                        onError={(e) => {
                           console.error('Error loading image', e.target.src);
                           e.target.src = defaultImage.USER_AVATAR;
                        }}
                     />
                     <label htmlFor="avatar" type="button" className="btn btn-secondary mt-3">
                        Chọn ảnh
                     </label>
                     <Form.Control
                        id="avatar"
                        type="file"
                        style={{ display: 'none' }}
                        accept=".jpg,.jpeg,.png"
                        onChange={handleAvatarChange}
                     />
                  </div>
               </Col>

               <Col sm={9}>
                  <div className="profile-content">
                     <h3 className="text-white p-3 product-content__title">Thông tin tài khoản</h3>
                  </div>

                  {!user?.data?.isConfirm && (
                     <Button
                        className="mb-3"
                        variant="success"
                        onClick={handleConfirmAccount}
                        style={{
                           backgroundColor: 'var(--primary-color)',
                           border: 'none',
                           color: 'white',
                           fontWeight: 500,
                        }}
                        disabled={loading}
                     >
                        Xác nhận tài khoản
                     </Button>
                  )}

                  <Form className="profile-input">
                     <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                           type="email"
                           value={profile?.email}
                           placeholder="Nhập địa chỉ email..."
                           onChange={(e) => processUpdateProfile('email', e.target.value)}
                        />
                     </Form.Group>

                     <Form.Group className="mb-3">
                        <Form.Label>Tên tài khoản</Form.Label>
                        <Form.Control
                           type="text"
                           name="username"
                           value={profile?.username}
                           placeholder="Nhập tên tài khoản..."
                           onChange={(e) => processUpdateProfile('username', e.target.value)}
                        />
                     </Form.Group>

                     <Row className="position-relative">
                        <Col md={4}>
                           <Form.Group className="mb-3 position-relative">
                              <Form.Label>Mật khẩu cũ</Form.Label>
                              <Form.Control
                                 type={showPassword ? 'text' : 'password'}
                                 name="password"
                                 value={profile?.password}
                                 placeholder="Nhập mật khẩu cũ..."
                                 onChange={(e) => processUpdateProfile('oldPassword', e.target.value)}
                              />
                              <Button
                                 variant="link"
                                 className="password-toggle-profile"
                                 onClick={() => setShowPassword(!showPassword)}
                              >
                                 {showPassword ? (
                                    <FaEyeSlash style={{ color: 'var(--primary-color)' }} />
                                 ) : (
                                    <FaEye style={{ color: 'var(--primary-color)' }} />
                                 )}
                              </Button>
                           </Form.Group>
                        </Col>

                        <Col md={4}>
                           <Form.Group className="mb-3 position-relative">
                              <Form.Label>Mật khẩu mới</Form.Label>
                              <Form.Control
                                 type={showPassword ? 'text' : 'password'}
                                 name="password"
                                 value={profile?.password}
                                 placeholder="Nhập mật khẩu mới..."
                                 onChange={(e) => processUpdateProfile('newPassword', e.target.value)}
                              />
                              <Button
                                 variant="link"
                                 className="password-toggle-profile"
                                 onClick={() => setShowPassword(!showPassword)}
                              >
                                 {showPassword ? (
                                    <FaEyeSlash style={{ color: 'var(--primary-color)' }} />
                                 ) : (
                                    <FaEye style={{ color: 'var(--primary-color)' }} />
                                 )}
                              </Button>
                           </Form.Group>
                        </Col>

                        <Col md={4}>
                           <Form.Group className="mb-3 position-relative">
                              <Form.Label>Xác nhận mật khẩu</Form.Label>
                              <Form.Control
                                 type={showPassword ? 'text' : 'password'}
                                 placeholder="Xác nhận mật khẩu mới..."
                                 value={confirmPassword}
                                 onChange={(e) => setConfirmPassword(e.target.value)}
                              />
                              <Button
                                 variant="link"
                                 className="password-toggle-profile"
                                 onClick={() => setShowPassword(!showPassword)}
                              >
                                 {showPassword ? (
                                    <FaEyeSlash style={{ color: 'var(--primary-color)' }} />
                                 ) : (
                                    <FaEye style={{ color: 'var(--primary-color)' }} />
                                 )}
                              </Button>
                           </Form.Group>
                        </Col>

                        <Form.Group className="mb-3">
                           <Form.Label>Vai trò</Form.Label>
                           <Form.Control type="text" value={rolesName[profile?.role]} disabled />
                        </Form.Group>

                        <Form.Group className="mb-3">
                           <Form.Label>Trạng thái xác thực</Form.Label>
                           <Form.Control
                              type="text"
                              value={profile?.isConfirm ? 'Đã xác thực' : 'Chưa xác thực'}
                              disabled
                           />
                        </Form.Group>

                        <div className="text-center">
                           <Button
                              onClick={handleUpdateAccount}
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
                     </Row>
                  </Form>
               </Col>
            </Row>
         </div>
      </Container>
   );
};

export default Account;
