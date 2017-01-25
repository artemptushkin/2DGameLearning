package graphic;

import entities.BattleEntityManager;
import entities.TurnManager;
import states.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;


/**
 * Created by Артем on 09.01.2017.
 */
public class LevelCanvas extends Canvas {
    private int width, height;
    private int supportZoneHeight;
    private String name;

    private BufferStrategy bs;
    private JFrame frame;
    private SupportZone supportZone;
    private Graphics graphics;

    public JFrame getFrame() {
        return frame;
    }

    public LevelCanvas(String title, int width, int height) {
        this.name = title;
        supportZoneHeight = 100;
        this.width = width ;
        this.height = height;

        createCanvas();

        supportZone = new SupportZone();
    }

    private void createCanvas(){
        frame = new JFrame();

        frame.setSize(width, height + supportZoneHeight);
        frame.setResizable(false);
        frame.setTitle(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setFocusable(false);
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height+ supportZoneHeight));
        setMaximumSize(new Dimension(width, height));

        frame.add(this, BorderLayout.CENTER);

        frame.pack();
    }

    public class SupportZone extends JPanel{

        public SupportZone(){
            setVisible(true);
            setSize(width, supportZoneHeight);
            setPreferredSize(new Dimension(width, supportZoneHeight));
            JButton button = new JButton("next turn");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    State.getCurrentState().getHandler().getBattle().getBattleManager().requestNextTurn();
                }
            });
            add(button);
            frame.add(this, BorderLayout.SOUTH);
        }
    }

    public void render(){
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        graphics = bs.getDrawGraphics();

        graphics.clearRect(0, 0, width, height);

        if (State.getCurrentState()!=null)
            State.getCurrentState().render(graphics);

        graphics.dispose();
        bs.show();
    }

    //Getters & setters

    @Override
    public Graphics getGraphics() {
        return graphics;
    }
}
