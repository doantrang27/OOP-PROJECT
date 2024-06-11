package GameStates;

import Main.GamePanel;
import TileMap.Background;
import UI.LevelButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Level extends GameState{

        private Background bgLevel;
        private int currentLevel = 0;
        private BufferedImage holder, title;

        private LevelButton[] buttons = new LevelButton[3];
        private final String[] options = {"Start", "Option", "Quit"};

        private Color titleColor;
        public Font titleFont;

        private Font font;
        //    private AudioPlayer audioInput;

        public Level (GameStateManager gsm) {
            this.gsm = gsm;

            try {
//                title = ImageIO.read(getClass().getResourceAsStream("/UI/button.png"));
//                holder = ImageIO.read(getClass().getResourceAsStream("/UI/button.png"));
                bgLevel = new Background("/out/production/OOP-PROJECT/Background/BgLevel.png", 0.5);
                // bg.setVector(-1, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            loadButtons();
        }

        public void init() {}

        private void loadButtons() {
            buttons[0] = new LevelButton(GamePanel.WIDTH  / 3  * 0 + 38, 135, 0);
            buttons[1] = new LevelButton(GamePanel.WIDTH  / 3  * 1 + 32 , 135, 1);
            buttons[2] = new LevelButton(GamePanel.WIDTH  / 3  * 2 + 32, 135, 2);
        }

        public void update() {
            // draw bg
            bgLevel.update();
            for (LevelButton mb : buttons)
                mb.update(currentLevel);
        }

        public void draw(Graphics2D g) {
            bgLevel.draw(g);
            g.drawImage(title, 50, 20, 225, 56, null);
            g.drawImage(holder, 116, 125, 90, 100, null);

            // draw title

            for (LevelButton mb : buttons) mb.draw(g);
        }

        private void select() {

            if (currentLevel == 0) {
                gsm.setState(GameStateManager.INSTRUCTION);
            }
            if (currentLevel == 1) {
                // help
                gsm.setState(3);
            }
            if (currentLevel == 2) {
                System.exit(0);
            }
        }

        public void keyPressed(int k) {

            if (k == KeyEvent.VK_ENTER) {
                select();
            }
            if (k == KeyEvent.VK_LEFT) {
                currentLevel--;
                if (currentLevel == -1) {
                    currentLevel = options.length - 1;
                }
            }
            if (k == KeyEvent.VK_RIGHT) {
                currentLevel++;
                if (currentLevel == options.length) {
                    currentLevel = 0;
                }
            }
        }

        public void keyReleased(int k) {}

        private void resetButtons() {
            for (LevelButton mb : buttons) mb.resetBools();
        }
    }
