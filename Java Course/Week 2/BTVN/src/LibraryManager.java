import java.util.ArrayList;

import static java.lang.Math.max;

public class LibraryManager {
    public Library library;

    public LibraryManager(Library library){
        this.library = library;
    }

    public void showBookList(ArrayList<Book> bookList) {
        int maxTitle = "Tiêu đề".length();
        int maxAuthor = "Tác giả".length();
        int maxGenre = "Thể loại".length();
        int maxReleaseTime = "Ngày xuất bản".length();

        for (Book book : bookList) {
            maxTitle = Math.max(maxTitle, book.getTitle().length());
            maxAuthor = Math.max(maxAuthor, book.getAuthor().length());
            maxGenre = Math.max(maxGenre, book.getGenre().length());
            maxReleaseTime = Math.max(maxReleaseTime, book.getReleaseTime().length());
        }

        printLine(maxTitle, maxAuthor, maxGenre, maxReleaseTime);

        System.out.printf("| %-8s | %-"+maxTitle+"s | %-"+maxAuthor+"s | %-"+maxGenre+"s | %-"+maxReleaseTime+"s |\n",
                "STT", "Tiêu đề", "Tác giả", "Thể loại", "Ngày xuất bản");

        printLine(maxTitle, maxAuthor, maxGenre, maxReleaseTime);

        int stt = 1;
        for (Book book : bookList) {
            System.out.printf("| %-8d | %-"+maxTitle+"s | %-"+maxAuthor+"s | %-"+maxGenre+"s | %-"+maxReleaseTime+"s |\n",
                    stt++, book.getTitle(), book.getAuthor(), book.getGenre(), book.getReleaseTime());
        }

        printLine(maxTitle, maxAuthor, maxGenre, maxReleaseTime);
    }

