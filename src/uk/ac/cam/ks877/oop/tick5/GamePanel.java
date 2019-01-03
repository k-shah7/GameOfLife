package uk.ac.cam.ks877.oop.tick5;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private World world;

    public GamePanel() {
        world = null;
    }

    public GamePanel(World w) {
        world = w;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {

        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        // get dimensions of square size

        if (world != null) {
            int squaredimension;
            int worldWidth = world.getWidth();
            int worldHeight = world.getHeight();
            int panelHeight = getHeight();
            int panelWidth = getWidth();
            int squareHeight = panelHeight / worldHeight;
            int squareWidth = panelWidth / worldWidth;
            if (squareHeight > squareWidth) {
                squaredimension = squareWidth;
            } else {
                squaredimension = squareHeight;
            }
            for (int i = 0; i < worldWidth; i++) {
                int start_x = i * squaredimension;
                for (int j = 0; j < worldHeight; j++) {
                    int start_y = j * squaredimension;
                    boolean state = world.getCell(i, j);
                    if(state) {
                        g.setColor(Color.BLACK);
                    }
                    else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(start_x, start_y, squaredimension, squaredimension);
                    g.setColor(Color.WHITE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(start_x, start_y, squaredimension, squaredimension);
                }
            }
        }
    }

    public void display(World w) {
        world = w;
        repaint();
    }
}