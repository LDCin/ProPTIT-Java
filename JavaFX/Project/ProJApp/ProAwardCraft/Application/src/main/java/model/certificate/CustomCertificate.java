package model.certificate;

import model.element.TextComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CustomCertificate extends Certificate {
    private static final long serialVersionUID = 1L;

    private String ownerName;
    private String frameColor;
    private String backgroundColor; // Thuộc tính backgroundColor đã có

    // Sửa hàm khởi tạo để nhận 5 tham số, bao gồm backgroundColor
    public CustomCertificate(String recipientName, String awardName, String ownerName, String frameColor, String backgroundColor) {
        super(recipientName, awardName);
        this.ownerName = ownerName;
        this.frameColor = frameColor;
        this.backgroundColor = backgroundColor; // Khởi tạo backgroundColor
        getTextComponents().add(new TextComponent("owner", ownerName, "Phải", 450, 270));
    }

    @Override
    public String getRecipientName() {
        TextComponent component = getTextComponent("recipient");
        return component != null ? component.getText() : "";
    }

    @Override
    public String getAwardName() {
        TextComponent component = getTextComponent("award");
        return component != null ? component.getText().replace("Giải thưởng: ", "") : "";
    }

    public String getOwnerName() {
        TextComponent component = getTextComponent("owner");
        return component != null ? component.getText() : "";
    }

    public void setRecipientName(String recipientName) {
        setTextComponent("recipient", recipientName);
    }

    public void setAwardName(String awardName) {
        setTextComponent("award", "Giải thưởng: " + awardName);
    }

    public void setOwnerName(String ownerName) {
        setTextComponent("owner", ownerName);
    }

    public void setRecipientPosition(int x, int y) {
        setTextPosition("recipient", x, y);
    }

    public void setAwardPosition(int x, int y) {
        setTextPosition("award", x, y);
    }

    public void setOwnerPosition(int x, int y) {
        setTextPosition("owner", x, y);
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

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public BufferedImage generateImage() {
        int width = 500;
        int height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Sử dụng backgroundColor để vẽ nền
        g2d.setColor(backgroundColor != null ? Color.decode(backgroundColor) : Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.decode(frameColor));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(10, 10, 480, 280);

        for (TextComponent component : getTextComponents()) {
            String type = component.getType();
            g2d.setFont(new Font(component.getFontName(),
                    (component.isBold() ? Font.BOLD : 0) | (component.isItalic() ? Font.ITALIC : 0),
                    component.getFontSize()));
            switch (type) {
                case "title":
                    g2d.setColor(Color.RED);
                    break;
                case "transition":
                case "description":
                case "rightRole":
                    g2d.setColor(Color.GRAY);
                    break;
                case "recipient":
                    g2d.setColor(Color.RED);
                    break;
                case "award":
                case "owner":
                    g2d.setColor(Color.BLACK);
                    break;
            }
            g2d.drawString(component.getText(), component.getX(), component.getY());
        }

        // Vẽ logo nếu có
        if (logoPath != null) {
            try {
                BufferedImage logoImage = ImageIO.read(new File(logoPath));
                g2d.drawImage(logoImage, logoX, logoY, (int) logoWidth, (int) logoHeight, null);
            } catch (IOException e) {
                System.err.println("Không thể vẽ logo: " + e.getMessage());
            }
        }

        g2d.dispose();
        return image;
    }
}