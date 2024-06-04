// package main;

// import javax.swing.*;
// import java.awt.*;

// public class Board_Test {
// ImageIcon[] board = new ImageIcon[2];
// private int board_height, board_width;

// Board_Test(int board_height, int board_width) {
// board[0] = new ImageIcon("Candy_Crush/res/board_1.png");
// board[1] = new ImageIcon("Candy_Crush/res/boar.png");
// this.board_height = board_height;
// this.board_width = board_width;
// }

// public void draw(Graphics g) {

// for (int j = 0; j < board_height; j++) {
// for (int i = 0; i < board_width; i++) {
// if (j % 2 == 0) {
// if (i % 2 == 0)
// g.drawImage(board[0].getImage(), 50 * i + 325, 50 * j + 150, null);
// else

// g.drawImage(board[1].getImage(), 50 * i + 325, 50 * j + 150, null);
// } else {
// if (i % 2 != 0)
// g.drawImage(board[0].getImage(), 50 * i + 325, 50 * j + 150, null);
// else
// g.drawImage(board[1].getImage(), 50 * i + 325, 50 * j + 150, null);

// }
// }
// }
// }
// }
