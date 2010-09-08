package com.wasurenairoku.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
/**
 * ページング機能を実装するクラス
 * @author yaji
 *
 */
public class JPagingPanel extends JComponent {

    public JPagingPanel() {
        super();
        initPagingBox();
    }
    
    public JPagingPanel(long maxPageSize) {
        super();
        initPagingBox();
        setMaxPageSize(maxPageSize);
    }
    
    private PrevButton prevButton;
    private NextButton nextButton;
    private FirstButton firstButton;
    private EndButton endButton;
    private JTextField pageTextField;
    private JLabel maxPageLabel;
    private PagingEvent pagingEvent;
    private long maxPageSize;
    private long currentPage;

    /**
     * コンポーネントを配置する
     */
    private void initPagingBox() {
        
        JPanel p = new JPanel();
        //p.setSize(new Dimension(295, 37));
        p.setSize(new Dimension(141, 37));
        p.setLayout(null);
        p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        //p.add(getPrevButton());
        //p.add(getFirstButton());
        p.add(getJPanel());
        //p.add(getNextButton());
        //p.add(getEndButton());
        
        JPanel p2 = new JPanel();
        p2.setSize(new Dimension(141, 37));
        p2.setLayout(null);
        p2.add(getPrevButton());
        p2.add(getFirstButton());
        p2.add(getNextButton());
        p2.add(getEndButton());


/*
        this.setLayout(null);
        this.setSize(new Dimension(295, 37));
        this.add(p);
*/
        this.setLayout(new GridLayout(2,1));
        this.add(p);
        this.add(p2);
        //this.setSize(new Dimension(147, 72));
        setPagingEvent();
    }
    
