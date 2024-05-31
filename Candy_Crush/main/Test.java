package main;

import java.awt.Dimension;

import javax.swing.*;

public class Test {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Candy Swap Game");
        GamePanel_Test gamePanel = new GamePanel_Test();

        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

    }

}
