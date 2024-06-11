package UI;

import GameStates.GameState;
import GameStates.GameStateManager;
import Image.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelButton {
    private int xPos, yPos, rowIndex, index;
    private GameState state;
    private GameStateManager stateManager;
    private BufferedImage [] imgs;

    private int B_WIDTH = 150;
    private int B_HEIGHT = 120;
    private int xOffsetCenter = 50 / 2;

    private boolean mouseOver, mousePressed;
    private boolean keyOver, keyPressed;
    private Rectangle bounds;

    public LevelButton (int xPos, int yPos, int rowIndex) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        loadImgs ();
        initBounds();
    }

    private void initBounds () {
        bounds = new Rectangle (xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs () {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_BUTTONS);
        for (int i = 0; i < 3; i++) {
           imgs[i] = temp.getSubimage(i * B_WIDTH, rowIndex * B_HEIGHT, B_WIDTH, B_HEIGHT);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, 168, 168, null);
    }

    public void update (int currentLevel) {
//        index = 0;
//        if (mouseOver)
//            index = 1;
//        if (mousePressed)
//            index = 2;
//    }
        if (currentLevel == rowIndex) {
            if (keyPressed) {
                index = 2;
            } else index = 1;
        } else index = 0;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

//    public void applyGameState () {
//        GameStateManager.LEVEL = ;
//    }
public boolean isKeyOver() {
    return keyOver;
}

    public void setKeyOver(boolean keyOver) {
        this.keyOver = keyOver;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void resetBools () {
        mouseOver = false;
        mousePressed = true;
        keyOver = false;
        keyPressed = false;
    }
}
