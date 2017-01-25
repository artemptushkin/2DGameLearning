package entities;

import entities.creatures.Player;
import game.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Артем on 13.01.2017.
 */
public class EntityManager {
    private Handler handler;

    private Player player;

    private ArrayList<Entity> entities;
    private Comparator<Entity> comparator ;

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        entities.add(player);

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                if (o1.getY()<o2.getY())
                    return -1;

                return 1;
            }
        };
    }

    public void tick(){
        for (int i=0;i<entities.size();i++){
            entities.get(i).tick();
        }

        entities.sort(comparator);
    }

    public Handler getHandler() {
        return handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void render(Graphics g){
        for (int i=0;i<entities.size();i++){
            entities.get(i).render(g);
        }
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
