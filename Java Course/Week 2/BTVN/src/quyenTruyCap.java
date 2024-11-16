import java.util.ArrayList;
import java.util.Scanner;

public class quyenTruyCap {
    ArrayList<Sach> sach = new ArrayList<Sach>();
    Scanner scanner = new Scanner(System.in);
    public void themSach(){
        System.out.println("Nhập tiêu đề cần thêm:");
        String tieuDe = scanner.nextLine();
        System.out.println("Nhập tác giả của cuốn sách:");
        String tacGia = scanner.nextLine();
        System.out.println("Nhập thể loại của cuốn sách:");
        String theLoai = scanner.nextLine();
        System.out.println("Nhập ngày xuất bản của cuốn sách:");
        String ngayXuatBan = scanner.nextLine();
        sach.add(new Sach(tieuDe, tacGia, theLoai, ngayXuatBan));
    }

    public void xoaSach(){
        System.out.println("Nhập tiêu đề sách bạn muốn xóa:");
        String tieuDeCanXoa = scanner.nextLine();
        boolean check = sach.removeIf(sachcanxoa -> sachcanxoa.getTieuDe().equalsIgnoreCase(tieuDeCanXoa));
        if (check == true) System.out.println("Đã xóa cuốn sách có tiêu đề \"" + tieuDeCanXoa + "\"");
        else System.out.println("Không có cuốn sách có tiêu đề \"" + tieuDeCanXoa + "\"");
    }
    // sua ten bien
    public void suaSach() {
        System.out.println("Nhập tiêu đề sách cần sửa:");
        String sachCanSua = scanner.nextLine();
        for (Sach sach_ : sach) {
            if (sach_.getTieuDe().equalsIgnoreCase(sachCanSua)) {
                System.out.println("Nhập tiêu đề mới:");
                String tieuDeMoi = scanner.nextLine();
                System.out.println("Nhập tác giả mới:");
                String tacGiaMoi = scanner.nextLine();
                System.out.println("Nhập thể loại mới:");
                String theLoaiMoi = scanner.nextLine();
                System.out.println("Nhập ngày xuất bản mới:");
                String ngayXuatBanMoi = scanner.nextLine();
                System.out.println("Đã cập nhật sách!");
                sach.set(sach.indexOf(sach_), new Sach(tieuDeMoi, tacGiaMoi, theLoaiMoi, ngayXuatBanMoi));
                return;
            }
        }
        System.out.println("Không tìm thấy sách!");
    }
    // chinh toString
    public void timSach() {
        System.out.println("Nhập từ khóa tìm kiếm (tiêu đề, tác giả, thể loại, ngày xuất bản):");
        String tuKhoa = scanner.nextLine();
        for (Sach sach_ : sach) {
            if (sach_.getTieuDe().contains(tuKhoa) || sach_.getTacGia().contains(tuKhoa) || sach_.getTheLoai().contains(tuKhoa) || sach_.getNgayXuatBan().contains(tuKhoa)) {
                System.out.println(sach_.toString());
            }
        }
    }

    // chinh toString
    public void inDanhSach() {
        if (sach.isEmpty()) {
            System.out.println("Không có sách nào trong thư viện.");
        } else {
            System.out.printf("%-20s %-20s %-20s %s\n", "Tiêu đề", "Tác giả", "Thể loại", "Ngày xuất bản");
            System.out.println("---------------------------------------------------------------------------------------");
            for (Sach sach_ : sach) {
                System.out.println(sach_.toString());
            }
        }
    }
}

