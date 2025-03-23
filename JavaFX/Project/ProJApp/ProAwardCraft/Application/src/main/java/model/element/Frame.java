package model.element;

import java.io.Serializable;

public class Frame implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String color;

    public Frame() {}

    public Frame(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}