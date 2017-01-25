package game;

import entities.TurnManager;
import graphic.assets.Assets;
import graphic.GameCamera;
import graphic.LevelCanvas;
import graphic.assets.CuteAngelAssets;
import input.KeyManager;
import input.MouseManager;
import states.BattleSate;
import states.GameState;
import states.MenuState;
import states.State;
import worlds.Battle;

/**
 * Created by Артем on 09.01.2017.
 */
public class Game implements Runnable{

    private static final String name = "Turn based handler";
    private final int width = 640, height = width/*/ 12*9*/, scale = 3;
    private LevelCanvas levelCanvas;
    private boolean running;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Thread thread;
    private State gameState, menuState;
    private BattleSate battleState;
    private GameCamera gameCamera;
    private Handler handler;

    private int x=0,y=0;

    public Game() {
        init();
    }

    private void init(){
        handler = new Handler(this);

        levelCanvas = new LevelCanvas(name, width, height);
        keyManager = new KeyManager();
        mouseManager = new MouseManager(handler);

        levelCanvas.getFrame().addKeyListener(keyManager);
        //TODO with Frame
        /*levelCanvas.getFrame().addMouseListener(mouseManager);
        levelCanvas.getFrame().addMouseMotionListener(mouseManager);*/
        levelCanvas.addMouseListener(mouseManager);
        levelCanvas.addMouseMotionListener(mouseManager);

        Assets.init();
        CuteAngelAssets.init();

        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        battleState = new BattleSate(handler);
        levelCanvas.getFrame().setTitle("Battle mode");
        levelCanvas.createBufferStrategy(3);

        gameCamera = new GameCamera(this, 0, 0);

    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public BattleSate getBattleState() {
        return battleState;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public State getGameState() {
        return gameState;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public LevelCanvas getLevelCanvas() {
        return levelCanvas;
    }

    public synchronized void start() {
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        int frames = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                levelCanvas.render();
                frames++;
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                //System.out.printf("Ticks: %d, Frames: %d\n", ticks, frames);
                ticks = 0;
                timer = 0;
                frames = 0;
            }
        }

        stop();
    }

    private void tick() {
        keyManager.tick();

        if (State.getCurrentState()!=null)
            State.getCurrentState().tick();;
    }
}
