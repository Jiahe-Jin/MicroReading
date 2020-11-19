import ij.ImagePlus;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class intends to read the image and give out several different methods to process the image.
 *
 * @author Jiahe Jin
 */
public class ImageProcessor {

    public ImageProcessor() {

    }

    public static void main(String[] args) throws IOException {
        DataStorage storage = new DataStorage();
        ImageTable table = storage.imageTable;
        DataAnalysis analysis = storage.dataAnalysis;
        storage.storeImage("pad1_ph4.5.png");
        storage.storeImage("pad1_ph5.0.png");
        storage.storeImage("pad1_ph5.5.png");
        storage.storeImage("pad1_ph6.0.png");
        storage.storeImage("pad1_ph7.0.png");
        storage.storeImage("pad1_ph7.5.png");
        storage.storeImage("pad1_ph8.0.png");
        storage.storeImage("pad1_ph8.5.png");
        storage.storeImage("pad1_ph9.0.png");
        storage.storeImage("pad1_ph9.5.png");
        storage.storeImage("pad1_ph10.0.png");

        Image imageA = table.get("pad1_ph4.5.png");
        Image imageB = table.get("pad1_ph5.0.png");
        Image imageC = table.get("pad1_ph5.5.png");
        Image imageD = table.get("pad1_ph6.0.png");
        Image imageE = table.get("pad1_ph7.0.png");
        Image imageF = table.get("pad1_ph7.5.png");
        Image imageG = table.get("pad1_ph8.0.png");
        Image imageH = table.get("pad1_ph8.5.png");
        Image imageI = table.get("pad1_ph9.0.png");
        Image imageJ = table.get("pad1_ph9.5.png");
        Image imageK = table.get("pad1_ph10.0.png");



        ImageProcessor processor = new ImageProcessor();

        storage.storeComparison(imageA, imageB, processor.colorComparison(imageA, imageB));

        System.out.println(processor.getSummary(imageA, imageB));
        System.out.println(processor.getSummary(imageA, imageC));
        System.out.println(processor.getSummary(imageA, imageD));
        System.out.println(processor.getSummary(imageA, imageE));
        System.out.println(processor.getSummary(imageA, imageF));
        System.out.println(processor.getSummary(imageA, imageG));
        System.out.println(processor.getSummary(imageA, imageH));
        System.out.println(processor.getSummary(imageA, imageI));
        System.out.println(processor.getSummary(imageA, imageJ));
        System.out.println(processor.getSummary(imageA, imageK));
        System.out.println(processor.getSummary(imageA, imageA));



    }

    /**
     * The private helper method intends to manipulate an image into grayscale.
     *
     * @param image the Image Class of the specific image.
     * @throws IOException when fails to write the fail.
     */
    private void getGrayscale(Image image) throws IOException {
        BufferedImage buffer = image.bufferedImage;
        for (int y = 0; y < buffer.getHeight(); y++) {
            for (int x = 0; x < buffer.getWidth(); x++) {
                Color color = new Color(buffer.getRGB(x, y));
                int grayLevel = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int r = grayLevel;
                int g = grayLevel;
                int b = grayLevel;
                int rgb = (r << 16) | (g << 8) | b;
                buffer.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(buffer, image.name.split("[.]")[1], new File("gray_" + image.name));
        ImagePlus grayImg = new ImagePlus("gray", buffer);
        grayImg.show();
    }

    /**
     * This method intends to compare colors between two images by using RGB tuples to measure the
     * Euclidean distances in the color space. If dimensions of two images are equal, the whole images
     * are compared. However, if dimensions are  different, only centered 90% (widths and heights)
     * part of smaller dimension in two pictures are compared.
     *
     * @param image1 the Image Object of one of two compared images
     * @param image2 the Image Object of one of two compared images
     * @return the RGB Euclidean Distance
     */
    private double colorComparison(Image image1, Image image2) {
        int red1 = image1.red;
        int red2 = image2.red;
        int green1 = image1.green;
        int green2 = image2.green;
        int blue1 = image1.blue;
        int blue2 = image2.blue;

        double distance = Math.sqrt(
            Math.pow((red1 - red2), 2) + Math.pow((green1 - green2), 2) + Math
                .pow((blue1 - blue2), 2));

        return distance;
    }

    public String getSummary(Image image1, Image image2) {
        int width1 = image1.width;
        int width2 = image2.width;
        int height1 = image1.height;
        int height2 = image2.height;

        double distance = this.colorComparison(image1, image2);
        double maximum_difference = Math.sqrt(195075);
        double normal_difference = distance / maximum_difference;
        double percentage_difference = normal_difference * 100;

        return "[" + image1.name + "] " + " (" + image1.date + ") " + " The RGB: " + image1.red
            + ", " + image1.green + ", " + image1.blue + "  The Height: " + height1
            + "  The Width: " + width1 + "\n" + "[" + image1.name + "] " + " (" + image1.date + ") "
            + " The RGB: " + image2.red + ", " + image2.green + ", " + image2.blue
            + "  The Height: " + height2 + "  The Width: " + width2 + "\n"
            + "The RGB Euclidean Distance: " + distance + "\n" + "The Percent Difference: "
            + percentage_difference + "%." + "\n";
    }


}
