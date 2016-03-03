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
    private Game game;
    private FieldOfView fov;
    private Viewport vp;
    public GamePanel(){
        game=new Game();

        fov = new FieldOfView();
        try{
            vp = new Viewport(13,13,game.getM());

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public synchronized void keyPressed(KeyEvent e) {
        game.getAction(e);
        repaint();
    }

    private void drawItem(Graphics g, Item i){
        CopyOnWriteArrayList<Item> items = game.getM().getItems();

    }

    private void drawTiles(Graphics g){
        Map m = game.getM();
        Tile[][] tiles = m.getTiles();
        ArrayList<Position> fovPos =fov.calculateFov(m, game.getC().getPos());

        vp.setNewPos(game.getC().getPos());

        for(int rows = 0;rows<vp.getSizeY();rows++) {
            for (int columns = 0; columns < vp.getSizeX(); columns++) {
                int y = rows + vp.getCurrentY();
                int x = columns + vp.getCurrentX();
                if (tiles[y][x].isBlocking()) {
                    g.setColor(Color.black);
                }
                else{
                    Position p = new Position(x,y);
                    if(fovPos.contains(p)){

                        g.setColor(Color.white);
                        if(tiles[y][x].getItems().size()>0){
                            g.setColor(Color.orange);

                            g.fillRect(x * 32, y * 32,32,32);
                        }
                    }else{
                        g.setColor(Color.gray);

                    }
                }

                g.fillRect(columns * 32, rows * 32, 32, 32);
                g.setColor(Color.blue);
            }
        }
    }
    private void drawMonsters(Graphics g){
        CopyOnWriteArrayList<Monster> monsters = game.getM().getMonsters();
        for(Monster mon : monsters){
            if(vp.withinViewport(mon.getPos())){
                if(mon.getAlignment() == Alignment.EVIL){
                    g.setColor(Color.RED);
                }else {
                    g.setColor(Color.magenta);
                }
                g.fillOval( (mon.getPos().getX()-vp.getCurrentX())*32,
                        (mon.getPos().getY()-vp.getCurrentY()) *32,
                        32, 32);
                drawHealthBar(g,
                        (mon.getPos().getX()-vp.getCurrentX())*32,
                        (mon.getPos().getY()-vp.getCurrentY())*32,mon);

            }
        }
    }
    public synchronized void draw(Graphics g){
        drawTiles(g);
        drawMonsters(g);

        g.setColor(Color.blue);
        Character c = game.getC();
        g.fillOval((c.getPos().getX() - vp.getCurrentX()) * 32, (c.getPos().getY() - vp.getCurrentY()) * 32, 32, 32);
        g.drawString("Health: " + game.getC().getCurrentHealth() + "/" + game.getC
                ().getMaxHealth(), 500,500);

    }
    private void drawHealthBar(Graphics g,int x, int y,Character ch){

        float percentage = (float)ch.getCurrentHealth()/(float)ch
                .getMaxHealth();
        if(percentage>0.7){
            g.setColor(Color.green);
        }else if(percentage>0.4){
            g.setColor(Color.yellow);
        }else{
            g.setColor(Color.red);
        }
        g.fillRect(x+3,y+26,(int)(26*percentage),5);
        g.setColor(Color.black);
        g.drawRect(x + 3, y + 26,26,5);
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
        g.setColor(Color.black);
    }
}
