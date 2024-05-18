package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.net.http.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Candy Crush");
        // GamePanel gamePanel = new GamePanel();
        // window.add(gamePanel);
        // window.pack();
        // window.setLocationRelativeTo(null);
        // window.setVisible(true);
        JPanel panel = new JPanel();
        ImageIcon img = new ImageIcon(Main.class.getResource("/res/Background.jpg"));
        JLabel jlPic = new JLabel(img);
        panel.add(jlPic);
        window.add(panel);
        window.pack();
        window.setVisible(true);
    }
}