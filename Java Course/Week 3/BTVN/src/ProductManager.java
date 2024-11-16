import java.util.ArrayList;

import static java.util.Collections.max;
import static java.util.Collections.shuffle;

public class ProductManager {
    public ArrayList<Product> productList;
    public ArrayList<Notebook> notebookList;
    public ArrayList<Pencil> pencilList;
    public ArrayList<FountainPen> fountainPenList;
    public ArrayList<Book> bookList;

// Thêm sản phẩm
    public void addNoteBook(){
        System.out.println("Nhập tên: ");
        String name = ScannerManage.scanner.nextLine();
        System.out.print("Nhập giá bán: ");
        String price = ScannerManage.scanner.nextLine();
        System.out.print("Nhập thương hiệu: ");
        String brand = ScannerManage.scanner.nextLine();
        System.out.print("Nhập số trang: ");
        String numberOfPage = ScannerManage.scanner.nextLine();
        System.out.print("Nhập loại vở: ");
        String genre = ScannerManage.scanner.nextLine();
        System.out.print("Nhập màu sắc bìa: ");
        String colorOfCover = ScannerManage.scanner.nextLine();
        System.out.print("Nhập chất liệu giấy: ");
        String material = ScannerManage.scanner.nextLine();
        System.out.print("Nhập kích thước: ");
        String size = ScannerManage.scanner.nextLine();

        notebookList.add(new Notebook(name, price, brand, numberOfPage, genre, colorOfCover, material, size));
        productList.add(new Notebook(name, price, brand, numberOfPage, genre, colorOfCover, material, size));
    }

    public void addPencil(){
        System.out.println("Nhập tên: ");
        String name = ScannerManage.scanner.nextLine();
        System.out.print("Nhập giá bán: ");
        String price = ScannerManage.scanner.nextLine();
        System.out.print("Nhập thương hiệu: ");
        String brand = ScannerManage.scanner.nextLine();
        System.out.print("Nhập màu sắc: ");
        String color = ScannerManage.scanner.nextLine();
        System.out.print("Nhập chất liệu: ");
        String material = ScannerManage.scanner.nextLine();
        System.out.print("Nhập độ cứng: ");
        String stiffness = ScannerManage.scanner.nextLine();

        pencilList.add(new Pencil(name , price, brand, color, material, stiffness));
        productList.add(new Pencil(name , price, brand, color, material, stiffness));
    }

    public void addFountainPen(){
        System.out.println("Nhập tên: ");
        String name = ScannerManage.scanner.nextLine();
        System.out.print("Nhập giá bán: ");
        String price = ScannerManage.scanner.nextLine();
        System.out.print("Nhập thương hiệu: ");
        String brand = ScannerManage.scanner.nextLine();
        System.out.print("Nhập màu sắc: ");
        String color = ScannerManage.scanner.nextLine();
        System.out.print("Nhập chất liệu: ");
        String material = ScannerManage.scanner.nextLine();
        System.out.print("Nhập loại mực: ");
        String genreOfInk = ScannerManage.scanner.nextLine();
        System.out.print("Nhập độ cứng: ");
        String fineness = ScannerManage.scanner.nextLine();

        fountainPenList.add(new FountainPen("Bút bi", price, brand, color, material, genreOfInk, fineness));
        productList.add(new FountainPen("Bút bi", price, brand, color, material, genreOfInk, fineness));
    }

    public void addBook(){
        System.out.print("Nhập tên sách: ");
        String name = ScannerManage.scanner.nextLine();
        System.out.print("Nhập giá bán: ");
        String price = ScannerManage.scanner.nextLine();
        System.out.print("Nhập nhà xuất bản: ");
        String brand = ScannerManage.scanner.nextLine();
        System.out.print("Nhập thể loại: ");
        String genre = ScannerManage.scanner.nextLine();
        System.out.print("Nhập tác giả: ");
        String author = ScannerManage.scanner.nextLine();
        System.out.print("Nhập ngày sản xuất: ");
        String realeaseTime = ScannerManage.scanner.nextLine();
        System.out.print("Nhập ngôn ngữ: ");
        String language = ScannerManage.scanner.nextLine();

        bookList.add(new Book(name, price, brand, genre, author, realeaseTime, language));
        productList.add(new Book(name, price, brand, genre, author, realeaseTime, language));
    }

