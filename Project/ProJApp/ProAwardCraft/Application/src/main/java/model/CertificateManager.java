package model;

import java.util.ArrayList;
import java.util.List;

public class CertificateManager {
    private List<Product> products = new ArrayList<>();

    public void saveProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}