import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoPlayer extends JPanel {
    IOHandler handler = new IOHandler();
    ImagePanel panel = new ImagePanel();
    ArrayList<File> imageSequence = new ArrayList<File>();
    int currentLocation = 0;
    Timer playTimer = null;
    static int frameTime = 50;
    AePlayWave audioPlayer = new AePlayWave();

    public VideoPlayer(String title) {

        this.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);


        JButton playButton = new JButton("Play");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(playButton);

        final VideoPlayer parent = this;

        playButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parent.play();
            }
        });

        panel.setPreferredSize(new Dimension(500, 200));
        this.add(panel, BorderLayout.CENTER);

        JButton pauseButton = new JButton("Pause");
        controlPanel.add(pauseButton);

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.pause();
            }
        });


        JButton stopButton = new JButton("Stop");
        controlPanel.add(stopButton);

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.stop();
            }
        });

        this.add(controlPanel, BorderLayout.SOUTH);
    }

    // Takes in the path to the folder containing the RGB files
    public void load(String videoPath)  {
        imageSequence.clear();
        File dir = new File(videoPath);
        File[] directoryListing = dir.listFiles();
        Arrays.sort(directoryListing);
        if (directoryListing != null) {
            for (File frameFile : directoryListing) {
                if (frameFile.getAbsolutePath().contains(".rgb")) { // Ensure it is a frame file
                    imageSequence.add(frameFile);

                }
                //WTF
                else if (frameFile.getAbsolutePath().contains(".wav")){
                    audioPlayer.load(frameFile.getAbsolutePath());
                }
            }
        }
    }

    public void play() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                File frameFile = imageSequence.get(currentLocation);
                //System.out.println(frameFile.getAbsolutePath());
                panel.img = handler.readImageFromFile(frameFile, 352, 288);
                panel.repaint();
                currentLocation++;
                //System.out.println(currentLocation);
                if (currentLocation == imageSequence.size()) {
                    currentLocation = 0;
                }
            }
        };
        playTimer = new Timer(frameTime, taskPerformer);
        playTimer.start();
        audioPlayer.play();
    }

    public void pause() {
        playTimer.stop();
        audioPlayer.pause();
    }

    public void stop() {
        playTimer.stop();
        currentLocation = 0;
    }
}
