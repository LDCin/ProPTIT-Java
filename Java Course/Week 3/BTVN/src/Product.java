public class Product {
    private String name;
    private String price;
    private String brand;

    public Product(String name, String price, String brand){
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

}
