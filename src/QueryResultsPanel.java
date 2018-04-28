import com.sun.tools.internal.jxc.ap.Const;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

public class QueryResultsPanel extends JPanel {
    private MainWindow mainWindow;
    private JTextField queryField = new JTextField();
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList<String> resultList = new JList<String>(listModel);

    public QueryResultsPanel(MainWindow mainWindow) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final QueryResultsPanel parent = this;

        queryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.didSelectQueryVideo();
            }
        });

        this.add(queryField);
        this.add(resultList);

        this.mainWindow = mainWindow;
    }

    public void didSelectQueryVideo() {

        if (File.exists(new Path(Constants.baseDirectory + Constants.queryDirectory + queryField.getText()))){
            mainWindow.didSelectQueryVideo();
        }
        else {
            System.out.println("Directory does not exist");
        }
    }

    public void setResults(String[] results) {
        listModel.removeAllElements();
        for (String result : results) {
            listModel.addElement(result);
        }
    }
}

