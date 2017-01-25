package entities.creatures;

import game.Handler;
import graphic.Animation;
import graphic.assets.Assets;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Артем on 10.01.2017.
 */
public class Player extends Creature {

    private BufferedImage currentImage;
    private PlayerSate currentState;
    private Animation animDown, animUp, animLeft, animRight;
    //private MovingHandler movingHandler;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        bounds.x = 24;
        bounds.y = 24;
        bounds.width = 32;
        bounds.height = 32;
        setNewState(PlayerSate.watchingBottom);

        animRight = new Animation(500, Assets.PlayerAssets.stepOneRight, Assets.PlayerAssets.stepTwoRight);
        animLeft = new Animation(500, Assets.PlayerAssets.stepOneLeft, Assets.PlayerAssets.stepTwoLeft);
        animUp = new Animation(500, Assets.PlayerAssets.stepOneTop, Assets.PlayerAssets.stepTwoTop);
        animDown = new Animation(500, Assets.PlayerAssets.stepOneBottom, Assets.PlayerAssets.stepTwoBottom);
        //movingHandler = new MovingHandler();
        //Thread thread = new Thread(movingHandler);
        //thread.setDaemon(true);
        //thread.start();
    }

    @Override
    public void tick() {
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
            animUp.tick();
            setNewState(PlayerSate.movingTop);
            return;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
            animDown.tick();
            setNewState(PlayerSate.movingBottom);
            return;
        }
        if (handler.getKeyManager().right) {
            xMove = +speed;
            animRight.tick();
            setNewState(PlayerSate.movingRight);
            return;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            animLeft.tick();
            setNewState(PlayerSate.movingLeft);
            return;
        }

        //stop moving
        if (xMove==0 && currentState == PlayerSate.movingLeft)
            setNewState(PlayerSate.watchingLeft);
        else if (xMove==0 && currentState == PlayerSate.movingRight)
            setNewState(PlayerSate.watchingRight);
        else if (yMove==0 && currentState == PlayerSate.movingBottom)
            setNewState(PlayerSate.watchingBottom);
        else if (yMove==0 && currentState == PlayerSate.movingTop)
            setNewState(PlayerSate.watchingTop);
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(currentImage, (int) (x- handler.getGameCamera().getXOffSet()),
                (int) (y- handler.getGameCamera().getYOffSet()), width, height, null);

        		/*g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);*/
    }

    private void checkCurrentAnimationFrame(){
        //watching = 1 frame
        //moving = animation frames
        switch (currentState) {
            case watchingBottom:
                currentImage = Assets.PlayerAssets.watchingBottom;
                break;
            case watchingLeft:
                currentImage = Assets.PlayerAssets.watchingLeft;
                break;
            case watchingRight:
                currentImage = Assets.PlayerAssets.watchingRight;
                break;
            case watchingTop:
                currentImage = Assets.PlayerAssets.watchingTop;
                break;
            case movingLeft:
                currentImage = animLeft.getCurrentFrame();
                break;
            case movingRight:
                currentImage= animRight.getCurrentFrame();
                break;
            case movingBottom:
                currentImage = animDown.getCurrentFrame();
                break;
            case movingTop:
                currentImage = animUp.getCurrentFrame();
                break;
        }
    }

    public void setNewState(PlayerSate newState) {
        if (newState!=currentState) {
            currentState = newState;
        }
        checkCurrentAnimationFrame();
    }

    private enum PlayerSate{
         movingBottom, watchingBottom, movingLeft, watchingLeft, movingRight, watchingRight, movingTop, watchingTop;
    }
}
