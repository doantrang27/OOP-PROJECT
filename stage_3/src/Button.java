package stage_3.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JPanel {

    public static JButton createCloseButton() {
        JButton closeButton = createImageButton("Textures/close.png");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Quit the game
            }
        });
        return closeButton;
    }

    public static JButton createRestartButton(Logic gameLogic) {
        JButton restartButton = createImageButton("Textures/restart.png");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.restart(); // Restart the game
            }
        });
        return restartButton;
    }

    private static JButton createImageButton(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);

        JButton button = new JButton(icon);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorderPainted(false);

        return button;

    }
}