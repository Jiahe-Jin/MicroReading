import java.io.Serializable;
import java.util.ArrayList;

/**
 * The DataAnalysis Class is very like the ImageTable to store the information node of each Comparison.
 */
public class DataAnalysis implements Serializable {
    protected ArrayList<CompNode> compList;

    /**
     * The default constructor just create the ArrayList for this class.
     */
    public DataAnalysis() {
        compList = new ArrayList<>();
    }


    /**
     * The method intends to store the compNode into the compList by giving two images and calculated distance.
     *
     * @param control  the control Image Object.
     * @param target   the target Image Object.
     * @param distance the Euclidean Distance between these two images.
     * @return true if the image is successfully added in, or false if the Image has already in the
     * compList.
     */
    public boolean storeComparison(Image control, Image target, double distance) {
        return this.compList.add(new CompNode(control, target, distance));
    }

    public boolean updateCompNode(CompNode compNode){
        for(CompNode node : compList){
            if(node.equals(compNode)){
                node = compNode;
                return true;
            }
        }
        return false;
    }

    public boolean removeCompNode(CompNode compNode){
        return this.compList.remove(compNode);
    }

    public boolean storeTarget(Image target) {
        return this.compList.add(new CompNode(target));
    }

    /**
     * The method intends to remove the compNode from the compList by entering the target's name
     *
     * @param name the name of the compNode (target).
     * @return true if the image is successfully removed, or false if the compNode is not removed.
     */
    public boolean removeComparison(String name) {
        return this.compList.remove(this.get(name));
    }

    /**
     * The method intends to find out and print out the compNode Object by entering its target's name.
     *
     * @param name the name of the compNode (target).
     * @return the compNode Object.
     */
    public CompNode get(String name) {
        for (int i = 0; i < compList.size(); i++) {
            if (compList.get(i).name.equalsIgnoreCase(name)) {
                return this.compList.get(i);
            }
        }
        return null;
    }

    /**
     * The method intends to give out the number of compNode Object being stored in the compList.
     *
     * @return the number of compNode Object being stored in the compNode.
     */
    public int size() {
        return this.compList.size();
    }

    /**
     * The method intends to clear all of items stored in the compList.
     */
    public void clear() {
        this.compList.clear();
    }

    public boolean contains(String name) {
        return this.compList.contains(this.get(name));
    }

}