    /**
     * ページ数を表示する部分のパネル
     * @return 生成したJPanel
     */
    private JPanel getJPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //panel.setBounds(new Rectangle(78, 6, 139, 25));
        panel.setBounds(new Rectangle(2, 6, 136, 30));
        panel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
        JLabel pageLabel = new JLabel();
        pageLabel.setText(" Page");
        pageLabel.setBounds(new Rectangle(3, 0, 34, 32));
        panel.add(pageLabel);
        panel.add(getPageTextField(), null);
        JLabel maxPageLabel = new JLabel();
        maxPageLabel.setText("");
        maxPageLabel.setBounds(new Rectangle(84, 0, 52, 32));
        panel.add(maxPageLabel);
        this.maxPageLabel = maxPageLabel;
        return panel;
    }
    
    /**
     * ページ数入力用のテキストフィールドを生成し、返却する
     * @return 生成したJTextField
     */
    private JTextField getPageTextField() {
        JTextField pageTextField = new JTextField();
        //pageTextField.setBounds(new Rectangle(39, 3, 43, 19));
        pageTextField.setBounds(new Rectangle(39, 3, 43, 24));
        this.pageTextField = pageTextField;
        return pageTextField;
    }

    /**
     * 最初のページに遷移するボタンを生成し、返却する
     * @return 生成したFirstButton
     */
    private FirstButton getFirstButton() {
        FirstButton firstButton = new FirstButton();
        //firstButton.setBounds(new Rectangle(6, 6, 31, 25));
        firstButton.setBounds(new Rectangle(3, 6, 28, 25));
        firstButton.setEnabled(false);
        this.firstButton = firstButton;
        return firstButton;
    }

    /**
     * 一つ前のページに遷移するボタンを生成し、返却する
     * @return 生成したPrevButton
     */
    private PrevButton getPrevButton() {
        PrevButton prevButton = new PrevButton();
        //prevButton.setBounds(new Rectangle(42, 6, 31, 25));
        prevButton.setBounds(new Rectangle(39, 6, 28, 25));
        prevButton.setEnabled(false);
        this.prevButton = prevButton;
        return prevButton;
    }

    /**
     * 一つ後のページに遷移するボタンを生成し、返却する
     * @return 生成したNextButton
     */
    private NextButton getNextButton() {
        NextButton nextButton = new NextButton();
        //nextButton.setBounds(new Rectangle(222, 6, 31, 25));
        nextButton.setBounds(new Rectangle(71, 6, 28, 25));
        nextButton.setEnabled(false);
        this.nextButton = nextButton;
        return nextButton;
    }

    /**
     * 最後のページに遷移するボタンを生成し、返却する
     * @return 生成したEndButton
     */
    private EndButton getEndButton() {
        EndButton endButton = new EndButton();
        //endButton.setBounds(new Rectangle(258, 6, 31, 25));
        endButton.setBounds(new Rectangle(107, 6, 28, 25));
        endButton.setEnabled(false);
        this.endButton = endButton;
        return endButton;
    }

    /**
     * 最大ページ数を設定する
     * @param maxPageSize 最大ページ数
     */
    public void setMaxPageSize(long maxPageSize) {
        this.maxPageSize = maxPageSize;
        this.prevButton.setEnabled(false);
        this.firstButton.setEnabled(false);
        this.nextButton.setEnabled(false);
        this.endButton.setEnabled(false);
        if (maxPageSize > 0) {
            if (maxPageSize > 1) {
                this.nextButton.setEnabled(true);
                this.endButton.setEnabled(true);
            }
            this.currentPage = 1;
            this.pageTextField.setText("1");
            this.maxPageLabel.setText("of " + maxPageSize);
        } else {
            this.currentPage = 0;
            this.pageTextField.setText("0");
            this.maxPageLabel.setText("");
        }
    }
    
    /**
     * 現在のページ数を返却する
     * @return 現在のページ数
     */
    public long getCurrentPage() {
        return this.currentPage;
    }
    
    /**
     * 最大ページ数を返却する
     * @return 最大ページ数
     */
    public long getMaxPageSize() {
        return this.maxPageSize;
    }
    
    /**
     * 矢印の色を設定する
     * @param color 矢印の色
     */
    public void setArrowColor(Color color) {
        this.prevButton.setArrowColor(color);
        this.nextButton.setArrowColor(color);
        this.firstButton.setArrowColor(color);
        this.endButton.setArrowColor(color);
    }
    
    /**
     * 矢印の色（Disable時）を設定する
     * @param color 矢印の色
     */
    public void setDisableArrowColor(Color color) {
        this.prevButton.setDisableArrowColor(color);
        this.nextButton.setDisableArrowColor(color);
        this.firstButton.setDisableArrowColor(color);
        this.endButton.setDisableArrowColor(color);
    }
    
    @Override
    public Dimension getMaximumSize() {
        //return new Dimension(295, 37);
        return new Dimension(141, 72);
    }

    @Override
    public Dimension getMinimumSize() {
        //return new Dimension(295, 37);
        return new Dimension(141, 72);
    }

    @Override
    public Dimension getPreferredSize() {
        //return new Dimension(295, 37);
        return new Dimension(141, 72);
    }

    /**
     * ページングイベント発生時のイベントを追加する
     * @param l ページングイベントリスナー
     */
    public void addPagingListener(PagingListener l) {
        listenerList.add(PagingListener.class, l);
    }
    
    /**
     * ページングイベント発生時のイベントを削除する
     * @param l ページングイベントリスナー
     */
    public void removePagingListener(PagingListener l) {
        listenerList.remove(PagingListener.class, l);
    }
    
    /**
     * ページングイベントを発生させるイベントを設定する
     */
    private void setPagingEvent() {
        this.prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long page = JPagingPanel.this.currentPage;
                fireActionPerformed(page, page - 1);
            }
        });
        this.nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long page = JPagingPanel.this.currentPage;
                fireActionPerformed(page, page + 1);
            }
        });
        this.firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long page = JPagingPanel.this.currentPage;
                fireActionPerformed(page, 1);
            }
        });
        this.endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long page = JPagingPanel.this.currentPage;
                fireActionPerformed(page, maxPageSize);
            }
        });
        this.pageTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String pageText = JPagingPanel.this.pageTextField.getText();
                    if (pageText != null && pageText.length() > 0) {
                        try {
                            long page = Long.parseLong(JPagingPanel.this.pageTextField.getText());
                            if (page >= 1 && page <= maxPageSize) {
                                fireActionPerformed(JPagingPanel.this.currentPage, page);
                            } else {
                                JPagingPanel.this.pageTextField.setText(Long.toString(JPagingPanel.this.currentPage));
                            }
                        } catch (NumberFormatException e1) {
                            JPagingPanel.this.pageTextField.setText(Long.toString(JPagingPanel.this.currentPage));
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }
    
    /**
     * 各ボタンの有効／無効を、現在のページから設定する
     */
    private void setPageButtonEnabled() {
        if (this.currentPage == 1) {
            this.prevButton.setEnabled(false);
            this.firstButton.setEnabled(false);
        } else {
            this.prevButton.setEnabled(true);
            this.firstButton.setEnabled(true);
        }
        if (this.currentPage == this.maxPageSize) {
            this.nextButton.setEnabled(false);
            this.endButton.setEnabled(false);
        } else {
            this.nextButton.setEnabled(true);
            this.endButton.setEnabled(true);
        }
    }
    
    /**
     * ページングイベントの設定
     * @param oldPage 変更前のページ
     * @param newPage 変更後のページ
     */
    protected void fireActionPerformed(long oldPage, long newPage) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == PagingListener.class) {
                if (pagingEvent == null) {
                    pagingEvent = new PagingEvent(this, ActionEvent.ACTION_PERFORMED);
                }
                pagingEvent.setPage(oldPage, newPage);
                
                ((PagingListener) listeners[i + 1]).actionPerformed(pagingEvent);
            }
        }
        JPagingPanel.this.currentPage = newPage;
        JPagingPanel.this.pageTextField.setText(Long.toString(JPagingPanel.this.currentPage));
        setPageButtonEnabled();
    }
}