    void printLine(int maxTitle, int maxAuthor, int maxGenre, int maxReleaseTime) {
        int totalWidth = 12 + maxTitle + maxAuthor + maxGenre + maxReleaseTime + 16; // Tổng độ rộng
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
//    public void showBookList(ArrayList<Book> bookList){
//        int maxColumn = 0;
//        for (Book book : library.bookList){
//            maxColumn = max(maxColumn, book.getTitle().length());
//            maxColumn = max(maxColumn, book.getAuthor().length());
//            maxColumn = max(maxColumn, book.getGenre().length());
//            maxColumn = max(maxColumn, book.getReleaseTime().length());
//        }
//
//        for (int i = 1; i <= 4 * maxColumn; i++)
//            System.out.print("-");
//        System.out.print("\n");
//
//        System.out.print("|");
//        System.out.print("   STT   |");
//
//        int space = (maxColumn - 8) / 2 + 1;
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//        System.out.print("Tiêu đề");
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//
//        System.out.print("|");
//        space = (maxColumn - 8) / 2 + 1;
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//        System.out.print("Tác giả");
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//
//        System.out.print("|");
//        space = (maxColumn - 9) / 2 + 1;
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//        System.out.print("Thể loại");
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//
//        System.out.print("|");
//        space = (maxColumn - 15) / 2 + 1;
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//        System.out.print("Ngày xuất bản");
//        for (int i = 1; i <= space; i++)
//            System.out.print(" ");
//        System.out.println("|");
//
//        int stt = 1;
//        for (Book book : library.bookList){
//            int size = Integer.toString(stt).length();
//            System.out.print("|");
//            for (int i = 1; i <= (9 - size) / 2; i++)
//                System.out.print(" ");
//            System.out.print(stt++);
//            for (int i = 1; i <= (9 - size) / 2; i++)
//                System.out.print(" ");
//
//            System.out.print("|");
//            System.out.print(book.getTitle());
//            for (int i = 1; i <= maxColumn - book.getTitle().length() - 1; i++)
//                System.out.print(" ");
//
//            System.out.print("|");
//            System.out.print(book.getAuthor());
//            for (int i = 1; i <= maxColumn - book.getAuthor().length() - 1; i++)
//                System.out.print(" ");
//
//            System.out.print("|");
//            System.out.print(book.getGenre());
//            for (int i = 1; i <= maxColumn - book.getGenre().length() - 1; i++)
//                System.out.print(" ");
//
//            System.out.print("|");
//            System.out.print(book.getReleaseTime());
//            for (int i = 1; i <= maxColumn - book.getReleaseTime().length() - 1; i++)
//                System.out.print(" ");
//            System.out.println("|");
//        }
//    }

    public void showLibrary(){
        showBookList(library.bookList);
    }

    public void addBook(){
        System.out.println("Vui lòng nhập thông tin của cuốn sách cần thêm:");
        System.out.print("Tiêu đề: ");
        String title = ScannerManager.scanner.nextLine();
        System.out.print("Tác giả: ");
        String author = ScannerManager.scanner.nextLine();
        System.out.print("Thể loại: ");
        String genre = ScannerManager.scanner.nextLine();
        System.out.print("Ngày xuất bản: ");
        String releaseTime = ScannerManager.scanner.nextLine();

        library.bookList.add(new Book(title, author, genre, releaseTime));
    }

    public void deleteBook(){
        showBookList(library.bookList);
        System.out.print("Vui lòng nhập số thứ tự quyển sách muốn xóa: ");
        int choice = Integer.parseInt(ScannerManager.scanner.nextLine());
        library.bookList.remove(choice - 1);
    }

    public void editBook(){
        System.out.println("Bạn có muốn xem thông tin sách của thư viện không?");
        System.out.println("1. Có\n2. Không");
        int choiceShowLibrabry = Integer.parseInt(ScannerManager.scanner.nextLine());
        if (choiceShowLibrabry == 1) showBookList(library.bookList);
        System.out.print("Vui lòng nhập số thứ tự quyển sách muốn sửa: ");
        int choiceBook = Integer.parseInt(ScannerManager.scanner.nextLine());
        System.out.println("Nội dung bạn có thể sửa:");
        System.out.println("1. Tiêu đề");
        System.out.println("2. Tác giả");
        System.out.println("3. Thể loại");
        System.out.println("4. Ngày xuất bản");
        System.out.println("5. Sửa tất cả nội dung trên");
        System.out.print("Vui lòng nhập lựa chọn của bạn: ");
        int choiceEdit = Integer.parseInt(ScannerManager.scanner.nextLine());
        String newTitle, newAuthor, newGenre, newReleaseTime;
        switch (choiceEdit){
            case 1:
                System.out.print("Nhập tên mới: ");
                newTitle = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setTitle(newTitle);
                break;
            case 2:
                System.out.print("Nhập tác giả mới: ");
                newAuthor = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setAuthor(newAuthor);
                break;
            case 3:
                System.out.print("Nhập thể loại mới: ");
                newGenre = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setGenre(newGenre);
                break;
            case 4:
                System.out.print("Nhập ngày xuất bản mới: ");
                newReleaseTime = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setReleaseTime(newReleaseTime);
                break;
            case 5:
                System.out.print("Nhập tên mới: ");
                newTitle = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setTitle(newTitle);
                System.out.print("Nhập tác giả mới: ");
                newAuthor = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setAuthor(newAuthor);
                System.out.print("Nhập thể loại mới: ");
                newGenre = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setGenre(newGenre);
                System.out.print("Nhập ngày xuất bản mới: ");
                newReleaseTime = ScannerManager.scanner.nextLine();
                library.bookList.get(choiceBook - 1).setReleaseTime(newReleaseTime);
                break;
            default:
                System.out.print("Lựa chọn không hợp lệ. Vui lòng thao tác lại!");
                editBook();
        }
    }

    public void searchBook(){
        System.out.print("Vui lòng nhập từ khóa bạn muốn tìm kiếm: ");
        String keyword = ScannerManager.scanner.nextLine();
        System.out.println("Kết quả tìm được dựa trên từ khóa \"" + keyword + "\"");
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : library.bookList){
            if (book.getTitle().contains(keyword)) foundBooks.add(book);
            else if (book.getAuthor().contains(keyword)) foundBooks.add(book);
            else if (book.getGenre().contains(keyword)) foundBooks.add(book);
            else if (book.getReleaseTime().contains(keyword)) foundBooks.add(book);
        }
        if (foundBooks.isEmpty()) System.out.println("Không có cuốn sách nào phù hợp");
        else showBookList(foundBooks);
    }

}
