public class Account {
    public Admin admin;
    public Guest guest;
    public Library library = new Library();
    public LibraryManager libraryManager = new LibraryManager(library);

    public Account (){
        this.admin = new Admin(libraryManager);
        this.guest = new Guest(libraryManager);
    }

    public void logIn(){
        while (true){
            System.out.println("Bạn đang nhập với tư cách là:");
            System.out.println("1. Admin");
            System.out.println("2. Khách");
            int choice = Integer.parseInt(ScannerManager.scanner.nextLine());
            if (choice == 1){
                System.out.println("Bạn đang đăng nhập với tư cách Admin...");
                System.out.print("Vui lòng nhập mật khẩu đăng nhập:");
                String passWord = ScannerManager.scanner.nextLine();
                if (passWord.equals("admin")) admin.showMenu();
                else{
                    int t = 4;
                    while (t-- > 0){
                        passWord = ScannerManager.scanner.nextLine();
                        if (passWord.equals("admin")){
                            admin.showMenu();
                            break;
                        }
                        else if (t != 1) {
                            System.out.println("Bạn đã nhập sai mật khẩu. Vui lòng nhập lại mật khẩu!");
                            System.out.print("Mật khẩu: ");
                        }
                    }
                    if (t == 0){
                        System.out.println("Bạn đã nhập sai 5 lần. Vui lòng thao tác lại từ đầu!");
                        logIn();
                    }
                }
            }
            else{
                System.out.println("Bạn đang đăng nhập với tư cách khách...");
                guest.showMenu();
            }
            System.out.println("Bạn có muốn tiếp tục thăm thư viện không?");
            System.out.println("1. Có");
            System.out.println("2. Không");
            int choiceVisit = Integer.parseInt(ScannerManager.scanner.nextLine());
            if (choiceVisit == 1) logIn();
            else System.out.println("Bạn đã rời thư viện!");
        }
    }
}
