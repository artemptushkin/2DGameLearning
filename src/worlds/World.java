package worlds;

import entities.EntityManager;
import entities.creatures.Player;
import entities.statics.Tree;
import game.Handler;
import tiles.Tile;
import utils.Util;

import java.awt.*;

/**
 * Created by Артем on 10.01.2017.
 */
public class World {
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    private Handler handler;

    private EntityManager manager;

    //constant picture world
    public World(Handler handler, String path){
        manager = new EntityManager(handler, new Player(handler, 100,100));
        loadWorld(path);
        this.handler = handler;

        manager.getPlayer().setX(spawnX);
        manager.getPlayer().setY(spawnY);

        manager.addEntity(new Tree(handler, 100,250));
    }

    private void loadWorld(String path) {
        String file = Util.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Util.parseInt(tokens[0]);
        height = Util.parseInt(tokens[1]);
        spawnX = Util.parseInt(tokens[2]);
        spawnY = Util.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int y=0;y<height;y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y]=Util.parseInt(tokens[(x+y*width)+4]);
            }
        }
        /*TEST METHOD
        width = 5;
        height = 5;
        tiles = new int[width][height]; //width x height tiles world

        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                tiles[x][y] = 0; //0 - трава
            }
        }*/
    }

    public void tick(){
        manager.tick();
    }

    public void render(Graphics g){
        int xStart = (int) Math.max(0, handler.getGameCamera().getXOffSet() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getXOffSet()+ handler.getWidth())/Tile.TILE_WIDTH+1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getYOffSet() / Tile.TILE_HEIGHT);;
        int yEnd = (int) Math.min(width, (handler.getGameCamera().getYOffSet()+ handler.getWidth())/Tile.TILE_HEIGHT+1);;

        for (int y=yStart;y<yEnd;y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffSet()),
                        (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffSet()));
            }
        }

        manager.render(g);
        /*unefficient
        for (int y=0;y<height;y++){
            for (int x=0;x<width;x++){
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffSet()),
                        (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffSet()));
            }
        }*/
    }

    public Tile getTile(int x, int y){
        if (x<0 || y<0  || x>=width || y>=height)
            return Tile.grassTile;

        Tile t = Tile.tiles[tiles[x][y]];
        if (t==null)
            return Tile.dirtTile; //TODO put 'default' tile here

        return t;
    }

    public EntityManager getManager() {
        return manager;
    }
}
