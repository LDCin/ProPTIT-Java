import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryManager libraryManager = new LibraryManager(library);
        Account account = new Account();
        account.logIn();
    }
}