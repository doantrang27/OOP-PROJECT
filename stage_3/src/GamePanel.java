package stage_3.src;

import javax.imageio.ImageIO;
import javax.swing.*;

import stage_3.src.Level.EasyLevel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    GamePanel() {
        String difficulty = "EASY";
        if (difficulty.equals("EASY")) {
            System.out.println("User selected difficuty: EASY");
            gameLogic = new LogicEasy(7, 7, 15, 5);
        } else {
            System.out.println("User selected difficuty: MEDIUM");
            gameLogic = new LogicMedium(7, 7, 16, 5);
        }

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
        this.setPreferredSize(new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight()));
        this.setDoubleBuffered(true);

    }

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
