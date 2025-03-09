package model;

public class Frame {
    private String style;
    private String color;

    public Frame(String style, String color) {
        this.style = style;
        this.color = color;
    }

    public String getStyle() { return style; }
    public String getColor() { return color; }

    @Override
    public String toString() {
        return style + " (" + color + ")";
    }
}