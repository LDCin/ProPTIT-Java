import java.util.Scanner;

class Khach {
    private quyenTruyCap quyenTruyCap_;

    public Khach(quyenTruyCap quyenTruyCap_) {
        this.quyenTruyCap_ = quyenTruyCap_;
    }

    public void menuCuaKhach() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Menu Khách ---");
            System.out.println("1. Xem danh sách sách");
            System.out.println("2. Tìm kiếm sách");
            System.out.println("3. Thoát đăng nhập");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    quyenTruyCap_.inDanhSach();
                    break;
                case 2:
                    quyenTruyCap_.timSach();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}