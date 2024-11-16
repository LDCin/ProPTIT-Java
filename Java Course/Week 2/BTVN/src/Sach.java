public class Sach {
    private String tieuDe;
    private String tacGia;
    private String theLoai;
    private String ngayXuatBan;

    public Sach(String tieuDe, String tacGia, String theLoai, String ngayXuatBan){
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.ngayXuatBan = ngayXuatBan;
    }

    public String getTieuDe() {
        return tieuDe;
    }
    public String getTacGia() {
        return tacGia;
    }
    public String getTheLoai() {
        return theLoai;
    }
    public String getNgayXuatBan() {
        return ngayXuatBan;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-20s %-20s %s\n",
                tieuDe, tacGia, theLoai, ngayXuatBan);
    }
}
