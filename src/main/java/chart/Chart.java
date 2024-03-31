package chart;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;

import java.awt.*;

public class Chart extends JFrame {

    DefaultCategoryDataset dataset;

    public Chart(String fileTitle){
        setTitle(fileTitle);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
    }

    public void addDataset(String[] isoDates, Double[] xData, String unit){


        dataset = new DefaultCategoryDataset();

        for (int i = 0; i < isoDates.length; i++) {
            dataset.addValue(xData[i],unit,isoDates[i]);
        }


    }

    public void showChart(String chartTitle){

        JFreeChart chart =
                ChartFactory.createLineChart(
                        chartTitle,
                        null,
                        null,
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800,600));
        setContentPane(chartPanel);
        setVisible(true);
    }
}