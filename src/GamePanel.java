import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-17.
 */
public class GamePanel extends JPanel{
    Game game;
    FieldOfView fov;
    Viewport vp;
    public GamePanel(){
        game=new Game();
        fov = new FieldOfView();
        try{
            vp = new Viewport(8,8,game.getM());

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }


    }

    public synchronized void keyPressed(KeyEvent e) {
        game.getAction(e);
        repaint();
    }



    public synchronized void draw(Graphics g){
        Map m = game.getM();
        Tile[][] tiles = m.getTiles();
        ArrayList<Position> fovPos =fov.calculateFov(m, game.getC().getPos());
        vp.setNewPos(game.getC().getPos());
        for(int rows = vp.getCurrentY();rows<vp.getSizeY();rows++) {
            for (int columns = vp.getCurrentX(); columns < vp.getSizeX(); columns++) {

                if (tiles[rows][columns].isBlocking()) {
                    g.setColor(Color.black);
                } else{
                    Position p = new Position(columns,rows);
                    if(fovPos.contains(p)){

                        g.setColor(Color.white);
                    }else{
                        g.setColor(Color.gray);

                    }
                }

                g.fillRect(columns * 32, rows * 32, 32, 32);
                g.setColor(Color.blue);
            }
        }
        CopyOnWriteArrayList<Monster> monsters = m.getMonsters();
        for(Monster mon : monsters){
            g.setColor(Color.magenta);
            g.drawOval(mon.getPos().getX()*32,mon.getPos().getY()*32,32,32);
        }

        g.setColor(Color.blue);
        Character c = game.getC();
        g.fillOval(c.getPos().getX() * 32, c.getPos().getY() * 32, 32, 32);


    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);

        g.setColor(Color.black);
    }
}
