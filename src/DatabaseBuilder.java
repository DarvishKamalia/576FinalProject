import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.javac.code.Attribute;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseBuilder {
    KMeans means = new KMeans();



    public static void main(String[] args) {
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.buildDatabase();
    }

    private void buildDatabase() {

        ArrayList<ColorTuple> colors = new ArrayList<ColorTuple>();

        for (int r = 0; r < 256; r+= 16) {
            for (int g = 0; g < 256; g+=16) {
                for (int b = 0; b < 256; b+=16) {
                    float[] hsv = new float[3];
                    Color.RGBtoHSB(r , g, b, hsv);
                    colors.add(new ColorTuple(hsv));
                }
            }
        }

        ColorTuple[] centers = means.createBuckets(colors);

        String[] videoNames = {"flowers"};

        IOHandler handler = new IOHandler();

        for (String videoName : videoNames) {
            String videoPath = Constants.baseDirectory + Constants.dataBaseDirectory + videoName;
            File dir = new File(videoPath);
            File[] directoryListing = dir.listFiles();
            Arrays.sort(directoryListing);

            ArrayList < ArrayList <Double > > frameHistograms = new ArrayList<ArrayList<Double>>();

            if (directoryListing != null) {
                for (int i = 0; i < directoryListing.length; i++) {
                    File frameFile = directoryListing[i];
                    if (frameFile.getAbsolutePath().contains(".rgb")) { // Ensure it is a frame file
                        BufferedImage frame = handler.readImageFromFile(frameFile);
                        ArrayList<Double> result = getCounts(frame, centers);
                        frameHistograms.add(result);
                        System.out.println("Completed a frame: " + Integer.toString(i));
                    }
                }
            }

            for (ArrayList<Double> list : frameHistograms) {
                System.out.println(Arrays.toString(list.toArray()));
            }
        }
    }

    private ArrayList<Double> getCounts (BufferedImage frame, ColorTuple[] centers) {
        ArrayList<Double> results = new ArrayList<Double>();
        KMeans means = new KMeans();

        for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
            results.add(0.0);
        }

        for (int i = 0; i < frame.getWidth(); i++) {
            for (int j = 0; j < frame.getHeight(); j++) {
                Color color = new Color(frame.getRGB(i, j));
                float[] hsv = new float[3];
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
                ColorTuple pixelValue = new ColorTuple(hsv);
                int targetIndex = means.getTargetIndex(pixelValue, centers);
                results.set(targetIndex, results.get(targetIndex) + 1);
            }
        }

        return results;
    }

    private void writeResultsToFile (ArrayList< ArrayList <Double> > )
}
