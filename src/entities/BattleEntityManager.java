package entities;

import entities.battle_mobs.AngelUnit;
import entities.battle_mobs.BattleUnit;
import entities.battle_mobs.HeroUnit;
import entities.fractions.Fraction;
import game.Handler;
import states.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 13.01.2017.
 */
public class BattleEntityManager {
    private Handler handler;
    private ArrayList<BattleUnit> playerEntity;
    private ArrayList<BattleUnit> enemyEntity;
    private TurnManager turnManager;

    public BattleEntityManager(Handler handler) {
        this.handler = handler;
        this.turnManager = new TurnManager(handler);

        playerEntity = new ArrayList<>();
        enemyEntity = new ArrayList<>();

        for (BattleUnit e:initPlayer())
            playerEntity.add(e);
        for (BattleUnit e:initEnemies())
            enemyEntity.add(e);

    }

    private BattleUnit[] initPlayer(){
        BattleUnit[] entities = new BattleUnit[2];
        HeroUnit heroUnit = new HeroUnit(Fraction.Player, 1, 1);

        AngelUnit angel = new AngelUnit(Fraction.Player, 2, 2);

        entities[0] = heroUnit;
        entities[1] = angel;

        return entities;
    }

    private BattleUnit[] initEnemies(){
        BattleUnit[] entities = new BattleUnit[2];
        AngelUnit cuteAngel1 = new AngelUnit(Fraction.Enemy, 7, 5);

        AngelUnit cuteAngel2 = new AngelUnit(Fraction.Enemy, 7, 6);

        entities[0] = cuteAngel1;
        entities[1] = cuteAngel2;

        return entities;
    }

    public ArrayList<BattleUnit> getPlayerEntity() {
        return playerEntity;
    }

    public ArrayList<BattleUnit> getEnemyEntity() {
        return enemyEntity;
    }

    public void addEntity(BattleUnit unit, Fraction fraction){
        if (fraction==Fraction.Player)
            playerEntity.add(unit);
        else
            enemyEntity.add(unit);
    }

    public void tick(){
        for (BattleUnit e:playerEntity)
            e.tick();
        for (BattleUnit e:enemyEntity)
            e.tick();
    }

    public void requestNextTurn(){
        if (checkUnitsForMove()) {
            turnManager.showNextTurnMessage();
        }
    }

    public void refreshMoves(){
        for (BattleUnit unit:playerEntity){
            unit.setMovedThisTurn(false);
        }
        for (BattleUnit unit:enemyEntity){
            unit.setMovedThisTurn(false);
        }

        handler.getBattle().unPressPoint(); //отжимаем, чтоб сначала хода ничего не святилось
    }

    private boolean checkUnitsForMove() {
        //check for a left turns
        boolean allMoved = true;

        List<BattleUnit> slowUnits = new ArrayList<>();

        for (BattleUnit unit:playerEntity){
            if (!unit.isMovedThisTurn()){
                slowUnits.add(unit);
                allMoved = false;
            }
        }
        for (BattleUnit unit:enemyEntity){
            if (!unit.isMovedThisTurn()){
                slowUnits.add(unit);
                allMoved = false;
            }
        }

        if (!allMoved)
            turnManager.showMessage(slowUnits);

        return allMoved;
    }

    public void render(Graphics g){
        for (BattleUnit e:playerEntity)
            e.render(g);
        for (BattleUnit e:enemyEntity)
            e.render(g);
    }

    //getters & setters

    public TurnManager getTurnManager() {
        return turnManager;
    }
}
