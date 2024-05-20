package GameStates;

import Main.GamePanel;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Level extends GameState {
    private Background bgLevel;
    private int currentLevel = 0;

    private LevelButton[] buttons = new LevelButton[3];
    private String[] options = {"Level 1", "Level 2", "Level 3"};

    private Font titlefont;
    private Color titlecolor;
    private Font font;

    public Level(GameStateManager gsm) {
        this.gsm = gsm;
        init();
        try {
            bgLevel = new Background("/Background/bg5.jpeg", 0);
            titlecolor = new Color(255, 130, 171);
            titlefont = new Font("Phosphate", Font.PLAIN, 40);
            font = new Font("Bradley Hand", Font.PLAIN, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    public void loadButtons() {
        buttons[0] = new LevelButton(GamePanel.WIDTH / 2 - 9, 130 + 0 * 30, 0);
        buttons[1] = new LevelButton(GamePanel.WIDTH / 2 - 9, 130 + 1 * 30, 1);
        buttons[2] = new LevelButton(GamePanel.WIDTH / 2 - 9, 130 + 2 * 30, 2);
    }

    @Override
    public void update() {
        bgLevel.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bgLevel.draw(g);

        //draw title
        g.setColor(titlecolor);
        g.setFont(titlefont);
        g.drawString("Level 1", 93, 50);

    }

    private void select() {

        if (currentLevel == 0) {

        }
        if (currentLevel == 1) {
        }
        if (currentLevel == 2) {
//
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentLevel--;
            if (currentLevel == -1) {
                currentLevel = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentLevel++;
            if (currentLevel == options.length) {
                currentLevel = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }

}
