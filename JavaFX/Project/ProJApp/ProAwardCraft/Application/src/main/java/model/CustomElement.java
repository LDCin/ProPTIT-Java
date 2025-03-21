package model;

import java.awt.Point;
import java.io.Serializable;

public class CustomElement implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String text;
    private Point position;
    private String type;

    public CustomElement(String id, String text, Point position, String type) {
        this.id = id;
        this.text = text;
        this.position = position != null ? position : new Point(0, 0);
        this.type = type != null ? type : "text";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Point getPosition() { return position; }
    public void setPosition(int x, int y) { this.position.setLocation(x, y); }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "CustomElement{id='" + id + "', text='" + text + "', position=" + position + ", type='" + type + "'}";
    }
}