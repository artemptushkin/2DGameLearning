package tiles;

import graphic.assets.Assets;

/**
 * Created by Артем on 10.01.2017.
 */
public class RockTile extends Tile {
    public RockTile(int id) {
        super(Assets.stone, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
