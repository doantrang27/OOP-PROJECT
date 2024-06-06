package stage_3.src;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.text.html.HTMLDocument.Iterator;

import stage_3.src.Level.IDifficultyAdjustment;
import stage_3.src.Level.DifficultLevel;
import stage_3.src.Level.EasyLevel;
import stage_3.src.Level.IntermediateLevel;

public class Logic {
    private final Board board;
    protected final int candies_width, candies_height;
    protected final Candies[][] candies;
    private final Candies[][] Replacement_candies;
    private final Random random = new Random();
    private int ClickCounter = 0;
    private int FirstCandyCol, FirstCandyRow, SecondCandyRow, SecondCandyCol;
    private final Candies switchedCandy = new Candies();
    private final Image game_background = new ImageIcon("Textures/bg-candy.jpg").getImage();
    private final Image win_gif = new ImageIcon("Textures/oie_OeMAOSwhprac.gif").getImage();
    private final Image out_of_moves_image = new ImageIcon(/* out of move imange */).getImage();
    private final Image next_icon_big = new ImageIcon("Textures/exit_button _large.png").getImage();
    private int level_game_index = 0;
    private int clicked_x, clicked_y;
    private boolean isHorizontal;
    private int direction;
    private int CurrentPos;
    private boolean swapping = false;
    private boolean Non_matching_swap = false;
    private Font Candy_font;
    private int Score;
    private int Moves_count;
    private int game_ended_index = -5;
    private int game_out_of_moves_index = -6;
    private final int AmountOfCandies; // max 6
    private final int targetScore;
    private final Image endGame = new ImageIcon(/* ảnh end */).getImage();
    private final Image Dispenser = new ImageIcon("Textures/Dispenser.png").getImage();
    private final Image obstacleImage = new ImageIcon("Textures/ice.png").getImage();
    private final Image wallImage = new ImageIcon("Textures/wall.png").getImage();
    private List<Point> obstacles = new ArrayList<>();
    private List<Point> wallObstacles = new ArrayList<>();

    public Logic(int candies_width, int candies_height, int moves_count, int AmountOfCandies, String gameDifficulty) {
        this.setGameDifficulty(gameDifficulty);
        this.Moves_count = moves_count;
        this.AmountOfCandies = AmountOfCandies;
        this.candies_width = candies_width;
        this.candies_height = candies_height;
        int heightDiffculty = candies_height * 50;
        int widthDiffculty = candies_width * 50;
        // chỗ này để set điểm
        // targetScore = Moves_count * 70 + heightDiffculty + widthDiffculty;
        switch (gameDifficulty.toUpperCase()) {
            case "EASY":
                this.Moves_count = 10;
                this.targetScore = this.Moves_count * 70 + heightDiffculty + widthDiffculty;
                break;
            case "MEDIUM":
                this.Moves_count = 15;
                this.targetScore = this.Moves_count * 70 + heightDiffculty + widthDiffculty;
                break;
            case "HARD":
                this.Moves_count = 20;
                this.targetScore = this.Moves_count * 70 + heightDiffculty + widthDiffculty;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + gameDifficulty);
        }
        candies = new Candies[candies_width][candies_height];
        Replacement_candies = new Candies[candies_width][candies_height];
        board = new Board(candies_height, candies_width, gameDifficulty);

