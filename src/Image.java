import ij.ImagePlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * This inner class that will include all information regarding this image before and after
 * analysis.
 *
 * @author Jiahe Jin
 */
public class Image implements java.io.Serializable {
    protected String name;
    protected Date date;
    protected transient BufferedImage bufferedImage;
    protected transient ImagePlus imagePlus;
    protected int red;
    protected int green;
    protected int blue;
    protected int height;
    protected int width;

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
        this.height = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();
        this.red = this.rgbConverter("red", bufferedImage);
        this.green = this.rgbConverter("green", bufferedImage);
        this.blue = this.rgbConverter("blue", bufferedImage);
    }

    /**
     * The mutator of name.
     *
     * @param name the name of the image
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * The mutator of name.
     *
     * @param year  the year of this image
     * @param month the month of this image
     * @param day   the day of this image
     */
    protected void setDate(int year, int month, int day) {
        this.date = new Date(year, month, day);
    }

    protected void setRed(int redValue){
        this.red = redValue;
    }

    protected void setGreen(int greenValue){
        this.green = greenValue;
    }

    protected void setBlue(int blueValue){
        this.blue = blueValue;
    }

    private int rgbConverter(String rgb, BufferedImage image){
        int[] dataBuffInt = image.getRGB(5,5,this.width-5,this.height-5,null, 0,this.width-5);
        Color color = new Color(dataBuffInt[100]);
        if(rgb.equalsIgnoreCase("red")){
            return color.getRed();
        }else if (rgb.equalsIgnoreCase("green")){
            return color.getGreen();
        }else if (rgb.equalsIgnoreCase("blue")){
            return color.getBlue();
        }else{
            return -1; // The false situation
        }
    }
}
