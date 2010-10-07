package com.terai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

class LineNumberView extends JComponent {
  private static final int MARGIN = 5;
  private final JTextArea text;
  private final FontMetrics fontMetrics;
  private final int topInset;
  private final int fontAscent;
  private final int fontHeight;
  public LineNumberView(JTextArea textArea) {
    text        = textArea;
    fontMetrics = getFontMetrics(text.getFont());
    fontHeight  = fontMetrics.getHeight();
    fontAscent  = fontMetrics.getAscent();
    topInset    = text.getInsets().top;
    text.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        repaint();
      }
      public void removeUpdate(DocumentEvent e) {
        repaint();
      }
      public void changedUpdate(DocumentEvent e) {}
    });
    text.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        revalidate();
        repaint();
      }
    });
    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
  }
  private int getComponentWidth() {
    Document doc  = text.getDocument();
    Element root  = doc.getDefaultRootElement();
    int lineCount = root.getElementIndex(doc.getLength());
    int maxDigits = Math.max(3, String.valueOf(lineCount).length());
    return maxDigits*fontMetrics.stringWidth("0")+MARGIN*2;
  }
  private int getLineAtPoint(int y) {
    Element root = text.getDocument().getDefaultRootElement();
    int pos = text.viewToModel(new Point(0, y));
    return root.getElementIndex(pos);
  }
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(getComponentWidth(), text.getHeight());
  }
  @Override
  public void paintComponent(Graphics g) {
    Rectangle clip = g.getClipBounds();
    g.setColor(getBackground());
    g.fillRect((int)clip.x, (int)clip.y, (int)clip.width, (int)clip.height);
    g.setColor(getForeground());
    int base  = clip.y - topInset;
    int start = getLineAtPoint(base);
    int end   = getLineAtPoint(base + (int)clip.height);
    int y = topInset-fontHeight+fontAscent+start*fontHeight;
    for(int i=start;i<=end;i++) {
      String text = String.valueOf(i+1);
      int x = getComponentWidth()-MARGIN-fontMetrics.stringWidth(text);
      y = y + fontHeight;
      g.drawString(text, x, y);
    }
  }
}
