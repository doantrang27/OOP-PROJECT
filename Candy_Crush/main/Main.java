package main;

import java.awt.Component;

import javax.swing.JFrame;
// import boardGame.Board;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Candy Crush");
        // GamePanel pn = new GamePanel();
        // window.add(pn);
        // JPanel panel = new JPanel();
        // ImageIcon img = new
        // ImageIcon(Main.class.getResource("/res/Background.jpg"));
        // // JLabel jlPic = new JLabel(img);
        // // panel.add(jlPic);
        // // window.add(panel);

        Board board = new Board();
        // add the jpanel to the window
        window.add(board);
        window.pack();
        window.setVisible(true);

    }
}

// // paint:
// // paintComponent
// // paintBorder
// // paintChildren