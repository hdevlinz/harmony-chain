import React from "react";
import { NavLink } from "react-router-dom";

const Footer = () => {
	return (
		<div className="bg-dark text-white pt-5 pb-4">
			<div className="container text-center text-md-start">
				<div className="row text-center text-md-start">
					<div className="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
						<h5
							className="text-uppercase mb-4 font-weight-bold"
							style={{
								color: "var(--primary-color)",
								fontSize: "1.55rem",
							}}>
							F&H Logistic
						</h5>

						<p>
							F&H Logistic là công ty cung cấp dịch vụ logistics
							toàn diện, bao gồm vận chuyển hàng hóa, kho bãi, và
							quản lý chuỗi cung ứng. Chúng tôi cam kết mang đến
							giải pháp logistics hiệu quả và linh hoạt, giúp
							doanh nghiệp tiết kiệm chi phí và tối ưu.
						</p>
					</div>

					<div className="col-md-2 col-lg-2 col-xl-2 mx-auto mt-3">
						<h5
							className="text-uppercase mb-4 font-weight-bold"
							style={{ color: "var(--primary-color)" }}>
							Sản phẩm
						</h5>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Quần áo
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Đồ gia dụng
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Nước uống
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Đồ ăn
							</NavLink>
						</p>
					</div>

					<div className="col-md-3 col-lg-2 col-xl-2 mx-auto mt-3">
						<h5
							className="text-uppercase mb-4 font-weight-bold"
							style={{ color: "var(--primary-color)" }}>
							Thông tin thêm
						</h5>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Chính sách
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Thuế
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Pháp lý
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								Lãi suất
							</NavLink>
						</p>
					</div>

					<div className="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
						<h5
							className="text-uppercase mb-4 font-weight-bold"
							style={{ color: "var(--primary-color)" }}>
							Liên hệ
						</h5>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								<i className="bx bx-map me-3"></i>Hồ Chí Minh
								City
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								<i className="bx bx-envelope me-3"></i>
								f&hlogistic@gmail.com
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								<i className="bx bx-phone me-3"></i>0355566677
							</NavLink>
						</p>
						<p>
							<NavLink
								to="/"
								className="text-white"
								style={{ textDecoration: "none" }}>
								<i className="bx bx-chat me-3"></i>Tư vấn online
							</NavLink>
						</p>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Footer;
