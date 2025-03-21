package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class CustomCertificate extends Certificate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ownerName;
    private Frame frame;
    private Point recipientPosition = new Point(0, 0);
    private Point awardPosition = new Point(0, 0);
    private Point ownerPosition = new Point(0, 0);
    private Rectangle recipientBounds;
    private Rectangle awardBounds;
    private Rectangle ownerBounds;
    private String fontName = "Arial";

    public CustomCertificate(String recipientName, String awardName, String ownerName, Frame frame) {
        super(recipientName, awardName);
        this.ownerName = ownerName;
        this.frame = frame != null ? frame : new Frame("Solid", "0x000000");
    }

    public void setRecipientName(String name) { this.recipientName = name; }
    public void setAwardName(String name) { this.awardName = name; }
    public void setOwnerName(String name) { this.ownerName = name; }
    public void setRecipientPosition(int x, int y) {
        this.recipientPosition.setLocation(x, y);
        updateBounds();
    }
    public void setAwardPosition(int x, int y) {
        this.awardPosition.setLocation(x, y);
        updateBounds();
    }
    public void setOwnerPosition(int x, int y) {
        this.ownerPosition.setLocation(x, y);
        updateBounds();
    }
    public Point getRecipientPosition() { return recipientPosition; }
    public Point getAwardPosition() { return awardPosition; }
    public Point getOwnerPosition() { return ownerPosition; }
    public String getRecipientName() { return recipientName; }
    public String getAwardName() { return awardName; }
    public String getOwnerName() { return ownerName; }

    public String getElementAt(int x, int y) {
        if (recipientBounds != null && recipientBounds.contains(x, y)) {
            System.out.println("Recipient detected at (" + x + ", " + y + ") within bounds: " + recipientBounds);
            return "recipient";
        } else if (awardBounds != null && awardBounds.contains(x, y)) {
            System.out.println("Award detected at (" + x + ", " + y + ") within bounds: " + awardBounds);
            return "award";
        } else if (ownerBounds != null && ownerBounds.contains(x, y)) {
            System.out.println("Owner detected at (" + x + ", " + y + ") within bounds: " + ownerBounds);
            return "owner";
        }
        System.out.println("No element detected at (" + x + ", " + y + ") - Recipient: " + recipientBounds + ", Award: " + awardBounds + ", Owner: " + ownerBounds);
        return null;
    }

    public void setFont(String fontName) {
        this.fontName = fontName != null ? fontName : "Arial";
    }

    @Override
    public BufferedImage generateImage() {
        int width = 500;
        int height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        try {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            java.awt.Color frameColor = frame != null && frame.getColor() != null ?
                    Color.decode(frame.getColor()) : Color.RED;
            g2d.setColor(frameColor);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(10, 10, width - 20, height - 20);

            int style = Font.PLAIN;
            if (isBold) style |= Font.BOLD;
            if (isItalic) style |= Font.ITALIC;
            Font font = new Font(fontName, style, fontSize);
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);

            String title = "GIẤY CHỨNG NHẬN";
            int titleWidth = metrics.stringWidth(title);
            g2d.setColor(Color.RED);
            g2d.drawString(title, (width - titleWidth) / 2, 50);

            String transition = "Được trao cho";
            int transitionWidth = metrics.stringWidth(transition);
            g2d.setColor(Color.GRAY);
            g2d.drawString(transition, (width - transitionWidth) / 2, 80);

            String recipientText = recipientName != null ? recipientName : "";
            int recipientWidth = metrics.stringWidth(recipientText);
            if (recipientPosition.x == 0 && recipientPosition.y == 0) {
                recipientPosition.setLocation((width - recipientWidth) / 2, 120);
            }
            g2d.setColor(Color.RED);
            g2d.drawString(recipientText, recipientPosition.x, recipientPosition.y);

            String description = "là thành viên xuất sắc của CLB Lập trình PTIT.";
            int descWidth = metrics.stringWidth(description);
            g2d.setColor(Color.GRAY);
            g2d.drawString(description, (width - descWidth) / 2, 160);

            String awardText = "Giải thưởng: " + (awardName != null ? awardName : "");
            int awardWidth = metrics.stringWidth(awardText);
            if (awardPosition.x == 0 && awardPosition.y == 0) {
                awardPosition.setLocation((width - awardWidth) / 2, 200);
            }

            String rightSignature = ownerName != null ? ownerName : "EFGH";
            String rightRole = "CHỦ NHIỆM CLB";
            int rightWidth = metrics.stringWidth(rightSignature);

            g2d.setColor(Color.BLACK);
            g2d.drawString(rightSignature, width - 50 - rightWidth, height - 30);
            g2d.setColor(Color.GRAY);
            g2d.drawString(rightRole, width - 50 - metrics.stringWidth(rightRole), height - 50);

            g2d.setColor(Color.RED);
            int sealSize = 40;
            g2d.fillOval((width - sealSize) / 2, height - 70, sealSize, sealSize);
            g2d.setColor(Color.WHITE);
            g2d.fillRect((width - sealSize / 2) / 2, height - 60, sealSize / 2, 10);
            g2d.fillRect((width - sealSize / 2) / 2, height - 50, sealSize / 2, 10);

            int textHeight = metrics.getHeight();
            recipientBounds = new Rectangle(recipientPosition.x, recipientPosition.y - metrics.getAscent(), recipientWidth, textHeight);
            awardBounds = new Rectangle(awardPosition.x, awardPosition.y - metrics.getAscent(), awardWidth, textHeight);
            ownerBounds = new Rectangle(ownerPosition.x, ownerPosition.y - metrics.getAscent(), rightWidth, textHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawString(awardText, awardPosition.x, awardPosition.y);

            // Vẽ các phần tử tùy chỉnh
            for (CustomElement element : customElements) {
                if ("text".equals(element.getType())) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(element.getText(), element.getPosition().x, element.getPosition().y);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi tạo hình ảnh: " + e.getMessage());
            e.printStackTrace();
        } finally {
            g2d.dispose();
        }
        return image;
    }

    private void updateBounds() {
        if (recipientBounds != null && awardBounds != null && ownerBounds != null) {
            Graphics2D g2d = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
            int style = Font.PLAIN;
            if (isBold) style |= Font.BOLD;
            if (isItalic) style |= Font.ITALIC;
            Font font = new Font(fontName, style, fontSize);
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);

            String recipientText = "Người nhận: " + (recipientName != null ? recipientName : "");
            String awardText = "Giải thưởng: " + (awardName != null ? awardName : "");
            String ownerText = "Chủ nhiệm: " + (ownerName != null ? ownerName : "");
            int textHeight = metrics.getHeight();

            int recipientWidth = metrics.stringWidth(recipientText);
            int awardWidth = metrics.stringWidth(awardText);
            int ownerWidth = metrics.stringWidth(ownerText);
            recipientBounds.setRect(recipientPosition.x, recipientPosition.y - metrics.getAscent(), recipientWidth, textHeight);
            awardBounds.setRect(awardPosition.x, awardPosition.y - metrics.getAscent(), awardWidth, textHeight);
            ownerBounds.setRect(ownerPosition.x, ownerPosition.y - metrics.getAscent(), ownerWidth, textHeight);

            g2d.dispose();
        }
    }
}