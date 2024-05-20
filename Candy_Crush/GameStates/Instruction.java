package GameStates;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Instruction extends GameState {
    private Background bgInstruction;

    private Font tilefont;
    private Color tilecolor;
    private Font font;
    private String [] instruction = {
            "First:...",
            "Second:...",
            "Third:..."
    };


    public Instruction(GameStateManager gsm) {
        this.gsm = gsm;
        init();
        try {
            bgInstruction = new Background("/Background/bg5.jpeg", 0);
            tilecolor = new Color(255, 130, 171);
            tilefont = new Font("Phosphate", Font.PLAIN, 40);
            font = new Font("Bradley Hand", Font.PLAIN, 20);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        bgInstruction.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bgInstruction.draw(g);

        //draw title
        g.setColor(tilecolor);
        g.setFont(tilefont);
        g.drawString("Instruction", 93, 50);

        //draw instruction
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(instruction[0], 60, 160);
        g.drawString(instruction[1], 60, 180);
        g.drawString(instruction[2], 60, 200);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE){
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}