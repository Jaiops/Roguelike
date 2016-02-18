import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-17.
 */
public class GamePanel extends JPanel{
    Game game;
    FieldOfView fov;
    public GamePanel(){
        game=new Game();
        fov = new FieldOfView();
    }

    public synchronized void keyPressed(KeyEvent e) {
        game.getAction(e);
        repaint();
    }



    public synchronized void draw(Graphics g){
        Map m = game.getM();
        Tile[][] tiles = m.getTiles();
        ArrayList<Position> fovPos =fov.calculateFov(m, game.getC().getPos());
        for(int rows = 0;rows<tiles.length;rows++) {
            for (int columns = 0; columns < tiles.length; columns++) {

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
        ArrayList<Monster> monsters = m.getMonsters();
        for(Monster mon : monsters){
            g.setColor(Color.magenta);
            g.drawOval(mon.getPos().getX()*32,mon.getPos().getY()*32,32,32);
        }
        /*ArrayList<Position> linePos = fov.calculateLine(game.getC().getPos(), new Position(6, 5));


        for(Position p : linePos){
            System.out.println("POSX :"+p.getX()+" POSY: "+p.getY());
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX() * 32, p.getY() * 32, 32, 32);
        }*/
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
