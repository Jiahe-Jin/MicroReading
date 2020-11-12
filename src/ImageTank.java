import ij.ImagePlus;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

/**
 * This class intends to read the image and give out several different methods to process the image.
 *
 * @author Jiahe Jin
 */
public class ImageTank implements java.io.Serializable {
    private Hashtable imageTable;


    /**
     * The default constructor will build up the hashtable for image storage.
     */
    public ImageTank() {
        imageTable = new Hashtable();
    }

    /**
     * This method intends to store the image into the hashtable by its name (non-absolute path).
     *
     * @param name the name (non-absolute path) of the image.
     * @return true if successfully adding an image; false if fails to add an image.
     */
    public boolean storeImage(String name) {
        try {
            imageTable.put(name, new Image(name));
            return true;
        } catch (Exception e) {
            System.out.println("Fails to store an image to the imageTable");
        }
        return false;
    }

    /**
     * This method intends to get the specific Image Class of an image by calling its name.
     *
     * @param name the name (non-absolute path) of the image.
     * @return the Image Class of  image.
     */
    public Image getImage(String name) {
        return (Image) imageTable.get(name);
    }

    /**
     * This method intends to see if the imageTable contains the same image
     *
     * @param name the name (non-absolute path) of the image.
     * @return true if the image is existed in the imageTable; false if the image is not yet stored.
     */
    public boolean containImage(String name) {
        return imageTable.containsKey(name);
    }

    /**
     * This method intends to remove the image from the imageTable.
     *
     * @param name the name (non-absolute path) of the image.
     * @return true if the image is successfully removed; false if the image can not be removed or
     * not existed.
     */
    public boolean deleteImage(String name) {
        try {
            imageTable.remove(name);
            return true;
        } catch (Exception e) {
            System.out.println("Unable to delete Image.");
        }
        return false;
    }

    /**
     * This method intends to open the specific image by its name to show it.
     *
     * @param name the name (non-absolute path) of the image.
     */
    public void showImage(String name) {
        ((Image) imageTable.get(name)).imagePlus.show();
    }

    /**
     * This method intends to manipulate an image into grayscale by calling its helper method.
     *
     * @param name the name (non-absolute path) of the image.
     * @throws IOException when fails to write the fail.
     */
    public void getGrayscale(String name) throws IOException {
        this.getGrayscale((Image) imageTable.get(name));
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
     */
    public void colorComparison(Image image1, Image image2) {
        int width1 = image1.bufferedImage.getWidth();
        int width2 = image2.bufferedImage.getWidth();
        int height1 = image1.bufferedImage.getHeight();
        int height2 = image2.bufferedImage.getHeight();
        double total_pixels = 0; // total number of pixels

        double difference = 0; // The different pixel
        if (width1 == width2 && height1 == height2) {
            for (int y = 0; y < height1; y++) {
                for (int x = 0; x < width1; x++) {
                    int rgb1 = image1.bufferedImage.getRGB(x, y);
                    int rgb2 = image2.bufferedImage.getRGB(x, y);
                    int red1 = (rgb1 >> 16) & 0xff;
                    int green1 = (rgb1 >> 8) & 0xff;
                    int blue1 = (rgb1 >> 0) & 0xff;
                    int red2 = (rgb2 >> 16) & 0xff;
                    int green2 = (rgb2 >> 8) & 0xff;
                    int blue2 = (rgb2 >> 0) & 0xff;
                    difference += Math.abs(red2 - red1) ^ 2;
                    difference += Math.abs(green2 - green1) ^ 2;
                    difference += Math.abs(blue2 - blue1) ^ 2;
                }
            }
            total_pixels = height1 * width1 * 3;
        } else {
            // Smaller Height and Width Selector
            int smallerHeight = 0;
            int smallerWidth = 0;
            if (height1 < height2) {
                smallerHeight = height1;
            } else {
                smallerHeight = height2;
            }
            if (width1 < width2) {
                smallerWidth = width1;
            } else {
                smallerWidth = width2;
            }

            for (int y = (int) (0.1 * smallerHeight); y < 0.9 * smallerHeight; smallerHeight++) {
                for (int x = (int) (0.1 * smallerWidth); x < 0.9 * smallerWidth; smallerWidth++) {
                    int rgb1 = image1.bufferedImage.getRGB(x, y);
                    int rgb2 = image2.bufferedImage.getRGB(x, y);
                    int red1 = (rgb1 >> 16) & 0xff;
                    int green1 = (rgb1 >> 8) & 0xff;
                    int blue1 = (rgb1 >> 0) & 0xff;
                    int red2 = (rgb2 >> 16) & 0xff;
                    int green2 = (rgb2 >> 8) & 0xff;
                    int blue2 = (rgb2 >> 0) & 0xff;
                    difference += Math.abs(red2 - red1) ^ 2;
                    difference += Math.abs(green2 - green1) ^ 2;
                    difference += Math.abs(blue2 - blue1) ^ 2;
                }
            }
            total_pixels = smallerHeight * smallerWidth * 3;
        }
        // The final difference
        double distance = Math.sqrt(difference);
        // Normalizing the value of different pixels
        double avg_difference_pixel = distance / total_pixels;
        // There are 255 values of pixels in total
        double percentage = (avg_difference_pixel / 255) * 100;

        System.out.println("The difference: " + percentage + "%.");
    }


    /**
     * This inner class that will include all information regarding this image before and after
     * analysis.
     *
     * @author Jiahe Jin
     */
    protected class Image {
        protected String name;
        protected Date date;
        protected BufferedImage bufferedImage;
        protected ImagePlus imagePlus;

        /**
         * The non-default constructor is used to construct the image class with its name and automatically
         * sets the current date. Also, it will store the ImagePlus and bufferedImage class of an image to
         * be prepared in future analysis.
         *
         * @param name the full name of the image (includes .jpg or .tiff ...)
         * @throws IOException when fails to read the file name.
         */
        protected Image(String name) throws IOException {
            this.name = name;
            this.imagePlus = new ImagePlus(name);
            this.bufferedImage = ImageIO.read(new File(name));
            this.date = new Date();

        }

        /**
         * The mutator of name.
         *
         * @param name the name of the image
         */
        private void setName(String name) {
            this.name = name;
        }

        /**
         * The mutator of name.
         *
         * @param year  the year of this image
         * @param month the month of this image
         * @param day   the day of this image
         */
        private void setDate(int year, int month, int day) {
            this.date = new Date(year, month, day);
        }
    }

}
