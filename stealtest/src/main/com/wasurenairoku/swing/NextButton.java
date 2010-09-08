package com.wasurenairoku.swing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JButton;


public class NextButton extends JButton {
    
    private int arrowSize = 10;
    private Color arrowColor = Color.BLACK;
    private Color disableArrowColor = Color.LIGHT_GRAY;
    
    public NextButton() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        double height = this.getSize().getHeight();
        double width = this.getSize().getWidth();
        
        g.setColor(getArrowColor());
        Polygon polygon = new Polygon();
        
        int top = (int) (height - arrowSize) / 2;
        int bottom = top + arrowSize;
        int middle = top + arrowSize / 2;
        int left = (int) (width  - arrowSize / 2) / 2;
        int right = left + arrowSize / 2;
        
        polygon.addPoint(left, top);
        polygon.addPoint(left, bottom);
        polygon.addPoint(right, middle);
        g.fillPolygon(polygon);
    }
    
    private Color getArrowColor() {
        if (isEnabled()) {
            return arrowColor;
        } else {
            return disableArrowColor;
        }
    }

    /**
     * 矢印のサイズを設定する。値は縦の長さ。
     * @param size 矢印のサイズ
     */
    public void setArrowSize(int size) {
        this.arrowSize = size;
    }
    
    /**
     * 矢印の色を設定する。
     * @param color 矢印の色
     */
    public void setArrowColor(Color color) {
        this.arrowColor = color;
    }
    
    /**
     * 矢印の色を設定する。（無効時）
     * @param color 矢印の色
     */
    public void setDisableArrowColor(Color color) {
        this.disableArrowColor = color;
    }
}
