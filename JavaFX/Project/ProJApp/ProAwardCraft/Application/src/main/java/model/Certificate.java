package model;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public abstract class Certificate implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String recipientName;
    protected String awardName;
    protected String fontName = "Arial";
    protected int fontSize = 12;
    protected boolean isBold = false;
    protected boolean isItalic = false;
    protected String alignment = "Giữa";
    protected List<CustomElement> customElements = new ArrayList<>();

    public Certificate(String recipientName, String awardName) {
        this.recipientName = recipientName;
        this.awardName = awardName;
    }

    public void setFont(String font, int size, boolean bold, boolean italic, String alignment) {
        this.fontName = font;
        this.fontSize = size;
        this.isBold = bold;
        this.isItalic = italic;
        this.alignment = alignment;
    }

    public String getFontName() { return fontName; }
    public int getFontSize() { return fontSize; }
    public boolean isBold() { return isBold; }
    public boolean isItalic() { return isItalic; }
    public String getAlignment() { return alignment; }

    // Getter và setter cho customElements
    public List<CustomElement> getCustomElements() { return customElements; }
    public void addCustomElement(CustomElement element) { customElements.add(element); }
    public void removeCustomElement(String id) {
        customElements.removeIf(element -> element.getId().equals(id));
    }

    public abstract BufferedImage generateImage();

    public void saveImage(String filename) {
        try {
            BufferedImage image = generateImage();
            File outputFile = new File(filename);
            if (!outputFile.getParentFile().exists()) outputFile.getParentFile().mkdirs();
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportImage(String filename) {
        saveImage(filename);
    }
}