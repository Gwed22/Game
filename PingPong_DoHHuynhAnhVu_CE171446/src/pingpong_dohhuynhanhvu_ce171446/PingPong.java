/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingpong_dohhuynhanhvu_ce171446;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dell
 */
public class PingPong extends javax.swing.JFrame {

    int time;
    Thread timer;
    Thread game;
    public static int BallSize = 20;
    public static int racketHeight = 40;
    public static int racketWidth = 6;
    public static int sceneH = 400;
    public static int sceneW = 800;

    int redPoint;
    int greenPoint;
    JLabel red;

    int yRed, xRed;
    int xGreen, yGreen;
    JLabel green;

    int xBall, yBall, dxBall, dyBall;
    int ballSpeed, racketSpeed;

    public void reset() {
        redPoint = greenPoint = 0;
        xRed = 10;

        xGreen = sceneW - racketWidth - 10;
        yRed = yGreen = (sceneH - racketHeight) / 2;

        xBall = (sceneW - BallSize) / 2;
        yBall = (sceneH - BallSize) / 2;

        int dx = Randomizer.random(0, 1);
        dxBall = dx == 0 ? -1 : 1;
        int dy = Randomizer.random(0, 1);
        dyBall = dy == 0 ? -1 : 1;

        ballSpeed = 7;
        racketSpeed = 5;

        time = 0;
    }

    public void replay() {
        xBall = (sceneW - BallSize) / 2;
        yBall = (sceneH - BallSize) / 2;

        int dx = Randomizer.random(0, 1);
        dxBall = dx == 0 ? -1 : 1;
        int dy = Randomizer.random(0, 1);
        dyBall = dy == 0 ? -1 : 1;
    }

    public void initScene() {
        pnlScene.setLayout(null);
        pnlScene.removeAll();
        pnlScene.revalidate();
        pnlScene.repaint();
        pnlScene.setSize(sceneW, sceneH);

        red = new JLabel();
        red.setOpaque(true);
        red.setBounds(xRed, yRed, racketWidth, racketHeight);
        red.setBackground(Color.red);
        red.setPreferredSize(new Dimension(racketWidth, racketHeight));
        red.setLocation(xRed, yRed);

        green = new JLabel();
        green.setOpaque(true);
        green.setBounds(xGreen, yGreen, racketWidth, racketHeight);
        green.setBackground(Color.green);
        green.setPreferredSize(new Dimension(racketWidth, racketHeight));
        green.setLocation(xGreen, yGreen);

        Ball = new JLabel();
        Ball.setPreferredSize(new Dimension(BallSize, BallSize));
        Ball.setBounds(xBall, yBall, BallSize, BallSize);
        Ball.setLocation(xBall, yBall);
        Ball.setIcon(new ImageIcon(getClass().getResource("pp.png")));

        pnlScene.add(red);
        pnlScene.add(green);
        pnlScene.add(Ball);
    }

    private String int2time(int time) {
        return String.format("%02d:%02d:%02d", time / 3600, (time / 60) % 60, time % 60);
    }

