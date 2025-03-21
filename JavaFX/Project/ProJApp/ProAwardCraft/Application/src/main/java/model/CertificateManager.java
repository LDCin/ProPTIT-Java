package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CertificateManager {
    private List<Product> products = new ArrayList<>();
    private static final String FILE_PATH = "products.dat";

    public CertificateManager() {
        loadProducts();
    }

    public void saveProduct(Product product) {
        products.add(product);
        saveToFile();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean removeProduct(Product product) {
        boolean removed = products.remove(product);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(products);
            System.out.println("Saved products to file: " + FILE_PATH + ", Total items: " + products.size());
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadProducts() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    products = (List<Product>) obj;
                    System.out.println("Loaded products from file: " + products.size() + " items");
                } else {
                    products = new ArrayList<>();
                    System.out.println("File content is not a List, initializing empty list.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading products: " + e.getMessage());
                e.printStackTrace();
                products = new ArrayList<>();
            }
        } else {
            System.out.println("No existing products file found. Starting with empty list.");
            products = new ArrayList<>();
        }
    }
}