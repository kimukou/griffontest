package com.wasurenairoku.swing;
import java.util.EventListener;

public interface PagingListener extends EventListener {
    public abstract void actionPerformed(PagingEvent event);
}
