package Main;

// import Audio.Music;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Candy Crush");
        GamePanel gp = GamePanel.getPanel();
        window.setContentPane(gp);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();

        // stage_3.src.GamePanel gamePanel = new stage_3.src.GamePanel();
        // window.add(gamePanel);
        window.setVisible(true);
        // Main.main(args);
        // Music.RunMucsic("Sound/MusicGame.mp3");
    }
}
