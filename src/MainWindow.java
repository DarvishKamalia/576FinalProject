import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("576 Final Project");
        setSize(1000, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2,2));
        contentPanel.add(new VideoPlayer("Query Video", "/Users/darvishkamalia/Desktop/finalProject/databse_videos/movie"));
        contentPanel.add(new VideoPlayer("Selected Video", "/Users/darvishkamalia/Desktop/finalProject/databse_videos/flowers"));
        this.setContentPane(contentPanel);
        this.pack();
    }

    public void didSelectQueryVideo() {

    }
}
