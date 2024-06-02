package stage_3.src;

import java.awt.Dimension;
import javax.swing.*;
import stage_3.src.Level.DifficultLevel;
import stage_3.src.Level.IntermediateLevel;
import stage_3.src.Level.EasyLevel;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("lv3");
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        // DifficultLevel difficultLevel = new DifficultLevel();
        // EasyLevel EasyLevel = new EasyLevel();

    }

}
