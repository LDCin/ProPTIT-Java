package model.template;

import model.element.TextComponent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Template implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name; // Tên của Template (ví dụ: "Giấy khen", "Giấy chứng nhận")
    private String frameColor; // Màu khung
    private String backgroundColor; // Màu nền
    private String previewImagePath; // Đường dẫn đến hình ảnh Preview
    private Map<String, TextComponent> textComponents; // Vị trí và thuộc tính của các thành phần văn bản

    public Template(String name, String frameColor, String backgroundColor, String previewImagePath) {
        this.name = name;
        this.frameColor = frameColor;
        this.backgroundColor = backgroundColor;
        this.previewImagePath = previewImagePath;
        this.textComponents = new HashMap<>();
    }

    // Getters và Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(String frameColor) {
        this.frameColor = frameColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public Map<String, TextComponent> getTextComponents() {
        return textComponents;
    }

    public void addTextComponent(String type, TextComponent component) {
        textComponents.put(type, component);
    }

    @Override
    public String toString() {
        return name;
    }
}