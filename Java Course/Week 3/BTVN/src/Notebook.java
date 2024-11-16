public class Notebook extends Product{
    private String numberOfPage;
    private String genre;
    private String size;
    private String colorOfCover;
    private String material;

    public Notebook(String name, String price, String brand, String numberOfPage, String genre, String colorOfCover, String material, String size){
        super(name, price, brand);
        this.numberOfPage = numberOfPage;
        this.genre = genre;
        this.colorOfCover = colorOfCover;
        this.material = material;
        this.size = size;
    }

    public void setNumberOfPage(String numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setColorOfCover(String colorOfCover) {
        this.colorOfCover = colorOfCover;
    }

    public String getNumberOfPage() {
        return numberOfPage;
    }

    public String getGenre() {
        return genre;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return colorOfCover;
    }

    public String getMaterial() {
        return material;
    }
}
