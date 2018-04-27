import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class VideoPlayer extends JPanel {
    IOHandler handler = new IOHandler();
    ImagePanel panel = new ImagePanel();
    ArrayList<File> imageSequence = new ArrayList<File>();
    int currentLocation = 0;
    Timer playTimer = null;

    public VideoPlayer(String title, String filePath) {
        JLabel titleLabel = new JLabel(title);
        this.add(titleLabel);

        JButton playButton = new JButton("Play");
        this.add(playButton);

        final VideoPlayer parent = this;

        playButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parent.play();
            }
        });

        panel.setPreferredSize(new Dimension(500, 200));
        this.add(panel);

        JButton pauseButton = new JButton("Pause");
        this.add(pauseButton);

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.pause();
            }
        });

        load(filePath);
    }

    // Takes in the path to the folder containing the RGB files
    public void load(String videoPath)  {
        File dir = new File(videoPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File frameFile : directoryListing) {
                if (frameFile.getAbsolutePath().contains(".rgb")) { // Ensure it is a frame file
                    imageSequence.add(frameFile);
                }
            }
        }
    }

    public void play() {
        int delay = 300; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                File frameFile = imageSequence.get(currentLocation);
                panel.img = handler.readImageFromFile(frameFile, 352, 288);
                panel.repaint();
                currentLocation++;
                System.out.println(currentLocation);
                if (currentLocation == imageSequence.size()) {
                    currentLocation = 0;
                }
            }
        };
        playTimer = new Timer(delay, taskPerformer);
        playTimer.start();
    }

    public void pause() {
        playTimer.stop();
    }
}
