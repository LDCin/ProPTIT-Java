public class Pen extends Product{
    private String color;
    private String material;

    public Pen(String name, String price, String brand, String color, String material) {
        super(name, price, brand);
        this.color = color;
        this.material = material;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }
}
