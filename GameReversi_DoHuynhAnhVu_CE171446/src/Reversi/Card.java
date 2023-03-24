/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reversi;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Do Huynh Anh Vu - CE171446
 */
public class Card extends JLabel {

    int col, row, value;
    private MouseListener mouseClicked;
    private MyReversi parent;

    public Card(MyReversi parent, int row, int col, int value) {
        this.parent = parent;
        this.row = row;
        this.col = col;
        this.value = value;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.mouseClicked = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardClicked();
            }
        };
        this.addMouseListener(mouseClicked);
        updateFace();
    }

    private void cardClicked() {
        int count = parent.countAllDir(row, col);
        if (count > 0) {
            turnOn(parent.getCount());
            parent.nextCount();
            this.removeMouseListener(mouseClicked);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            parent.updatePoint();
        }
    }

    public void updateFace() {
        this.setIcon(getFace());
    }

    private ImageIcon getFace() {
        return new ImageIcon(getClass().getResource("/img/" + value + ".png"));
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateFace();
    }

    public void turnOn(int count) {
        this.value = value * parent.turnValue + count;
        updateFace();
    }

    public void reTurn() {
        int bg = this.value / parent.turnValue;
        int v1 = this.value % parent.turnValue;
        v1 = 1 - v1;
        this.value = bg * parent.turnValue + v1;
        updateFace();
    }
}
