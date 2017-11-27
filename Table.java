package stockviewer1320342;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class Table {
    
    public static Object displayMainTable(String stocksFileDirectory,
            String namesFileDirectory) {
        
        // ----- TABLEVIEW
        
        // http://stackoverflow.com/questions/10152828/javafx-2-automatic-column-width
        // https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm
        
        TableView<TableEntryMain> table = new TableView<TableEntryMain>();
        
        // ----- COLUMNS
        TableColumn symbol = new TableColumn("Stock Symbol:");  
        TableColumn name = new TableColumn("Company Name:");
        TableColumn price = new TableColumn("Latest Price:");
        
        // ----- WIDTH
        symbol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        name.prefWidthProperty().bind(table.widthProperty().multiply(0.34));
        price.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        
        // ----- CELL VALUE FACTORY
        symbol.setCellValueFactory(
                new PropertyValueFactory<TableEntryMain, Integer>("symbol"));
        name.setCellValueFactory(
                new PropertyValueFactory<TableEntryMain, String>("name"));
        price.setCellValueFactory(
                new PropertyValueFactory<TableEntryMain, Integer>("price"));

        table.getColumns().addAll(symbol, name, price);
        
        // ----- CSV
        CSV csv = new CSV();
        
        // ----- FILEPATHS
        List stockFilePaths = csv.getFilePaths(stocksFileDirectory);
        List nameFilePaths = csv.getFilePaths(namesFileDirectory);
        
        // ----- LISTS
        List companySymbolList = new ArrayList();
        List companyNameList = new ArrayList();
        List recentCloseList = new ArrayList();
        
        // ----- GET COMPANY NAMES
        for (int i=0; i<nameFilePaths.size(); i++) {
            List fileContent = csv.readFile((String) nameFilePaths.get(i));
            
            for (int j=0; j<fileContent.size(); j+=3) {
                companySymbolList.add(fileContent.get(j));
                companyNameList.add(fileContent.get(j+1));
            }
        }
        
        // ----- GET RECENT CLOSE
        for (int i=0; i<stockFilePaths.size(); i++) {
            List fileContent = csv.readFile((String) stockFilePaths.get(i));
            String recentClose = (String) fileContent.get(11);
            recentCloseList.add(recentClose);
        }
        
        // ----- INSERT IN TABLE
        ObservableList<TableEntryMain> tableList = FXCollections.observableArrayList();
        
        for (int i=0; i<recentCloseList.size(); i++) {
            String companySymbol = (String) companySymbolList.get(i);
            String companyName = (String) companyNameList.get(i);
            Float recentClose = Float.parseFloat((String) recentCloseList.get(i));
            tableList.add(new TableEntryMain(
                    companySymbol, companyName, recentClose));
        }
        table.setItems(tableList);
        
        // ----- RETURN
        return table;
    }

    public static Object displayLargeTable(String filePath) {
        
        // ----- TABLEVIEW
        // http://stackoverflow.com/questions/10152828/javafx-2-automatic-column-width
        // http://www.java2s.com/Tutorials/Java/JavaFX/0670__JavaFX_Tree_Table_View.htm
        
        // ----- CSV
        CSV csv = new CSV();
        List contentList = csv.readFile(filePath);
        ObservableList<TableEntryLarge> tableList = FXCollections.observableArrayList();
        
        // ---------- ELEMENTS
        // GET ELEMENTS AND ADD TO LIST
        for (int i=7; i<contentList.size(); i+=7) {
            
            String date = (String) contentList.get(i);
            Float open = Float.valueOf((String) contentList.get(i+1));
            Float close = Float.valueOf((String) contentList.get(i+4));
            Float high = Float.valueOf((String) contentList.get(i+2));
            Float low = Float.valueOf((String) contentList.get(i+3));
            Float volume = Float.valueOf((String) contentList.get(i+5));
            Float adj = Float.valueOf((String) contentList.get(i+6));

            tableList.add(
                    new TableEntryLarge(date, open, close, high, low, volume, adj));
        }
        
        // ----- TABLEVIEW
        // http://stackoverflow.com/questions/10152828/javafx-2-automatic-column-width
        // https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm
        
        TableView<TableEntryLarge> table = new TableView<TableEntryLarge>();
        
        // ----- COLUMNS
        TableColumn date = new TableColumn("Date:");
        TableColumn open = new TableColumn("Open:");
        TableColumn close = new TableColumn("Close:");
        TableColumn high = new TableColumn("High:");
        TableColumn low = new TableColumn("Low:");
        TableColumn volume = new TableColumn("Volume:");
        TableColumn adj = new TableColumn("Adjusted Close:");
        
        // ----- WIDTH
        date.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        open.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        close.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        high.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        low.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        volume.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        adj.prefWidthProperty().bind(table.widthProperty().multiply(0.142));
        
        // ----- CELL VALUE FACTORY
        date.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("date"));
        open.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("open"));
        close.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("close"));
        high.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("high"));
        low.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("low"));
        volume.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("volume"));
        adj.setCellValueFactory(
                new PropertyValueFactory<TableEntryLarge, Integer>("adj"));
        
        table.getColumns().addAll(date, open, close, high, low, volume, adj);
        table.setItems(tableList);
        
        // ----- RETURN
        return table;
    }
}
