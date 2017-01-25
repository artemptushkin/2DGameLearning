package entities.battle_mobs;

import entities.fractions.Fraction;
import game.Handler;
import graphic.assets.Assets;
import graphic.assets.CuteAngelAssets;
import states.State;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Артем on 17.01.2017.
 */
public abstract class BattleUnit {
    protected int battleX, battleY; //at battle tiles coordinate

    protected BufferedImage currentImage;
    protected int width, height;
    protected int health; //basic
    protected int speed;  //extend
    protected float xMove, yMove;
    private Fraction fraction;

    protected static final int DEFAULT_HEALTH = 10;
    protected static final int DEFAULT_SPEED = 2;
    protected static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

    private boolean movedThisTurn;

    public BattleUnit(Fraction fraction,int x, int y) {
        this.battleX = x;
        this.battleY = y;
        health = DEFAULT_HEALTH;

        this.fraction = fraction;
        width = DEFAULT_CREATURE_WIDTH;
        height = DEFAULT_CREATURE_HEIGHT;

        movedThisTurn = false;
    }

    protected void rotateCurrentImage() {
        if (currentImage==null)
            currentImage = CuteAngelAssets.stand;

        if (fraction==Fraction.Player)
            currentImage = Assets.rotateImage(currentImage);
    }

    public abstract void tick();

    public void render(Graphics g){
        g.drawImage(currentImage, getBattleX()*width, getBattleY()*height, width, height, null);
    }

    //GETTERS & SETTERS

    public int getBattleX() {
        return battleX;
    }

    public void setBattleX(int battleX) {
        this.battleX = battleX;
    }

    public int getBattleY() {
        return battleY;
    }

    public void setBattleY(int battleY) {
        this.battleY = battleY;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public Fraction getFraction() {
        return fraction;
    }
}
