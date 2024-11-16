public class Book extends Product{
    private String genre;
    private String author;
    private String realeaseTime;
    private String language;

    public Book(String name, String price, String brand, String genre, String author, String realeaseTime, String language){
        super(name, price, brand);
        this.genre = genre;
        this.author = author;
        this.realeaseTime = realeaseTime;
        this.language = language;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRealeaseTime(String realeaseTime) {
        this.realeaseTime = realeaseTime;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getRealeaseTime() {
        return realeaseTime;
    }

    public String getLanguage() {
        return language;
    }
}
