package states;

import game.Handler;
import worlds.World;

import java.awt.*;

/**
 * Created by Артем on 10.01.2017.
 */
public class GameState extends State {
    //private Player player;
    private World world;


    public GameState(Handler handler) {
        super(handler);
        world = new World(handler, "res/worlds/world.txt");
        handler.setWorld(world);
        //player = new Player(handler, 100, 100);
    }

    @Override
    public void tick() {
        world.tick();
        //player.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        //player.render(g);
    }
}
