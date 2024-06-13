package stage_3.src;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameStates.GameStateManager;
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
    private BufferedImage boardButton;
    private BufferedImage moveFigure;
    private BufferedImage loadBar;
    private BufferedImage pipeLoad;
    private BufferedImage smallBoard;
    private BufferedImage boardInside;
    private Logic gameLogic;
    private Timer timer;
    Thread gameThread;
    Image image;
    Graphics graphics;
    private Logic logic;
    private BufferedImage zero;
    private BufferedImage one;
    private BufferedImage two;
    private BufferedImage five;
    // star
    private BufferedImage star_1;
    private BufferedImage star_2;
    private BufferedImage star_3;

    private JButton pauseButton;
    private JButton continueButton;
    private JButton restartButton;
    private String gameDifficulty;

    public GamePanel(String gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        gameLogic = new Logic(7, 7, 15, 5, gameDifficulty);
        this.setLayout(null);

        // Create and add the close button
        JButton closeButton = Button.createCloseButton();
        closeButton.setBounds(70, 341, 50, 50); // replace with actual values

        this.add(closeButton);

        // Create and add the restart button
        JButton restartButton = Button.createRestartButton(gameLogic);
        restartButton.setBounds(130, 341, 50, 50); // replace with actual values

        this.add(restartButton);

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
            boardButton = ImageIO.read(new File("Textures/up.png"));
            moveFigure = ImageIO.read(new File("Textures/moves.png"));
            pipeLoad = ImageIO.read(new File("Textures/pipe.png"));
            loadBar = ImageIO.read(new File("Textures/load.png"));
            smallBoard = ImageIO.read(new File("Textures/bg.png"));
            boardInside = ImageIO.read(new File("Textures/table2.png"));
            // moves
            one = ImageIO.read(new File("Textures/1.png"));
            two = ImageIO.read(new File("Textures/2.png"));
            five = ImageIO.read(new File("Textures/5.png"));
            zero = ImageIO.read(new File("Textures/0.png"));
            // star
            star_1 = ImageIO.read(new File("Textures/star_1.png"));
            star_2 = ImageIO.read(new File("Textures/star_2.png"));
            star_3 = ImageIO.read(new File("Textures/star_3.png"));
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        gameLogic.draw(g);

        // Calculate the x and y coordinates to center the image
        int x = getWidth() - boardButton.getWidth() / 4 - 50; // 50 pixels from the right
        int y = (getHeight() - boardButton.getHeight() / 4) / 2 - 50; // vertically centered
        // Calculate the x and y coordinates to center moveFigure on the boardButton
        int moveFigureX = x + (boardButton.getWidth() / 4 - moveFigure.getWidth() / 4) / 2;
        int moveFigureY = y + (boardButton.getHeight() / 4 - moveFigure.getHeight() / 4) / 2;
        g.drawImage(moveFigure, moveFigureX - 33, moveFigureY - 30, moveFigure.getWidth() / 4,
                moveFigure.getHeight() / 4, this);

        // Calculate the x and y coordinates to center pipeLoad behind the moveFigure
        int pipeLoadX = x + (boardButton.getWidth() / 4 - pipeLoad.getWidth() / 5) / 2;
        int pipeLoadY = y + (boardButton.getHeight() / 4 - pipeLoad.getHeight() / 3) / 2;
        g.drawImage(pipeLoad, pipeLoadX + 3, pipeLoadY, pipeLoad.getWidth() / 6, pipeLoad.getHeight() / 3, this);

        // Calculate the x and y coordinates to center loadBar behind the moveFigure
        int loadBarX = x + (boardButton.getWidth() / 4 - loadBar.getWidth() / 5) / 2;
        int loadBarY = y + (boardButton.getHeight() / 4 - loadBar.getHeight() / 3) / 2;
        g.drawImage(loadBar, loadBarX + 3, loadBarY, loadBar.getWidth() / 6, loadBar.getHeight() / 3, this);

        // Calculate the x and y coordinates to center the image on the left side of the
        // screen
        int smallBoardX = 300; // x-coordinate is 0 for left side of the screen
        int smallBoardY = (getHeight() - image.getHeight(this)) / 4;

        // Draw the small board
        g.drawImage(smallBoard, smallBoardX - 265, smallBoardY + 150,
                smallBoard.getWidth() / 5,
                smallBoard.getHeight() / 5,
                this);

        // draw the board inside the small board
        g.drawImage(boardInside, smallBoardX - 250, smallBoardY + 160,
                boardInside.getWidth() / 7,
                boardInside.getHeight() / 6, this);

        // targetScore
        int targetScore = gameLogic.getTargetScore();
        int movesCount = gameLogic.getMovesCount();
        int playerScore = gameLogic.getScore();
        // g.drawString("Target:" + "\n " + targetScore, smallBoardX - 230, smallBoardY
        // + 195);
        g.drawString("Target:", smallBoardX - 210, smallBoardY + 200);
        g.drawString(" ", smallBoardX - 190, smallBoardY + 215);
        g.drawString(" " + targetScore, smallBoardX - 205, smallBoardY + 225);
        g.drawString("Move count:", smallBoardX - 235, smallBoardY + 250);
        g.drawString(" ", smallBoardX - 205, smallBoardY + 260);
        g.drawString(" " + movesCount, smallBoardX - 190, smallBoardY + 270);
        g.drawString("Score:", smallBoardX - 205, smallBoardY + 295);
        g.drawString(" ", smallBoardX - 190, smallBoardY + 300);
        g.drawString(" " + playerScore, smallBoardX - 195, smallBoardY + 320);

        // g.setFont(Candy_font)
        g.setColor(new Color(144, 238, 144));
        if (gameDifficulty.equals("EASY")) {
            g.drawString("10", moveFigureX - 10, moveFigureY + 20);
        } else if (gameDifficulty.equals("MEDIUM")) {
            g.drawString("15", moveFigureX - 10, moveFigureY + 20);
        } else if (gameDifficulty.equals("HARD")) {
            g.drawString("20", moveFigureX - 10, moveFigureY + 20);
        }
        // star
        // Draw star_1 on top of pipeLoad
        g.drawImage(star_3, pipeLoadX + pipeLoad.getWidth() / 12 - 20, pipeLoadY - star_1.getHeight() / 4 + 5,
                star_3.getWidth() / 3, star_1.getHeight() / 3, this);

        // Draw star_2 in the middle of pipeLoad
        g.drawImage(star_2, pipeLoadX + pipeLoad.getWidth() / 12 - 15,
                pipeLoadY + pipeLoad.getHeight() / 6 - star_2.getHeight() / 8 + 5, star_2.getWidth() / 3,
                star_2.getHeight() / 3, this);

        // Draw star_3 at the bottom of pipeLoad
        g.drawImage(star_1, pipeLoadX + pipeLoad.getWidth() / 12 - 15,
                pipeLoadY + pipeLoad.getHeight() / 3 - star_3.getHeight() / 4 + 5, star_3.getWidth() / 4,
                star_1.getHeight() / 4, this);
        if (playerScore >= targetScore) {
            Reset();
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            // Create a semi-transparent color (e.g., semi-transparent black)
            Color pinkBackground = new Color(255, 105, 180, 127); // RGB for pink with half transparency

            // Set the color
            g.setColor(pinkBackground);

            // Draw a rectangle that covers the entire game area
            g.fillRect(0, 0, getWidth(), getHeight());

            // Create a centered rectangle
            int rectWidth = 400; // Increased size for a bigger rectangle
            int rectHeight = 200; // Increased size for a bigger rectangle
            int rectX = (getWidth() - rectWidth) / 2;
            int rectY = (getHeight() - rectHeight) / 2;
            g.setColor(Color.WHITE);
            g.fillRect(rectX, rectY, rectWidth, rectHeight);

            // Draw "YOU WIN" text in the center of the rectangle
            g.setColor(Color.BLACK);
            g.setFont(new Font("Impact", Font.PLAIN, 50)); // Increased size for bigger text
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth("YOU WIN");
            int textX = rectX + (rectWidth - textWidth) / 2;
            int textY = rectY + ((rectHeight - fm.getHeight()) / 2) + fm.getAscent();
            g.drawString("YOU WIN", textX, textY);
        } else if (playerScore < targetScore) {
            Reset();
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            Color pinkBackground = new Color(255, 105, 180, 127); // RGB for pink with half transparency

            // Set the color
            g.setColor(pinkBackground);

            // Draw a rectangle that covers the entire game area
            g.fillRect(0, 0, getWidth(), getHeight());
            // Create a red color for losing
            Color redBackground = new Color(255, 0, 0);

            // Set the color
            g.setColor(redBackground);

            // Draw a rectangle that covers the entire game area
            g.fillRect(0, 0, getWidth(), getHeight());

            // Create a centered rectangle
            int rectWidth = 400; // Increased size for a bigger rectangle
            int rectHeight = 200; // Increased size for a bigger rectangle
            int rectX = (getWidth() - rectWidth) / 2;
            int rectY = (getHeight() - rectHeight) / 2;
            g.setColor(Color.WHITE);
            g.fillRect(rectX, rectY, rectWidth, rectHeight);

            // Draw "YOU LOSE" text in the center of the rectangle
            g.setColor(Color.BLACK);
            g.setFont(new Font("Impact", Font.PLAIN, 50)); // Increased size for bigger text
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth("YOU LOSE");
            int textX = rectX + (rectWidth - textWidth) / 2;
            int textY = rectY + ((rectHeight - fm.getHeight()) / 2) + fm.getAscent();
            g.drawString("YOU LOSE", textX, textY);
        }
    }

    public void Reset() {

        gameLogic.Reset();

        // If you have other state to reset, do it here
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
