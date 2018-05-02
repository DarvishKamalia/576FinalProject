import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
    JSlider scrubber = new JSlider(JSlider.HORIZONTAL);
    MainWindow mainWindow;
    boolean isPaused = false;

    public VideoPlayer(String title, MainWindow mainWindow) {

        this.mainWindow = mainWindow;

        this.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);
        final VideoPlayer parent = this;


        scrubber.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isPaused) scrubToPosition(scrubber.getValue());
            }
        });

        this.add(scrubber, BorderLayout.NORTH);

        JButton playButton = new JButton("Play");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(playButton);


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

        scrubber.setMaximum(imageSequence.size() - 1);
        loadFromCurrentPosition();
    }

    public void scrubToPosition (int position) {
        if (playTimer != null && playTimer.isRunning()) {
            pause();
        }

        currentLocation = position;
        loadFromCurrentPosition();
    }

    public void loadFromCurrentPosition() {
        File frameFile = imageSequence.get(currentLocation);
        //System.out.println(frameFile.getAbsolutePath());
        panel.img = handler.readImageFromFile(frameFile);
        panel.repaint();
        scrubber.setValue(currentLocation);
    }

    public void play() {
        isPaused = false;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadFromCurrentPosition();
                currentLocation++;
                //System.out.println(currentLocation);
                if (currentLocation == imageSequence.size()) {
                    currentLocation = 0;
                }

                scrubber.setValue(currentLocation);
            }
        };
        playTimer = new Timer(frameTime, taskPerformer);
        playTimer.start();
        audioPlayer.play();
    }

    public void pause() {
        playTimer.stop();
        audioPlayer.pause();
        isPaused = true;
    }

    public void stop() {
        isPaused = true;
        playTimer.stop();
        currentLocation = 0;
        loadFromCurrentPosition();
    }
}
