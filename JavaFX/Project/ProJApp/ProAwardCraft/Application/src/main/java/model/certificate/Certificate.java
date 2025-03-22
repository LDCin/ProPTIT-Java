package model.certificate;

import model.element.TextComponent;

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

    protected String logoPath = "images/logo/default_logo.png"; // Đường dẫn đến file logo
    protected int logoX = 230; // Vị trí X mặc định
    protected int logoY = 210; // Vị trí Y mặc định
    protected double logoWidth = 40; // Chiều rộng mặc định
    protected double logoHeight = 40; // Chiều cao mặc định

    // Getter và Setter cho logo
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

    public Certificate(String recipientName, String awardName) {
        // Khởi tạo các thành phần văn bản mặc định
        textComponents.add(new TextComponent("title", "GIẤY CHỨNG NHẬN", "Giữa", 250, 50));
        textComponents.add(new TextComponent("transition", "Được trao cho", "Giữa", 250, 80));
        textComponents.add(new TextComponent("recipient", recipientName, "Giữa", 250, 120));
        textComponents.add(new TextComponent("description", "là thành viên xuất sắc của CLB Lập trình PTIT.", "Giữa", 250, 160));
        textComponents.add(new TextComponent("award", "Giải thưởng: " + awardName, "Giữa", 250, 200));
        textComponents.add(new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 250));
    }

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