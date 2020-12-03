import ij.ImagePlus;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Font;

/**
 * This class intends to read the image and give out several different methods to process the image.
 *
 * @author Jiahe Jin
 */
public class ImageProcessor extends JFrame {

    public ImageProcessor() {

    }

    public static void main(String[] args) throws IOException {
        DataStorage storage = new DataStorage();
        ImageTable table = storage.imageTable;
        DataAnalysis analysis = storage.dataAnalysis;
        storage.storeImage("pad1_ph4.5.png");
        storage.storeImage("pad1_ph5.0.png");
        storage.storeImage("pad1_ph5.5.png");
        storage.storeImage("pad1_ph6.0.png");
        storage.storeImage("pad1_ph7.0.png");
        storage.storeImage("pad1_ph7.5.png");
        storage.storeImage("pad1_ph8.0.png");
        storage.storeImage("pad1_ph8.5.png");
        storage.storeImage("pad1_ph9.0.png");
        storage.storeImage("pad1_ph9.5.png");
        storage.storeImage("pad1_ph10.0.png");

        Image imageA = table.get("pad1_ph4.5.png");
        Image imageB = table.get("pad1_ph5.0.png");
        Image imageC = table.get("pad1_ph5.5.png");
        Image imageD = table.get("pad1_ph6.0.png");
        Image imageE = table.get("pad1_ph7.0.png");
        Image imageF = table.get("pad1_ph7.5.png");
        Image imageG = table.get("pad1_ph8.0.png");
        Image imageH = table.get("pad1_ph8.5.png");
        Image imageI = table.get("pad1_ph9.0.png");
        Image imageJ = table.get("pad1_ph9.5.png");
        Image imageK = table.get("pad1_ph10.0.png");



        ImageProcessor processor = new ImageProcessor();

        storage.storeComparison(imageA, imageB, processor.colorComparison(imageA, imageB));

        System.out.println(processor.getSummary(imageA, imageB));
        System.out.println(processor.getSummary(imageA, imageC));
        System.out.println(processor.getSummary(imageA, imageD));
        System.out.println(processor.getSummary(imageA, imageE));
        System.out.println(processor.getSummary(imageA, imageF));
        System.out.println(processor.getSummary(imageA, imageG));
        System.out.println(processor.getSummary(imageA, imageH));
        System.out.println(processor.getSummary(imageA, imageI));
        System.out.println(processor.getSummary(imageA, imageJ));
        System.out.println(processor.getSummary(imageA, imageK));
        System.out.println(processor.getSummary(imageA, imageA));



    }

    /**
     * The private helper method intends to manipulate an image into grayscale.
     *
     * @param image the Image Class of the specific image.
     * @throws IOException when fails to write the fail.
     */
    private void getGrayscale(Image image) throws IOException {
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
        ImageIO.write(buffer, image.name.split("[.]")[1], new File("gray_" + image.name));
        ImagePlus grayImg = new ImagePlus("gray", buffer);
        grayImg.show();
    }

    /**
     * This method intends to compare colors between two images by using RGB tuples to measure the
     * Euclidean distances in the color space. If dimensions of two images are equal, the whole images
     * are compared. However, if dimensions are  different, only centered 90% (widths and heights)
     * part of smaller dimension in two pictures are compared.
     *
     * @param image1 the Image Object of one of two compared images
     * @param image2 the Image Object of one of two compared images
     * @return the RGB Euclidean Distance
     */
    public double colorComparison(Image image1, Image image2) {
        int red1 = image1.red;
        int red2 = image2.red;
        int green1 = image1.green;
        int green2 = image2.green;
        int blue1 = image1.blue;
        int blue2 = image2.blue;

        double distance = Math.sqrt(
            Math.pow((red1 - red2), 2) + Math.pow((green1 - green2), 2) + Math
                .pow((blue1 - blue2), 2));

        return distance;
    }

    public String getSummary(Image image1, Image image2) {
        int width1 = image1.width;
        int width2 = image2.width;
        int height1 = image1.height;
        int height2 = image2.height;

        double distance = this.colorComparison(image1, image2);
        double maximum_difference = Math.sqrt(195075);
        double normal_difference = distance / maximum_difference;
        double percentage_difference = normal_difference * 100;

        return "[" + image1.name + "] " + " (" + image1.date + ") " + " The RGB: " + image1.red
            + ", " + image1.green + ", " + image1.blue + "  The Height: " + height1
            + "  The Width: " + width1 + "\n" + "[" + image1.name + "] " + " (" + image1.date + ") "
            + " The RGB: " + image2.red + ", " + image2.green + ", " + image2.blue
            + "  The Height: " + height2 + "  The Width: " + width2 + "\n"
            + "The RGB Euclidean Distance: " + distance + "\n" + "The Percent Difference: "
            + percentage_difference + "%." + "\n";
    }



    /**
     * This method intends to create the XYSeries for the generated calcium concentration analysis
     * chart.
     *
     * @param seriesName    the data set label for these data (Usually like "November 2020")
     * @param days          the array of day in a month
     * @param concentration the array of calcium concentrations in order
     */
    public XYSeries createSeries(String seriesName, Object[] days, Object[] concentration) {
        var series = new XYSeries(seriesName);
        for (int i = 0; i < concentration.length; i++) {
            series.add(((int) days[i]), (Double) concentration[i]);
        }

        return series;
    }

    /**
     * This method intends to create the XYDataset for the generated calcium concentration analysis
     * chart.
     *
     * @param seriesList the array of XYSeries which are all being added in the dataset
     */
    public XYDataset createDataset(XYSeries[] seriesList) {
        var dataset = new XYSeriesCollection();
        for (XYSeries series : seriesList) {
            dataset.addSeries(series);
        }
        return dataset;
    }

    /**
     * This method intends to create the chart by giving the specific dataset.
     *
     * @param dataset the XYDataset
     */
    public JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory
            .createXYLineChart("Calcium Level Per Day Trend", "Day", "Calcium Concentration (mM)",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            int r = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            renderer.setSeriesPaint(i, new Color(r, b, g));
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }


        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Calcium Level Per Day Trend",
            new Font("Serif", java.awt.Font.BOLD, 18)));

        return chart;
    }

    /**
     * This method is the initial set of chart
     *
     * @param chart the JFreeChart Object creating by the createChart method.
     */
    public void initUI(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Calcium Concentration Analysis Chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
