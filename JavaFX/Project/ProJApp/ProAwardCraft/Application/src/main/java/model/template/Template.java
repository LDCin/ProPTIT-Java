package model.template;

import model.element.TextComponent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Template implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String frameColor;
    private String backgroundColor;
    private String previewImagePath;
    private Map<String, TextComponent> textComponents;

    public Template(String name, String frameColor, String backgroundColor, String previewImagePath) {
        this.name = name;
        this.frameColor = frameColor;
        this.backgroundColor = backgroundColor;
        this.previewImagePath = previewImagePath;
        this.textComponents = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getFrameColor() {
        return frameColor;
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