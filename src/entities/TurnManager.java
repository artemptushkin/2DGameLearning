package entities;

import entities.battle_mobs.BattleUnit;
import game.Handler;
import states.State;

import java.awt.*;
import java.util.List;

/**
 * Created by Артем on 18.01.2017.
 */

//shows messages
public class TurnManager implements Runnable {
    public int turnNum;
    private Handler handler;
    private Thread thread;
    public volatile Boolean isWaitingRender;
    private boolean nextTurnFlag;
    private float fontSize;
    private volatile String strToShow;

    public TurnManager(Handler handler) {
        this.handler = handler;
        turnNum= 1;
        isWaitingRender = false;
        nextTurnFlag = false;

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {

        while (true){

            if (isWaitingRender) {
                    try {
                        thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isWaitingRender = false;

                    if (nextTurnFlag) {
                        handler.getBattle().getBattleManager().refreshMoves();
                        nextTurnFlag = false;
                    }
            }
        }
    }

    public void showNextTurnMessage() {
            strToShow = String.format("Turn %d", ++turnNum);
            fontSize = 60f;
            isWaitingRender = true;
            nextTurnFlag = true;
    }

    public void showMessage(List<BattleUnit> slowUnits) {
            StringBuilder resultString = new StringBuilder();

            if (!slowUnits.isEmpty())
                resultString.insert(0, "Units hasn't moved:\n").toString();

            for (BattleUnit unit : slowUnits) {
                resultString.append(String.format("%s, [%s]\n", unit.getClass().getSimpleName(), unit.getFraction()));
            }

            strToShow = resultString.toString();
            fontSize = 30f;
            isWaitingRender = true;
    }

    public void render(Graphics g) {
        g.setFont(g.getFont().deriveFont(fontSize));
        g.setColor(Color.WHITE);

        if (strToShow.contains("\n")){
            int x = (State.getCurrentState().getHandler().getGame().getWidth()) / 2-100;
            int y = (State.getCurrentState().getHandler().getGame().getHeight()) / 2 - g.getFontMetrics().getHeight()*2;

            for (String line : strToShow.split("\n")) {
                g.drawString(line, x, y +=g.getFontMetrics().getHeight());
            }
        }else
            g.drawString(strToShow,  (State.getCurrentState().getHandler().getGame().getWidth())/2-100,
                    (State.getCurrentState().getHandler().getGame().getHeight())/2-50); //TODO magic numbers
    }

}
