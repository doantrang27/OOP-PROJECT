// package main;

// import java.awt.Graphics;
// import java.awt.image.BufferedImage;
// import java.awt.image.ImageObserver;
// import java.awt.Point;
// import java.io.File;
// import java.io.IOException;
// import javax.imageio.ImageIO;

// public class Candy {
// // ImageIcon[] icon = new ImageIcon[5];

// // JPanel jp = new JPanel(new GridLayout (6,10));

// // image that represents the coin's position on the board
// private BufferedImage[] images = new BufferedImage[5];
// // current position of the coin on the board grid
// private Point pos;
// public int size;

// public Candy(int x, int y) {
// // load the assets
// loadImage();
// // initialize the state
// pos = new Point(x, y);
// }

// private void loadImage() {
// try {

// for (int i = 0; i < images.length; i++) {
// images[i] = ImageIO.read(new File("Candy_Crush/res/Obj" + (i + 1) + ".png"));
// }

// } catch (IOException exc) {
// System.out.println("Error opening image file: " + exc.getMessage());
// }
// }

// public void draw(Graphics g, ImageObserver observer) {

// for (BufferedImage img : images) {
// // g.drawImage(
// // img,
// // pos.x * Board.TILE_SIZE,
// // pos.y * Board.TILE_SIZE,
// // observer);
// // Draw only one image instead of all, assuming each candy has only one image
// if (images[0] != null) {
// g.drawImage(images[0], pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE,
// observer);
// }
// // g.drawImage(img, pos.x, pos.y, observer);
// }
// }

// public Point getPos() {
// return pos;
// }

// public void setSize(int i, int j) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'setSize'");
// }

// // public int rand(){
// // Random r = new Random();
// // return r.nextInt(5);
// // }
// }