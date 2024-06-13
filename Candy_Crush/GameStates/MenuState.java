
package GameStates;

import GameStates.GameState;
import GameStates.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import UI.MenuButton;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

    private Background bg;
    private int currentChoice = 0;
    private BufferedImage holder, title;

    private MenuButton[] buttons = new MenuButton[3];
    private final String[] options = { "Start", "Option", "Quit" };

    private Color titleColor;
    public Font titleFont;

    private Font font;
    // private AudioPlayer audioInput;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background("/Resource/Background/editbg-transformed (1).png", 0.5);
            bg.resize(550, 355);
            bg.setVector(0, 0);
            titleColor = new Color(255, 130, 171);
            titleFont = new Font("Phosphate", Font.PLAIN, 40);
            font = new Font("Bradley Hand", Font.PLAIN, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadButtons();
    }

    public void init() {
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GamePanel.WIDTH / 2 - 10, 240 + 0 * 30, 0);
        buttons[1] = new MenuButton(GamePanel.WIDTH / 2 - 10, 240 + 1 * 30, 1);
        buttons[2] = new MenuButton(GamePanel.WIDTH / 2 - 10, 240 + 2 * 30, 2);
    }

    public void update() {
        // draw bg
        bg.update();
        for (MenuButton mb : buttons)
            mb.update(currentChoice);
    }

    public void draw(Graphics2D g) {
        bg.draw(g);
        g.drawImage(title, 50, 20, 225, 56, null);
        g.drawImage(holder, 116, 125, 90, 100, null);

        // draw title

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    private void select() {

        if (currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL);
        }
        if (currentChoice == 1) {
            // help
            gsm.setState(GameStateManager.INSTRUCTION);
        }
        if (currentChoice == 2) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {

        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }
}
