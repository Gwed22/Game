/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reversi;

import java.awt.GridLayout;

/**
 *
 * @author Do Huynh Anh Vu - CE171446
 */
public class MyReversi extends javax.swing.JFrame {

    public static final int numCols = 8;
    public static final int numRows = 8;
    public static final int RedTurn = 0;
    public static final int BlueTurn = 1;
    public static final int turnValue = 10;
    Card map[][];
    int count; //count = 0 => red , count = 1 => blue

    public void generateBoard() {
        map = new Card[numRows][numCols];

        pnlBoard.setLayout(new GridLayout(numRows, numCols));
        pnlBoard.removeAll();
        pnlBoard.revalidate();
        pnlBoard.repaint();

        int color = 1;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                color = ((i + j) % 2 == 0) ? 2 : 1;
                map[i][j] = new Card(this, i, j, color);
                pnlBoard.add(map[i][j]);
            }
        }
        map[3][3].turnOn(RedTurn);
        map[4][4].turnOn(RedTurn);

        map[3][4].turnOn(BlueTurn);
        map[4][3].turnOn(BlueTurn);

        count = RedTurn;
    }

    public int getCount() {
        return count;
    }

    public void nextCount() {
//        if (count == RedTurn) {
//            count = BlueTurn;
//        } else {
//            count = RedTurn;
//        }
        count = 1 - count;
    }

    public boolean coQuanCo(Card c) {
        return c.getValue() > 9;
    }

    public boolean khacMau(Card c, int count) {
        return (c.getValue() % turnValue) != count;
    }

    public boolean cungMau(Card c, int count) {
        return (c.getValue() % turnValue) == count;
    }

    public int countUp(int row, int col) {
        int i = 0, dem = 0;
        for (i = row - 1; i >= 0; --i) {
            if (coQuanCo(map[i][col])
                    && khacMau(map[i][col], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i >= 0) {
            if (coQuanCo(map[i][col])
                    && cungMau(map[i][col], count)) {
                for (int j = i + 1; j < row; j++) {
                    map[j][col].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countDown(int row, int col) {
        int i = 0, dem = 0;
        for (i = row + 1; i < numRows; ++i) {
            if (coQuanCo(map[i][col])
                    && khacMau(map[i][col], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i < numRows) {
            if (coQuanCo(map[i][col])
                    && cungMau(map[i][col], count)) {
                for (int j = row + 1; j < i; j++) {
                    map[j][col].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countLeft(int row, int col) {
        int i = 0, dem = 0;
        for (i = col - 1; i >= 0; --i) {
            if (coQuanCo(map[row][i])
                    && khacMau(map[row][i], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i >= 0) {
            if (coQuanCo(map[row][i])
                    && cungMau(map[row][i], count)) {
                for (int j = i + 1; j < col; j++) {
                    map[row][j].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countRight(int row, int col) {
        int i = 0, dem = 0;
        for (i = col + 1; i < numCols; ++i) {
            if (coQuanCo(map[row][i])
                    && khacMau(map[row][i], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i < numCols) {
            if (coQuanCo(map[row][i])
                    && cungMau(map[row][i], count)) {
                for (int j = col + 1; j < i; j++) {
                    map[row][j].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countLeftUp(int row, int col) {
        int i = 0, dem = 0, j = 0;
        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i >= 0 && j >= 0) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], count)) {
                for (int h = i + 1, k = j + 1; h < row && k < col; h++, k++) {
                    map[h][k].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countRightUp(int row, int col) {
        int i = 0, dem = 0, j = 0;
        for (i = row - 1, j = col + 1; i >= 0 && j < numCols; --i, ++j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i >= 0 && j < numCols) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], count)) {
                for (int h = i + 1, k = j - 1; h < row && k >= col + 1; h++, k--) {
                    map[h][k].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countLeftDown(int row, int col) {
        int i = 0, dem = 0, j = 0;
        for (i = row + 1, j = col - 1; i < numRows && j >= 0; ++i, --j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i < numRows && j >= 0) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], count)) {
                for (int h = i - 1, k = j + 1; h >= row + 1 && k < col; h--, k++) {
                    map[h][k].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countRightDown(int row, int col) {
        int i = 0, dem = 0, j = 0;
        for (i = row + 1, j = col + 1; i < numRows && j < numCols; ++i, ++j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], count)) {
                dem++;
            } else {
                break;
            }
        }
        if (i < numRows && j < numCols) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], count)) {
                for (int h = row + 1, k = col + 1; h < i && k < j; h++, k++) {
                    map[h][k].reTurn();
                }
                return dem;
            }
        }
        return 0;
    }

    public int countAllDir(int row, int col) {
        int up = countUp(row, col);
        int down = countDown(row, col);
        int left = countLeft(row, col);
        int right = countRight(row, col);
        int leftup = countLeftUp(row, col);
        int rightup = countRightUp(row, col);
        int leftdown = countLeftDown(row, col);
        int rightdown = countRightDown(row, col);

        return up + down + left + right + leftup + rightup + leftdown + rightdown;
    }
    
    public void updatePoint() {
        int countRed = 0;
        int countBlue= 0;
        int v;
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (coQuanCo(map[i][j])) {
                    v = map[i][j].getValue() % MyReversi.turnValue;
                    if (v == RedTurn) {
                        countRed++;
                    } else {
                        countBlue++;
                    }
                }
            }
        }
        rPoint.setText(countRed + "" );
        bPoint.setText(countBlue + "");
    }

    /**
     * Creates new form NewJFrame
     */
    public MyReversi() {
        initComponents();
        this.setLocationRelativeTo(null); //canh frame giua man hinh
        generateBoard();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GameInfo = new javax.swing.JPanel();
        Red = new javax.swing.JLabel();
        rPoint = new javax.swing.JLabel();
        Blue = new javax.swing.JLabel();
        bPoint = new javax.swing.JLabel();
        pnlBoard = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GameInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Game Information"));

        Red.setForeground(new java.awt.Color(204, 0, 0));
        Red.setText("Red:");

        rPoint.setForeground(new java.awt.Color(204, 0, 0));
        rPoint.setText("2");

        Blue.setForeground(new java.awt.Color(51, 0, 204));
        Blue.setText("Blue:");

        bPoint.setForeground(new java.awt.Color(51, 0, 204));
        bPoint.setText("2");

        javax.swing.GroupLayout GameInfoLayout = new javax.swing.GroupLayout(GameInfo);
        GameInfo.setLayout(GameInfoLayout);
        GameInfoLayout.setHorizontalGroup(
            GameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Red)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rPoint)
                .addGap(90, 90, 90)
                .addComponent(Blue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bPoint)
                .addContainerGap(320, Short.MAX_VALUE))
        );
        GameInfoLayout.setVerticalGroup(
            GameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameInfoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(GameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Red)
                    .addComponent(rPoint)
                    .addComponent(Blue)
                    .addComponent(bPoint))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBoardLayout = new javax.swing.GroupLayout(pnlBoard);
        pnlBoard.setLayout(pnlBoardLayout);
        pnlBoardLayout.setHorizontalGroup(
            pnlBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlBoardLayout.setVerticalGroup(
            pnlBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyReversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyReversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyReversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyReversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyReversi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Blue;
    private javax.swing.JPanel GameInfo;
    private javax.swing.JLabel Red;
    private javax.swing.JLabel bPoint;
    private javax.swing.JPanel pnlBoard;
    private javax.swing.JLabel rPoint;
    // End of variables declaration//GEN-END:variables
}
