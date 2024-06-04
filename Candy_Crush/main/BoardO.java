// package main;

// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.awt.*;
// import java.awt.event.MouseMotionAdapter;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Random;

// import javax.imageio.ImageIO;
// import javax.swing.*;

// public class Board extends JPanel {
// private static final int EMPTY_CELL = -1;
// private Point hoveredCandy = null;
// private Point selectedCandy = null;

// // controls the delay between each tick in ms
// private final int DELAY = 25;

// // controls the size of the board
// public static final int TILE_SIZE = 50;
// public static final int ROWS = 8;
// public static final int COLUMNS = 8;
// // controls how many coins appear on the board
// public static final int NUM_CANDIES = 64;
// // suppress serialization warning
// private static final long serialVersionUID = 490905409104883233L;
// private int[][] grid = new int[ROWS][COLUMNS];
// private static final int ANIMATION_STEPS = 1;
// private static final int ANIMATION_DELAY = 30; // in milliseconds
// private static final int ANIMATION_DELAY_MS = 10; // Delay between frames
// (adjust as needed)
// private Random random;
// private Map<Integer, Image> candyImages = new HashMap<>();

// public Board() {
// // set the game board size
// setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
// // set the game board background color
// setBackground(new Color(255, 204, 229));
// random = new Random();

// // Load candy images (replace with your actual file paths)
// try {
// for (int i = 0; i < 5; i++) {
// String imagePath = "Candy_Crush/res/Candy_" + i + ".png";
// File imageFile = new File(imagePath);
// if (imageFile.exists()) {
// BufferedImage image = ImageIO.read(imageFile);
// candyImages.put(i, image);
// } else {
// System.err.println("File not found at path: " + imagePath);
// }

// }
// } catch (IOException e) {
// System.err.println("Error loading images: " + e.getMessage());
// }

// initializeGrid();

// // Add mouse motion listener for hover effect
// addMouseMotionListener(new MouseMotionAdapter() {
// @Override
// public void mouseMoved(MouseEvent e) {
// int row = e.getY() / TILE_SIZE;
// int col = e.getX() / TILE_SIZE;
// hoveredCandy = new Point(col, row);
// repaint(); // Repaint to show the hover effect
// }
// });

// addMouseListener(new MouseAdapter() {
// @Override
// public void mouseClicked(MouseEvent e) {
// int row = e.getY() / TILE_SIZE;
// int col = e.getX() / TILE_SIZE;
// if (selectedCandy == null) {
// selectedCandy = new Point(col, row);
// } else {
// swapCandies(selectedCandy.x, selectedCandy.y, col, row);
// selectedCandy = null;
// }
// repaint();
// }
// });
// }

// private void initializeGrid() {
// for (int i = 0; i < ROWS; i++) {
// for (int j = 0; j < COLUMNS; j++) {
// grid[i][j] = random.nextInt(5); // 5 candy types (0-4)
// }
// }
// }

// @Override
// public void paintComponent(Graphics g) {
// super.paintComponent(g);
// drawBackground(g);

// for (int i = 0; i < ROWS; i++) {
// for (int j = 0; j < COLUMNS; j++) {
// int x = j * TILE_SIZE;
// int y = i * TILE_SIZE;
// Image candyImage = candyImages.get(grid[i][j]);
// if (candyImage != null) {
// // Draw the candy image normally
// g.drawImage(candyImage, x, y, TILE_SIZE, TILE_SIZE, this);

// // If the candy is hovered, draw a highlighted border
// if (hoveredCandy != null && hoveredCandy.equals(new Point(j, i))) {
// g.setColor(Color.YELLOW); // Color for hover effect
// g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
// }

// // If the candy is selected, draw a thicker border
// if (selectedCandy != null && selectedCandy.equals(new Point(j, i))) {
// Graphics2D g2 = (Graphics2D) g;
// g2.setStroke(new BasicStroke(3)); // Thicker border for selection
// g2.setColor(Color.white); // Color for selection effect
// g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
// }
// } else {
// System.err.println("No image for candy type: " + grid[i][j]);
// }
// }
// }
// }

// private void drawBackground(Graphics g) {
// g.drawRect(0, 0, TILE_SIZE * COLUMNS, TILE_SIZE * ROWS);
// g.setColor(new Color(255, 153, 204));

// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS; col++) {
// // only color every other tile
// if ((row + col) % 2 == 1) {
// // draw a square tile at the current row/column position
// g.fillRect(
// col * TILE_SIZE,
// row * TILE_SIZE,
// TILE_SIZE,
// TILE_SIZE);
// }
// }
// }
// }

// // Add a method to check for matches
// private boolean hasMatch() {
// boolean matchFound = false;

// // Check for horizontal matches
// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS - 2; col++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row][col + 1] &&
// candyType == grid[row][col + 2]) {
// matchFound = true;
// break; // Exit the loop early if a match is found
// }
// }
// }

// // Check for vertical matches
// for (int col = 0; col < COLUMNS; col++) {
// for (int row = 0; row < ROWS - 2; row++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row + 1][col] &&
// candyType == grid[row + 2][col]) {
// matchFound = true;
// break; // Exit the loop early if a match is found
// }
// }
// }

// return matchFound;
// }

// private void swapCandies(int x1, int y1, int x2, int y2) {
// // Check if the candies are adjacent
// if (Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1) {
// // Swap the candies
// int temp = grid[y1][x1];
// grid[y1][x1] = grid[y2][x2];
// grid[y2][x2] = temp;

