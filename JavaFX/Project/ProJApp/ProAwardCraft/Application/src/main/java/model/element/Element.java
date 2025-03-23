package model.element;

import java.io.Serializable;

public class Element implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String path;
    private double x, y;
    private double width, height;

    public Element(String type, String path, double x, double y, double width, double height) {
        this.type = type;
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getType() { return type; }
    public String getPath() { return path; }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setWidth(double fitWidth) {
        this.width = fitWidth;
    }

    public void setHeight(double fitHeight) {
        this.height = fitHeight;
    }
}