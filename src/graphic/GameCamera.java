package graphic;

import entities.Entity;
import game.Game;

/**
 * Created by Артем on 10.01.2017.
 */
public class GameCamera {
    private float xOffSet, yOffSet;
    private Game game;

    public GameCamera(Game game, float xOffSet, float yOffSet) {
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
        this.game = game;
    }

    public void move(float xAmt, float yAmt){
        xOffSet +=xAmt;
        yOffSet +=yAmt;

        editBlankSpace();
    }

    private void editBlankSpace(){
        if (xOffSet<0)
            xOffSet =0;
        if (xOffSet>game.getWidth())
            xOffSet = game.getWidth();
        if (yOffSet<0)
            yOffSet=0;
        if (yOffSet>game.getHeight())
            yOffSet = game.getHeight();
    }

    public void centerOnEntity(Entity e){
        xOffSet = e.getX() - game.getWidth()/2 + e.getWidth()/2;
        yOffSet = e.getY() - game.getHeight()/2 + e.getHeight()/2;

        editBlankSpace();
    }

    public float getXOffSet() {
        return xOffSet;
    }

    public float getYOffSet() {
        return yOffSet;
    }
}