        try {
            Candy_font = Font.createFont(Font.TRUETYPE_FONT, new File("Textures/CANDY.TTF")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Textures/CANDY.TTF")));
        } catch (IOException | FontFormatException exp) {
        }

        // placeObstaclesRandomly();
        this.gameLevel.placeObstaclesRandomly(candies_width, candies_height, obstacles);
        this.gameLevel.placeWallObstaclesRandomly(candies_width, candies_height, wallObstacles);

        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                candies[i][j] = new Candies();
                candies[i][j].setPx(i * 50 + 325);
                candies[i][j].setPy(j * 50 + 150);
                candies[i][j].setShape(random.nextInt(AmountOfCandies));

                Replacement_candies[i][j] = new Candies();
                Replacement_candies[i][j].setPx(i * 50 + 325);
                Replacement_candies[i][j].setPy(100);
                Replacement_candies[i][j].setShape(random.nextInt(AmountOfCandies));
            }
        }
        do {
            checker();
        } while (match_checker());
        ResetCandies();

    }

    IDifficultyAdjustment gameLevel;

    public void setGameDifficulty(String difficulty) {
        if (difficulty.equals("EASY")) {
            gameLevel = new EasyLevel();
        } else if (difficulty.equals("MEDIUM")) {
            gameLevel = new IntermediateLevel();
        } else if (difficulty.equals("HARD")) {
            gameLevel = new DifficultLevel();
        } else {
            gameLevel = new EasyLevel();
        }
    }

    public void mouseClicked(MouseEvent e) {
        clicked_x = e.getX();
        clicked_y = e.getY();
        Detection();
        if (ClickCounter == 1) {
        } else if (ClickCounter == 2) {
            ClickCounter = 0;
        }
    }

    /**
     * Checks for any matches of three or more candies in a row or column and
     * replaces them with new candies.
     */
    public void checker() {

        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (j < candies_height - 2 && candies[i][j].getCandyType() == candies[i][j + 1].getCandyType()
                        && candies[i][j].getCandyType() == candies[i][j + 2].getCandyType()) {
                    do {
                        candies[i][j].setShape(random.nextInt(AmountOfCandies));
                    } while (candies[i][j].getCandyType() == candies[i][j + 1].getCandyType());

                } else if (i < candies_width - 2 && candies[i][j].getCandyType() == candies[i + 1][j].getCandyType()
                        && candies[i][j].getCandyType() == candies[i + 2][j].getCandyType()) {
                    do {
                        candies[i][j].setShape(random.nextInt(AmountOfCandies));
                    } while (candies[i][j].getCandyType() == candies[i + 1][j].getCandyType());

                }
            }
        }
    }

    public boolean match_checker() {
        boolean matchFound = false; // Flag to indicate if a match is found
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {

                if (j < candies_height - 2 && candies[i][j].getCandyType() == candies[i][j + 1].getCandyType()
                        && candies[i][j].getCandyType() == candies[i][j + 2].getCandyType()) {
                    matchFound = true; // A match is found
                    // removeObstaclesNear(i, j);
                    this.gameLevel.removeObstaclesNear(new Point(i, j), obstacles);
                    this.gameLevel.removeWallObstaclesNear(new Point(i, j), wallObstacles);
                    // return true;
                } else if (i < candies_width - 2 && candies[i][j].getCandyType() == candies[i + 1][j].getCandyType()
                        && candies[i][j].getCandyType() == candies[i + 2][j].getCandyType()) {
                    // return true;
                    matchFound = true; // A match is found
                    // removeObstaclesNear(i, j); // Call the method to remove obstacles near the
                    // match
                    this.gameLevel.removeObstaclesNear(new Point(i, j), obstacles);
                    this.gameLevel.removeWallObstaclesNear(new Point(i, j), wallObstacles);

                }
            }
        }

        // return false;
        return matchFound;
    }

    public void ResetCandies() {
        while (!noMatchingCandies()) {
            for (int i = 0; i < candies_width; i++) {
                for (int j = 0; j < candies_height; j++) {
                    candies[i][j].setShape(random.nextInt(AmountOfCandies));
                }
            }
        }
    }

    // bg
    public void draw(Graphics g) {
        if (level_game_index == 0) {

            board.draw(g);
            g.setFont(Candy_font);
            g.setColor(Color.RED);
            g.drawString("Target:" + targetScore, 15, 350);
            g.drawString("" + Moves_count, 80, 250);
            g.drawString("Score:" + Score, 15, 450);

            for (int i = 0; i < candies_width; i++) {
                for (int j = 0; j < candies_height; j++) {
                    g.drawImage(candies[i][j].getCandyShape(), candies[i][j].getPx(), candies[i][j].getPy(), null);
                    g.drawImage(Replacement_candies[i][j].getCandyShape(), Replacement_candies[i][j].getPx(),
                            Replacement_candies[i][j].getPy(), null);
                }
                g.drawImage(Dispenser, i * 50 + 325, 100 + 5, null);
            }

            // Draw obstacles
            drawObstacles(g);
            drawWall(g);

        } else if (level_game_index == game_ended_index) {
            g.drawImage(endGame, 0, 0, null);
            g.drawImage(win_gif, 250, 0, null);

        } else if (level_game_index == game_out_of_moves_index) {
            g.drawImage(out_of_moves_image, 0, 0, null);
            g.drawImage(next_icon_big, 10, 490, null);
        }
    }

    private void drawObstacles(Graphics g) {
        for (Point obstacle : obstacles) {
            // Resize the obstacle image to 50x50 pixels

            g.drawImage(obstacleImage, obstacle.x * 50 + 325, obstacle.y * 50 + 150, 50,
                    50, null);
        }
    }

    private void drawWall(Graphics g) {
        for (Point wall : wallObstacles) {
            g.drawImage(wallImage, wall.x * 50 + 325, wall.y * 50 + 150, 50,
                    50, null);
        }
    }

    // LOGIC
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Switch(Candies firstCandy, Candies secondCandy) {

        switchedCandy.setPx(firstCandy.getPx());
        switchedCandy.setPy(firstCandy.getPy());
        switchedCandy.setShape(firstCandy.getCandyType());
        switchedCandy.setCandiesPower(firstCandy.getCandiesPower());
        firstCandy.setPx(secondCandy.getPx());
        firstCandy.setPy(secondCandy.getPy());
        firstCandy.setShape(secondCandy.getCandyType());
        firstCandy.setCandiesPower(secondCandy.getCandiesPower());
        secondCandy.setPx(switchedCandy.getPx());
        secondCandy.setPy(switchedCandy.getPy());
        secondCandy.setShape(switchedCandy.getCandyType());
        secondCandy.setCandiesPower(switchedCandy.getCandiesPower());
        if (firstCandy.getCandiesPower() == 1) {
            firstCandy.setSpecialCandy(firstCandy.getCandyType());
        } else if (firstCandy.getCandiesPower() == 2) {
            firstCandy.setStripedCandiesV(firstCandy.getCandyType());
        } else if (firstCandy.getCandiesPower() == 3) {
            firstCandy.setStripedCandiesH(firstCandy.getCandyType());
        }
        if (secondCandy.getCandiesPower() == 1) {
            secondCandy.setSpecialCandy(secondCandy.getCandyType());
        } else if (secondCandy.getCandiesPower() == 2) {
            secondCandy.setStripedCandiesV(secondCandy.getCandyType());
        } else if (secondCandy.getCandiesPower() == 3) {
            secondCandy.setStripedCandiesH(secondCandy.getCandyType());
        }
    }

    public boolean noMatchingCandies() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (j < candies_height - 1 && candies[i][j].getCandyType() == candies[i][j + 1].getCandyType()
                        && ((i < candies_width - 1 && j < candies_height - 2
                                && candies[i + 1][j + 2].getCandyType() == candies[i][j].getCandyType())
                                || (i > 0 && j < candies_height - 2
                                        && candies[i - 1][j + 2].getCandyType() == candies[i][j].getCandyType())
                                || (j < candies_height - 3
                                        && candies[i][j + 3].getCandyType() == candies[i][j].getCandyType())
                                || (j > 0 && i > 0
                                        && candies[i - 1][j - 1].getCandyType() == candies[i][j].getCandyType())
                                || (j > 0 && i < candies_width - 1
                                        && candies[i + 1][j - 1].getCandyType() == candies[i][j].getCandyType())
                                || (j > 1 && candies[i][j - 2].getCandyType() == candies[i][j].getCandyType()))) {
                    return true;
                } else if (i < candies_width - 1 && candies[i][j].getCandyType() == candies[i + 1][j].getCandyType()
                        && ((j < candies_height - 1 && i < candies_width - 2
                                && candies[i + 2][j + 1].getCandyType() == candies[i][j].getCandyType())
                                || (j > 0 && i < candies_width - 2
                                        && candies[i + 2][j - 1].getCandyType() == candies[i][j].getCandyType())
                                || (i < candies_width - 3
                                        && candies[i + 3][j].getCandyType() == candies[i][j].getCandyType())
                                || (i > 0 && j > 0
                                        && candies[i - 1][j - 1].getCandyType() == candies[i][j].getCandyType())
                                || (i > 0 && j < candies_height - 1
                                        && candies[i - 1][j + 1].getCandyType() == candies[i][j].getCandyType())
                                || (i > 1 && candies[i - 2][j].getCandyType() == candies[i][j].getCandyType()))) {
                    return true;
                } else if (j < candies_height - 2 && candies[i][j].getCandyType() == candies[i][j + 2].getCandyType() &&
                        ((i > 0 && candies[i - 1][j + 1].getCandyType() == candies[i][j].getCandyType())
                                || (i < candies_width - 1
                                        && candies[i + 1][j + 1].getCandyType() == candies[i][j].getCandyType()))) {
                    return true;
                } else if (i < candies_width - 2 && candies[i][j].getCandyType() == candies[i + 2][j].getCandyType() &&
                        ((j > 0 && candies[i + 1][j - 1].getCandyType() == candies[i][j].getCandyType())
                                || (j < candies_height - 1
                                        && candies[i + 1][j + 1].getCandyType() == candies[i][j].getCandyType()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void Detection() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (clicked_x >= candies[i][j].getPx() && clicked_x <= candies[i][j].getPx() + 50
                        && clicked_y >= candies[i][j].getPy() && clicked_y <= candies[i][j].getPy() + 50
                        && ClickCounter == 0) {
                    FirstCandyRow = i;
                    FirstCandyCol = j;
                    ClickCounter += 1;
                    System.out.println(candies[i][j].getCandiesPower());
                } else if (clicked_x >= candies[i][j].getPx() && clicked_x <= candies[i][j].getPx() + 50
                        && clicked_y >= candies[i][j].getPy() && clicked_y <= candies[i][j].getPy() + 50) {
                    ClickCounter += 1;
                    SecondCandyRow = i;
                    SecondCandyCol = j;
                    CurrentPos = candies[i][j].getPx();
                }
            }
        }
        if (ClickCounter == 2) {
            // left
            if (SecondCandyRow + 1 == FirstCandyRow && SecondCandyCol == FirstCandyCol) {
                System.out.println("left");
                CurrentPos = candies[SecondCandyRow][SecondCandyCol].getPx();
                isHorizontal = true;
                direction = -1;
                if (match_confirm()) {
                    swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                } else
                    Non_Matching_swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
            }
            // right
            else if (SecondCandyRow - 1 == FirstCandyRow && SecondCandyCol == FirstCandyCol) {
                CurrentPos = candies[SecondCandyRow][SecondCandyCol].getPx();
                isHorizontal = true;
                direction = 1;
                if (match_confirm()) {
                    swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                    if (candies[FirstCandyRow][FirstCandyCol].isBroke()) {
                        candies[FirstCandyRow][FirstCandyCol].setCandiesPower(3);
                    } else if (candies[SecondCandyRow][SecondCandyCol].isBroke()) {
                        candies[SecondCandyRow][SecondCandyCol].setCandiesPower(3);
                    }
                } else
                    Non_Matching_swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                System.out.println("matchright");
            }
            // down
            else if (SecondCandyRow == FirstCandyRow && SecondCandyCol - 1 == FirstCandyCol) {
                isHorizontal = false;
                direction = 1;
                CurrentPos = candies[SecondCandyRow][SecondCandyCol].getPy();
                if (match_confirm()) {
                    swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                } else
                    Non_Matching_swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                System.out.println("matchdown");
            }
            // up
            else if (SecondCandyRow == FirstCandyRow && SecondCandyCol + 1 == FirstCandyCol) {
                isHorizontal = false;
                direction = -1;
                CurrentPos = candies[SecondCandyRow][SecondCandyCol].getPy();
                if (match_confirm()) {
                    swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                } else
                    Non_Matching_swap(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
                System.out.println("matchup");
            }
        }
    }

    public void swap(Candies c1, Candies c2) {
        Moves_count--;
        swapping = true;
        if (isHorizontal) {
            c1.setVelX(-5 * direction);
            c2.setVelX(5 * direction);

        } else {
            c1.setVelY(-5 * direction);
            c2.setVelY(5 * direction);
        }
        if (Moves_count == 0) {
            level_game_index = game_out_of_moves_index;
        }

    }

    public void Non_Matching_swap(Candies candy1, Candies candy2) {
        Non_matching_swap = true;
        if (isHorizontal) {
            candy1.setVelX(direction * 5);
            candy2.setVelX(direction * -5);
        } else {
            candy1.setVelY(direction * 5);
            candy2.setVelY(direction * -5);
        }
    }

    public boolean match_confirm() {
        Switch(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
        if (match_checker()) {
            return true;
        }
        Switch(candies[FirstCandyRow][FirstCandyCol], candies[SecondCandyRow][SecondCandyCol]);
        return false;
    }

    public void Break_detector() {
        drop_down();
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (!swapping && !moving() && j < candies_height - 2
                        && candies[i][j].getCandyType() == candies[i][j + 1].getCandyType() &&
                        candies[i][j].getCandyType() == candies[i][j + 2].getCandyType() &&
                        !candies[i][j].isBroke() && !candies[i][j + 1].isBroke() && !candies[i][j + 2].isBroke()) {
                    candies[i][j].setBroke(true);
                    candies[i][j + 1].setBroke(true);
                    candies[i][j + 2].setBroke(true);
                    candies[i][j].setCandyAnimation(candies[i][j].getCandyType());
                    candies[i][j + 1].setCandyAnimation(candies[i][j].getCandyType());
                    candies[i][j + 2].setCandyAnimation(candies[i][j].getCandyType());
                    for (int x = j; x <= j + 2; x++) {
                        if (i < candies_width - 2 && candies[i][x].getCandyType() == candies[i + 1][x].getCandyType()
                                && candies[i][x].getCandyType() == candies[i + 2][x].getCandyType()) {

                            candies[i + 1][x].setBroke(true);
                            candies[i + 1][x].setCandyAnimation(candies[i + 1][x].getCandyType());
                            candies[i + 2][x].setBroke(true);
                            candies[i + 2][x].setCandyAnimation(candies[i + 2][x].getCandyType());
                            candies[i][x].setCandiesPower(1);
                            candies[i][x].setSpecialCandy(candies[i][x].getCandyType());
                            candies[i][x].setBroke(false);
                            System.out.println("i am in");
                        } else if (i >= 2 && candies[i][x].getCandyType() == candies[i - 1][x].getCandyType()
                                && candies[i][x].getCandyType() == candies[i - 2][x].getCandyType()) {
                            candies[i - 1][x].setBroke(true);
                            candies[i - 1][x].setCandyAnimation(candies[i - 1][x].getCandyType());
                            candies[i - 2][x].setBroke(true);
                            candies[i - 2][x].setCandyAnimation(candies[i - 2][x].getCandyType());
                            candies[i][x].setCandiesPower(1);
                            candies[i][x].setSpecialCandy(candies[i][x].getCandyType());
                            candies[i][x].setBroke(false);
                            System.out.println("i am in");
                        }
                    }

                    // sọc
                    if (j < candies_height - 3 && candies[i][j + 3].getCandyType() == candies[i][j].getCandyType()) {
                        System.out.println("can");
                        candies[i][j + 3].setBroke(true);
                        candies[i][j + 3].setCandyAnimation(candies[i][j + 3].getCandyType());
                        for (int x = j; x <= j + 3; x++) {
                            if (i == FirstCandyRow && FirstCandyRow == x
                                    || i == SecondCandyRow && SecondCandyCol == x) {
                                candies[i][x].setBroke(false);
                                if (isHorizontal) {
                                    candies[i][x].setStripedCandiesH(candies[i][x].getCandyType());
                                    candies[i][x].setCandiesPower(3);
                                } else {
                                    candies[i][x].setStripedCandiesV(candies[i][x].getCandyType());
                                    candies[i][x].setCandiesPower(2);
                                }
                            }
                        }
                    }

                } else if (!swapping && !moving() && i < candies_width - 2
                        && candies[i][j].getCandyType() == candies[i + 1][j].getCandyType() &&
                        candies[i][j].getCandyType() == candies[i + 2][j].getCandyType() &&
                        !candies[i][j].isBroke() && !candies[i + 1][j].isBroke() && !candies[i + 2][j].isBroke()) {
                    // 3 candies
                    candies[i][j].setBroke(true);
                    candies[i + 1][j].setBroke(true);
                    candies[i + 2][j].setBroke(true);
                    candies[i][j].setCandyAnimation(candies[i][j].getCandyType());
                    candies[i + 1][j].setCandyAnimation(candies[i][j].getCandyType());
                    candies[i + 2][j].setCandyAnimation(candies[i][j].getCandyType());

                    // explode
                    for (int x = i; x <= i + 2; x++) {
                        if (j < candies_height - 2 && candies[x][j].getCandyType() == candies[x][j + 1].getCandyType()
                                && candies[x][j].getCandyType() == candies[x][j + 2].getCandyType()) {
                            candies[x][j + 1].setBroke(true);
                            candies[x][j + 1].setCandyAnimation(candies[x][j + 1].getCandyType());
                            candies[x][j + 2].setBroke(true);
                            candies[x][j + 2].setCandyAnimation(candies[x][j + 2].getCandyType());
                            candies[x][j].setCandiesPower(1);
                            candies[x][j].setSpecialCandy(candies[x][j].getCandyType());
                            candies[x][j].setBroke(false);
                            System.out.println("i am in");
                        } else if (j >= 2 && candies[x][j].getCandyType() == candies[x][j - 1].getCandyType()
                                && candies[x][j].getCandyType() == candies[x][j - 2].getCandyType()) {
                            candies[x][j - 1].setBroke(true);
                            candies[x][j - 1].setCandyAnimation(candies[x][j - 1].getCandyType());
                            candies[x][j - 2].setBroke(true);
                            candies[x][j - 2].setCandyAnimation(candies[x][j - 2].getCandyType());
                            candies[x][j].setCandiesPower(1);
                            candies[x][j].setSpecialCandy(candies[x][j].getCandyType());
                            candies[x][j].setBroke(false);
                            System.out.println("i am in");
                        }
                    }

                    // stripe
                    if (i < candies_width - 3 && candies[i + 3][j].getCandyType() == candies[i][j].getCandyType()) {
                        candies[i + 3][j].setBroke(true);
                        candies[i + 3][j].setCandyAnimation(candies[i + 3][j].getCandyType());
                        for (int x = i; x <= i + 3; x++) {
                            if (x == FirstCandyRow && FirstCandyRow == j
                                    || x == SecondCandyRow && SecondCandyCol == j) {
                                candies[x][j].setBroke(false);
                                if (isHorizontal) {
                                    candies[x][j].setStripedCandiesH(candies[i + 1][j].getCandyType());
                                    candies[x][j].setCandiesPower(3);
                                } else {
                                    candies[x][j].setStripedCandiesV(candies[x][j].getCandyType());
                                    candies[x][j].setCandiesPower(2);
                                }
                            }
                        }
                    }

                }
                if (!moving() && !swapping)
                    candies[i][j].getCandyAnimation(candies[i][j].getCandyType()).flush();
            }
        }
    }

    public void drop_down() {
        int[] counter = new int[candies_width];
        for (int i = 0; i < candies_width; i++) {
            for (int j = candies_height - 1; j >= 0; j--) {
                if (candies[i][j].isBroke() && !swapping) {
                    if (candies[i][j].getCandiesPower() == 1) {
                        explode(i, j);
                    }
                    if (candies[i][j].getCandiesPower() > 1) {
                        stripedCandiesBroken(i, j);
                    }
                    counter[i]++;
                    Replacement_candies[i][0].setVelY(5);
                    for (int n = 0; n < candies_height; n++) {
                        if (Replacement_candies[i][n].getPy() >= 150 && counter[i] > n + 1)
                            Replacement_candies[i][n + 1].setVelY(5);

                    }
                    if (Replacement_candies[i][counter[i] - 1].getPy() == 150) {
                        for (int y = 0; y < counter[i]; y++) {
                            Replacement_candies[i][y].setVelY(0);
                        }
                    }
                    if (j != 0) {
                        for (int x = j; x > 0; x--) {
                            candies[i][x - 1].setVelY(5);
                        }
                        if (candies[i][j - 1].getPy() >= candies[i][j].getPy()) {
                            for (int x = j; x > 0; x--) {
                                candies[i][x - 1].setVelY(0);
                            }
                        }
                    }
                }
                if (candies[i][j].isBroke() && candies[i][j].getCandiesPower() >= 1) {
                    candies[i][j].setCandiesPower(0);
                }
            }
        }

        if (!getCounterChanges(counter)) {
            if (!moving()) {
                for (int i = 0; i < candies_width; i++) {
                    int counterIndex = 0;
                    for (int j = candies_height - 1; j >= 0; j--) {
                        if (candies[i][j].isBroke()) {
                            Switch(candies[i][j], Replacement_candies[i][counterIndex]);
                            counterIndex++;
                            for (int x = 0; x < j; x++) {
                                if (!candies[i][x].isBroke()) {
                                    Switch(candies[i][x], candies[i][j]);
                                }
                            }
                            System.out.println(i + " " + j);
                            candies[i][j].setBroke(false);
                            Score += 30;
                        }
                    }
                }
                Reset();
                ResetCandies();
            }
        }
        if (level_game_index == 0)
            endGame();
    }

    public void Reset() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                Replacement_candies[i][j].setPx(i * 50 + 325);
                Replacement_candies[i][j].setPy(100);
                Replacement_candies[i][j].setShape(random.nextInt(AmountOfCandies));
            }
        }
    }

    public void endGame() {
        if (Score >= targetScore) {
            Score += Moves_count * 60;
            level_game_index = game_ended_index;
        }

    }

    // nổ
    public void explode(int i, int j) {
        try {
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    candies[x][y].setBroke(true);
                    candies[x][y].setCandyAnimation(candies[x][y].getCandyType());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e1) {
            try {
                for (int x = i + 1; x >= i - 1; x--) {
                    for (int y = j + 1; y >= j - 1; y--) {
                        candies[x][y].setBroke(true);
                        candies[x][y].setCandyAnimation(candies[x][y].getCandyType());
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e2) {
                try {
                    for (int x = i - 1; x <= i + 1; x++) {
                        for (int y = j + 1; y >= j - 1; y--) {
                            candies[x][y].setBroke(true);
                            candies[x][y].setCandyAnimation(candies[x][y].getCandyType());
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e3) {
                    try {
                        for (int x = i + 1; x >= i - 1; x--) {
                            for (int y = j - 1; y <= j + 1; y++) {
                                candies[x][y].setBroke(true);
                                candies[x][y].setCandyAnimation(candies[x][y].getCandyType());
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e4) {
                        System.out.println("outOfBounds4");
                    }
                    System.out.println("outOfBounds3");
                }
                System.out.println("outOfBounds2");
            }
            System.out.println("outOfBounds1");
        }
        gameLevel.removeObstaclesNear(new Point(i, j), this.obstacles);
        gameLevel.removeWallObstaclesNear(new Point(i, j), this.wallObstacles);

    }

    public class Obstacle {
        private Point position;
        private boolean isActive;

        public Obstacle(int x, int y) {
            this.position = new Point(x, y);
            this.isActive = true;
        }

        public Point getPosition() {
            return position;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    public class wallObstacle {
        private Point position;
        private boolean isActive;

        public wallObstacle(int x, int y) {
            this.position = new Point(x, y);
            this.isActive = true;
        }

        public Point getPosition() {
            return position;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    public void Move() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                candies[i][j].setPx(candies[i][j].getPx() + candies[i][j].getVelX());
                candies[i][j].setPy(candies[i][j].getPy() + candies[i][j].getVelY());
                Replacement_candies[i][j]
                        .setPy(Replacement_candies[i][j].getPy() + Replacement_candies[i][j].getVelY());
            }
        }
        if (CurrentPos == candies[SecondCandyRow][SecondCandyCol].getPx()
                || CurrentPos == candies[FirstCandyRow][FirstCandyCol].getPx()
                || CurrentPos == candies[SecondCandyRow][SecondCandyCol].getPy()
                || CurrentPos == candies[FirstCandyRow][FirstCandyCol].getPy()) {
            candies[FirstCandyRow][FirstCandyCol].setVelX(0);
            candies[SecondCandyRow][SecondCandyCol].setVelX(0);
            candies[FirstCandyRow][FirstCandyCol].setVelY(0);
            candies[SecondCandyRow][SecondCandyCol].setVelY(0);
            swapping = false;
            if (Non_matching_swap) {
                if (isHorizontal) {
                    candies[SecondCandyRow][SecondCandyCol].setVelX(direction * 5);
                    candies[FirstCandyRow][FirstCandyCol].setVelX(direction * -5);
                } else {
                    candies[SecondCandyRow][SecondCandyCol].setVelY(direction * 5);
                    candies[FirstCandyRow][FirstCandyCol].setVelY(direction * -5);
                }
                Non_matching_swap = false;
            }
        }
    }

    public boolean moving() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (Replacement_candies[i][j].getVelY() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getCounterChanges(int[] arr) {
        int x = 0;
        for (int j : arr) {
            if (j == 0) {
                x++;
            }
        }
        return x == arr.length;
    }

    public void stripedCandiesBroken(int i, int j) {
        if (candies[i][j].getCandiesPower() == 2) {
            for (int x = 0; x < candies_height; x++) {
                candies[i][x].setBroke(true);
                candies[i][x].setCandyAnimation(candies[i][x].getCandyType());
            }
        } else if (candies[i][j].getCandiesPower() == 3) {
            for (int x = 0; x < candies_width; x++) {
                candies[x][j].setBroke(true);
                candies[x][j].setCandyAnimation(candies[x][j].getCandyType());
            }
        }
    }

}