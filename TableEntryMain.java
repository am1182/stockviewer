package stockviewer1320342;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class TableEntryMain {

    // http://www.drdobbs.com/jvm/coding-the-javafx-tableview/240001874
    // http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
    
    public SimpleStringProperty symbol;
    public SimpleStringProperty name;
    public SimpleFloatProperty price;
    
    TableEntryMain(String fSymbol, String fName, Float fPrice) {
        this.symbol = new SimpleStringProperty(fSymbol);
        this.name = new SimpleStringProperty(fName);
        this.price = new SimpleFloatProperty(fPrice);
    }
    
    // ----- SYMBOL
    public String getSymbol() {
        return symbol.get();
    }
    public void setSymbol(String fSymbol) {
        symbol.set(fSymbol);
    }
    
    // ----- NAME
    public String getName() {
        return name.get();
    }
    public void setName(String fName) {
        name.set(fName);
    }
    
    // ----- PRICE
    public Float getPrice() {
        return price.get();
    }
    public void setPrice(Float fPrice) {
        price.set(fPrice);
    }
    
    // ----- ON ADD
    public void onAddItem(ActionEvent e) {
        
    }
}
