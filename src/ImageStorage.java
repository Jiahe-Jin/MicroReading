import java.io.*;

/**
 * This class intends to load data from from the ImageTank Object and write it down in the cache
 * under the same folder.
 */
public class ImageStorage {
    static final String ImageTankCache = "./.image_tank_cache.micro_reading_app_cache";
    protected ImageTank imageTank;

    /**
     * The default constructor to load all images into the imageTable.
     * */
    public ImageStorage() throws IOException {
        this.imageTank = this.loadImageTank();
    }

    /**
     * write an object to a file using serialization.
     *
     * @param obj      the object written
     * @param filename path of the file that object write to
     */
    static void writeObject(Serializable obj, String filename) {
        try {
            FileOutputStream fo = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(obj);
            out.close();
            fo.close();
        } catch (IOException e) {
            System.out.println("error when write the cache: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * read an object from a file
     *
     * @param filename path of the file read from
     * @return the object
     */
    static Object readObject(String filename) {
        Object ret = null;
        try {
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fi);
            ret = in.readObject();
            in.close();
            fi.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return ret;
    }

    /**
     * delete a file
     *
     * @param filename the file name
     */
    static void deleteFile(String filename) {
        File file = new File(filename);
        file.delete();
    }

    /**
     * load WeatherTree from cache
     *
     * @return the WeatherTree
     * @throws IOException when failing to creating the WebReading Object
     */
    static ImageTank loadImageTank() throws IOException {
        ImageTank webReading;
        webReading = (ImageTank) readObject(ImageTankCache);
        if (webReading == null) {
            webReading = new ImageTank();
            writeObject(webReading, ImageTankCache);
        }
        return webReading;
    }

}
