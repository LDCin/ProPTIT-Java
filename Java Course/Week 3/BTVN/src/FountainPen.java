public class FountainPen extends Pen{
    private String genreOfInk;
    private String fineness;

    public FountainPen(String name, String price, String brand, String color, String material, String genreOfInk, String fineness){
        super(name, price, brand, color, material);
        this.genreOfInk = genreOfInk;
        this.fineness = fineness;
    }

    public void setGenreOfInk(String genreOfInk) {
        this.genreOfInk = genreOfInk;
    }

    public void setFineness(String fineness) {
        this.fineness = fineness;
    }

    public String getGenreOfInk() {
        return genreOfInk;
    }

    public String getFineness() {
        return fineness;
    }
}
