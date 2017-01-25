package entities.creatures;

import entities.Entity;
import game.Game;
import game.Handler;
import tiles.Tile;

import java.awt.*;

/**
 * Created by Артем on 10.01.2017.
 */
public abstract class Creature extends Entity {

    protected static final int DEFAULT_HEALTH = 10;
    protected static final float DEFAULT_SPEED = 4f;
    protected static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;
    protected float xMove, yMove;
    protected int battleX, battleY;
    private boolean rotateImage = false;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove=0;
        yMove=0;
    }

    public void moveX(){
        if (xMove>0){ //moving right
            int tx = (int) (x + xMove + bounds.x + bounds.width)/ Tile.TILE_WIDTH;

            if (!collisionWithTile(tx, (int)(y + bounds.y)/Tile.TILE_HEIGHT ) &&
                    !collisionWithTile(tx, (int) (y+ bounds.y + bounds.height)/Tile.TILE_HEIGHT)){
                x +=xMove;
            }else {
                x = tx*Tile.TILE_WIDTH - bounds.x - bounds.width-1;
            }
        }else if (xMove<0){ //moving left
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            }else {
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
            }
        }
    }
    public void moveY(){
        if(yMove < 0){//Up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }else{
                y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }

        }else if(yMove > 0){//Down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }else{
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
            }

        }
    }

    protected boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public void move(){
        if(!checkEntityCollision(xMove, 0f))
            moveX();
        if (!checkEntityCollision(0f, yMove))
            moveY();
    }

    //battle getters, setters
    public boolean isRotateImage() {
        return rotateImage;
    }

    public void setRotateImage(boolean rotateImage) {
        this.rotateImage = rotateImage;
    }

    /*public int getBattleX() {
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
    }*/
}
