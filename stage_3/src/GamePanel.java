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
    // prize
    private BufferedImage win;
    private BufferedImage lose;
    private BufferedImage threeStar;
    private BufferedImage zeroStar;
    private BufferedImage aboutButton;
    private BufferedImage prizeButButton;
    private JButton closeButton;
    private JButton restartButton;
    private String gameDifficulty;

    public GamePanel(String gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        // prize = new Prize(this, gameLogic);
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
            // prize
            win = ImageIO.read(new File("Textures/win.png"));
            threeStar = ImageIO.read(new File("Textures/threestar.png"));
            lose = ImageIO.read(new File("Textures/lose.png"));
            zeroStar = ImageIO.read(new File("Textures/zeroStar.png"));
            // bu
            aboutButton = ImageIO.read(new File("Textures/about.png"));
            prizeButButton = ImageIO.read(new File("Textures/prize.png"));
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
        // DRAW BORDER AROUND THE SMALL BOARD
        Color borderColor1 = Color.WHITE;
        Color borderColor2 = new Color(0, 100, 0); // Dark green
        Color originalColor1 = new Color(0, 100, 0); // Dark green
        Color originalColor2 = new Color(144, 238, 144); // Light green
        int borderSize = 1; // Change this to make the border thicker

        // Draw the border for "Target", "Move count", "Score"
        g.setColor(borderColor1);
        for (int dx = -borderSize; dx <= borderSize; dx++) {
            for (int dy = -borderSize; dy <= borderSize; dy++) {
                g.drawString("Target:", smallBoardX - 210 + dx, smallBoardY + 200 + dy);
                g.drawString("Move count:", smallBoardX - 235 + dx, smallBoardY + 246 + dy);
                g.drawString("Score:", smallBoardX - 209 + dx, smallBoardY + 294 + dy);
            }
        }

        // Draw the text in the original color
        g.setColor(originalColor1);
        g.drawString("Target:", smallBoardX - 210, smallBoardY + 200);
        g.drawString("Move count:", smallBoardX - 235, smallBoardY + 246);
        g.drawString("Score:", smallBoardX - 209, smallBoardY + 294);

        // Draw the border for targetScore, movesCount, playerScore
        g.setColor(borderColor2);
        for (int dx = -borderSize; dx <= borderSize; dx++) {
            for (int dy = -borderSize; dy <= borderSize; dy++) {
                g.drawString(" " + targetScore, smallBoardX - 205 + dx, smallBoardY + 225 + dy);
                g.drawString(" " + movesCount, smallBoardX - 190 + dx, smallBoardY + 270 + dy);
                g.drawString(" " + playerScore, smallBoardX - 199 + dx, smallBoardY + 320 + dy);
            }
        }

        // Draw the output in the original color
        g.setColor(originalColor2);
        g.drawString(" " + targetScore, smallBoardX - 205, smallBoardY + 225);
        g.drawString(" " + movesCount, smallBoardX - 190, smallBoardY + 270);
        g.drawString(" " + playerScore, smallBoardX - 199, smallBoardY + 320);

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

        // WIN PRIZE

        if (playerScore >= targetScore) {

            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g.drawImage(smallBoard, 350, 200,
                    smallBoard.getWidth() / 4, smallBoard.getHeight() / 4, this);
            g.drawImage(boardInside, 375, 223,
                    boardInside.getWidth() / 6, boardInside.getHeight() / 5, this);
            g.drawImage(win, 310, 150, win.getWidth() / 3, win.getHeight() / 3, this);
            g.drawImage(threeStar, 398, 260, star_3.getWidth(), star_3.getHeight() / 2,
                    this);

            Font font = Logic.getCandyFont();
            font = font.deriveFont(28f); // Change 10f to your desired font size

            g.setFont(font);

            g.setColor(borderColor1);
            for (int dx = -borderSize; dx <= borderSize; dx++) {
                for (int dy = -borderSize; dy <= borderSize; dy++) {
                    g.drawString("Your score:", 395 + dx, 330 + dy);
                    g.drawString("Target score:", 390 + dx, 383 + dy);
                }
            }

            // Draw the text in the original color
            g.setColor(originalColor1);
            g.drawString("Your score:", 395, 330);
            g.drawString("Target score:", 390, 383);

            // Draw the border for targetScore, movesCount, playerScore
            g.setColor(borderColor2);
            for (int dx = -borderSize; dx <= borderSize; dx++) {
                for (int dy = -borderSize; dy <= borderSize; dy++) {
                    g.drawString(" " + playerScore, 425 + dx, smallBoardY + 290 + 67 + dy);
                    g.drawString(" " + targetScore, 425 + dx, smallBoardY + 320 + 90 + dy);
                }
            }

            // Draw the output in the original color
            g.setColor(originalColor2);
            g.drawString(" " + playerScore, 425, 290 + 67);
            g.drawString(" " + targetScore, 425, 320 + 90);
            g.drawImage(prizeButButton, 400, 443, prizeButButton.getWidth() / 5, prizeButButton.getHeight() / 5, this);
            g.drawImage(aboutButton, 480, 443, aboutButton.getWidth() / 5, aboutButton.getHeight() / 5, this);

        } else if (playerScore < targetScore && movesCount == 0) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g.drawImage(smallBoard, 350, 200,
                    smallBoard.getWidth() / 4, smallBoard.getHeight() / 4, this);
            g.drawImage(boardInside, 375, 225,
                    boardInside.getWidth() / 6, boardInside.getHeight() / 5, this);
            g.drawImage(lose, 310, 150, win.getWidth() / 3, win.getHeight() / 3, this);
            g.drawImage(zeroStar, 398, 260, star_3.getWidth(), star_3.getHeight() / 2,
                    this);

            Font font = Logic.getCandyFont();
            font = font.deriveFont(28f); // Change 10f to your desired font size

            g.setFont(font);

            g.setColor(borderColor1);
            for (int dx = -borderSize; dx <= borderSize; dx++) {
                for (int dy = -borderSize; dy <= borderSize; dy++) {
                    g.drawString("Your score:", 395 + dx, 330 + dy);
                    g.drawString("Target score:", 390 + dx, 383 + dy);
                }
            }

            // Draw the text in the original color
            g.setColor(originalColor1);
            g.drawString("Your score:", 395, 330);
            g.drawString("Target score:", 390, 383);

            // Draw the border for targetScore, movesCount, playerScore
            g.setColor(borderColor2);
            for (int dx = -borderSize; dx <= borderSize; dx++) {
                for (int dy = -borderSize; dy <= borderSize; dy++) {
                    g.drawString(" " + playerScore, 425 + dx, smallBoardY + 290 + 67 + dy);
                    g.drawString(" " + targetScore, 425 + dx, smallBoardY + 320 + 90 + dy);
                }
            }

            // Draw the output in the original color
            g.setColor(originalColor2);
            g.drawString(" " + playerScore, 425, 290 + 67);
            g.drawString(" " + targetScore, 425, 320 + 90);
            g.drawImage(prizeButButton, 400, 443, prizeButButton.getWidth() / 5, prizeButButton.getHeight() / 5, this);
            g.drawImage(aboutButton, 480, 443, aboutButton.getWidth() / 5, aboutButton.getHeight() / 5, this);
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
