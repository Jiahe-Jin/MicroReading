import java.io.IOException;
/**
 * The temporally test class that is used to see if the code can store and load the picture and have
 * a function to manipulate them into greyscale.
 */
public class Test {

    /**
     * You just need to put the full name of the picture, and The location of the picture should
     * just under the MicroReading Directory. You can try any kinds of format of the picture (png,
     * jpeg, jpg, tiff, and ...)
     */
    public static void main(String[] args) throws IOException {
        ImageTank myImages = new ImageTank();
        myImages.storeImage("heart.png");
        myImages.getGrayscale("heart.png");
    }

}
