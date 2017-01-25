package entities.statics;

import game.Handler;
import graphic.assets.Assets;
import tiles.Tile;

import java.awt.*;

/**
 * Created by Артем on 13.01.2017.
 */
public class Tree extends StaticEntity {

    public Tree(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT*2);

        bounds.x = 10;
        bounds.y = (int) (height/1.5f);
        bounds.width = width - 20;
        bounds.height = (int)( height- height/1.5f);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.tree, (int) (x- handler.getGameCamera().getXOffSet()), (int) (y- handler.getGameCamera().getYOffSet()), width, height, null);

        /*g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getXOffSet()),
				(int) (y + bounds.y - handler.getGameCamera().getYOffSet()),
				bounds.width, bounds.height);*/
    }
}
