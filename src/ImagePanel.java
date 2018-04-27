import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {
    public Image img;

    public ImagePanel() {
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
