/**
** Class creates the plot and passes the component to Menu.java
*
* @author Carson Sharp
* @ClassID 70605
* @Final Project 
*
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
* Plot class makes a component containing the plotted graph which will be passed to Menu
*/
public class Plot extends JFrame{

    /**
     * Function to plot graph using JFreeChart
     */
    public Component plotGraph(ArrayList<ArrayList<String>> roster, ArrayList<String> header) {

        XYSeriesCollection collection = new XYSeriesCollection();
        for (int i = 6; i < roster.get(0).size(); i++) {
            XYSeries series = new XYSeries(header.get(i));
            ArrayList<Integer> dateColumn = new ArrayList<>();
            for (int j = 0; j < roster.size(); j++)
                dateColumn.add(Integer.parseInt(String.valueOf(roster.get(j).get(i))));
            for (int j = 0; j < dateColumn.size(); j++)
                series.add((int) dateColumn.get(j), Collections.frequency(dateColumn, dateColumn.get(j)));
            collection.addSeries(series);

        }
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Attendance of Class",
                "Percentage Attended", "Number of Students who attended Attended", collection);

        //Changes background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));


        //Create Panel
        ChartPanel panel = new ChartPanel(chart);

        //Pass component back to Menu to be added to the frame
        return panel;

    }



}