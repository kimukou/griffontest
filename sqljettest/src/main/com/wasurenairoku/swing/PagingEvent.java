package com.wasurenairoku.swing;
import java.awt.AWTEvent;


public class PagingEvent extends AWTEvent {

    private long oldPage;
    private long newPage;

    public long getOldPage() {
        return oldPage;
    }

    public long getNewPage() {
        return newPage;
    }
    
    protected void setOldPage(long oldPage) {
        this.oldPage = oldPage;
    }
    
    protected void setNewPage(long newPage) {
        this.newPage = newPage;
    }
    
    protected void setPage(long oldPage, long newPage) {
        this.oldPage = oldPage;
        this.newPage = newPage;
    }
    
    public PagingEvent(Object obj, int i) {
        super(obj, i);
    }
}
