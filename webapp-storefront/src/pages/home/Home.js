import { Carousel, Col, Container, Row } from 'react-bootstrap';
import carousel_1 from '../../assets/images/carousel/carousel_1.jpg';
import carousel_2 from '../../assets/images/carousel/carousel_2.jpg';
import carousel_3 from '../../assets/images/carousel/carousel_3.jpg';
import './Home.css';

const Home = () => {
   return (
      <>
         <Carousel>
            <Carousel.Item>
               <img className="d-block w-100 height-img" src={carousel_1} alt="First slide" />
               <Carousel.Caption>
                  <h3>Vận chuyển nhanh chóng và an toàn</h3>
                  <p>Đảm bảo hàng hóa của bạn đến nơi đúng hẹn và trong tình trạng tốt nhất.</p>
               </Carousel.Caption>
            </Carousel.Item>

            <Carousel.Item>
               <img className="d-block w-100 height-img" src={carousel_2} alt="Second slide" />
               <Carousel.Caption>
                  <h3>Đảm bảo an toàn hàng hóa</h3>
                  <p>Chúng tôi cam kết bảo vệ hàng hóa của bạn với các giải pháp kho bãi an toàn và đáng tin cậy.</p>
               </Carousel.Caption>
            </Carousel.Item>

            <Carousel.Item>
               <img className="d-block w-100 height-img" src={carousel_3} alt="Third slide" />
               <Carousel.Caption>
                  <h3>Đội ngũ chuyên nghiệp</h3>
                  <p>Chúng tôi có đội ngũ nhân viên giàu kinh nghiệm, luôn sẵn sàng hỗ trợ bạn.</p>
               </Carousel.Caption>
            </Carousel.Item>
         </Carousel>

         <Container className="container p-5 content-container">
            <div className="content__subject">
               <h1 className="content__subject__title text-center">Tầm nhìn và sứ mệnh</h1>
               <span></span>
            </div>
            <Row className="row">
               <Col sm={6}>
                  <div className="card text-center border-0 shadow rounded-0 p-3 card-principles">
                     <div className="card-principles__icon">
                        <i class="bx bxs-bullseye"></i>
                     </div>

                     <div className="card-body">
                        <h4 className="card-principles__title fw-bold">Tầm nhìn</h4>
                        <p className="card-principles__text">
                           Trở thành đối tác hàng đầu trong ngành logistics và quản lý kho bãi, dẫn đầu về công nghệ và
                           dịch vụ khách hàng trên toàn cầu.
                        </p>
                     </div>
                  </div>
               </Col>

               <Col sm={6}>
                  <div className="card text-center border-0 shadow rounded-0 p-3 card-principles">
                     <div className="card-principles__icon">
                        <i className="bx bxs-paper-plane"></i>
                     </div>

                     <div className="card-body">
                        <h4 className="card-principles__title fw-bold">Sứ mệnh</h4>
                        <p className="card-principles__text">
                           Chúng tôi cung cấp các giải pháp logistics và quản lý kho bãi tối ưu, giúp khách hàng nâng
                           cao hiệu quả hoạt động và giảm chi phí.
                        </p>
                     </div>
                  </div>
               </Col>
            </Row>
         </Container>

         <Container className="container p-5 content-container">
            <div className="content__subject">
               <h1 className="content__subject__title text-center">Giá trị cốt lõi</h1>
               <span></span>
            </div>

            <div className="row">
               <div className="col-3 col-custom">
                  <div className="card text-center border-0 shadow card-corevalue">
                     <div className="card-corevalue__icon position-absolute start-50 translate-middle rounded-circle p-2">
                        <i className="bx bx-line-chart"></i>
                     </div>

                     <div className="card-corevalue__body mt-5 p-3">
                        <h5 className="card-corevalue__body--title fw-5">Đổi mới</h5>
                        <p className="card-corevalue__body--text fw-3">
                           Luôn cải tiến và áp dụng công nghệ mới để mang lại giải pháp tối ưu nhất.
                        </p>
                     </div>
                  </div>
               </div>

               <div className="col-3 col-custom">
                  <div className="card text-center border-0 shadow card-corevalue">
                     <div className="card-corevalue__icon position-absolute start-50 translate-middle rounded-circle p-2">
                        <i class="bx bxs-happy-alt"></i>
                     </div>

                     <div className="card-corevalue__body mt-5 p-3">
                        <h5 className="card-corevalue__body--title fw-5">Tin cậy</h5>
                        <p className="card-corevalue__body--text fw-3">
                           Đảm bảo chất lượng dịch vụ và độ chính xác trong mọi giao dịch.
                        </p>
                     </div>
                  </div>
               </div>

               <div className="col-3 col-custom">
                  <div className="card text-center border-0 shadow card-corevalue">
                     <div className="card-corevalue__icon position-absolute start-50 translate-middle rounded-circle p-2">
                        <i class="bx bx-male-female"></i>
                     </div>

                     <div className="card-corevalue__body mt-5 p-3">
                        <h5 className="card-corevalue__body--title fw-5">Khách hàng</h5>
                        <p className="card-corevalue__body--text fw-3">
                           Đặt nhu cầu và sự hài lòng của khách hàng lên hàng đầu.
                        </p>
                     </div>
                  </div>
               </div>

               <div className="col-3 col-custom">
                  <div className="card text-center border-0 shadow card-corevalue">
                     <div className="card-corevalue__icon position-absolute start-50 translate-middle rounded-circle p-2">
                        <i class="bx bxs-color"></i>
                     </div>

                     <div className="card-corevalue__body mt-5 p-3">
                        <h5 className="card-corevalue__body--title fw-5">Bền vững</h5>
                        <p className="card-corevalue__body--text fw-3">
                           Luôn cải tiến và áp dụng công nghệ mới để mang lại giải pháp tối ưu nhất.
                        </p>
                     </div>
                  </div>
               </div>
            </div>
         </Container>
      </>
   );
};

export default Home;
