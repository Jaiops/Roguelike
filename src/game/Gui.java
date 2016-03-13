package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Johan on 2016-02-17.
 */
public class Gui implements KeyListener {
    JFrame frame;
    GamePanel game;
    public Gui(){

    }
    public void createFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = new GamePanel();
        game.setSize(new Dimension(800,600));
        frame.setSize(new Dimension(800,600));
        frame.addKeyListener(this);
        frame.add(game);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
