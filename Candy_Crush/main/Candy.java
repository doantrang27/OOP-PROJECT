package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
// import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Candy {
    // ImageIcon[] icon = new ImageIcon[5];

    // JPanel jp = new JPanel(new GridLayout (6,10

    // image that represents the coin's position on the board
    private BufferedImage[] images = new BufferedImage[5];
    // current position of the coin on the board grid
    private Point pos;

    public Candy(int x, int y) {
        // load the assets
        loadImage();
        // initialize the state
        pos = new Point(x, y);
    }

    private void loadImage() {
        try {
            // // for(int i=0;i<images.length;i++){
            // // images[i]=ImageIO.read(new File());
            // // }
            // // you can use just the filename if the image file is in your
            // // project folder, otherwise you need to provide the file path.
            // images[1] = ImageIO.read(new File("Candy_Crush/res/Obj1.png"));
            // images[2] = ImageIO.read(new File("Candy_Crush/res/Obj2.png"));
            // images[3] = ImageIO.read(new File("Candy_Crush/res/Obj3.png"));
            // images[4] = ImageIO.read(new File("Candy_Crush/res/Obj4.png"));
            // images[5] = ImageIO.read(new File("Candy_Crush/res/Obj5.png"));
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(new File("Candy_Crush/res/Obj" + (i + 1) + ".png"));
            }
            // url[0] = getClass().getResource("Candy_Crush/res/Candy_0.png");
            // url[1] = getClass().getResource("Candy_Crush/res/Candy_1.png");
            // url[2] = getClass().getResource("Candy_Crush/res/Candy_2.png");
            // url[3] = getClass().getResource("Candy_Crush/res/Candy_3.png");
            // url[4] = getClass().getResource("Candy_Crush/res/Candy_4.png");
            // for (int i = 0; i < 5; i++) {
            // icon[i] = new ImageIcon(url[i]);
            // }
            // for (int i=0;i<8;i++){
            // for(int j=0;j<8;j++){
            // int d = rand();
            // bn[i][j] = new JButton();
            // button[i][j].setIcon(new ImageIcon(url[d]));

            // }
            // }

        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int.
        // https: // stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        for (BufferedImage img : images) {
            // g.drawImage(
            // img,
            // pos.x * Board.TILE_SIZE,
            // pos.y * Board.TILE_SIZE,
            // observer);
            // Draw only one image instead of all, assuming each candy has only one image
            if (images[0] != null) {
                g.drawImage(images[0], pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE,
                        observer);
            }
            // g.drawImage(img, pos.x, pos.y, observer);
        }
    }

    public Point getPos() {
        return pos;
    }

// public int rand(){
//     Random r = new Random();
//     return r.nextInt(5);
// }
}