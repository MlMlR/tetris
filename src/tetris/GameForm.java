

package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * @author unknown
 */
public class GameForm extends JFrame
{
    private GameArea ga;
    private JLabel sd;
    private JLabel ld;

    public GameForm()
    {
        initComponents();

        ga = new GameArea(10);
        this.add( ga );

        sd = new scoreDisplay();
        this.add(sd);

        ld = new levelDisplay();
        this.add(ld);
        initControls();

        startGame();
    }

    private void initControls()
    {
        InputMap in = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        in.put(KeyStroke.getKeyStroke("RIGHT"),"right");
        in.put(KeyStroke.getKeyStroke("LEFT"),"left");
        in.put(KeyStroke.getKeyStroke("UP"),"up");
        in.put(KeyStroke.getKeyStroke("DOWN"),"down");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockLeft();
            }
        });

        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.rotateBlock();
            }
        });

        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.dropBlock();
            }
        });
    }

    public void startGame()
    {
        new GameThread(ga, this).start();
    }

    public void updateScore(int score)
    {
        sd.setText("Score:  " + score);
    }

    public void updateLevel(int level)
    {
        ld.setText("Level: " + level);
    }

    private void initComponents() {
        this.setLayout(null);
        this.setSize(600, 600);
        this.setContentPane(getContentPane());
        this.setTitle("TETRIS");
        this.getContentPane().setBackground(Color.BLUE);
        this.setResizable(false);
    }
    @SuppressWarnings("unchecked")

    public static void main(String[] args) {

        /*create and display the form*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }
}