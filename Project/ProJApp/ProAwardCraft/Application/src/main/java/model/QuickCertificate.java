package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QuickCertificate extends Certificate {
    public QuickCertificate(String recipientName, String awardName) {
        super(recipientName, awardName);
    }

    @Override
    public BufferedImage generateImage() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Người nhận: " + recipientName, 100, 100);
        g2d.drawString("Giải thưởng: " + awardName, 100, 150);
        g2d.dispose();
        return image;
    }
}