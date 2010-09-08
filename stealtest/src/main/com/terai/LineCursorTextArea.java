package com.terai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

class LineCursorTextArea extends JTextArea {
  private static final Color cfc = Color.BLUE;
  private final DefaultCaret caret;
  public LineCursorTextArea() {
    super();
    caret = new DefaultCaret() {
      @Override
      protected synchronized void damage(Rectangle r) {
        if(r!=null) {
          JTextComponent c = getComponent();
          x = 0;
          y = r.y;
          width  = c.getSize().width;
          height = r.height;
          c.repaint();
        }
      }
    };
    caret.setBlinkRate(getCaret().getBlinkRate());
    setCaret(caret);
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    Insets i = getInsets();
    //int y = g2.getFontMetrics().getHeight()*getLineAtCaret(this)+i.top;
    int y = caret.y+caret.height-1;
    g2.setPaint(cfc);
    g2.drawLine(i.left, y, getSize().width-i.left-i.right, y);
  }
//   public static int getLineAtCaret(JTextComponent component) {
//     int caretPosition = component.getCaretPosition();
//     Element root = component.getDocument().getDefaultRootElement();
//     return root.getElementIndex(caretPosition)+1;
//   }
}
