package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;

public class Board extends JPanel {

    // controls the delay between each tick in ms
    private final int DELAY = 25;
    // controls the size of the board
    public static final int TILE_SIZE = 60;
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    // controls how many coins appear on the board
    public static final int NUM_CANDIES = 64;
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    private int[][] grid = new int[ROWS][COLUMNS];
    // JButton[][] button = new JButton[ROWS][COLUMNS];
    private Random random;
    private Map<Integer, Image> candyImages = new HashMap<>();

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(new Color(232, 232, 232));
        random = new Random();
        // for (int i = 0; i < ROWS; i++) {
        // for (int j = 0; j < COLUMNS; j++) {
        // int d = rand();
        // // bn[i][j] = new JButton(String.valueOf(d));
        // button[i][j] = new JButton();
        // button[i][j].setActionCommand(String.valueOf(d));
        // button[i][j].addActionListener(new Action());
        // // bn[i][j].setFont(bn[i][j].getFont().deriveFont(48.0f));
        // button[i][j].setIcon(new ImageIcon(url[d]));
        // button[i][j].setOpaque(false);
        // button[i][j].setBorderPainted(false);

        // }
        // }
        // Load candy images (replace with your actual file paths)
        try {
            for (int i = 0; i < 6; i++) {
                String imagePath = "Candy_Crush/res/Candy_" + i + ".png";
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    BufferedImage image = ImageIO.read(imageFile);
                    candyImages.put(i, image);
                } else {
                    System.err.println("File not found at path: " + imagePath);
                }

            }
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                grid[i][j] = random.nextInt(5); // 5 candy types (0-4)
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g); // Call drawBackground() here

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int x = j * TILE_SIZE;
                int y = i * TILE_SIZE;
                Image candyImage = candyImages.get(grid[i][j]);
                if (candyImage != null) {
                    g.drawImage(candyImage, x, y, TILE_SIZE, TILE_SIZE, this);
                } else {
                    System.err.println("No image for candy type: " + grid[i][j]);
                }
            }
        }
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE);
                }
            }
        }
    }

    // public int rand() {
    // Random r = new Random();
    // return r.nextInt(5);
    // }

    int a = 5;
}