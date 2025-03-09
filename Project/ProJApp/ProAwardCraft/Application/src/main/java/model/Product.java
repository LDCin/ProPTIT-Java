package model;

import java.util.Date;

public class Product {
    private String name;
    private Certificate certificate;
    private Date creationDate;
    private String filename;

    public Product(String name, Certificate certificate, Date creationDate) {
        this.name = name;
        this.certificate = certificate;
        this.creationDate = creationDate;
    }

    public String getName() { return name; }
    public Certificate getCertificate() { return certificate; }
    public Date getCreationDate() { return creationDate; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    @Override
    public String toString() {
        return name;
    }
}