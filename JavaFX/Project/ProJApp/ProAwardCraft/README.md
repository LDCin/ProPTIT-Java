# ProAwardCraft 🏆

![Java](https://img.shields.io/badge/Java-17-blue) ![JavaFX](https://img.shields.io/badge/JavaFX-17-orange) ![License](https://img.shields.io/badge/license-MIT-green)

**ProAwardCraft** là một ứng dụng JavaFX đơn giản, được thiết kế để tạo và quản lý bằng khen (certificates) với giao diện thân thiện và dễ sử dụng nhằm giúp bạn tiết kiệm thời gian trong việc tự tạo những chứng chỉ, bằng khen nhanh chóng. Dự án sử dụng JavaFX và FXML (kết hợp với Scene Builder) để xây dựng giao diện, cùng với kiến trúc MVC (Model-View-Controller) để quản lý logic và dữ liệu.

Dự án này phù hợp cho các cá nhân hoặc tổ chức muốn tạo bằng khen nhanh chóng, tùy chỉnh giao diện, và lưu trữ hoặc xuất file hình ảnh.

---
## Authors
- [@LDCin](https://github.com/LDCin)

## Demo
-Link video demo: https://youtu.be/3kTAVpmESTg

## Screenshots
![image](https://github.com/user-attachments/assets/092dbf11-6f9c-49e4-92f0-01ba94fab9e1)
![image](https://github.com/user-attachments/assets/18f036b7-3a7b-4533-a0de-ab1bdd622571)
![image](https://github.com/user-attachments/assets/1abfdeb0-c6d4-4325-8168-6ac7cd64e49b)
![image](https://github.com/user-attachments/assets/41834e04-3504-4ecf-9f9d-ae1d6bd43a5a)
![image](https://github.com/user-attachments/assets/a8915a99-9100-441f-b293-7113f3ef2610)

## Features
- **Tạo Bằng Khen Nhanh (Quick Certificate)**:
  - Nhập tên người nhận và giải thưởng.
  - Chọn Template có sẵn.
  - Tạo và hiển thị bằng khen ngay lập tức.
- **Tạo Bằng Khen Tùy Chỉnh (Custom Certificate)**:
  - Nhập thêm thông tin chủ nhiệm và chọn khung (frame),... từ danh sách.
  - Tùy chỉnh giao diện bằng khen với các khung khác nhau.
- **Quản Lý Sản Phẩm (Product Manage)**:
  - Xem danh sách các bằng khen đã lưu.
  - Xóa bằng khen.
---

## Project Structure

Dự án được tổ chức theo mô hình MVC với cấu trúc thư mục rõ ràng:
```
ProAwardCraft/
├── .idea/
├── Application [ProAwardCraft]/
│   ├── cert_images/
│   ├── certificates/
│   ├── lib/
│   ├── target/
│   ├── UML_Diagram/
│   ├── README.md
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── app/
│   │               ├── MainApp.java
│   │               ├── controller/
│   │               │   ├── certificate/
│   │               │   │   ├── CustomCertificateController.java
│   │               │   │   ├── QuickCertificateController.java
│   │               │   │   └── customize/
│   │               │   │       └── CustomFontController.java
│   │               │   ├── main/
│   │               │   │   └── MainController.java
│   │               │   └── product/
│   │               │       └── ProductManageController.java
│   │               ├── model/
│   │               │   ├── certificate/
│   │               │   │   ├── Certificate.java
│   │               │   │   ├── CustomCertificate.java
│   │               │   │   └── QuickCertificate.java
│   │               │   ├── element/
│   │               │   │   ├── Element.java
│   │               │   │   ├── Frame.java
│   │               │   |   └── TextComponent.java
│   │               │   ├── product/
│   │               │   │   ├── CertificateManager.java
│   │               │   │   └── Product.java
│   │               │   └── template/
│   │               │       ├── Template.java
│   │               │       └── TemplateFactory.java
│   │               └── (các gói khác nếu có)
└── (các file/thư mục khác)
```

## Installion
### Requires
- Java: Phiên bản 17 trở lên.
- JavaFX: Phiên bản 17 (tải từ GluonHQ).
- IDE: IntelliJ IDEA hoặc Eclipse (khuyến nghị IntelliJ).
- Scene Builder: Để chỉnh sửa giao diện FXML (tải từ GluonHQ).

## How to install?
### 1. Clone Repository
### 2. Mở Dự Án:
- Mở dự án trong IntelliJ IDEA (hoặc IDE khác).
- Nếu sử dụng Maven, đảm bảo pom.xml đã cấu hình JavaFX

### 3. Chạy Ứng Dụng:
- Chạy ProAwardCraftApplication từ IDE.
- Ứng dụng sẽ khởi động và hiển thị giao diện chính.

## License
- Dự án được phát hành dưới Giấy phép MIT.

## Liên Hệ
- Nếu bạn có câu hỏi hoặc cần hỗ trợ, hãy liên hệ qua email: ldcin2409@gmail.com
