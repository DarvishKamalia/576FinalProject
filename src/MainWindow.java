import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("576 Final Project");
        setSize(1000, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1,2));
        contentPanel.add(new VideoPlayer("QueryVideo"));
        contentPanel.add(new VideoPlayer("SelectedVideo"));
        this.setContentPane(contentPanel);
        this.pack();
    }
}
