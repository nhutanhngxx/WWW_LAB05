# LAB 05 - LẬP TRÌNH WWW (JAVA)
## Sinh viên thực hiện
- **Họ và tên:** Nguyễn Nhựt Anh
- **MSSV:** 21139431
- https://nhutanhngxx.my.canva.site/dev
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
## Tính năng hiện thực dựa trên Định hướng trước đó
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

## Một số hình ảnh minh họaS
### Trang chủ Người dùng
![Home_Nguoi_dung](https://i.ibb.co/mhdShRh/Trang-chu-Nguoi-dung.png)

### Trang chủ Doanh nghiệp
![Home_Doanh-nghiep](https://i.ibb.co/fVgtM33/Trang-chu-Doanh-nghiep.png)

### Trang Đăng nhập sau chọn loại người dùng
![Login_Page](https://i.ibb.co/G3V8FWr/Man-hinh-login.png)

### Trang Đăng ký
#### Người dùng
![Register_Page](https://i.ibb.co/7K03VLX/Man-hinh-Dang-ky-Ca-nhan.png)
#### Doanh nghiệp
![Register_Page](https://i.ibb.co/Yhh4hF3/Man-hinh-Dang-ky-Doanh-nghiep.png)

### Trang chủ sau khi đăng nhập
#### Cá nhân
![Ca_nhan_index](https://i.ibb.co/kDhbyDS/Dashbroad-Nguoi-dung.png)
#### Doanh nghiệp
![Nguoi_dung_index](https://i.ibb.co/DWyMY58/Dashbroad-Doanh-nghiep.png)

### Trang thông tin
#### Cá nhân
![Ca_nhan_info](https://i.ibb.co/x7kDzK2/Trang-thong-tin-ca-nhan-nguoi-dung.png)
#### Doanh nghiệp
![Nguoi_dung_info](https://i.ibb.co/cN72Yr3/Trang-thong-tin-ca-nhan-Doanh-nghiep.png)

### Trang quản lý tin đăng
#### Quản lý tin đăng
![Quan_ly_tin_dang](https://i.ibb.co/jLYsYbW/Danh-sach-tin-dang-tuyen.png)
#### Thêm tin đăng
![Them_tin_dang](https://i.ibb.co/PWh7bP5/Posting.png)

### Tìm kiếm công việc
![Quan_ly_tin_dang](https://i.ibb.co/PTCSzgf/Ket-qua-tim-kiem.png)

