public class Admin {
    public LibraryManager libraryManager;

    public Admin(LibraryManager libraryManager){
        this.libraryManager = libraryManager;
    }

    public void addBook(){
        libraryManager.addBook();
    }

    public void deleteBook(){
        libraryManager.deleteBook();
    }

    public void editBook(){
        libraryManager.editBook();
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
            System.out.println("1. Thêm sách");
            System.out.println("2. Xóa sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Tìm sách");
            System.out.println("5. Xem toàn bộ sách");
            System.out.println("6. Dừng thao tác");
            System.out.print("Vui lòng nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(ScannerManager.scanner.nextLine());
            switch (choice){
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    editBook();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    showLibrary();
                    break;
                case 6:
                    repeat = 1;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại lựa chọn");
                    showMenu();
            }
        }
    }
}
