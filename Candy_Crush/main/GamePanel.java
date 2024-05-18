package main;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    final int originalTileSize = 16; // defaul 16x16 size
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tiles
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Panel size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }
}
