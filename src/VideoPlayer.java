import javax.swing.*;
import java.awt.*;

public class VideoPlayer extends JPanel {

    public VideoPlayer(String title) {
        JLabel titleLabel = new JLabel(title);
        this.add(titleLabel);

        JButton button = new JButton("Play");
        this.add(button);

        button = new JButton("Video");
        button.setPreferredSize(new Dimension(500, 200));
        this.add(button);

        button = new JButton("Pause");
        this.add(button);
    }

    // Takes in the path to the folder containing the RGB files 
    public load(String videoPath) {

    }
}
