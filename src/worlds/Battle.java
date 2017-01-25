package worlds;

import entities.BattleEntityManager;
import entities.battle_mobs.BattleUnit;
import game.Handler;
import tiles.Tile;
import utils.Util;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Артем on 13.01.2017.
 */
public class Battle {
    private int width, height;
    private int[][] tiles;
    private Handler handler;
    private BattleEntityManager battleManager;
    private boolean creatureIsPressed;
    private BattleUnit pressedBattleUnit;
    private int movingWidth, movingHeight;
    private int pressedX, pressedY;

    public Battle(Handler handler, String path){
        this.handler = handler;
        loadWorld(path);
        creatureIsPressed = false;
        pressedBattleUnit = null;
        battleManager = new BattleEntityManager(handler);
    }

    public void tick(){
        battleManager.tick();
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

        battleManager.render(g);
    }

    //x and y are int
    public Tile getTile(int x, int y){
        if (x<0 || y<0  || x>=width || y>=height)
            return Tile.battleGrassTile;

        Tile t;

        //если выбран юнит, ячейка находится в зоне окрашивания и юнит еще не ходил, то окрашиваем его мув зону
        if(creatureIsPressed && checkMovingZone(x, y) && !pressedBattleUnit.isMovedThisTurn()) {
            t = Tile.tiles[5]; //light
        }else
            t = Tile.tiles[tiles[x][y]];

        if (t==null)
            return Tile.dirtTile; //TODO put 'default' tile here

        return t;
    }

    private boolean checkMovingZone(int x, int y){
        if (pressedBattleUnit ==null)
            return false;

        //если нельзя ходить
        if (Tile.tiles[tiles[x][y]].isSolid())
            return false;

        movingWidth = pressedBattleUnit.getSpeed();
        movingHeight = pressedBattleUnit.getSpeed();

        int creatureX = pressedBattleUnit.getBattleX();
        int creatureY = pressedBattleUnit.getBattleY();

        //проверяем координаты по x и по y
        if (((x <= creatureX + movingWidth && x >= creatureX - movingWidth) && (y == creatureY)) ||
                ((y <= creatureY + movingHeight && y >= creatureY - movingHeight) && (x == creatureX)))
            return true;

        return false;
    }

    private void loadWorld(String path) {
        String file = Util.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Util.parseInt(tokens[0]);
        height = Util.parseInt(tokens[1]);

        tiles = new int[width][height];
        for (int y=0;y<height;y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y]=Util.parseInt(tokens[(x+y*width)+4]);
            }
        }
    }

    public void pressPoint(int x, int y) {

        pressedX = (int) (x - handler.getGameCamera().getXOffSet())/Tile.TILE_WIDTH;
        pressedY = (int) (y - handler.getGameCamera().getXOffSet())/Tile.TILE_HEIGHT;

        ArrayList<BattleUnit> list = new ArrayList();
        list.addAll(battleManager.getEnemyEntity());
        list.addAll(battleManager.getPlayerEntity());

        for (BattleUnit battleUnit:list){
            if (battleUnit.getBattleY()==pressedY && battleUnit.getBattleX()==pressedX) {
                if (battleUnit==pressedBattleUnit) {// если нашли того, на которого уже нажимали, то отжимаем
                    unPressPoint();
                    return;
                }
                else {
                    pressedBattleUnit = battleUnit;
                    creatureIsPressed = true;
                }
                return; //нажали на какой-то юнит, больше ничего делать не надо
            }
        }

        if (pressedBattleUnit!=null)
            moveUnit(); //двигаем юнит, если есть кого двигать
        unPressPoint(); //не нашли нажатый юнит - отжимаем
    }

    private void moveUnit() {

        if (pressedBattleUnit.isMovedThisTurn())
            return;

        //если мы не в той зоне
        if (!checkMovingZone(pressedX, pressedY) ||
                (pressedBattleUnit.getBattleX()==pressedX && pressedBattleUnit.getBattleY()==pressedY) )
            return;

        pressedBattleUnit.setBattleX(pressedX);
        pressedBattleUnit.setBattleY(pressedY);
        pressedBattleUnit.setMovedThisTurn(true); //поменять, чтобы ходить часто за один ход
    }

    public void unPressPoint(){
        if (!creatureIsPressed)
            return;

        pressedBattleUnit = null;
        creatureIsPressed = false;
    }

    //getters & setters

    public BattleEntityManager getBattleManager() {
        return battleManager;
    }
}
