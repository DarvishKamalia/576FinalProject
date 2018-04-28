import com.sun.tools.internal.jxc.ap.Const;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

public class QueryResultsPanel extends JPanel {
    private final MainWindow mainWindow;
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

        queryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        resultList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedValue = resultList.getSelectedValue();
                parent.mainWindow.didSelectResultVideo(selectedValue);
            }
        });

        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        this.add(queryField);
        this.add(resultList);

        this.mainWindow = mainWindow;
    }

    public void didSelectQueryVideo() {
        File queryDirectory = new File(Constants.baseDirectory + Constants.queryDirectory + queryField.getText());

        if (queryDirectory.exists() && queryDirectory.isDirectory()){
            mainWindow.didSelectQueryVideo(queryField.getText());
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

