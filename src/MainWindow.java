import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame {

    private QueryResultsPanel queryResultsPanel;
    private VideoPlayer queryVideoPlayer;
    private VideoPlayer resultVideoPlayer;
    private SimilarityChartView chart = new SimilarityChartView();
    private

    public MainWindow() {
        setTitle("576 Final Project");
        setSize(1000, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        queryResultsPanel = new QueryResultsPanel(this);
        queryVideoPlayer = new VideoPlayer("Query Video", this);
        resultVideoPlayer = new VideoPlayer("Result Video", this);
        contentPanel.setLayout(new GridLayout(2,2));
        contentPanel.add(queryResultsPanel);
        contentPanel.add(chart);
        contentPanel.add(queryVideoPlayer);
        contentPanel.add(resultVideoPlayer);
        this.setContentPane(contentPanel);
        this.pack();
    }

    public void didSelectQueryVideo(String video) {
        queryVideoPlayer.load(Constants.baseDirectory + Constants.queryDirectory + video);

        // TODO: Replace
        String[] results = {"flowers", "movie", "musicvideo"};
        queryResultsPanel.setResults(results);

    }

    public void didSelectResultVideo(String video) {
        resultVideoPlayer.load(Constants.baseDirectory + Constants.dataBaseDirectory + video);

        // TODO: Replace
        ArrayList<Double> values = new ArrayList<Double>();

        for (int i = 0; i < 100; i++) {
            double start = 0;
            double end = 1;
            double random = new Random().nextDouble();
            double result = start + (random * (end - start));
            values.add(result);
        }
        chart.loadSimilarityChart(values);
    }


}
