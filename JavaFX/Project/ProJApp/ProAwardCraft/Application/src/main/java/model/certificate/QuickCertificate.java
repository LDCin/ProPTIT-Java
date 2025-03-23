package model.certificate;

import model.template.Template;
import model.element.TextComponent;
import model.element.Element;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class QuickCertificate extends Certificate {
    private static final long serialVersionUID = 1L;

    private String frameColor;
    private String backgroundColor;

    public QuickCertificate(String recipientName, String awardName, Template template) {
        super(recipientName, awardName);
        this.frameColor = template.getFrameColor();
        this.backgroundColor = template.getBackgroundColor();

        getTextComponents().clear();
        for (TextComponent component : template.getTextComponents().values()) {
            TextComponent newComponent = new TextComponent(
                    component.getType(),
                    component.getText(),
                    component.getAlignment(),
                    component.getX(),
                    component.getY()
            );
            newComponent.setFont(component.getFontName(), component.getFontSize(), component.isBold(), component.isItalic(), component.getAlignment());
            getTextComponents().add(newComponent);
        }

        setTextComponent("recipient", recipientName);
        setTextComponent("award", "Giải thưởng: " + awardName);
        setTextComponent("owner", "EFGH");

        String defaultLogoPath = "Application/src/main/logo/default_logo.png";
        try {
            if (defaultLogoPath != null) {
                this.logoPath = defaultLogoPath;
                BufferedImage logoImage = ImageIO.read(new File(logoPath));
                double maxWidth = 480;
                double maxHeight = 280;
                double widthRatio = maxWidth / logoImage.getWidth();
                double heightRatio = maxHeight / logoImage.getHeight();
                double scale = Math.min(widthRatio, heightRatio);
                // Giảm kích thước logo đi 4 lần ngay từ đầu
                this.logoWidth = logoImage.getWidth() * scale;
                this.logoHeight = logoImage.getHeight() * scale;
            } else {
                System.err.println("Không tìm thấy tài nguyên logo mặc định: " + defaultLogoPath);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập logo mặc định: " + e.getMessage());
            e.printStackTrace();
        }
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

    public void setRecipientName(String recipientName) {
        setTextComponent("recipient", recipientName);
    }

    public void setAwardName(String awardName) {
        setTextComponent("award", "Giải thưởng: " + awardName);
    }

    public void setRecipientPosition(int x, int y) {
        setTextPosition("recipient", x, y);
    }

    public void setAwardPosition(int x, int y) {
        setTextPosition("award", x, y);
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

        g2d.setColor(Color.decode(backgroundColor));
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
                BufferedImage logoImage;
                if (logoPath.startsWith("file:") || logoPath.startsWith("jar:")) {
                    // Nếu logoPath là tài nguyên trong JAR
                    java.net.URL logoUrl = new java.net.URL(logoPath);
                    logoImage = ImageIO.read(logoUrl);
                } else {
                    File logoFile = new File(logoPath);
                    if (!logoFile.exists()) {
                        System.err.println("File logo không tồn tại: " + logoPath);
                        return image;
                    }
                    logoImage = ImageIO.read(logoFile);
                }
                // Sử dụng kích thước và vị trí đã lưu
                int scaledWidth = (int) logoWidth;
                int scaledHeight = (int) logoHeight;
                g2d.drawImage(logoImage, logoX, logoY, scaledWidth, scaledHeight, null);
            } catch (Exception e) {
                System.err.println("Không thể vẽ logo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        g2d.dispose();
        return image;
    }
}