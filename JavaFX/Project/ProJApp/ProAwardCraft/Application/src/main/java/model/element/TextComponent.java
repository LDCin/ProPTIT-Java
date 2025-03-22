package model.element;

import java.io.Serializable;

public class TextComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type; // Loại thành phần (title, recipient, award, v.v.)
    private String text; // Nội dung văn bản
    private String fontName = "Arial"; // Tên font
    private int fontSize = 12; // Kích thước font
    private boolean isBold = false; // In đậm
    private boolean isItalic = false; // In nghiêng
    private String alignment = "Giữa"; // Căn chỉnh
    private int x, y; // Vị trí

    public TextComponent(String type, String text, String alignment, int x, int y) {
        this.type = type;
        this.text = text;
        this.alignment = alignment;
        this.x = x;
        this.y = y;
    }

    // Getters và Setters
    public String getType() { return type; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getFontName() { return fontName; }
    public void setFontName(String fontName) { this.fontName = fontName; }
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    public boolean isBold() { return isBold; }
    public void setBold(boolean bold) { isBold = bold; }
    public boolean isItalic() { return isItalic; }
    public void setItalic(boolean italic) { isItalic = italic; }
    public String getAlignment() { return alignment; }
    public void setAlignment(String alignment) { this.alignment = alignment; }
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