public class Account {
    public static void logIn(){
        System.out.println("Đăng nhập với tư cách:");
        System.out.println("1. Admin");
        System.out.println("2. Khách");
        System.out.print("Nhập lựa chọn của bạn: ");
        String choice = ScannerManage.scanner.nextLine();
        if (choice.equals("1")){
            System.out.print("Bạn đang đăng nhập với tư cách Admin. Vui lòng nhập mật khẩu: ");
            String passWord = ScannerManage.scanner.nextLine();
//            if (passWord.equals("Admin"))
        }
    }
}
