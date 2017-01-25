package states;

import entities.TurnManager;
import game.Game;
import game.Handler;

import java.awt.*;

/**
 * Created by Артем on 10.01.2017.
 */
public abstract class State {
    private static State currentState = null;
    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(State currentState) {
        State.currentState = currentState;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Handler getHandler() {
        return handler;
    }
}
