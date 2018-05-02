import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MainWindow window = null;
        try {
            window = new MainWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.setVisible(true);
    }
}
