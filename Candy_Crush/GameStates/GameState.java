package GameStates;

import stage_3.src.Level.EasyLevel;

public abstract class GameState {
    protected GameStateManager gsm;

    public abstract void init();

    public abstract void update();

    public abstract void draw(java.awt.Graphics2D g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

}
