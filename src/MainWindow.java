import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private QueryResultsPanel queryResultsPanel;
    private VideoPlayer queryVideoPlayer = new VideoPlayer("Query Video");
    private VideoPlayer resultVideoPlayer = new VideoPlayer("Result Video");

    public MainWindow() {
        setTitle("576 Final Project");
        setSize(1000, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        queryResultsPanel = new QueryResultsPanel(this);
        contentPanel.setLayout(new GridLayout(2,2));
        contentPanel.add(queryResultsPanel);
        contentPanel.add(queryVideoPlayer);
        contentPanel.add(resultVideoPlayer);
        this.setContentPane(contentPanel);
        this.pack();
    }

    public void didSelectQueryVideo(String video) {
        queryVideoPlayer.load(Constants.baseDirectory + Constants.queryDirectory + video);

        // Perform Query
        String[] results = {"flowers", "movie", "musicvideo"};
        queryResultsPanel.setResults(results);

    }

    public void didSelectResultVideo(String video) {
        resultVideoPlayer.load(Constants.baseDirectory + Constants.dataBaseDirectory + video);
        // Update chart view R
    }
}
