import java.io.*;

/**
 * This class intends to load data from from the ImageTank Object and write it down in the cache
 * under the same folder.
 */
public class DataStorage implements Serializable {
    static final String ImageTableCache = "./.image_table_cache.micro_reading_app_cache";
    static final String DataAnalysisCache = "./.data_analysis_cache.micro_reading_app_cache";
    protected ImageTable imageTable;
    protected DataAnalysis dataAnalysis;

    /**
     * The default constructor to load all images into the imageTable.
     */
    public DataStorage() throws IOException {
        this.imageTable = this.loadImageTable();
        this.dataAnalysis = this.loadDataAnalysis();

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
     * load ImageTable from cache
     *
     * @return the ImageTable
     */
    static DataAnalysis loadDataAnalysis() {
        DataAnalysis dataAnalysis;
        dataAnalysis = (DataAnalysis) readObject(DataAnalysisCache);
        if (dataAnalysis == null) {
            dataAnalysis = new DataAnalysis();
            writeObject(dataAnalysis, ImageTableCache);
        }
        return dataAnalysis;
    }

    /**
     * The method to write the DataAnalysis
     *
     * @param analysis the DataAnalysis Object
     */
    static void writeDataAnalysis(DataAnalysis analysis) {
        writeObject(analysis, DataAnalysisCache);
    }

    /**
     * load DataAnalysis from cache
     *
     * @return the DataAnalysis
     */
    static ImageTable loadImageTable() {
        ImageTable imageTable;
        imageTable = (ImageTable) readObject(ImageTableCache);
        if (imageTable == null) {
            imageTable = new ImageTable();
            writeObject(imageTable, ImageTableCache);
        }
        return imageTable;
    }

    /**
     * The method to write the ImageTable
     *
     * @param table the ImageTable Object
     */
    static void writeImageTable(ImageTable table) {
        writeObject(table, ImageTableCache);
    }

    /**
     * The method will store the image and write down to the cache to record.
     *
     * @param name the name of the Image.
     * @return true if the image is successfully stored, or false if the image is not stored.
     * @throws IOException for writing image.
     */
    public boolean storeImage(String name) throws IOException {
        if (!this.imageTable.contains(name)) {
            this.imageTable.storeImage(name);
            writeImageTable(this.imageTable);
            return true;
        }
        return false;
    }

    /**
     * The method will store the compNode and write down to the cache to record.
     *
     * @param control  the control Image Object.
     * @param target   the target Image Object.
     * @param distance the Euclidean Distance between these two images.
     * @return true if the image is successfully stored, or false if the compNode is not stored.
     * @throws IOException for writing image.
     */
    public boolean storeComparison(Image control, Image target, double distance)
        throws IOException {
        if (!this.dataAnalysis.contains(target.name)) {
            this.dataAnalysis.storeComparison(control, target, distance);
            writeDataAnalysis(this.dataAnalysis);
            return true;
        }
        return false;
    }
}
