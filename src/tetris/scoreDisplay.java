package tetris;

import javax.swing.*;
import java.awt.*;

public class scoreDisplay extends JLabel
{

    public scoreDisplay()
    {
        this.setBounds(320, 100, 100, 30);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        // if true the component paints every pixel within its bounds
        this.setOpaque(true);
        // sets the background color of this component
        // the background color is used only if the component is opaque
        this.setBackground(Color.ORANGE);
        this.setText("Score:  0");
    }
}
