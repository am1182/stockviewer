package stockviewer1320342;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class Report {

    public static void writeToFile(String stockFileDirectory, 
            String nameFileDirectory, String reportFileDirectory) {
        
        // https://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
        try {
            File file = new File(reportFileDirectory + "report.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                else {
                    file.delete();
                    file.createNewFile();
                }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        CSV csv = new CSV();
        List stockFilePaths = csv.getFilePaths(stockFileDirectory);
        List nameFilePaths = csv.getFilePaths(nameFileDirectory);
        List nameContentList = new ArrayList();
        List companyNameList = new ArrayList();
        List symbolsList = new ArrayList();

        int sequenceNumber = 0;
        String symbol = "";
        String companyName = "";

        // COMPANY NAMES FILE
        for (int j=0; j<nameFilePaths.size(); j++) {
            nameContentList = csv.readFile((String) nameFilePaths.get(j));
            for (int k=0; k<nameContentList.size(); k+=3) {
                symbolsList.add(nameContentList.get(k));
                companyNameList.add(nameContentList.get(k+1));
            }
        }
        
        // WRITE COLUMNS INTO FILE
        // http://beginnersbook.com/2014/01/how-to-write-to-file-in-java-using-bufferedwriter/
        // https://www.mkyong.com/java/how-to-append-content-to-file-in-java/
       
        BufferedWriter bw = null;
        FileWriter fw = null;
        
        File file = new File(reportFileDirectory + "report.txt");
        
        
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        
            // Format into table
            // http://stackoverflow.com/questions/6000810/printing-with-t-tabs-does-not-result-in-aligned-columns
            
            bw.write(String.format("%-10s", "Number"));
            bw.write(String.format("%-20s", "Company Symbol"));
            bw.write(String.format("%-40s", "Company Name"));
            bw.write(String.format("%-20s", "Highest Price"));
            bw.write(String.format("%-20s", "Highest Price Date"));
            bw.write(String.format("%-20s", "Lowest Price"));
            bw.write(String.format("%-20s", "Lowest Price Date"));
            bw.write(String.format("%-20s", "Average Close"));
            bw.write(String.format("%-20s", "Latest Close"));
            
            bw.newLine();
            bw.newLine();
            
            System.out.println("Content written successfully.");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        // STOCK FILE
        for (int i=0; i<stockFilePaths.size(); i++) {
            
            List contentList = new ArrayList();
            List dateList = new ArrayList();
            List highList = new ArrayList();
            List sortedHighList = new ArrayList();
            List lowList = new ArrayList();
            List sortedLowList = new ArrayList();
            List closeList = new ArrayList();
            
            // CONTENT LIST
            List cList = csv.readFile((String) stockFilePaths.get(i));
            for (int u=7; u<cList.size(); u++) {
                contentList.add(cList.get(u));
            }

            // CREATE VARIABLES
            symbol = (String) symbolsList.get(i);
            companyName = (String) companyNameList.get(i);
            sequenceNumber = i+1;
            float latestClose = Float.valueOf((String) contentList.get(4));
            
            // CREATE LISTS
            for (int n=0; n<contentList.size(); n+=7) {
                dateList.add(contentList.get(n));
                highList.add(Float.valueOf((String) contentList.get(n+2)));
                sortedHighList.add(Float.valueOf((String) contentList.get(n+2)));
                lowList.add(Float.valueOf((String) contentList.get(n+3)));
                sortedLowList.add(Float.valueOf((String) contentList.get(n+3)));
                closeList.add(Float.valueOf((String) contentList.get(n+4)));
            }
            
            // ----- HIGHEST & DATE
            // http://stackoverflow.com/questions/9008532/how-to-find-index-position-of-an-element-in-a-list-when-contains-returns-true
            // http://stackoverflow.com/questions/16252269/how-to-sort-a-arraylist-in-java
            sort(sortedHighList);
            Float highestPrice = (Float) sortedHighList.get(sortedHighList.size()-1);
            
            // Find date with highest price
            int indexOfHighest = highList.indexOf(highestPrice);
            String highestDate = (String) dateList.get(indexOfHighest);
            
            // ----- LOWEST & DATE
            // http://stackoverflow.com/questions/9008532/how-to-find-index-position-of-an-element-in-a-list-when-contains-returns-true
            // http://stackoverflow.com/questions/16252269/how-to-sort-a-arraylist-in-java
            sort(sortedLowList);
            Float lowestPrice = (Float) sortedLowList.get(0);
            
            // Find date with lowest price
            int indexOfLowest = lowList.indexOf(lowestPrice);
            String lowestDate = (String) dateList.get(indexOfLowest);
            
            // AVERAGE CLOSE
            Float averageClose = 0f;
            Float closeSum = 0f;
            for (int y=0; y<closeList.size(); y++) {
                closeSum += (Float) closeList.get(y);
            }
            averageClose = closeSum/closeList.size();
            
            // ----- BUFFERED WRITER
            
            try {
                // WRITE
                // http://beginnersbook.com/2014/01/how-to-write-to-file-in-java-using-bufferedwriter/
                // https://www.mkyong.com/java/how-to-append-content-to-file-in-java/
                
                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);
                
                // Format into table
                // http://stackoverflow.com/questions/6000810/printing-with-t-tabs-does-not-result-in-aligned-columns
                
                bw.write(String.format("%-10s", sequenceNumber));
                bw.write(String.format("%-20s", symbol));
                bw.write(String.format("%-40s", companyName));
                bw.write(String.format("%-20s", highestPrice));
                bw.write(String.format("%-20s", highestDate));
                bw.write(String.format("%-20s", highestPrice));
                bw.write(String.format("%-20s", lowestDate));
                bw.write(String.format("%-20s", averageClose));
                bw.write(String.format("%-20s", latestClose));
                
                bw.newLine();
                
                System.out.println("Content written successfully.");
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }
            finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw != null) {
                        fw.close();
                    }
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
