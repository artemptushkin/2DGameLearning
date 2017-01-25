package tiles;

import graphic.assets.Assets;

import java.awt.image.BufferedImage;

/**
 * Created by Артем on 17.01.2017.
 */
public class LightUpMovingTile extends Tile {
    public LightUpMovingTile(int id) {
        super(Assets.battleGrassToMove, id);
    }
}
