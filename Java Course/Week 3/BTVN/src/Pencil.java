public class Pencil extends Pen{
    private String stiffness;

    public Pencil(String name, String price, String brand, String color, String material, String stiffness){
        super(name, price, brand, color, material);
        this.stiffness = stiffness;
    }

    public void setStiffness(String stiffness) {
        this.stiffness = stiffness;
    }

    public String getStiffness() {
        return stiffness;
    }

}
