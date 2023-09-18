package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public RoundPanel() {
        super();
        setOpaque(true);
        setSize(80, 30);
        setBackground(new Color(36, 179, 193));
    }

    @Override
    public void paint(Graphics g) {
        int fieldX = 0;
        int fieldY = 0;
        int fieldWeight = getSize().width;
        int fieldHeight = getSize().height;
        RoundRectangle2D rect = new RoundRectangle2D.Double(fieldX, fieldY, fieldWeight, fieldHeight, 45, 45);
        g.setClip(rect);
        super.paint(g);
    }
}

