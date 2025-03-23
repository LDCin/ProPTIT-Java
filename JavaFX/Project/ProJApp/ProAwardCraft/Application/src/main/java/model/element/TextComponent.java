package model.element;

import java.io.Serializable;

public class TextComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String text;
    private String fontName = "Times New Roman";
    private int fontSize = 15;
    private boolean isBold = true;
    private boolean isItalic = false;
    private String alignment = "Giữa";
    private int x, y;

    public TextComponent(String type, String text, String alignment, int x, int y) {
        this.type = type;
        this.text = text;
        this.alignment = alignment;
        this.x = x;
        this.y = y;
    }

    public String getType() { return type; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getFontName() { return fontName; }
    public int getFontSize() { return fontSize; }
    public boolean isBold() { return isBold; }
    public boolean isItalic() { return isItalic; }
    public String getAlignment() { return alignment; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public void setFont(String font, int size, boolean bold, boolean italic, String alignment) {
        this.fontName = font;
        this.fontSize = size;
        this.isBold = bold;
        this.isItalic = italic;
        this.alignment = alignment;
    }
}