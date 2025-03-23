package model.certificate;

import javafx.scene.image.ImageView;
import model.element.TextComponent;
import model.element.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public abstract class Certificate implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected List<TextComponent> textComponents = new ArrayList<>();
    protected List<Element> elements = new ArrayList<>(); // Danh sách các element kéo thả

    protected String logoPath = "default_logo.png";
    protected int logoX = 215;
    protected int logoY = 210;
    protected double logoWidth = 40;
    protected double logoHeight = 40;
    protected boolean checkSaveLogo = false;

    public Certificate(String recipientName, String awardName) {
        textComponents.add(new TextComponent("title", "GIẤY CHỨNG NHẬN", "Giữa", 250, 50));
        textComponents.add(new TextComponent("transition", "Được trao cho", "Giữa", 250, 80));
        textComponents.add(new TextComponent("recipient", recipientName, "Giữa", 250, 120));
        textComponents.add(new TextComponent("description", "là thành viên xuất sắc của CLB Lập trình PTIT.", "Giữa", 250, 160));
        textComponents.add(new TextComponent("award", "Giải thưởng: " + awardName, "Giữa", 250, 200));
        textComponents.add(new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 250));
    }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
    public int getLogoX() { return logoX; }
    public void setLogoX(int logoX) { this.logoX = logoX; }
    public int getLogoY() { return logoY; }
    public void setLogoY(int logoY) { this.logoY = logoY; }
    public double getLogoWidth() { return logoWidth; }
    public void setLogoWidth(double logoWidth) { this.logoWidth = logoWidth; }
    public double getLogoHeight() { return logoHeight; }
    public void setLogoHeight(double logoHeight) { this.logoHeight = logoHeight; }
    public boolean isCheckSaveLogo() { return checkSaveLogo; }
    public void setCheckSaveLogo(boolean checkSaveLogo) { this.checkSaveLogo = checkSaveLogo; }

    public List<TextComponent> getTextComponents() {
        return textComponents;
    }

    public TextComponent getTextComponent(String type) {
        return textComponents.stream()
                .filter(tc -> tc.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public void setTextComponent(String type, String text) {
        TextComponent component = getTextComponent(type);
        if (component != null) {
            component.setText(text);
        }
    }

    public void setTextPosition(String type, int x, int y) {
        TextComponent component = getTextComponent(type);
        if (component != null) {
            component.setX(x);
            component.setY(y);
        }
    }

    public List<Element> getElements() {
        return elements;
    }

    public void addElement(String path, String type, double x, double y, double width, double height) {
        elements.add(new Element(type, path, x, y, width, height));
    }

    public void updateElementPosition(ImageView elementView) {
        for (Element element : elements) {
            if (element.getPath().equals(elementView.getImage().getUrl())) {
                element.setX(elementView.getX() - 250); // offsetX tạm thời hardcode, thay bằng biến từ MainController nếu cần
                element.setY(elementView.getY() - 150); // offsetY tạm thời hardcode
                break;
            }
        }
    }

    public void removeElement(ImageView elementView) {
        elements.removeIf(element -> element.getPath().equals(elementView.getImage().getUrl()));
    }

    public abstract String getRecipientName();
    public abstract String getAwardName();
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