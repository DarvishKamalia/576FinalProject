import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class SimilarityChartView extends JPanel {
    private ChartPanel chartPanel = null;

    public SimilarityChartView() {
        //this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
    }

    public void loadSimilarityChart(ArrayList<Double> values) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (int i = 0 ; i < values.size(); i++) {
            dataSet.addValue(values.get(i), "similarity", Integer.toString(i));
        }

        JFreeChart chart = ChartFactory.createLineChart("Similarity", "", "Similarity", dataSet);
        chart.removeLegend();


        if (chartPanel == null) {
            chartPanel = new ChartPanel(chart);
            this.add(chartPanel, BorderLayout.CENTER);
            this.repaint();
            this.validate();
        }
        else {
            chartPanel.setChart(chart);
        }
    }
}
