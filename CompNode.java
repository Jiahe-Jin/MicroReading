import java.io.Serializable;

/**
 * The node class of being used in the DataAnalysis.
 */
public class CompNode implements Serializable, Comparable<CompNode>{
    protected String name;
    protected Image control; // The control group of this test
    protected Image target; // The target test
    protected double distance; // The Euclidean Distance between two Images
    protected double concentration; // The converted calcium concentration
    protected boolean standard; // To see if the compNode is one of standard members
    protected double rate; // The rate of algorithm
    protected double intercept; // The Intercept of algorithm
    protected double r2; // the r^2 of the standard line being used

    /**
     * The default constructor sets all knowing knowledge, the concentration level will be converted
     * once the method being implemented.
     *
     * @param control  the control Image Object.
     * @param target   the target Image Object.
     * @param distance the Euclidean Distance between these two images.
     */
    public CompNode(Image control, Image target, double distance) {
        this.name = target.name;
        this.control = control;
        this.target = target;
        this.distance = distance;
        this.standard = false;
    }

    /**
     * The simple constructor
     *
     * @param target   the target Image Object.
     */
    public CompNode(Image target) {
        this.name = target.name;
        this.target = target;
        this.standard = false;
        this.concentration = 0;
    }

    /**
     * This method intends to set the Concentration of target test object.
     *
     * @param concentration the concentration level of calcium for target image.
     */
    public void setConcentration(double concentration) {
        this.concentration = concentration;
        DataStorage.writeDataAnalysis(DataStorage.dataAnalysis);

    }

    public void convertToConcentration(){
        this.concentration = this.rate*this.distance + this.intercept;
    }

    /**
     * This method intends to set the standard
     *
     * @param ifStandard the boolean parameter to see if this object is one of standard members.
     */
    public void setStandard(boolean ifStandard) {
        this.standard = ifStandard;
        DataStorage.writeDataAnalysis(DataStorage.dataAnalysis);
    }

    public String toString() {
        return "[" + target.name + "] --> [Euclidean Distance]: " + distance + "   [concentration]: "
            + concentration;
    }

    @Override public int compareTo(CompNode o) {
        int result = Double.compare(this.concentration, o.concentration);
        if(result == 0){
            result = Double.compare(this.distance, o.distance);
        }
        return result;
    }
}
