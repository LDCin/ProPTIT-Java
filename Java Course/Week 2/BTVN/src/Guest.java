public class Guest {
    public LibraryManager libraryManager;

    public Guest(LibraryManager libraryManager){
        this.libraryManager = libraryManager;
    }

    public void searchBook(){
        libraryManager.searchBook();
    }

    public void showLibrary(){
        libraryManager.showLibrary();
    }

    public void showMenu(){
        int repeat = 0;
        while (repeat == 0){
            System.out.println("Bạn có thể thực hiện các thao tác sau:");
            System.out.println("1. Tìm sách");
            System.out.println("2. Xem toàn bộ sách");
            System.out.print("Vui lòng nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(ScannerManager.scanner.nextLine());
            switch (choice){
                case 1:
                    searchBook();
                    break;
                case 2:
                    showLibrary();
                    break;
                case 3:
                    repeat = 1;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại lựa chọn");
                    showMenu();
            }
        }
    }
}
