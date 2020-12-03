import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The ImageTable contains methods which are related to image storage in this application. To implements
 * the Serializable, parameters of all of being stored images will be stored.
 */
public class ImageTable implements Serializable {
    protected ArrayList<Image> imageList;

    public ImageTable() {
        imageList = new ArrayList<>();
    }

    /**
     * The method intends to store the picture into the imageList by entering its name.
     *
     * @param name the name of the Image.
     * @return true if the image is successfully added in, or false if the Image has already in the
     * imageList.
     */
    public boolean storeImage(String name) throws IOException {
        return this.imageList.add(new Image(name));
    }

    /**
     * The method intends to remove the image from the imageList by entering its name
     *
     * @param name the name of the Image.
     * @return true if the image is successfully removed, or false if the Image is not removed.
     */
    public boolean removeImage(String name) {
        return this.imageList.remove(this.get(name));
    }

    /**
     * The method intends to find out and print out the Image Object by entering its name.
     *
     * @param name the name of the Image.
     * @return the Image Object.
     */
    public Image get(String name) {
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).name.equalsIgnoreCase(name)) {
                return this.imageList.get(i);
            }
        }
        return null;
    }

    /**
     * The method intends to give out the number of Image Object being stored in the imageList.
     *
     * @return the number of Image Object being stored in the imageList.
     */
    public int size() {
        return this.imageList.size();
    }

    /**
     * The method intends to clear all of items stored in the imageList.
     */
    public void clear() {
        this.imageList.clear();
    }

    public boolean contains(String name) {
        return this.imageList.contains(this.get(name));
    }
}
