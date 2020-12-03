import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Launch {
    private DataStorage storage;
    private ImageProcessor processor;

    /**
     * The default constructor defines the DataStorage and ImageProcessor Objects
     */
    public Launch() throws IOException {
        storage = new DataStorage();
        processor = new ImageProcessor();
        this.startPage();
    }

    public static void main(String[] args) throws IOException {
        Launch launch = new Launch();
    }

    /**
     * The starter method intends to give out the introduction to the user in GUI
     */
    public void startPage() throws IOException {
        System.out.println("==================================================");
        System.out.println("              Welcome to MicroReading             ");
        System.out.println("==================================================");
        System.out.println("The MicroReading intends to give out the complete");
        System.out.println("analysis of the Concentrations of Calcium tested ");
        System.out.println("in the urine by the \"Urine Stone Risk Detector\"");
        System.out.println("==================================================");
        System.out.println("[a] or [add] -- adding the image by its name.");
        System.out.println("[d] or [delete] -- deleting the image by its name.");
        System.out.println("[l] or [list] -- listing all images has been added in.");
        System.out.println("[u] or [update] -- update all images from the lib fold.");
        System.out.println("[c] or [clear] -- clear all images.");
        System.out.println("[q] or [quit] -- quit the program.");
        System.out.println("[*] -- enter any other keys can go back to welcome page.");
        System.out.println("[s] or [standard] -- set standard line for the testing.");
        System.out.println("[r] or [read] -- read contents of the standard line.");
        System.out.println("[reset] -- reset standard line for the testing.");
        System.out.println("===================================================");
        System.out.println("[process] -- process the chosen image to read Calcium Value.");
        System.out.println("[draw] -- draw the result of analyzed Calcium Value.");
        System.out.println("[RESTART] -- restart all analyzed tests, including the standard line.");
        System.out.println("==================================================");

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the command you want: ");
        String command = input.nextLine();
        this.launchPad(command);
    }

    /**
     * The launchPad intends to distinguish the command users entered in.
     */
    public void launchPad(String command) throws IOException {
        Scanner input = new Scanner(System.in);
        if (command.equalsIgnoreCase("a") || command.equalsIgnoreCase("add")) {
            this.command_add();
        } else if (command.equalsIgnoreCase("d") || command.equalsIgnoreCase("delete")) {
            this.command_delete();
        } else if (command.equalsIgnoreCase("l") || command.equalsIgnoreCase("list")) {
            this.command_list();
        } else if (command.equalsIgnoreCase("u") || command.equalsIgnoreCase("update")) {
            this.command_update();
        } else if (command.equalsIgnoreCase("c") || command.equalsIgnoreCase("clear")) {
            this.command_remove();
        } else if (command.equalsIgnoreCase("s") || command.equalsIgnoreCase("standard")) {
            this.command_standard();
        } else if (command.equalsIgnoreCase("r") || command.equalsIgnoreCase("read")) {
            this.command_read_standard();
        } else if (command.equalsIgnoreCase("reset")) {
            this.command_reset_standard();
        } else if (command.equalsIgnoreCase("RESTART")) {
            this.command_clear_analysis();
        } else if (command.equalsIgnoreCase("process")) {
            this.command_process();
        } else if (command.equalsIgnoreCase("draw")) {
            this.command_draw();
        } else if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
            this.quit();
        } else {
            this.startPage();
        }

        String newCommand = input.nextLine();
        this.launchPad(newCommand);
    }

    private void command_add() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the image you want to add ");
        System.out.println("and the image has been put in the lib folder: ");
        String name = input.nextLine();
        try {
            if (storage.imageTable.contains(name)) {
                System.out.println("Sorry, the image has already being added.");
            } else {
                storage.storeImage(name);
                System.out.println("The " + name + " is successfully added in.");
            }
        } catch (Exception e) {
            System.out.println("Sorry, the image was not in the lib folder.");
            this.command_add();
        }
    }

    private void command_delete() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the image you want to delete");
        String name = input.nextLine();
        try {
            if (!storage.imageTable.contains(name)) {
                System.out.println("Sorry, the image doesn't exist in the system.");
            } else {
                storage.deleteImage(name);
                System.out.println("The " + name + " is successfully deleted.");
            }
        } catch (Exception e) {
            System.out.println("Sorry, the image was not in the lib folder.");
            this.command_delete();
        }
    }

    private void command_list() {
        for (Image image : storage.imageTable.imageList) {
            System.out.println("[" + image.date + "]  " + image.name);
        }
        System.out.println("Total Images: " + storage.imageTable.size());
    }

    private void command_update() throws IOException {
        File folder = new File("./lib");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().equalsIgnoreCase(".DS_Store")) {
                listOfFiles[i].delete();
            }
            try {
                if (listOfFiles[i].isFile()) {
                    if (storage.imageTable.contains(listOfFiles[i].getName())) {
                        System.out
                            .println("[" + listOfFiles[i].getName() + "] " + "was already in.");
                    } else {
                        storage.storeImage(listOfFiles[i].getName());
                        System.out.println(
                            "[" + listOfFiles[i].getName() + "] " + "is successfully added in");
                    }
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("[" + listOfFiles[i].getName() + "] " + "is a folder");
                }
            } catch (Exception e) {
                System.out.println("Problems occur!");
            }
        }

        if (listOfFiles.length == 0) {
            System.out
                .println("Sorry, please add all target images in the lib folder to be updated.");
        }
    }

    private void command_remove() {
        File folder = new File("./lib");
        File[] listOfFiles = folder.listFiles();
        storage.clearImages();
        for (int i = 0; i < listOfFiles.length; i++) {
            listOfFiles[i].delete();
        }
        System.out.println("All of images has been cleared.");
    }

    private void command_standard() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println(
            "Please enter the name of the control image: (The control image will be used to compare with other images.)");
        String name = input.nextLine();
        if (!storage.imageTable.contains(name)) {
            System.out.println("Sorry, the system does not contain the image of " + name);
            this.command_standard();
        }
        Image control = storage.imageTable.get(name); // get the Image object by its name
        for (Image image : storage.imageTable.imageList) {
            double difference = processor.colorComparison(control, image);
            storage.storeComparison(control, image, difference);
            System.out.println("Please enter the target concentration of " + "[" + image.name + "]"
                + " based on the control image " + control.name + " or [no] to skip this image:");
            String concentration = input.nextLine();
            try {
                storage.dataAnalysis.get(image.name)
                    .setConcentration(Double.valueOf(concentration));
                storage.dataAnalysis.get(image.name).setStandard(true);
            } catch (NumberFormatException e) {
                if (concentration.equalsIgnoreCase("no")) {
                    System.out.println("[Successfully Skipping This Image!]");
                    continue;
                }
                System.out.println(
                    "Sorry, please enter the number, so you need to reset standard again.");
                this.command_reset_standard();
            }

        }
        System.out.println(
            "The standard values between Euclidean distance and Calcium concentration is successfully set up.");
        this.command_read_standard();
    }

    private void command_read_standard() {
        ArrayList standardList = new ArrayList();
        for (CompNode compNode : storage.dataAnalysis.compList) {
            if (compNode.standard) {
                standardList.add(compNode);
            }
        }
        Collections.sort(standardList);
        for (Object compNode : standardList) {
            System.out.println(compNode);
        }

        // Assign the distance and concentration for standards
        double[] distanceList = new double[standardList.size()];
        double[] concentrationList = new double[standardList.size()];
        for (int i = 0; i < standardList.size(); i++) {
            distanceList[i] = ((CompNode) standardList.get(i)).distance;
            concentrationList[i] = ((CompNode) standardList.get(i)).concentration;
        }

        // Assign distance as X and concentration as Y
        LinearRegression linearRegression = new LinearRegression(distanceList, concentrationList);
        System.out.println("==================================================");
        System.out.println(
            "[Standard Linear Regression Line] " + "<Concentration> = " + linearRegression.slope()
                + " * <Color Difference> + " + linearRegression.intercept());
        System.out.println("[R^2] " + linearRegression.R2());
        System.out.println("==================================================");


        if (storage.dataAnalysis.size() == 0) {
            System.out.println("Sorry, there are no Standard line being set up.");
        }
    }

    private void command_reset_standard() throws IOException {
        for (int i = 0; i < storage.dataAnalysis.size(); i++) {
            if (storage.dataAnalysis.compList.get(i).standard) {
                storage.deleteComparison(storage.dataAnalysis.compList.get(i).name);
                storage.removeCompNode(storage.dataAnalysis.compList.get(i));
            }
        }
        System.out.println("The original Standard Line has been removed, please reset it.");
        this.command_standard();
    }

    private void command_clear_analysis() throws IOException {
        for (int i = 0; i < storage.dataAnalysis.size(); i++) {
            if (storage.dataAnalysis.compList.get(i).standard == false) {
                storage.deleteComparison(storage.dataAnalysis.compList.get(i).name);
                storage.removeCompNode(storage.dataAnalysis.compList.get(i));
                storage.deleteFile(".data_analysis_cache.micro_reading_app_cache");
                storage.deleteFile(".image_table_cache.micro_reading_app_cache");
            }
        }
        System.out.println(
            "All Analysis results are removed from the system, including the standard values.");
    }

    private void command_process() throws IOException {
        int standardCount = 0;
        Scanner input = new Scanner(System.in);
        for (CompNode each : storage.dataAnalysis.compList) {
            if (each.standard == true) {
                standardCount++;
            }
        }
        // When no standard line was set
        if (standardCount == 0) {
            System.out.println(
                "Please to set the standard line for the testing before processing the image.");
            System.out.println("Please try to use [s] or [standard] to set the standard line:");
            String newCommand = input.nextLine();
            this.launchPad(newCommand);
        }
        // When the standard line has been set
        else {
            for (Image each : storage.imageTable.imageList) {
                if (!storage.dataAnalysis.compList.contains(each)) {
                    storage.storeTarget(each);
                }
            }
            this.inputParameter();
            int count = 0;
            for (CompNode each : storage.dataAnalysis.compList) {
                if (each.standard == false && each.concentration == 0) {
                    count++;
                    each.convertToConcentration();
                    storage.updateCompNode(each);
                    System.out.println("The Calcium Concentration of [" + each.target.name + "] is "
                        + each.concentration + " mM(mmol/L)");
                }
            }
            if (count == 0) {
                System.out.println("All of target images have been processed.");
                System.out.println("If you want to check, please you [draw]:");
            }
        }
    }

    /**
     * The helper method intends to input all related static standard line parameters into the node.
     */
    private void inputParameter() {
        ArrayList standardList = new ArrayList();
        for (CompNode node : storage.dataAnalysis.compList) {
            if (node.standard == true) {
                standardList.add(node);

            }
        }
        Collections.sort(standardList); // sort the standardList

        // Assign the distance and concentration for standards
        double[] distanceList = new double[standardList.size()];
        double[] concentrationList = new double[standardList.size()];
        for (int i = 0; i < standardList.size(); i++) {
            distanceList[i] = ((CompNode) standardList.get(i)).distance;
            concentrationList[i] = ((CompNode) standardList.get(i)).concentration;
        }

        // Assign distance as X and concentration as Y
        LinearRegression linearRegression = new LinearRegression(distanceList, concentrationList);
        for (CompNode each : storage.dataAnalysis.compList) {
            each.rate = linearRegression.slope();
            each.intercept = linearRegression.intercept();
            storage.updateCompNode(each);
        }
    }

    /**
     * This method intends to draw the result of analyzed data. 1) It needs to include the information
     * related to the Command Line, such as r^2, linear regression line, and how many test units. 2)
     * it needs to include the diagram of concentration analysis (y is calcium level, x is date). 3)
     * it needs to include all historically processed calcium concentrations.
     */
    private void command_draw() {
        try {
            double slope = storage.dataAnalysis.compList.get(0).rate;
            double intercept = storage.dataAnalysis.compList.get(0).intercept;
            double r2 = storage.dataAnalysis.compList.get(0).r2;
            int numberOfTestUnit = 0;
            for (CompNode node : storage.dataAnalysis.compList) {
                if (node.standard == true) {
                    numberOfTestUnit++;
                }
            }

            System.out.println(">>>>>>>>>>>>>>>>>>>> Standard Information <<<<<<<<<<<<<<<<<<<<");
            System.out.println("[r^2] --> " + r2);
            System.out.println("[Number of test units] --> " + numberOfTestUnit);
            System.out.println(
                "[Standard Linear Regression Line] --> " + "<Concentration> = " + slope
                    + " * <Color Difference> + " + intercept);
            System.out.println("");
            System.out.println(">>>>>>>>>>>>>>>>>>> Processed Tests History <<<<<<<<<<<<<<<<<<<");
            int count = 0;
            for (CompNode each : storage.dataAnalysis.compList) {
                if (each.standard == false) {
                    count++;
                    System.out.println(
                        "The Calcium Concentration of [" + each.target.name + "] --> "
                            + each.concentration + " mM(mmol/L)");
                }
            }
            if (count == 0) {
                System.out.println("Please use [process] to process the image before drawing:");
            }
            System.out.println("");
            System.out.println(">>>>>>>>>>>>>>>>>>> Processed Tests History <<<<<<<<<<<<<<<<<<<");
            this.drawing();
        } catch (IndexOutOfBoundsException e1) {
            System.out.println("Please to [u] or [update] target images before drawing:");
        }

    }

    private void drawing() {
        ArrayList<String> seriesNames = new ArrayList();
        for (CompNode node : storage.dataAnalysis.compList) {
            if (node.standard == false) {
                String month = node.target.date.toString().split(" ")[1];
                String year = node.target.date.toString().split(" ")[5];
                if (!seriesNames.contains(month + " " + year)) {
                    seriesNames.add(month + " " + year);
                }
            }
        }
        ArrayList days = new ArrayList();
        ArrayList concentrations = new ArrayList();
        for (CompNode node : storage.dataAnalysis.compList) {
            if (node.standard == false) {
                days.add(Integer.valueOf(node.target.date.toString().split(" ")[2]));
                concentrations.add(node.concentration);
            }
        }

        XYSeries[] seriesList = new XYSeries[seriesNames.size()];
        for (int i = 0; i < seriesNames.size(); i++) {
            seriesList[i] = processor
                .createSeries(seriesNames.get(i), days.toArray(), concentrations.toArray());
        }
        XYDataset dataset = processor.createDataset(seriesList);
        JFreeChart chart = processor.createChart(dataset);
        processor.initUI(chart);

        // showing the chart
        processor.setVisible(true);

        System.out.println("The Chart has been successfully depicted in other window.");
    }

    /**
     * This method intends to quit the application.
     */
    public void quit() {
        try {
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            System.out.println(
                "Sorry, the MicroReading failed to exit, please directly close the window!");
        }
    }
}

