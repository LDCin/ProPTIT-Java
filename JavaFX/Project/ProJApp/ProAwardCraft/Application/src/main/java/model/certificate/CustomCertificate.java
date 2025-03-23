package model.certificate;

import model.element.TextComponent;
import model.element.Element;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CustomCertificate extends Certificate {
    private static final long serialVersionUID = 1L;

    private String ownerName;
    private String frameColor;
    private String backgroundColor;

    public CustomCertificate(String recipientName, String awardName, String ownerName, String frameColor, String backgroundColor) {
        super(recipientName, awardName);
        this.ownerName = ownerName;
        this.frameColor = frameColor;
        this.backgroundColor = backgroundColor;
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

        for (Element element : getElements()) {
            if ("image".equals(element.getType())) {
                try {
                    BufferedImage elementImage = ImageIO.read(new File(element.getPath()));
                    g2d.drawImage(elementImage, (int) element.getX(), (int) element.getY(),
                            (int) element.getWidth(), (int) element.getHeight(), null);
                } catch (IOException e) {
                    System.err.println("Không thể vẽ element: " + e.getMessage());
                }
            }
        }

        if (logoPath != null) {
            try {
                File logoFile = new File(logoPath);
                if (!logoFile.exists()) {
                    System.err.println("File logo không tồn tại: " + logoPath);
                } else {
                    BufferedImage logoImage = ImageIO.read(logoFile);
                    int scaledWidth = (int) (logoWidth / 4);
                    int scaledHeight = (int) (logoHeight / 4);
                    g2d.drawImage(logoImage, logoX, logoY, scaledWidth, scaledHeight, null);
                }
            } catch (IOException e) {
                System.err.println("Không thể vẽ logo: " + e.getMessage());
            }
        }

        g2d.dispose();
        return image;
    }
}