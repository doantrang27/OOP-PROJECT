// package main;

// import java.awt.BasicStroke;
// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.Toolkit;
// import java.awt.event.ActionListener;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Random;
// import java.util.Timer;

// import javax.swing.JComponent;
// import javax.swing.JLabel;
// import javax.imageio.ImageIO;
// import javax.swing.JPanel;

// // A simple game panel
// /**
// * The GamePanel class represents the panel where the game is displayed.
// * It extends the JPanel class and provides methods for painting the game
// * components on the panel.
// */
// public class GamePanel extends JPanel {
// static final int ORIGINAL_TILE_SIZE = 16; // defaul 16x16 size
// final int SCALE = 3;

// final int tileSize = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tiles
// final int maxScreenCol = 16;
// final int maxScreenRow = 12;
// final int screenWidth = tileSize * maxScreenCol; // 768 pixels
// final int screenHeight = tileSize * maxScreenRow; // 576 pixels

// private BufferedImage backgroundImage;
// // TileManager tileM = new TileManager(this);

// // Create a game panel without any parameter
// public GamePanel() {
// try {
// backgroundImage = ImageIO
// .read(new File("Candy_Crush/res/Background.jpg"));
// } catch (IOException ex) {
// ex.printStackTrace();
// }
// // this.setPreferredSize(new Dimension(screenWidth, screenHeight));
// // this.setPreferredSize(new Dimension(800, 600));
// // Panel size
// // this.setDoubleBuffered(true);
// this.setPreferredSize(new Dimension(backgroundImage.getWidth(),
// backgroundImage.getHeight()));
// this.setDoubleBuffered(true);

// // create a square component and add into the panel
// SquareComponent square = new SquareComponent(350);
// this.add(square, BorderLayout.CENTER);

// }

// @Override
// public void paintComponent(Graphics g) {
// super.paintComponent(g);

// g.drawImage(this.backgroundImage, 0, 0, this);

// // tileM.draw(g2D);
// // g2D.dispose();

// }

// class SquareComponent extends JComponent {
// final int size;

// // constructor from width and height
// public SquareComponent(int size) {
// this.size = size;
// this.setPreferredSize(new Dimension(size, size));
// }

// @Override
// protected void paintComponent(Graphics g) {
// super.paintComponent(g);
// int size = Math.min(this.getWidth(), getHeight()) - 50; // Subtract 50 for
// padding
// int x = (getWidth() - size) / 2;
// int y = (getHeight() - size) / 2;

// // Draw the square with a yellow border
// Graphics2D g2D = (Graphics2D) g;
// g2D.setColor(Color.YELLOW);
// g2D.setStroke(new BasicStroke(6));
// g2D.drawRect(x, y, size, size);

// // Fill the square with a blurred grey color
// g2D.setColor(new Color(192, 192, 192, 100)); // Semi-transparent grey
// g2D.fillRect(x, y, size, size);
// }
// }

// }
