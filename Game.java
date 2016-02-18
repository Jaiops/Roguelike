import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {
    boolean isPlaying;
    Character c;
    Map m;
    float turn;
    public Game(){
        m = new Map();
        m.buildMap();
        c = new Character(new Position(2,3),"Player");
        c.setAlignment(Alignment.GOOD);
        isPlaying = true;

        turn = 0;
    }

    public Character getC() {
        return c;
    }

    public Map getM() {
        return m;
    }

    public float getTurn() {
        return turn;
    }

    public void getAction(KeyEvent e){


        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                c.move(-1,0,m);
                break;
            case KeyEvent.VK_UP:
                c.move(0,-1,m);
                break;
            case KeyEvent.VK_DOWN:
                c.move(0,1,m);
                break;
            case KeyEvent.VK_RIGHT:
                c.move(1,0,m);
                break;
            case KeyEvent.VK_D:
                m.addMonster(new Monster(new Position(4,5),new StandardAi(),"Monster"));
                break;
            case KeyEvent.VK_S:
                Monster mon = new Monster(new Position(7,5),new StandardAi(),"rat");
                mon.setAlignment(Alignment.GOOD);
                m.addMonster(mon);

        }
        for(Monster mon: m.getMonsters()){
            mon.takeTurn(m);
        }

    }
}
