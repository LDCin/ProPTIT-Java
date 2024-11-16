import java.util.Scanner;

class Admin {
    private quyenTruyCap quyenTruyCap_;

    public Admin(quyenTruyCap quyenTruyCap_) {
        this.quyenTruyCap_ = quyenTruyCap_;
    }

    public void menuCuaAdmin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Thêm sách mới");
            System.out.println("2. Xóa sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xem danh sách sách");
            System.out.println("5. Tìm kiếm sách");
            System.out.println("6. Thoát đăng nhập");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    quyenTruyCap_.themSach();
                    break;
                case 2:
                    quyenTruyCap_.xoaSach();
                    break;
                case 3:
                    quyenTruyCap_.suaSach();
                    break;
                case 4:
                    quyenTruyCap_.inDanhSach();
                    break;
                case 5:
                    quyenTruyCap_.timSach();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}