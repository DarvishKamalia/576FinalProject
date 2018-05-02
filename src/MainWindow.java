import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame {

    private QueryResultsPanel queryResultsPanel;
    private VideoPlayer queryVideoPlayer;
    private VideoPlayer resultVideoPlayer;
    private SimilarityChartPanel chart = new SimilarityChartPanel();
    private FrameHistogramPanel queryHistogramPanel = new FrameHistogramPanel();
    private FrameHistogramPanel resultVideoPanel = new FrameHistogramPanel();
    private Random random = new Random();

    public MainWindow() {
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
    }

    public void didSelectQueryVideo(String video) {
        queryVideoPlayer.load(Constants.baseDirectory + Constants.queryDirectory + video);

        // TODO: Replace
        String[] results = {"flowers", "movie", "musicvideo"};
        queryResultsPanel.setResults(results);

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
