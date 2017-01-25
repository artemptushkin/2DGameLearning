package entities;

import game.Game;
import game.Handler;

import java.awt.*;

/**
 * Created by Артем on 10.01.2017.
 */
public abstract class Entity {
    protected float x, y;

    protected Handler handler;
    protected int width, height;
    protected Rectangle bounds;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(0, 0, width, height);
    }

    public Rectangle getCollisionBounds(float xOffSet, float yOffSet){
        return new Rectangle((int) (x+bounds.x+xOffSet), (int) (y+bounds.y+yOffSet), bounds.width, bounds.height);
    }

    public boolean checkEntityCollision(float xOffSet, float yOffSet){
        for (Entity e:handler.getWorld().getManager().getEntities()){
            if (e.equals(this))
                continue;

            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffSet, yOffSet)))
                return true;
        }

        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
