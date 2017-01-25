package tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Артем on 10.01.2017.
 */
public class Tile {

    //STATIC STUFF

    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile battleGrassTile = new GrassTile(4);
    public static Tile battleGrassToMoveTile = new LightUpMovingTile(5);

    //CLASS STUFF
    protected BufferedImage texture;
    protected final int id;
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;
        tiles[id] = this;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public int getId() {
        return id;
    }

    public void tick(){

    }

    public boolean isSolid(){
        return false;
    }

    //takes pixel coordinates
    public void render(Graphics graphics, int x, int y){
        graphics.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
