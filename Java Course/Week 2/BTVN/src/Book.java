public class Book {
    private String title;
    private String author;
    private String genre;
    private String releaseTime;

    public Book(String title, String author, String genre, String releaseTime){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.releaseTime = releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

}