    public void addProduct(){
        System.out.println("Danh sách sản phẩm có thể thêm: ");
        System.out.println("1. Vở ghi");
        System.out.println("2. Bút chì");
        System.out.println("3. Bút bi");
        System.out.println("4. Sách");
        System.out.print("Nhập loại sản phẩm cần thêm: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                addNoteBook();
                break;
            case 2:
                addPencil();
                break;
            case 3:
                addFountainPen();
                break;
            case 4:
                addBook();
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng nhập lại lựa chọn!");
                addProduct();
        }

    }

//  In sản phẩm
    // Dạng danh sách
    public void showNotebook(Notebook notebook){
        System.out.println("Tên: " + notebook.getName());
        System.out.println("Gía: " + notebook.getPrice());
        System.out.println("Thương hiệu: " + notebook.getBrand());
        System.out.println("Số trang: " + notebook.getNumberOfPage());
        System.out.println("Thể loại: " + notebook.getGenre());
        System.out.println("Màu sắc bìa: " + notebook.getColor());
        System.out.println("Chất liệu giấy: " + notebook.getMaterial());
        System.out.println("Kích thước: " + notebook.getSize());
    }

    public void showPencil(Pencil pencil){
        System.out.println("Tên: " + pencil.getName());
        System.out.println("Gía: " + pencil.getPrice());
        System.out.println("Thương hiệu: " + pencil.getBrand());
        System.out.println("Màu sắc: " + pencil.getColor());
        System.out.println("Chất liệu: " + pencil.getMaterial());
        System.out.println("Độ cứng: " + pencil.getStiffness());
    }

    public void showFountainPen(FountainPen fountainPen){
        System.out.println("Tên: " + fountainPen.getName());
        System.out.println("Gía" + fountainPen.getPrice());
        System.out.println("Thương hiệu: " + fountainPen.getBrand());
        System.out.println("Màu sắc: " + fountainPen.getColor());
        System.out.println("Chất liệu: " + fountainPen.getMaterial());
        System.out.println("Loại mực: " + fountainPen.getGenreOfInk());
        System.out.println("Độ mịn: " + fountainPen.getFineness());
    }

    public void showBook(Book book){
        System.out.println("Tên: " + book.getName());
        System.out.println("Gía: " + book.getPrice());
        System.out.println("Thương hiệu: " + book.getBrand());
        System.out.println("Thể loại: " + book.getGenre());
        System.out.println("Tác giả: " + book.getAuthor());
        System.out.println("Năm xuất bản: " + book.getRealeaseTime());
        System.out.println("Ngôn ngữ: " + book.getLanguage());
    }

    public void showProductByList(){
        for (Notebook notebook : notebookList)
            showNotebook(notebook);
        System.out.println("\n");
        for (Pencil pencil : pencilList)
            showPencil(pencil);
        System.out.println("\n");
        for (FountainPen fountainPen : fountainPenList)
            showFountainPen(fountainPen);
        System.out.println("\n");
        for (Book book : bookList)
            showBook(book);
    }

