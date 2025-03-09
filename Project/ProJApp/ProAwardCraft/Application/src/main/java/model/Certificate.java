package model;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Certificate {
    protected String recipientName;
    protected String awardName;

    public Certificate(String recipientName, String awardName) {
        this.recipientName = recipientName;
        this.awardName = awardName;
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