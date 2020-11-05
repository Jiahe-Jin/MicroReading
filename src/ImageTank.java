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
public class ImageTank {
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
     * This method intends to manipulate an image into greyscale by calling its helper method.
     *
     * @param name the name (non-absolute path) of the image.
     * @throws IOException when fails to write the fail.
     */
    public void getGreyscale(String name) throws IOException {
        this.getGreyscale((Image) imageTable.get(name));
    }

    /**
     * The private helper method intends to manipulate an image into greyscale.
     *
     * @param image the Image Class of the specific image.
     * @throws IOException when fails to write the fail.
     */
    private void getGreyscale(Image image) throws IOException {
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
        ImageIO.write(buffer, image.name.split("[.]")[1], new File("grey_" + image.name));
        ImagePlus grayImg = new ImagePlus("grey", buffer);
        grayImg.show();

    }


    /**
     * This inner class that will include all information regarding this image before and after
     * analysis.
     *
     * @author Jiahe Jin
     */
    protected class Image {
        String name;
        Date date;
        BufferedImage bufferedImage;
        ImagePlus imagePlus;

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
            imagePlus = new ImagePlus(name);
            bufferedImage = ImageIO.read(new File(name));
            date = new Date();

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