    // Dạng bảng
    public void showProductByTable() {
        int maxOfRow = 20;
        for (Notebook notebook : notebookList) {
            maxOfRow = Math.max(notebook.getName().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getPrice().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getBrand().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getGenre().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getColor().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getMaterial().length(), maxOfRow);
            maxOfRow = Math.max(notebook.getSize().length(), maxOfRow);
        }

        for (Pencil pencil : pencilList) {
            maxOfRow = Math.max(pencil.getName().length(), maxOfRow);
            maxOfRow = Math.max(pencil.getPrice().length(), maxOfRow);
            maxOfRow = Math.max(pencil.getBrand().length(), maxOfRow);
            maxOfRow = Math.max(pencil.getStiffness().length(), maxOfRow);
            maxOfRow = Math.max(pencil.getColor().length(), maxOfRow);
            maxOfRow = Math.max(pencil.getMaterial().length(), maxOfRow);
        }

        for (FountainPen fountainPen : fountainPenList) {
            maxOfRow = Math.max(fountainPen.getName().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getPrice().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getBrand().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getFineness().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getColor().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getMaterial().length(), maxOfRow);
            maxOfRow = Math.max(fountainPen.getGenreOfInk().length(), maxOfRow);
        }

        for (Book book : bookList) {
            maxOfRow = Math.max(book.getName().length(), maxOfRow);
            maxOfRow = Math.max(book.getPrice().length(), maxOfRow);
            maxOfRow = Math.max(book.getBrand().length(), maxOfRow);
            maxOfRow = Math.max(book.getAuthor().length(), maxOfRow);
            maxOfRow = Math.max(book.getGenre().length(), maxOfRow);
            maxOfRow = Math.max(book.getAuthor().length(), maxOfRow);
            maxOfRow = Math.max(book.getRealeaseTime().length(), maxOfRow);
            maxOfRow = Math.max(book.getLanguage().length(), maxOfRow);
        }
        for (int i = 0; i < 4 * maxOfRow; i++)
            System.out.println("-");
        System.out.print("| Tên sản phẩm");
        for (int i = 0; i < maxOfRow - 14; i++)
            System.out.print(" ");
        System.out.print("| Giá bán");
        for (int i = 0; i < maxOfRow - 9; i++)
            System.out.print(" ");
        System.out.print("| Thương hiệu");
        for (int i = 0; i < maxOfRow - 13; i++)
            System.out.print(" ");
        System.out.print("| Thông tin thêm");
        for (int i = 0; i < maxOfRow - 15; i++)
            System.out.print(" ");
        System.out.println("|");
        for (Notebook notebook : notebookList){
            System.out.print("| " + notebook.getName());
            for (int i = 0; i < maxOfRow - notebook.getName().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + notebook.getPrice());
            for (int i = 0; i < maxOfRow - notebook.getPrice().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + notebook.getBrand());
            for (int i = 0; i < maxOfRow - notebook.getBrand().length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("\n|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Thể loại: " + notebook.getGenre());
            String tmp1 = "| " + "- Thể loại: " + notebook.getGenre();
            for (int i = 0; i < maxOfRow - tmp1.length(); i++)
                System.out.print(" ");
            System.out.println("|");
            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Kích thước: " + notebook.getSize());
            String tmp2 = "| " + "- Kích thước: " + notebook.getSize();
            for (int i = 0; i < maxOfRow - tmp2.length(); i++)
                System.out.print(" ");
            System.out.println("|");
            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Màu sắc: " + notebook.getColor());
            String tmp3 = "| " + "- Màu sắc: " + notebook.getColor();
            for (int i = 0; i < maxOfRow - tmp3.length(); i++)
                System.out.print(" ");
            System.out.println("|");
            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Số trang: " + notebook.getNumberOfPage());
            String tmp4 = "| " + "- Màu sắc: " + notebook.getColor();
            for (int i = 0; i < maxOfRow - tmp4.length(); i++)
                System.out.print(" ");
            System.out.println("|");
            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "Chất liệu giấy: " + notebook.getMaterial());
            String tmp5 = "| " + "Chất liệu giấy: " + notebook.getMaterial();
            for (int i = 0; i < maxOfRow - tmp5.length(); i++)
                System.out.print(" ");
            System.out.println("|");
        }
        for (int i = 0; i < 4 * maxOfRow; i++)
            System.out.print("-");

        for (Pencil pencil : pencilList){
            System.out.print("\n| " + pencil.getName());
            for (int i = 0; i < maxOfRow - pencil.getName().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + pencil.getPrice());
            for (int i = 0; i < maxOfRow - pencil.getPrice().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + pencil.getBrand());
            for (int i = 0; i < maxOfRow - pencil.getBrand().length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Độ cứng: " + pencil.getStiffness());
            String tmp1 = "| " + "- Độ cứng: " + pencil.getStiffness();
            for (int i = 0; i < maxOfRow - tmp1.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Màu sắc: " + pencil.getColor());
            String tmp2 = "| " + "- Màu sắc: " + pencil.getColor();
            for (int i = 0; i < maxOfRow - tmp2.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "Chất liệu: " + pencil.getMaterial());
            String tmp3 = "| " + "Chất liệu: " + pencil.getMaterial();
            for (int i = 0; i < maxOfRow - tmp3.length(); i++)
                System.out.print(" ");
            System.out.println("|");
        }
        for (int i = 0; i < 4 * maxOfRow; i++)
            System.out.println("-");

        for (FountainPen fountainPen : fountainPenList){
            System.out.print("\n| " + fountainPen.getName());
            for (int i = 0; i < maxOfRow - fountainPen.getName().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + fountainPen.getPrice());
            for (int i = 0; i < maxOfRow - fountainPen.getPrice().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + fountainPen.getBrand());
            for (int i = 0; i < maxOfRow - fountainPen.getBrand().length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Độ mịn: " + fountainPen.getFineness());
            String tmp1 = "| " + "- Độ mịn: " + fountainPen.getFineness();
            for (int i = 0; i < maxOfRow - tmp1.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Màu sắc: " + fountainPen.getColor());
            String tmp2 = "| " + "- Màu sắc: " + fountainPen.getColor();
            for (int i = 0; i < maxOfRow - tmp2.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "Chất liệu: " + fountainPen.getMaterial());
            String tmp3 = "| " + "Chất liệu: " + fountainPen.getMaterial();
            for (int i = 0; i < maxOfRow - tmp3.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "Loại mực: " + fountainPen.getGenreOfInk());
            String tmp4 = "| " + "Chất liệu: " + fountainPen.getGenreOfInk();
            for (int i = 0; i < maxOfRow - tmp4.length(); i++)
                System.out.print(" ");
            System.out.println("|");
        }
        for (int i = 0; i < 4 * maxOfRow; i++)
            System.out.println("-");

        for (Book book : bookList){
            System.out.print("\n| " + book.getName());
            for (int i = 0; i < maxOfRow - book.getName().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + book.getPrice());
            for (int i = 0; i < maxOfRow - book.getPrice().length() - 2; i++)
                System.out.println(" ");
            System.out.print("| " + book.getBrand());
            for (int i = 0; i < maxOfRow - book.getBrand().length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Tác giả: " + book.getAuthor());
            String tmp1 = "| " + "- Tác giả: " + book.getAuthor();
            for (int i = 0; i < maxOfRow - tmp1.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Năm xuất bản: " + book.getRealeaseTime());
            String tmp2 = "| " + "- Năm xuất bản: " + book.getRealeaseTime();
            for (int i = 0; i < maxOfRow - tmp2.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Thể loại: " + book.getGenre());
            String tmp3 = "| " + "- Thể loại: " + book.getGenre();
            for (int i = 0; i < maxOfRow - tmp3.length(); i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.print("|");
            for (int i = 0; i < 3 * maxOfRow; i++)
                System.out.print(" ");
            System.out.print("| " + "- Ngôn ngữ: " + book.getLanguage());
            String tmp4 = "| " + "- Ngôn ngữ: " + book.getLanguage();
            for (int i = 0; i < maxOfRow - tmp4.length(); i++)
                System.out.print(" ");
            System.out.println("|");
        }
        for (int i = 0; i < 4 * maxOfRow; i++)
            System.out.println("-");
    }

// Sửa sản phẩm
    // Sửa vở ghi
    public void editName(Notebook notebook){
        System.out.print("Nhập tên mới cho vở ghi: ");
        String newName = ScannerManage.scanner.nextLine();
        notebook.setName(newName);
    }

    public void editPrice(Notebook notebook){
        System.out.println("Nhập giá mới cho vở ghi: ");
        String newPrice = ScannerManage.scanner.nextLine();
        notebook.setPrice(newPrice);
    }

    public void editBrand(Notebook notebook){
        System.out.println("Nhập thương hiệu mới cho vở ghi: ");
        String newBrand = ScannerManage.scanner.nextLine();
        notebook.setBrand(newBrand);
    }

    public void editNumberOfPage(Notebook notebook){
        System.out.println("Nhập số trang mới cho vở ghi: ");
        String newNumberOfPage = ScannerManage.scanner.nextLine();
        notebook.setNumberOfPage(newNumberOfPage);
    }

    public void editGenre(Notebook notebook){
        System.out.println("Nhập thể loại mới cho vở ghi: ");
        String newGenre = ScannerManage.scanner.nextLine();
        notebook.setGenre(newGenre);
    }

    public void editColorOfCover(Notebook notebook){
        System.out.println("Nhập màu sắc bìa mới cho vở ghi: ");
        String newColorOfCover = ScannerManage.scanner.nextLine();
        notebook.setColorOfCover(newColorOfCover);
    }

    public void editMaterial(Notebook notebook){
        System.out.println("Nhập chất liệu giấy mới cho vở ghi: ");
        String newMaterial = ScannerManage.scanner.nextLine();
        notebook.setMaterial(newMaterial);
    }

    public void editSize(Notebook notebook){
        System.out.println("Nhập kích thước mới cho vở ghi: ");
        String newSize = ScannerManage.scanner.nextLine();
        notebook.setSize(newSize);
    }

    public void editNotebook(Notebook notebook){
        System.out.println("Danh sách nội dung có thể sửa: ");
        System.out.println("1. Tên");
        System.out.println("2. Gía");
        System.out.println("3. Thương hiệu");
        System.out.println("4. Số trang");
        System.out.println("5. Thể loại");
        System.out.println("6. Màu sắc bìa");
        System.out.println("7. Chất liệu giấy");
        System.out.println("8. Kích thước");
        System.out.print("Nhập nội dung cần sửa: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                editName(notebook);
                break;
            case 2:
                editPrice(notebook);
                break;
            case 3:
                editBrand(notebook);
                break;
            case 4:
                editNumberOfPage(notebook);
                break;
            case 5:
                editGenre(notebook);
                break;
            case 6:
                editColorOfCover(notebook);
                break;
            case 7:
                editMaterial(notebook);
                break;
            case 8:
                editSize(notebook);
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editNotebook(notebook);
        }
    }

    // Sửa bút chì

    public void editName(Pencil pencil){
        System.out.print("Nhập tên mới cho bút chì: ");
        String newName = ScannerManage.scanner.nextLine();
        pencil.setName(newName);
    }

    public void editPrice(Pencil pencil){
        System.out.println("Nhập giá mới cho bút chì: ");
        String newPrice = ScannerManage.scanner.nextLine();
        pencil.setPrice(newPrice);
    }

    public void editBrand(Pencil pencil){
        System.out.println("Nhập thương hiệu mới cho bút chì: ");
        String newBrand = ScannerManage.scanner.nextLine();
        pencil.setBrand(newBrand);
    }

    public void editColor(Pencil pencil){
        System.out.println("Nhập màu mới cho bút chì: ");
        String newColor = ScannerManage.scanner.nextLine();
        pencil.setColor(newColor);
    }

    public void editMaterial(Pencil pencil){
        System.out.println("Nhập màu mới cho bút chì: ");
        String newMaterial = ScannerManage.scanner.nextLine();
        pencil.setMaterial(newMaterial);
    }

    public void editStiffness(Pencil pencil){
        System.out.println("Nhập độ cứng mới cho bút chì: ");
        String newStiffness = ScannerManage.scanner.nextLine();
        pencil.setStiffness(newStiffness);
    }

    public void editPencil(Pencil pencil){
        System.out.println("Danh sách nội dung có thể sửa:");
        System.out.println("1. Tên");
        System.out.println("2. Gía");
        System.out.println("3. Thương hiệu");
        System.out.println("4. Màu sắc");
        System.out.println("5. Chất liệu");
        System.out.println("6. Độ cứng");
        System.out.print("Nhập nội dung cần sửa: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                editName(pencil);
                break;
            case 2:
                editPrice(pencil);
                break;
            case 3:
                editBrand(pencil);
                break;
            case 4:
                editColor(pencil);
                break;
            case 5:
                editStiffness(pencil);
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editPencil(pencil);
        }
    }

    // Sửa bút mực

    public void editName(FountainPen fountainPen){
        System.out.print("Nhập tên mới cho bút mực: ");
        String newName = ScannerManage.scanner.nextLine();
        fountainPen.setName(newName);
    }

    public void editPrice(FountainPen fountainPen){
        System.out.println("Nhập giá mới cho bút chì: ");
        String newPrice = ScannerManage.scanner.nextLine();
        fountainPen.setPrice(newPrice);
    }

    public void editBrand(FountainPen fountainPen){
        System.out.println("Nhập thương hiệu mới cho bút chì: ");
        String newBrand = ScannerManage.scanner.nextLine();
        fountainPen.setBrand(newBrand);
    }

    public void editColor(FountainPen fountainPen){
        System.out.println("Nhập màu mới cho bút chì: ");
        String newColor = ScannerManage.scanner.nextLine();
        fountainPen.setColor(newColor);
    }

    public void editGenreOfInk(FountainPen fountainPen){
        System.out.println("Nhập loại mực mới cho bút mực: ");
        String newGenreOfInk = ScannerManage.scanner.nextLine();
        fountainPen.setGenreOfInk(newGenreOfInk);
    }

    public void editMaterial(FountainPen fountainPen){
        System.out.println("Nhập màu mới cho bút chì: ");
        String newMaterial = ScannerManage.scanner.nextLine();
        fountainPen.setMaterial(newMaterial);
    }

    public void editFineness(FountainPen fountainPen){
        System.out.println("Nhập độ cứng mới cho bút chì: ");
        String newStiffness = ScannerManage.scanner.nextLine();
        fountainPen.setFineness(newStiffness);
    }

    public void editFountainPen(FountainPen fountainPen){
        System.out.println("Danh sách nội dung có thể sửa:");
        System.out.println("1. Tên");
        System.out.println("2. Gía");
        System.out.println("3. Thương hiệu");
        System.out.println("4. Màu sắc");
        System.out.println("5. Chất liệu");
        System.out.println("6. Loại mực");
        System.out.println("7. Độ mịn");
        System.out.print("Nhập nội dung cần sửa: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                editName(fountainPen);
                break;
            case 2:
                editPrice(fountainPen);
                break;
            case 3:
                editBrand(fountainPen);
                break;
            case 4:
                editColor(fountainPen);
                break;
            case 5:
                editMaterial(fountainPen);
                break;
            case 6:
                editGenreOfInk(fountainPen);
                break;
            case 7:
                editFineness(fountainPen);
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editFountainPen(fountainPen);
        }
    }

    // Sửa sách

    public void editName(Book book){
        System.out.print("Nhập tên mới cho sách: ");
        String newName = ScannerManage.scanner.nextLine();
        book.setName(newName);
    }

    public void editPrice(Book book){
        System.out.println("Nhập giá mới cho sách: ");
        String newPrice = ScannerManage.scanner.nextLine();
        book.setPrice(newPrice);
    }

    public void editBrand(Book book){
        System.out.println("Nhập nhà xuất bản mới cho sách: ");
        String newBrand = ScannerManage.scanner.nextLine();
        book.setBrand(newBrand);
    }

    public void editGenre(Book book){
        System.out.println("Nhập thể loại mới cho sách: ");
        String newGenre = ScannerManage.scanner.nextLine();
        book.setGenre(newGenre);
    }

    public void editAuthor(Book book){
        System.out.println("Nhập tác giả mới cho sách: ");
        String newAuthor = ScannerManage.scanner.nextLine();
        book.setAuthor(newAuthor);
    }

    public void editRealeaseTime(Book book){
        System.out.println("Nhập ngày xuất bản mới cho bút chì: ");
        String newRealeaseTime = ScannerManage.scanner.nextLine();
        book.setRealeaseTime(newRealeaseTime);
    }

    public void editLanguage(Book book){
        System.out.println("Nhập ngôn ngữ mới cho bút chì: ");
        String newLanguage = ScannerManage.scanner.nextLine();
        book.setLanguage(newLanguage);
    }

    public void editBook(Book book){
        System.out.println("Danh sách nội dung có thể sửa: ");
        System.out.println("1. Tên");
        System.out.println("2. Gía");
        System.out.println("3. Thương hiệu");
        System.out.println("4. Thể loại");
        System.out.println("5. Tác giả");
        System.out.println("6. Năm xuất bản");
        System.out.println("7. Ngôn ngữ");
        System.out.print("Nhập nội dung cần sửa: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                editName(book);
                break;
            case 2:
                editPrice(book);
                break;
            case 3:
                editBrand(book);
                break;
            case 4:
                editGenre(book);
                break;
            case 5:
                editAuthor(book);
                break;
            case 6:
                editRealeaseTime(book);
                break;
            case 7:
                editLanguage(book);
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editBook(book);
        }
    }

    public void editProduct(){
        System.out.println("Danh sách sản phẩm có thể sửa: ");
        System.out.println("1. Vở ghi");
        System.out.println("2. Bút chì");
        System.out.println("3. Bút bi");
        System.out.println("4. Sách");
        System.out.println("Nhập lựa chọn của bạn: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                if (!notebookList.isEmpty()) {
                    System.out.println("Danh sách vở ghi phù hợp:");
                    for (Notebook notebook : notebookList) {
                            showNotebook(notebook);
                            System.out.println("Bạn có muốn sửa quyển vở này không?");
                            System.out.println("1. Có\n2. Không");
                            System.out.print("Nhập lựa chọn của bạn: ");
                            String confirm = ScannerManage.scanner.nextLine();
                            if (confirm.equals("1")) editNotebook(notebook);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 2:
                if (!pencilList.isEmpty()) {
                    System.out.println("Danh sách bút chì phù hợp:");
                    for (Pencil pencil : pencilList) {
                        showPencil(pencil);
                        System.out.println("Bạn có muốn sửa cái bút chì này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) editPencil(pencil);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 3:
                if (!fountainPenList.isEmpty()) {
                    System.out.println("Danh sách bút bi phù hợp:");
                    for (FountainPen fountainPen : fountainPenList) {
                        showFountainPen(fountainPen);
                        System.out.println("Bạn có muốn sửa cái bút bi này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) editFountainPen(fountainPen);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 4:
                if (!bookList.isEmpty()) {
                    System.out.println("Danh sách sách phù hợp:");
                    for (Book book : bookList) {
                        showBook(book);
                        System.out.println("Bạn có muốn sửa quyển sách này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) editBook(book);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editProduct();
        }
        System.out.println("Hoàn tất sửa sản phẩm!");
    }

// Xóa sản phẩm
    public void deleteProduct(){
        System.out.println("Danh sách sản phẩm có thể xóa: ");
        System.out.println("1. Vở ghi");
        System.out.println("2. Bút chì");
        System.out.println("3. Bút bi");
        System.out.println("4. Sách");
        System.out.println("Nhập lựa chọn của bạn: ");
        int choice = ScannerManage.scanner.nextInt();
        switch (choice){
            case 1:
                if (!notebookList.isEmpty()) {
                    System.out.println("Danh sách vở ghi phù hợp:");
                    for (Notebook notebook : notebookList) {
                        showNotebook(notebook);
                        System.out.println("Bạn có muốn sửa quyển vở này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) notebookList.remove(notebook);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 2:
                if (!pencilList.isEmpty()) {
                    System.out.println("Danh sách bút chì phù hợp:");
                    for (Pencil pencil : pencilList) {
                        showPencil(pencil);
                        System.out.println("Bạn có muốn sửa cái bút chì này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) pencilList.remove(pencil);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 3:
                if (!fountainPenList.isEmpty()) {
                    System.out.println("Danh sách bút bi phù hợp:");
                    for (FountainPen fountainPen : fountainPenList) {
                        showFountainPen(fountainPen);
                        System.out.println("Bạn có muốn sửa cái bút bi này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) fountainPenList.remove(fountainPen);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            case 4:
                if (!bookList.isEmpty()) {
                    System.out.println("Danh sách sách phù hợp:");
                    for (Book book : bookList) {
                        showBook(book);
                        System.out.println("Bạn có muốn sửa quyển sách này không?");
                        System.out.println("1. Có\n2. Không");
                        System.out.print("Nhập lựa chọn của bạn: ");
                        String confirm = ScannerManage.scanner.nextLine();
                        if (confirm.equals("1")) bookList.remove(book);
                    }
                }
                else System.out.println("Không có sản phẩm. Vui lòng chọn sản phẩm khác!");
                break;
            default:
                System.out.println("Lựa chọn không phù hợp. Vui lòng lựa chọn lại!");
                editProduct();
        }
        System.out.println("Hoàn tất xóa sản phẩm!");
    }

// Tìm sản phẩm
    public void searchProduct(){
        System.out.print("Nhập từ khóa: ");
        String key = ScannerManage.scanner.nextLine();
        System.out.println("Lựa chọn hình thức hiển thị:");
        System.out.println("1. Dạng bảng\n 2. Dạng danh sách");
        for (Notebook notebook : notebookList){
            if (notebook.getName().contains(key) || notebook.getPrice().contains(key)
                    || notebook.getBrand().contains(key) || notebook.getSize().contains(key)
                        || notebook.getNumberOfPage().contains(key) || notebook.getMaterial().contains(key)
                            || notebook.getColor().contains(key) || notebook.getGenre().contains(key)
            ){
                showNotebook(notebook);
            }
        }

        for (Pencil pencil : pencilList){
            if (pencil.getName().contains(key) || pencil.getPrice().contains(key)
                    || pencil.getBrand().contains(key) || pencil.getStiffness().contains(key)
                        || pencil.getMaterial().contains(key) || pencil.getColor().contains(key)
            ){
                showPencil(pencil);
            }
        }

        for (FountainPen fountainPen : fountainPenList){
            if (fountainPen.getName().contains(key) || fountainPen.getPrice().contains(key)
                    || fountainPen.getBrand().contains(key) || fountainPen.getFineness().contains(key)
                        || fountainPen.getMaterial().contains(key) || fountainPen.getColor().contains(key)
                            || fountainPen.getGenreOfInk().contains(key)
            ){
                showFountainPen(fountainPen);
            }
        }

        for (Book book : bookList){
            if (book.getName().contains(key) || book.getPrice().contains(key)
                    || book.getBrand().contains(key) || book.getAuthor().contains(key)
                        || book.getRealeaseTime().contains(key) || book.getGenre().contains(key)
                            || book.getLanguage().contains(key)
            ){
                showBook(book);
            }
        }
    }
}
