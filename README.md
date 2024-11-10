# LAB 05 - LẬP TRÌNH WWW (JAVA)
## Sinh viên thực hiện
- **Họ và tên:** Nguyễn Nhựt Anh
- **MSSV:** 21139431
## Nội dung trong bài tập
- Tạo các entities
- Viết các repositories interface
- Viết các lớp services
- Tạo các trang wed để sử dụng tính năng
## Tech stack
- **Frontend:** `HTML, CSS, JavaScript, Bootstrap`
- **Backend:** `Java, Spring Boot, Spring Data JPA, MySQL`
- **Database:** `HeidiSQL`
- **IDE:** `IntelliJ IDEA`
- **Version control:** `Git, GitHub`
- **Documentation:** `Markdown`
- **Others:** `Lombok, MapStruct`
## Tính năng
- Đăng nhập phân quyền bằng `RequestMapping`
	+ Candidate đăng nhập: Các Job gợi ý cho Candidate
	+ Company đăng nhập: Quản lý Tin đăng ứng tuyển
- Hiển thị danh sách các ứng viên
- Hiển thị danh sách các ứng viên bằng `pagination`
- Tìm kiếm ứng viên theo tên 
- Thêm ứng viên mới 
- Sửa thông tin ứng viên
## Hướng dẫn sử dụng
1. Clone project từ GitHub về máy tính bằng lệnh:
`git clone https://github.com/nhutanhngxx/WWW_LAB05.git`
2. Mở project bằng IntelliJ IDEA (đảm bảo đã cài đặt plugin Lombok, Spring Boot, Spring Data JPA,...)
3. Mở HeidiSQL và tạo database có tên `works` hoặc mở HeidiSQL và chạy file `Script.sql`
4. Chỉnh sửa thông tin kết nối database trong file `application.properties`
5. Chạy project và truy cập vào đường dẫn `http://localhost:8080/list` hoặc `http://localhost:8080/candidates`
6. Bắt đầu sử dụng các tính năng của ứng dụng
