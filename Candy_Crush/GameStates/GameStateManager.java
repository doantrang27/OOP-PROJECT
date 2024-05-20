package GameStates;

import java.util.ArrayList;

public class GameStateManager {
    private ArrayList <GameState> gameStates;
    private int currentState;
    private Instruction instructions;

    public static final int MENUSTATE = 0;
    public static final int LEVEL = 1;
    public static final int INSTRUCTION = 2;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        gameStates.add (new MenuState (this));
        gameStates.add (new Level(this));
        gameStates.add(new Instruction (this));
    }

    public void setState (int state ) {
        currentState = state;
        gameStates.get(currentState).init ();
    }
    public void update () {
        gameStates.get(currentState).update();
    }
    public void draw (java.awt.Graphics2D g) {
        gameStates.get (currentState).draw(g);
    }
    public void keyPressed (int k) {
        gameStates.get (currentState).keyPressed(k);
    }
    public void keyReleased (int k) {
        gameStates.get(currentState).keyReleased(k);
    }

//    private void addInstructionStateToWindow() {
//        // Assuming you have a reference to the main window (e.g., 'window')
//        gameStates.add(instructions.getInstructionPanel()); // Add instruction panel to window
//        gameStates.revalidate(); // Revalidate window layout
//        window.repaint(); // Repaint window to display the panel
    }




