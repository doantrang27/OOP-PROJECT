package stage_3.src;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameStates.Level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private BufferedImage backgroundImage;
    private Logic gameLogic;
    private Timer timer;
    Thread gameThread;
    Image image;
    Graphics graphics;
    private Logic logic;
    // private String currentLevel;

    public GamePanel() {
        gameLogic = new Logic(7, 7, 15, 5, "EASY");
        // currentLevel = "EASY"; // Set default level
        // gameLogic = new Logic(7, 7, 15, 5, currentLevel);
        int delay = 1000 / 100;
        timer = new Timer(delay, e -> {
            gameLogic.Break_detector();
            gameLogic.Move();
            repaint();
        });
        timer.start();
        addMouseListener(new AL());
        addMouseMotionListener(new AL());
        setFocusable(true);
        try {
            backgroundImage = ImageIO.read(new File("Textures/bg-candy.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(backgroundImage.getWidth(),
                backgroundImage.getHeight()));
        this.setDoubleBuffered(true);

    }

    public void setLogic(Logic logic) {

        this.logic = logic;
    }
    // GamePanel() {
    // initializeGameLogic();
    // Level level = new Level();
    // level.setOnLevelSelectedListener(this);

    // int delay = 1000 / 100;
    // timer = new Timer(delay, e -> {
    // gameLogic.Break_detector();
    // gameLogic.Move();
    // repaint();
    // });
    // timer.start();
    // addMouseListener(new AL());
    // addMouseMotionListener(new AL());
    // setFocusable(true);
    // try {
    // backgroundImage = ImageIO.read(new File("Textures/bg-candy.jpg"));
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // this.setPreferredSize(new Dimension(backgroundImage.getWidth(),
    // backgroundImage.getHeight()));
    // this.setDoubleBuffered(true);

    // }

    // private void initializeGameLogic() {
    // if (currentLevel == Level.EASY) {
    // gameLogic = new Logic(7, 7, 15, 5, "EASY");
    // } else if (currentLevel == Level.INTERMEDIATE) {
    // gameLogic = new Logic(7, 7, 15, 5, "INTERMEDIATE");
    // } else if (currentLevel == Level.DIFFICULT) {
    // gameLogic = new Logic(7, 7, 15, 5, "DIFFICULT");
    // }
    // }

    // public void keyPressed(KeyEvent e) {
    // if (e.getKeyCode() == KeyEvent.VK_UP) {
    // currentLevel--;
    // if (currentLevel < Level.EASY)
    // currentLevel = Level.EASY;
    // }
    // if (e.getKeyCode() == KeyEvent.VK_DOWN) {
    // currentLevel++;
    // if (currentLevel > Level.DIFFICULT)
    // currentLevel = Level.DIFFICULT;
    // }
    // if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    // initializeGameLogic();
    // }
    // }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        gameLogic.draw(g);

    }

    public class AL extends MouseAdapter implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            gameLogic.mouseClicked(e);

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}
