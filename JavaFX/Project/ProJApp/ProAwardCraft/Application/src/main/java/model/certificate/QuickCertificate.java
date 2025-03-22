package model.certificate;

import model.template.Template;
import model.element.TextComponent;

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
        // Áp dụng thuộc tính từ Template
        this.frameColor = template.getFrameColor();
        this.backgroundColor = template.getBackgroundColor();

        // Xóa các thành phần văn bản mặc định và sử dụng từ Template
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

        // Cập nhật nội dung cho recipient và award
        setTextComponent("recipient", recipientName);
        setTextComponent("award", "Giải thưởng: " + awardName);
        setTextComponent("owner", "EFGH");

        // Thiết lập logo mặc định
        String defaultLogoPath = "images/logo/default_logo.png";
        try {
            System.out.println("Đang tìm logo mặc định tại: " + defaultLogoPath);
            java.net.URL logoUrl = getClass().getClassLoader().getResource(defaultLogoPath);
            if (logoUrl == null) {
                System.err.println("Không tìm thấy logo mặc định tại: " + defaultLogoPath);
                System.err.println("Hãy đảm bảo file 'default_logo.png' được đặt trong thư mục 'src/main/resources/images/'");
                System.err.println("Kiểm tra thư mục 'target/classes/images/' sau khi build để đảm bảo file đã được sao chép.");
            } else {
                this.logoPath = logoUrl.toExternalForm();
                System.out.println("Đã tìm thấy logo mặc định tại: " + this.logoPath);
                BufferedImage logoImage = ImageIO.read(logoUrl);
                if (logoImage == null) {
                    System.err.println("Không thể đọc file logo mặc định từ tài nguyên: " + defaultLogoPath);
                    this.logoPath = null;
                } else {
                    double maxWidth = 480;
                    double maxHeight = 280;
                    double widthRatio = maxWidth / logoImage.getWidth();
                    double heightRatio = maxHeight / logoImage.getHeight();
                    double scale = Math.min(widthRatio, heightRatio);
                    this.logoWidth = logoImage.getWidth() * scale;
                    this.logoHeight = logoImage.getHeight() * scale;
                    System.out.println("Đã thiết lập logo mặc định với kích thước: " + this.logoWidth + "x" + this.logoHeight);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập logo mặc định từ tài nguyên: " + e.getMessage());
            e.printStackTrace();
            this.logoPath = null;
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

        // Sử dụng backgroundColor để vẽ nền
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