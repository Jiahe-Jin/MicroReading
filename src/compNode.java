import java.io.Serializable;

/**
 * The node class of being used in the DataAnalysis.
 */
public class compNode implements Serializable {
    protected String name;
    protected Image control; // The control group of this test
    protected Image target; // The target test
    protected double distance; // The Euclidean Distance between two Images
    protected double concentration; // The converted calcium concentration

    /**
     * The default constructor sets all knowing knowledge, the concentration level will be converted
     * once the method being implemented.
     *
     * @param control  the control Image Object.
     * @param target   the target Image Object.
     * @param distance the Euclidean Distance between these two images.
     */
    public compNode(Image control, Image target, double distance) {
        this.name = target.name;
        this.control = control;
        this.target = target;
        this.distance = distance;
    }

    /**
     * This method intends to set the Concentration of target test object.
     *
     * @param concentration the concentration level of calcium for target image.
     */
    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }
}
