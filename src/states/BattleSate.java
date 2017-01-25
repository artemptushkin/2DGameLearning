package states;

import entities.TurnManager;
import game.Handler;
import worlds.Battle;

import java.awt.*;

/**
 * Created by Артем on 13.01.2017.
 */
public class BattleSate extends State {
    private Battle battle;
    private String battleTxtField = "res/worlds/battle1.txt";
    protected TurnManager turnManager;

    public BattleSate(Handler handler) {
        super(handler);
        State.setCurrentState(this);
        turnManager = new TurnManager(handler);

        battle = new Battle(handler, battleTxtField );
        turnManager = battle.getBattleManager().getTurnManager();
        handler.setBattle(battle);
    }

    @Override
    public void tick() {
        battle.tick();
    }

    @Override
    public void render(Graphics g) {
        battle.render(g);

        if (turnManager.isWaitingRender)
            turnManager.render(g);
    }

    public Battle getBattle() {
        return battle;
    }

}
