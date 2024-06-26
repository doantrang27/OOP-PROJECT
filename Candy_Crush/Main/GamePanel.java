package Main;

import GameStates.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    //dimension
    public static final int WIDTH = 550;
    public static final int HEIGHT = 355;
    public static final int SCALE = 2;

    //game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    //image
    private BufferedImage image;
    private Graphics2D g;

    //game state manager
    private GameStateManager gsm;

    private static GamePanel gp;

    public GamePanel() {
        super ();
        setPreferredSize(new Dimension((WIDTH*SCALE), (HEIGHT*SCALE)));
        setFocusable(true);
        requestFocus();
    }

    public static GamePanel getPanel() {
        if (gp == null) {
            gp = new GamePanel();
        }
        return gp;
    }

    @Override
public void addNotify () {
        super.addNotify();
    if (thread == null) {
        thread = new Thread (this);
        addKeyListener(this);
        thread.start ();
    }
}
private void init () {
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) image.getGraphics();
    running = true;
    gsm = new GameStateManager();
}


public void run () {
    init ();

    long start;
    long elapsed;
    long wait;
    //game loop
    while (running) {

        start = System.nanoTime();

        update ();
        draw ();
        drawToScreen ();

        elapsed = System.nanoTime() - start;

        wait = targetTime - elapsed/1000000;
        if (wait < 0) wait = 5;

        try {
            Thread.sleep(wait);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//repaint
private void update () {
        gsm.update();
}
public void draw () {
        gsm.draw(g);
}
public void drawToScreen () {
        Graphics g2 = getGraphics ();
        g2.drawImage (image, 0, 0,WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose ();
    }


    public void keyTyped (KeyEvent key) {}
    public void keyPressed (KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased (KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }
}
