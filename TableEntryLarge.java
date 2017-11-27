package stockviewer1320342;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class TableEntryLarge {
    
    // http://www.drdobbs.com/jvm/coding-the-javafx-tableview/240001874
    // http://docs.oracle.com/javafx/2/ui_controls/table-view.htm

    public SimpleStringProperty date;
    public SimpleFloatProperty open;
    public SimpleFloatProperty close;
    public SimpleFloatProperty high;
    public SimpleFloatProperty low;
    public SimpleFloatProperty volume;
    public SimpleFloatProperty adj;
    
    TableEntryLarge(String fDate, Float fOpen, Float fClose, Float fHigh, 
            Float fLow, Float fVolume, Float fAdj) {
        this.date = new SimpleStringProperty(fDate);
        this.open = new SimpleFloatProperty(fOpen);
        this.close = new SimpleFloatProperty(fClose);
        this.high = new SimpleFloatProperty(fHigh);
        this.low = new SimpleFloatProperty(fLow);
        this.volume = new SimpleFloatProperty(fVolume);
        this.adj = new SimpleFloatProperty(fAdj);
    }
    
    // ----- DATE
    public String getDate() {
        return date.get();
    }
    public void setDate(String fDate) {
        date.set(fDate);
    }
    
    // ----- OPEN
    public Float getOpen() {
        return open.get();
    }
    public void setOpen(Float fOpen) {
        open.set(fOpen);
    }
    
    // ----- CLOSE
    public Float getClose() {
        return close.get();
    }
    public void setClose(Float fClose) {
        close.set(fClose);
    }
    
    // ----- HIGH
    public Float getHigh() {
        return high.get();
    }
    public void setHigh(Float fHigh) {
        high.set(fHigh);
    }
    
    // ----- LOW
    public Float getLow() {
        return low.get();
    }
    public void setLow(Float fLow) {
        low.set(fLow);
    }
    
    // ----- VOLUME
    public Float getVolume() {
        return volume.get();
    }
    public void setVolume(Float fVolume) {
        volume.set(fVolume);
    }
    
    // ----- CLOSE
    public Float getAdj() {
        return adj.get();
    }
    public void setAdj(Float fAdj) {
        adj.set(fAdj);
    }
    
    // ----- ON ADD
    public void onAddItem(ActionEvent e) {
        
    }
}
