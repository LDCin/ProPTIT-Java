package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomCertificate extends Certificate {
    private String ownerName;
    private Frame frame;

    public CustomCertificate(String recipientName, String awardName, String ownerName, Frame frame) {
        super(recipientName, awardName);
        this.ownerName = ownerName;
        this.frame = frame;
    }

    @Override
    public BufferedImage generateImage() {
        try {
            BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setPaint(Color.WHITE);
            g2d.fillRect(0, 0, 800, 600);

            // Vẽ khung
            g2d.setPaint(Color.decode(frame.getColor()));
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(50, 50, 700, 500);

            g2d.setPaint(Color.BLACK);
            try {
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
            } catch (Exception e) {
                g2d.setFont(new Font("Serif", Font.BOLD, 24)); // Fallback font
            }
            g2d.drawString("Người nhận: " + recipientName, 100, 100);
            g2d.drawString("Giải thưởng: " + awardName, 100, 150);
            g2d.drawString("Chủ nhiệm: " + ownerName, 100, 200);
            g2d.dispose();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}