// // Check if the swap resulted in a match
// if (!hasMatch()) {
// // Swap back if no match is found
// temp = grid[y1][x1];
// grid[y1][x1] = grid[y2][x2];
// grid[y2][x2] = temp;
// } else {
// // If a match is found, handle match removal and grid refill
// checkForMatches();
// }
// }
// }

// private void checkForMatches() {
// boolean matchFound;
// do {
// matchFound = false;
// boolean[][] markedForRemoval = new boolean[ROWS][COLUMNS];

// // Check for horizontal and vertical matches of 4
// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS - 3; col++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row][col + 1] &&
// candyType == grid[row][col + 2] &&
// candyType == grid[row][col + 3]) {
// for (int i = 0; i < 4; i++) {
// markedForRemoval[row][col + i] = true;
// }

// matchFound = true;

// }
// }
// }

// // Check for vertical matches of 4
// for (int col = 0; col < COLUMNS; col++) {
// for (int row = 0; row < ROWS - 3; row++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row + 1][col] &&
// candyType == grid[row + 2][col] &&
// candyType == grid[row + 3][col]) {
// for (int i = 0; i < 4; i++) {
// markedForRemoval[row + i][col] = true;
// }

// matchFound = true;

// }
// }
// }

// // Check for horizontal matches of 3
// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS - 2; col++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row][col + 1] &&
// candyType == grid[row][col + 2] &&
// !markedForRemoval[row][col] &&
// !markedForRemoval[row][col + 1] &&
// !markedForRemoval[row][col + 2]) {
// for (int i = 0; i < 3; i++) {
// markedForRemoval[row][col + i] = true;
// }
// matchFound = true;
// }
// }
// }

// // Check for vertical matches of 3
// for (int col = 0; col < COLUMNS; col++) {
// for (int row = 0; row < ROWS - 2; row++) {
// int candyType = grid[row][col];
// if (candyType != EMPTY_CELL &&
// candyType == grid[row + 1][col] &&
// candyType == grid[row + 2][col] &&
// !markedForRemoval[row][col] &&
// !markedForRemoval[row + 1][col] &&
// !markedForRemoval[row + 2][col]) {
// for (int i = 0; i < 3; i++) {
// markedForRemoval[row + i][col] = true;
// }
// matchFound = true;
// }
// }
// }
// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS; col++) {
// if (markedForRemoval[row][col]) {
// // Check if it's a horizontal match of 4
// if (col < COLUMNS - 3 && markedForRemoval[row][col + 1] &&
// markedForRemoval[row][col + 2]
// && markedForRemoval[row][col + 3]) {
// loadSpecialCandy(row, col + 3); // Place the special candy at the end of the
// match
// break; // Break to ensure only one special candy is placed per match
// }
// // Check if it's a vertical match of 4
// if (row < ROWS - 3 && markedForRemoval[row + 1][col] && markedForRemoval[row
// + 2][col]
// && markedForRemoval[row + 3][col]) {
// loadSpecialCandy(row + 3, col); // Place the special candy at the end of the
// match
// break; // Break to ensure only one special candy is placed per match
// }
// }
// }
// }

// // Remove matched candies
// for (int row = 0; row < ROWS; row++) {
// for (int col = 0; col < COLUMNS; col++) {
// if (markedForRemoval[row][col]) {
// grid[row][col] = EMPTY_CELL;
// }
// }
// }

// refillGrid(); // Refill the grid after removal

// } while (matchFound); // Continue checking for matches until no more are
// found
// }

// private void loadSpecialCandy(int row, int col) {
// String specialCandyPath = "Candy_Crush/res/Candy_5.png";
// File specialCandyFile = new File(specialCandyPath);
// try {
// BufferedImage specialCandyImage = ImageIO.read(specialCandyFile);
// candyImages.put(5, specialCandyImage); // Assuming 5 is the key for the
// special candy
// grid[row][col] = 5; // Replace the last candy with the special candy
// } catch (IOException e) {
// System.err.println("Error loading special candy image: " + e.getMessage());
// }
// }

// private void refillGrid() {
// for (int col = 0; col < COLUMNS; col++) {
// int emptyCount = 0;
// for (int row = ROWS - 1; row >= 0; row--) {
// if (grid[row][col] == EMPTY_CELL) {
// emptyCount++;
// } else if (emptyCount > 0) {
// animateCandyFall(row, col, emptyCount);

// // Shift the non-empty candy down
// grid[row + emptyCount][col] = grid[row][col];
// grid[row][col] = EMPTY_CELL;

// }
// }

// // Generate new candies for empty cells at the top
// for (int i = 0; i < emptyCount; i++) {
// // Set the new candy type (replace with actual candy types)

// grid[i][col] = random.nextInt(5);
// }
// }
// }

// private void animateCandyFall(int row, int col, int emptyCount) {
// int initialY = row * TILE_SIZE;
// int targetY = (row + emptyCount) * TILE_SIZE;
// for (int step = 1; step <= ANIMATION_STEPS; step++) {
// int currentY = initialY + (targetY - initialY) * step / ANIMATION_STEPS;
// // Update the UI with the new position
// repaint();
// try {
// Thread.sleep(ANIMATION_DELAY);
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
// }
// }

// }