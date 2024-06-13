package GameStates;

import Main.Game;
import Main.GamePanel;
import TileMap.Background;
import UI.LevelButton;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import stage_3.src.Level.EasyLevel;
import stage_3.src.Level.IntermediateLevel;
import stage_3.src.Logic;
import stage_3.src.Level.DifficultLevel;

public class Level extends GameState {
    private int selectedLevel;

    private Background bgLevel;
    public static int currentLevel = 0;
    private BufferedImage holder, title;

    private LevelButton[] buttons = new LevelButton[3];
    private final String[] options = { "Start", "Option", "Quit" };

    private Color titleColor;
    public Font titleFont;

    private Font font;
    // private AudioPlayer audioInput;
    private Logic logic;
    private OnLevelSelectedListener onLevelSelectedListener;

    public Level(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            // title = ImageIO.read(getClass().getResourceAsStream("/UI/button.png"));
            // holder = ImageIO.read(getClass().getResourceAsStream("/UI/button.png"));
            bgLevel = new Background("/out/production/OOP-PROJECT/Background/BgLevel.png", 0.5);
            // bg.setVector(-1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadButtons();
    }

    public void init() {
    }

    private void loadButtons() {
        buttons[0] = new LevelButton(GamePanel.WIDTH / 3 * 0 + 38, 135, 0);
        buttons[1] = new LevelButton(GamePanel.WIDTH / 3 * 1 + 32, 135, 1);
        buttons[2] = new LevelButton(GamePanel.WIDTH / 3 * 2 + 32, 135, 2);
        // buttons[0].addActionListener(e -> gamePanel.setLevel("EASY"));
        // buttons[1].addActionListener(e -> gamePanel.setLevel("INTERMEDIATE"));
        // buttons[2].addActionListener(e -> gamePanel.setLevel("HARD"));

    }
    // make the button to select level connected to gamePanel by using key pressed
    // button 0 was pressed then set level to easy
    // button 1 was pressed then set level to intermediate
    // button 2 was pressed then set level to difficult

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

        for (LevelButton mb : buttons)
            mb.draw(g);
    }

    private void select() {
        // stage_3.src.GamePanel gamePanel = new stage_3.src.GamePanel();
        Logic gameLogic;
        String gameDifficulty = "";
        if (currentLevel == 0) {
            gameDifficulty = "EASY";
            gameLogic = new Logic(7, 7, 15, 5, "EASY");
        } else if (currentLevel == 1) {
            gameDifficulty = "MEDIUM";
            gameLogic = new Logic(7, 7, 15, 5, gameDifficulty);
        } else if (currentLevel == 2) {
            gameDifficulty = "HARD";
            gameLogic = new Logic(7, 7, 15, 5, gameDifficulty);
        } else {
            // Handle the case where currentLevel is not 0, 1, or 2
            return;
        }
        // gamePanel.setLogic(gameLogic);
        stage_3.src.GamePanel gamePanel = new stage_3.src.GamePanel(gameDifficulty);

        if (onLevelSelectedListener != null) {
            onLevelSelectedListener.onLevelSelected(currentLevel);
        }
        JFrame frame = new JFrame("Candy Crush");
        frame.setSize(300, 355);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setSize(1000, 750);

        frame.setVisible(true);
    }

    // public String getGameDifficulty() {
    // if (currentLevel == 0) {
    // return "EASY";
    // }
    // if (currentLevel == 1) {
    // return "MEDIUM";
    // }
    // if (currentLevel == 2) {
    // return "HARD";
    // }
    // // Add other conditions for different levels if needed
    // else {
    // return "UNKNOWN"; // Default case
    // }
    // }
    // private void select() {
    // stage_3.src.GamePanel gamePanel = new stage_3.src.GamePanel();
    // Logic gameLogic;
    // if (currentLevel == 0) {
    // gameLogic = new Logic(7, 7, 15, 5, "EASY");
    // // logic.getGameDifficulty();

    // // gamePanel.setLogic(logic);

    // // gameLogic.setGameDifficulty("EASY");
    // // gamePanel.setLogic(logic);
    // // gamePanel.add(gameLogic);
    // // gameLogic.setGameDifficulty("EASY");

    // // JLabel gameLogicLabel = new JLabel(gameLogic.getGameDifficulty());
    // // gamePanel.add(gameLogicLabel);

    // } else if (currentLevel == 1) {
    // gameLogic = new Logic(7, 7, 15, 5, "MEDIUM");
    // logic.setGameDifficulty("MEDIUM");
    // gamePanel.setLogic(logic);

    // } else if (currentLevel == 2) {
    // logic = new Logic(7, 7, 15, 5, "HARD");
    // logic.setGameDifficulty("HARD");
    // gamePanel.setLogic(logic);
    // }

    // if (onLevelSelectedListener != null) {
    // onLevelSelectedListener.onLevelSelected(currentLevel);
    // }
    // JFrame frame = new JFrame("Candy Crush");
    // frame.add(gamePanel);
    // frame.pack();
    // frame.setVisible(true);

    // }

    public void setOnLevelSelectedListener(OnLevelSelectedListener listener) {
        this.onLevelSelectedListener = listener;
    }

    public Logic getLogic() {
        return logic;
    }

    public interface OnLevelSelectedListener {
        void onLevelSelected(int level);
    }

    public void keyPressed(int k) {

        if (k == KeyEvent.VK_ENTER) {
            select();
            // setOnLevelSelectedListener(listener);
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

    public void keyReleased(int k) {
    }

    private void resetButtons() {
        for (LevelButton mb : buttons)
            mb.resetBools();
    }
}
