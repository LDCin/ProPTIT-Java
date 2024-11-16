import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        quyenTruyCap quyenTruyCap_ = new quyenTruyCap();
        Admin admin = new Admin(quyenTruyCap_);
        Khach khach = new Khach(quyenTruyCap_);
        while (true) {
            System.out.println("Vui lòng đăng nhập (nhập 'admin' để đăng nhập với tư cách admin, 'khach' để đăng nhập với tư cách khách):");
            String role = scanner.nextLine();

            if ("admin".equalsIgnoreCase(role)) {
                String password = scanner.nextLine();
                if ("admin".equalsIgnoreCase(password)){
                    admin.menuCuaAdmin();
                }
            } else if ("khach".equalsIgnoreCase(role)) {
                khach.menuCuaKhach();
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}