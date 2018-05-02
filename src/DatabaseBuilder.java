import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.javac.code.Attribute;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseBuilder {
    private KMeans means = new KMeans();

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

        writeObjectToFile(centers, Constants.baseDirectory + Constants.dataBaseDirectory + "means.hst");

        String[] videoNames = {"interview", "movie", "sports", "starcraft"};

        IOHandler handler = new IOHandler();

        for (String videoName : videoNames) {
            String videoPath = Constants.baseDirectory + Constants.dataBaseDirectory + videoName;
            File dir = new File(videoPath);
            File[] directoryListing = dir.listFiles();
            Arrays.sort(directoryListing);

            ArrayList < int[] > frameHistograms = new ArrayList< int [] >();

            if (directoryListing != null) {
                for (int i = 0; i < directoryListing.length; i++) {
                    File frameFile = directoryListing[i];
                    if (frameFile.getAbsolutePath().contains(".rgb")) { // Ensure it is a frame file
                        BufferedImage frame = handler.readImageFromFile(frameFile);
                        int[] result = getCounts(frame, centers);
                        frameHistograms.add(result);
                        System.out.println("Completed a frame: " + Integer.toString(i));
                    }
                }
            }

            writeObjectToFile(frameHistograms, Constants.baseDirectory + Constants.dataBaseDirectory + Constants.histogramDirectory + videoName + ".hst");

//            for (int[] list : frameHistograms) {
//                System.out.println(Arrays.toString(list);
//            }
        }
    }

    public int[] getCounts (BufferedImage frame, ColorTuple[] centers) {
        int[] results = new int[Constants.NUMBER_OF_CLUSTERS];
        KMeans means = new KMeans();

        for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
            results[i] = 0;
        }

        for (int i = 0; i < frame.getWidth(); i++) {
            for (int j = 0; j < frame.getHeight(); j++) {
                Color color = new Color(frame.getRGB(i, j));
                float[] hsv = new float[3];
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
                ColorTuple pixelValue = new ColorTuple(hsv);
                int targetIndex = means.getTargetIndex(pixelValue, centers);
                results[targetIndex]++;
            }
        }

        return results;
    }

    private void writeObjectToFile (Serializable object , String filePath) {
        try{
            FileOutputStream fos= new FileOutputStream(filePath);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
