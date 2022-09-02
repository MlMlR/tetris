package tetris;

import javax.swing.*;
import java.awt.*;

public class levelDisplay extends JLabel {

    public levelDisplay()
    {
        this.setBounds(320, 140, 100, 30);
        this.setBackground(Color.orange);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setOpaque(true);
        this.setBackground(Color.yellow);
        this.setText("Level:  0");
    }
}
