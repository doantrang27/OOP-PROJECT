package stage_3.src;

import java.awt.*;
import java.nio.file.Paths;
import javax.swing.*;

public class Candies {

    private final Image[] candies = new Image[6];
    private final Image[] candyAnimation = new Image[6];
    private final Image[] explodingCandies = new Image[6];
    private final Image[] stripedCandiesV = new Image[6];
    private final Image[] stripedCandiesH = new Image[6];
    private final Image obstacle = new ImageIcon("Textures/obstacle.png").getImage();

    private Image candyShape;
    private int Px, Py;
    private int VelX = 0, VelY = 0;
    private int candyType;
    private boolean broke = false;
    private int candiesPower;
    private boolean isObstacle;

    Candies() {
        candies[CandyColors.BLUE] = getImageCandies("blue.png");
        candies[CandyColors.GREEN] = getImageCandies("green.png");
        candies[CandyColors.RED] = getImageCandies("red.png");
        candies[CandyColors.ORANGE] = getImageCandies("orange.png");
        candies[CandyColors.VIOLET] = getImageCandies("violet.png");
        candies[CandyColors.YELLOW] = getImageCandies("yellow.png");

        candyAnimation[CandyColors.BLUE] = getImageCandiesAnimation("blue.gif");
        candyAnimation[CandyColors.GREEN] = getImageCandiesAnimation("green.gif");
        candyAnimation[CandyColors.RED] = getImageCandiesAnimation("red.gif");
        candyAnimation[CandyColors.ORANGE] = getImageCandiesAnimation("orange.gif");
        candyAnimation[CandyColors.VIOLET] = getImageCandiesAnimation("purple.gif");
        candyAnimation[CandyColors.YELLOW] = getImageCandiesAnimation("yellow.gif");

        explodingCandies[CandyColors.BLUE] = getImageSpecialCandies("blue.png");
        explodingCandies[CandyColors.GREEN] = getImageSpecialCandies("green.png");
        explodingCandies[CandyColors.RED] = getImageSpecialCandies("red.png");
        explodingCandies[CandyColors.ORANGE] = getImageSpecialCandies("orange.png");
        explodingCandies[CandyColors.VIOLET] = getImageSpecialCandies("violet.png");
        explodingCandies[CandyColors.YELLOW] = getImageSpecialCandies("yellow.png");

        stripedCandiesV[CandyColors.BLUE] = getImageSpecialCandies("Striped_blue_v.png");
        stripedCandiesV[CandyColors.GREEN] = getImageSpecialCandies("Striped_green_v.png");
        stripedCandiesV[CandyColors.RED] = getImageSpecialCandies("Striped_red_v.png");
        stripedCandiesV[CandyColors.ORANGE] = getImageSpecialCandies("Striped_orange_v.png");
        stripedCandiesV[CandyColors.VIOLET] = getImageSpecialCandies("Striped_purple_v.png");
        stripedCandiesV[CandyColors.YELLOW] = getImageSpecialCandies("Striped_yellow_v.png");

        stripedCandiesH[CandyColors.BLUE] = getImageSpecialCandies("Striped_blue_h.png");
        stripedCandiesH[CandyColors.GREEN] = getImageSpecialCandies("Striped_green_h.png");
        stripedCandiesH[CandyColors.RED] = getImageSpecialCandies("Striped_red_h.png");
        stripedCandiesH[CandyColors.ORANGE] = getImageSpecialCandies("Striped_orange_h.png");
        stripedCandiesH[CandyColors.VIOLET] = getImageSpecialCandies("Striped_purple_h.png");
        stripedCandiesH[CandyColors.YELLOW] = getImageSpecialCandies("Striped_yellow_h.png");

    }

    // public Image getImageCandies(String fileName) {
    // return new ImageIcon("Textures/Candies 50x50" + fileName).getImage();
    // }

    // public Image getImageCandiesAnimation(String fileName) {
    // return new ImageIcon("Textures/CandyBreaking" + fileName).getImage();
    // }

    public Image getImageCandies(String fileName) {
        return new ImageIcon(
                Paths.get("Textures", "Candies 50x50", fileName).toString())
                .getImage();

    }

    public Image getImageCandiesAnimation(String fileName) {
        return new ImageIcon(
                Paths.get("Textures", "CandyBreaking", fileName).toString())
                .getImage();
    }

    public Image getImageSpecialCandies(String fileName) {
        return new ImageIcon(
                Paths.get("Textures", "Special_candies", fileName).toString())
                .getImage();
        // Paths.get("Textures", "Special_candies", fileName) -->
        // "Textures/Special_candies/fileName"
        // on Windows, it will be "Textures\\Special_candies\\fileName"
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public void setShape(int x) {
        candyShape = candies[x];
        candyType = x;
    }

    public int getCandyType() {
        return candyType;
    }

    public void setCandyAnimation(int x) {
        candyShape = candyAnimation[x];
        candyType = x;
    }

    public void setSpecialCandy(int x) {
        candyShape = explodingCandies[x];
        candyType = x;
    }

    public void setStripedCandiesV(int x) {
        candyShape = stripedCandiesV[x];
        candyType = x;
    }

    public void setStripedCandiesH(int x) {
        candyShape = stripedCandiesH[x];
        candyType = x;
    }

    public Image getCandyAnimation(int x) {
        return candyAnimation[x];
    }

    public int getPx() {
        return Px;
    }

    public void setPx(int px) {
        Px = px;
    }

    public int getPy() {
        return Py;
    }

    public void setPy(int py) {
        Py = py;
    }

    public int getVelX() {
        return VelX;
    }

    public void setVelX(int velX) {
        VelX = velX;
    }

    public int getVelY() {
        return VelY;
    }

    public void setVelY(int velY) {
        VelY = velY;
    }

    public Image getCandyShape() {
        return candyShape;
    }

    public boolean isBroke() {
        return broke;
    }

    public void setBroke(boolean broke) {
        this.broke = broke;
    }

    public int getCandiesPower() {
        return candiesPower;
    }

    public void setCandiesPower(int candiesPower) {
        this.candiesPower = candiesPower;
    }
}
