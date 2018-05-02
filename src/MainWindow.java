import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class MainWindow extends JFrame {

    private QueryResultsPanel queryResultsPanel;
    private VideoPlayer queryVideoPlayer;
    private VideoPlayer resultVideoPlayer;
    private SimilarityChartPanel chart = new SimilarityChartPanel();
    private FrameHistogramPanel queryHistogramPanel = new FrameHistogramPanel();
    private FrameHistogramPanel resultVideoPanel = new FrameHistogramPanel();
    private Random random = new Random();

    // Query stuff
    private ColorTuple[] centers;
    private DatabaseBuilder builder = new DatabaseBuilder();
    private IOHandler handler = new IOHandler();
    private HashMap< String, ArrayList< int []> > databaseMap = new HashMap<String, ArrayList<int[]>>();
    private KMeans means = new KMeans();

    public MainWindow() throws Exception {
        setTitle("576 Final Project");
        setSize(1000, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        queryResultsPanel = new QueryResultsPanel(this);
        queryVideoPlayer = new VideoPlayer("Query Video", this);
        resultVideoPlayer = new VideoPlayer("Result Video", this);
        contentPanel.setLayout(new GridLayout(3,2));
        contentPanel.add(queryResultsPanel);
        contentPanel.add(chart);
        contentPanel.add(queryVideoPlayer);
        contentPanel.add(resultVideoPlayer);
        contentPanel.add(queryHistogramPanel);
        contentPanel.add(resultVideoPanel);

        this.setContentPane(contentPanel);
        this.pack();


        FileInputStream fis = new FileInputStream(Constants.baseDirectory + Constants.dataBaseDirectory + "means.hst");
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            centers = (ColorTuple[]) ois.readObject();
        } catch (OptionalDataException e) {
           e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        readHistograms();
    }

    private void readHistograms() throws IOException {
        String directoryPath = Constants.baseDirectory + Constants.dataBaseDirectory + Constants.histogramDirectory;

        File dir = new File(directoryPath);
        File[] directoryListing = dir.listFiles();
        Arrays.sort(directoryListing);

        if (directoryListing != null) {
            for (File histogramFile : directoryListing) {
                String path = histogramFile.getAbsolutePath().toString();
                String name = path.substring(path.lastIndexOf("/") + 1);
                name = name.substring(0, name.indexOf("."));

                FileInputStream fis = new FileInputStream(histogramFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                try {
                    ArrayList < int[] > frameHistograms = (ArrayList < int[] >)  ois.readObject();
                    databaseMap.put(name,frameHistograms);
                } catch (OptionalDataException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        System.out.println("Read database");

    }

    public void didSelectQueryVideo(String video) {
        String videoPath = Constants.baseDirectory + Constants.queryDirectory + video;
        queryVideoPlayer.load(videoPath);


        queryResultsPanel.setResults(processQuery(videoPath));

        // TODO: Replace

        ArrayList < ArrayList <Double> > topLeft = new ArrayList < ArrayList < Double> >();
        ArrayList < ArrayList <Double> > topRight = new ArrayList < ArrayList < Double> >();
        ArrayList < ArrayList <Double> > bottomLeft = new ArrayList < ArrayList < Double> >();
        ArrayList < ArrayList <Double> > bottomRight = new ArrayList < ArrayList < Double> >();

        for (int i = 0; i < 64; i++) {
            topLeft.add(generateValues());
            topRight.add(generateValues());
            bottomLeft.add(generateValues());
            bottomRight.add(generateValues());
        }

        queryHistogramPanel.loadHistograms(topLeft, topRight, bottomLeft, bottomRight);
    }

    private ArrayList<String> processQuery(String videoPath) {
        File dir = new File(videoPath);
        File[] directoryListing = dir.listFiles();
        Arrays.sort(directoryListing);

        ArrayList<String> results;

        if (directoryListing != null) {
            for (File frameFile : directoryListing) {
                if (frameFile.getAbsolutePath().contains(".rgb")) { // Ensure it is a frame file
                    BufferedImage frame = handler.readImageFromFile(frameFile);
                    return getDatabaseMatchScores(frame);
                }
            }
        }

        return new ArrayList<String>();
    }

    private ArrayList<String> getDatabaseMatchScores (BufferedImage frame) {
        int[] queryHistogram = builder.getCounts(frame, centers);

        ArrayList<String> result = new ArrayList<String>();

        Iterator it = databaseMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ArrayList< int[] >> pair = (Map.Entry)it.next();

            // Go through the histograms for each frame and find the best match
            double minDistance = Double.MAX_VALUE;

            for (int[] databaseFrameHistogram : pair.getValue()) {
                double distance = KMeans.calculateChiDistance(queryHistogram, databaseFrameHistogram);
                System.out.println(distance);
                if (distance < minDistance) minDistance = distance;
            }


            // Min distance frame in the video has been found

            result.add(pair.getKey() + "    distance = " + Double.toString(minDistance));
        }

        return result;
    }

    public void didSelectResultVideo(String video) {
        resultVideoPlayer.load(Constants.baseDirectory + Constants.dataBaseDirectory + video);

        // TODO: Replace
        chart.loadSimilarityChart(generateValues());

    }

    private ArrayList <Double> generateValues() {
        ArrayList<Double> values = new ArrayList<Double>();

        for (int i = 0; i < 100; i++) {
            double start = 0;
            double end = 1;
            double rand = random.nextDouble();
            double result = start + (rand * (end - start));
            values.add(result);
        }

        return values;
    }
}
