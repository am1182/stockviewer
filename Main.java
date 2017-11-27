package stockviewer1320342;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        // ----- FILE DIRECTORY
        // http://stackoverflow.com/questions/14209085/how-to-define-a-relative-path-in-java
        
        String stocksFileDirectory = "src/StockFiles/Stocks/";
        String namesFileDirectory = "src/StockFiles/Names/";
        String reportFileDirectory = "src/Stockfiles/Report/";
        
        // ----- CLASSES
        CSV csv = new CSV();
        Chart chart = new Chart();
        Table table = new Table();
        ComboBox comboBox = new ComboBox();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        
        // ---------- LAYOUT ----------
        // http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm#CHDGHCDG
        BorderPane borderPane = new BorderPane();
        
        // BOXES
        VBox topBox = new VBox();
        HBox buttonBox = new HBox(20);
        VBox centerBox = new VBox();
        VBox chartsBox = new VBox();
        
        // Set padding
        topBox.setPadding(new Insets(20));
        centerBox.setPadding(new Insets(20));
        buttonBox.setPadding(new Insets(20));
        chartsBox.setPadding(new Insets(20));
        
        // Set alignment
        // http://stackoverflow.com/questions/29707882/javafx-hbox-alignment
        topBox.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        chartsBox.setAlignment(Pos.CENTER);
        
        // SET BOXES
        borderPane.setTop(topBox);
        borderPane.setCenter(centerBox);
        
        
        // ---------- TITLE ----------
        // http://docs.oracle.com/javafx/2/text/jfxpub-text.htm 
        Text title = new Text("STOCK VIEWER");
        title.setFont(Font.font ("Helvetica", 40));
        
        // ADD TO TOP BOX
        topBox.getChildren().addAll(title);

        
        // ---------- HOME ----------
        
        // ----- CENTER

        // BAR CHART AND TABLE
        Object barChart = chart.displayBarChart(
                stocksFileDirectory, namesFileDirectory);
        Object mainTable = table.displayMainTable(stocksFileDirectory,
                    namesFileDirectory);
        
        // ADD TO CENTER BOX
        centerBox.getChildren().addAll((Node) barChart, (Node) mainTable);
        
        
        // ---------- DETAILS ----------
        
        // FILEPATH
        List stocksFilePathList = csv.getFilePaths(stocksFileDirectory);
        String AHTFilePath = (String) stocksFilePathList.get(0);
        
        // ----- TABS
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TabPane.html
        
        Tab chartsTab = new Tab("CHARTS");
        Tab tableTab = new Tab("TABLE");
        tabPane.setTabMinWidth(200);
        tabPane.setPadding(new Insets(20));
        tabPane.getTabs().addAll(chartsTab, tableTab);
        
        // ----- CHARTS
        Object lineChart = chart.displayLineChart(AHTFilePath);
        Object areaChart = chart.displayAreaChart(AHTFilePath);
        chartsBox.getChildren().addAll((Node) lineChart, (Node) areaChart);
        chartsTab.setContent(chartsBox);
        
        // ----- TABLE
        Object largeTable = table.displayLargeTable(AHTFilePath);
        tableTab.setContent((Node) largeTable);

        // ----- COMBO BOX
        // http://docs.oracle.com/javafx/2/ui_controls/combo-box.htm
        
        List stockFilePaths = csv.getFilePaths(stocksFileDirectory);
        List companyFilePath = csv.getFilePaths(namesFileDirectory);
        List companyNames = new ArrayList();
        
        // Add company names to list for displaying in comboBox
        for (int i=0; i<companyFilePath.size(); i++) {
            List fileContent = csv.readFile((String) companyFilePath.get(i));
            for (int j=0; j<fileContent.size(); j+=3) {
                String companyName = (String) fileContent.get(j+1);
                companyNames.add(companyName);
            }
        }
        comboBox.setValue((String) companyNames.get(0));
        comboBox.setPadding(new Insets(5));
        
        // COMBOBOX OBSERAVBLE LIST
        ObservableList comboBoxList = FXCollections.observableArrayList();
        for (int k=0; k<companyNames.size(); k++) {
            comboBoxList.add(companyNames.get(k));
        }
        comboBox.setItems(comboBoxList);
        comboBox.setMaxWidth(200);
        
        // GET SELECTION FROM BOX
        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                public void changed(ObservableValue ov, Number oldValue, 
                        Number newValue) {
                    
                    // Get index value of selection from box
                    int i = newValue.intValue();
                    Chart chart = new Chart();
                    
                    // Create new charts with new index to display chosen content
                    Object lineChart = chart.displayLineChart((String) 
                            stockFilePaths.get(i));
                    Object areaChart = chart.displayAreaChart((String) 
                            stockFilePaths.get(i));
                    Object largeTable = table.displayLargeTable((String)
                            stockFilePaths.get(i));
                    
                    // Clear box and add new content
                    chartsBox.getChildren().clear();
                    chartsBox.getChildren().addAll(
                            (Node) lineChart, (Node) areaChart);
                    tableTab.setContent((Node) largeTable);
                }
            });
        
        
        // ---------- MENU ----------
        
        // HOME BUTTON
        Button homeButton = new Button("HOME");
        homeButton.setPrefWidth(200);
        homeButton.setPadding(new Insets(5));
        homeButton.setOnAction(e -> {
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll((Node) barChart, (Node) mainTable);
        });
        
        // DETAILS BUTTON
        Button detailsButton = new Button("DETAILS");
        detailsButton.setPrefWidth(200);
        detailsButton.setPadding(new Insets(5));
        detailsButton.setOnAction(e -> {
            
            // CLEAR VBOX
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll(comboBox, tabPane);
        });
        
        // REPORT BUTTON
        Button reportButton = new Button("REPORT");
        reportButton.setPrefWidth(200);
        reportButton.setPadding(new Insets(5));
        reportButton.setOnAction(e -> {
            Report report = new Report();
            report.writeToFile(stocksFileDirectory, namesFileDirectory, 
                    reportFileDirectory);
        });
        
        // ADD TO BOXES
        buttonBox.getChildren().addAll(
                homeButton, detailsButton, reportButton);
        topBox.getChildren().addAll(buttonBox);
        
        
        // ---------- SCENE ----------
        Scene scene = new Scene(borderPane, 1000, 900);
        primaryStage.setTitle("StockViewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