    public void runTimer() {
        timer = new Thread() {
            public void run() {
                while (true) {
                    try {
                        ++time;
                        Time.setText(int2time(time));
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PingPong.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        timer.start();
    }

    public void GameOver() {
        if (xBall <= 0) {
            greenPoint++;
            Message.setText("Green player is the winner! Press SPACE BAR to continue.");
            Message.setForeground(Color.green);
        }
        if (xBall > sceneW) {
            redPoint++;
            Message.setText("Red player is the winner! Press SPACE BAR to contibue.");
            Message.setForeground(Color.red);
        }
        Red.setText(redPoint + "");
        Green.setText(greenPoint + "");

        timer.stop();
        game.stop();
    }

    public void runGame() {
        game = new Thread() {
            public void run() {
                while (true) {
                    try {
                        //di chuyen vot do
                        red.setBounds(xRed, yRed, racketWidth, racketHeight);
                        //di chuyen vot xanh
                        green.setBounds(xGreen, yGreen, racketWidth, racketHeight);
                        //di chuyen banh
                        xBall = xBall + ballSpeed * dxBall;
                        yBall = yBall + ballSpeed * dyBall;
                        if (isGameOver()) {
                            GameOver();
                        } else if (isHitRed() || isHitGreen()) {
                            dxBall = -dxBall;
                        }
//                        if (xBall < 0) {
//                            xBall = 0;
//                            dxBall = -dxBall;//doi huong di chuyen
//                        } else if (xBall > sceneW - BallSize) {
//                            xBall = sceneW - BallSize;
//                            dxBall = -dxBall;
//                        }
                        if (yBall < 0) {
                            yBall = 0;
                            dyBall = -dyBall;//doi huong di chuyen
                        } else if (yBall > sceneH - BallSize) {
                            yBall = sceneH - BallSize;
                            dyBall = -dyBall;
                        }
                        Ball.setBounds(xBall, yBall, BallSize, BallSize);
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PingPong.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        game.start();
    }

    public boolean isGameOver() {
        return xBall <= 0 || xBall >= sceneW;
    }

    public boolean isHitRed() {
        int bx = xBall + BallSize / 2;
        int by = yBall + BallSize / 2;

        int rx = xRed + racketWidth / 2;

        if (Math.abs(bx - rx) <= BallSize / 2) {
            if (yRed <= by && by <= yRed + racketHeight) {
                return true;
            }
        }
        return false;
    }

    public boolean isHitGreen() {
        int bx = xBall + BallSize / 2;
        int by = yBall + BallSize / 2;

        int gx = xGreen + racketWidth / 2;

        if (Math.abs(bx - gx) <= BallSize / 2) {
            if (yGreen <= by && by <= yGreen + racketHeight) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates new form P
     */
    public PingPong() {
        initComponents();
        this.setLocationRelativeTo(null); //Center the frame to the screen

        reset();
        initScene();
        runTimer();
        runGame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGameInfo = new javax.swing.JPanel();
        Redlb = new javax.swing.JLabel();
        Greenlb = new javax.swing.JLabel();
        Red = new javax.swing.JLabel();
        Green = new javax.swing.JLabel();
        Timelb = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        Message = new javax.swing.JLabel();
        pnlScene = new javax.swing.JPanel();
        Ball = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pnlGameInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlGameInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Game Infomation"));

        Redlb.setForeground(new java.awt.Color(255, 0, 0));
        Redlb.setText("Red:  ");

        Greenlb.setForeground(new java.awt.Color(0, 204, 51));
        Greenlb.setText("Green: ");

        Red.setForeground(new java.awt.Color(255, 0, 0));
        Red.setText("0");

        Green.setForeground(new java.awt.Color(0, 204, 51));
        Green.setText("0");

        Timelb.setText("Time: ");

        Time.setText("00:00:00");

        Message.setForeground(new java.awt.Color(255, 153, 51));
        Message.setText("Message");

        javax.swing.GroupLayout pnlGameInfoLayout = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(pnlGameInfoLayout);
        pnlGameInfoLayout.setHorizontalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Redlb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Red)
                .addGap(75, 75, 75)
                .addComponent(Greenlb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Green)
                .addGap(117, 117, 117)
                .addComponent(Timelb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Time)
                .addGap(103, 103, 103)
                .addComponent(Message)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlGameInfoLayout.createSequentialGroup()
                        .addComponent(Message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Redlb)
                        .addComponent(Greenlb)
                        .addComponent(Red)
                        .addComponent(Green)
                        .addComponent(Timelb)
                        .addComponent(Time)))
                .addGap(78, 78, 78))
        );

        pnlScene.setBackground(new java.awt.Color(255, 255, 255));
        pnlScene.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlScene.setMinimumSize(new java.awt.Dimension(800, 400));
        pnlScene.setLayout(null);

        Ball.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pingpong_dohhuynhanhvu_ce171446/pp.png"))); // NOI18N
        pnlScene.add(Ball);
        Ball.setBounds(440, 160, 20, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlScene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            yGreen -= racketSpeed;
        }
        if (key == KeyEvent.VK_DOWN) {
            yGreen += racketSpeed;
        }
        if (key == KeyEvent.VK_W) {
            yRed -= racketSpeed;
        }
        if (key == KeyEvent.VK_S) {
            yRed += racketSpeed;
        }
        if (key == KeyEvent.VK_SPACE) {
            replay();
            initScene();
            runTimer();
            runGame();
        }
    }//GEN-LAST:event_formKeyPressed

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
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PingPong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ball;
    private javax.swing.JLabel Green;
    private javax.swing.JLabel Greenlb;
    private javax.swing.JLabel Message;
    private javax.swing.JLabel Red;
    private javax.swing.JLabel Redlb;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel Timelb;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlScene;
    // End of variables declaration//GEN-END:variables
}
