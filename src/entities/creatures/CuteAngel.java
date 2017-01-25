package entities.creatures;

import game.Handler;
import graphic.assets.CuteAngelAssets;

import java.awt.*;

/**
 * Created by Артем on 13.01.2017.
 */
public class CuteAngel extends Creature {
    public CuteAngel(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(CuteAngelAssets.stand, (int) (x- handler.getGameCamera().getXOffSet()),
                    (int) (y- handler.getGameCamera().getYOffSet()), width, height, null);
    }
}
