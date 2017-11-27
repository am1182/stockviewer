package stockviewer1320342;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class Chart {

    
    public static Object displayLineChart(String filePath) {
        
        // REFERENCES
        // http://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
        // http://stackoverflow.com/questions/14932788/javafx-customzied-categoryaxis-manual-without-useing-autoranging
        
        // ----- AXES
        NumberAxis priceAxis = new NumberAxis();
        CategoryAxis dateAxis = new CategoryAxis();
        priceAxis.setLabel("Close");
        dateAxis.setLabel("Date");
        
        // ----- CHART
        LineChart<String, Number> lineChart = new LineChart<String, Number>(
                dateAxis, priceAxis);
        
        // ----- CSV
        CSV csv = new CSV();
        List contentList = csv.readFile(filePath);
        List dateList = new ArrayList();
        List openList = new ArrayList();
        
        // ----- GET DATES AND OPEN
        for (int i=7; i<contentList.size(); i+=7) {
            String date = (String) contentList.get(i);
            String closeString = (String) contentList.get(i+4);
            Float close = Float.valueOf(closeString);

            openList.add(close);
            dateList.add(date);
        }
        
        // ----- SERIES
        XYChart.Series series = new XYChart.Series();
        
        // Reverse list order so the most recent date is on the right
        Collections.reverse(dateList);
        Collections.reverse(openList);
        
        // Add items from lists to series which will be displayed on chart
        // http://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
        for (int i=0; i<dateList.size(); i++) {
            String dateData = (String) dateList.get(i);
            Float openData = (Float) openList.get(i);
            series.getData().add(new XYChart.Data(dateData, openData));
        }
                
        // Sort list to set upper and lower bound to chart
        // http://stackoverflow.com/questions/16252269/how-to-sort-a-arraylist-in-java
        Collections.sort(openList);
        Double lowerBound = Double.valueOf((Float) openList.get(0)-20);
        Double upperBound = Double.valueOf((Float) openList.get(openList.size()-1)+20);
        
        // Set upper and lower bound to chart for easier viewing
        // // http://stackoverflow.com/questions/28141565/how-to-set-axis-range-in-java-fx-using-samplecontroller
        priceAxis.setAutoRanging(false);
        priceAxis.setLowerBound(lowerBound);
        priceAxis.setUpperBound(upperBound);
        
        // ADD TO CHART
        // http://stackoverflow.com/questions/20983131/remove-javafx-2-linechart-legend-items
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);
        
        // RETURN
        return lineChart;
    }
    
    
    public static Object displayBarChart(String stocksFileDirectory,
            String namesFileDirectory) {
        
        // ----- CSV
        CSV csv = new CSV();
        
        // ----- FILEPATHS
        List stockFilePaths = csv.getFilePaths(stocksFileDirectory);
        List nameFilePaths = csv.getFilePaths(namesFileDirectory);
        
        // ----- LISTS
        List companySymbolList = new ArrayList();
        List recentCloseList = new ArrayList();
        
        // ----- GET COMPANY SYMBOLS
        for (int i=0; i<nameFilePaths.size(); i++) {
            List fileContent = csv.readFile((String) nameFilePaths.get(i));
            
            // Add symbols to list
            for (int j=0; j<fileContent.size(); j+=3) {
                companySymbolList.add(fileContent.get(j));
            }
        }
        
        // ----- GET RECENT CLOSE
        for (int i=0; i<stockFilePaths.size(); i++) {
            List fileContent = csv.readFile((String) stockFilePaths.get(i));
            Float recentClose = Float.valueOf((String) fileContent.get(11));
            recentCloseList.add(recentClose);
        }
        
        // ----- BAR CHART
        // http://docs.oracle.com/javafx/2/charts/bar-chart.htm
        
        // AXES
        CategoryAxis symbolAxis = new CategoryAxis();
        NumberAxis closeAxis = new NumberAxis();
        symbolAxis.setLabel("Stock Symbol");
        closeAxis.setLabel("Recent Close");
        
        // CHART
        BarChart<String, Number> barChart = new BarChart<String, Number>(
                symbolAxis,closeAxis);
        
        // SERIES
        XYChart.Series series = new XYChart.Series();
        for (int i=0; i<companySymbolList.size(); i++) {
            series.getData().add(new XYChart.Data(
                    companySymbolList.get(i), recentCloseList.get(i)));
        }
        
        // ADD DATA TO CHART
        // http://stackoverflow.com/questions/20983131/remove-javafx-2-linechart-legend-items
        barChart.getData().addAll(series);
        barChart.setLegendVisible(false);
        
        // RETURN
        return barChart;
    }
    
    
    public static Object displayAreaChart(String filePath) {
        
        // REFERENCES
        // http://docs.oracle.com/javafx/2/charts/area-chart.htm
        
        // ----- CSV
        CSV csv = new CSV();
        List contentList = csv.readFile(filePath);
        List dateList = new ArrayList();
        List volumeList = new ArrayList();
        
        // ----- GET DATES AND VOLUME
        for (int i=7; i<contentList.size(); i+=7) {
            String date = (String) contentList.get(i);
            int volume = Integer.valueOf((String) contentList.get(i+5));
            volumeList.add(volume);
            dateList.add(date);
        }
        
        // Reverse list order so the most recent date is on the right
        Collections.reverse(dateList);
        Collections.reverse(volumeList);
        
        // AXES
        CategoryAxis dateAxis = new CategoryAxis();
        NumberAxis volumeAxis = new NumberAxis();
        dateAxis.setLabel("Date");
        volumeAxis.setLabel("Volume");
        
        // CHART
        AreaChart<String, Number> areaChart = new AreaChart<String, Number>(
                dateAxis, volumeAxis
        );
        
        // SERIES
        XYChart.Series series = new XYChart.Series();
        for (int i=0; i<volumeList.size(); i++) {
            String date = (String) dateList.get(i);
            int volume = (int) volumeList.get(i);
            series.getData().add(new XYChart.Data(date, volume));
        }
        
        // ADD TO CHART
        // http://stackoverflow.com/questions/20983131/remove-javafx-2-linechart-legend-items
        areaChart.getData().addAll(series);
        areaChart.setLegendVisible(false);
        
        // RETURN
        return areaChart;
    }
}
