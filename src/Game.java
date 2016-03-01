import java.awt.event.KeyEvent;

public class Game {
    private boolean isPlaying;
    private Character c;
    private Map[] m;
    private float turn;
    private int currentMap;
    public Game(){
        currentMap = 0;
        m = new Map[2];
        m[0] = new Map();
        m[1] = new Map();
        m[0].generateRoomsMap();
        m[1].buildMap();

        c = new Character(m[0].getFreePosition(),"Player",30);
        c.setAlignment(Alignment.GOOD);
        c.setDamage(1);
        isPlaying = true;

        turn = 0;
    }

    public Character getC() {
        return c;
    }

    public Map getM() {
        return m[currentMap];
    }

    public float getTurn() {
        return turn;
    }

    public void getAction(KeyEvent e){


        switch (e.getKeyCode()){
            case KeyEvent.VK_NUMPAD4:
                c.move(-1,0,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD8:
                c.move(0,-1,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD2:
                c.move(0,1,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD6:
                c.move(1,0,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD7:
                c.move(-1,-1,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD9:
                c.move(1,-1,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD1:
                c.move(-1,1,m[currentMap]);
                break;
            case KeyEvent.VK_NUMPAD3:
                c.move(1,1,m[currentMap]);
                break;

            case KeyEvent.VK_D:
                Monster mon = new Monster(new Position(4,5),new StandardEvilAi(),"Monster",10);
                mon.setAlignment(Alignment.EVIL);
                mon.setDamage(5);
                m[currentMap].addMonster(mon);
                m[currentMap].getTiles()[5][4].setOccupant(mon);

                break;
            case KeyEvent.VK_S:
                Monster rat = new Monster(new Position(7,5),new StandardGoodAi(),"rat",10);
                rat.setAlignment(Alignment.GOOD);
                rat.setDamage(5);
                m[currentMap].addMonster(rat);
                m[currentMap].getTiles()[5][7].setOccupant(rat);


        }
        for(Monster mon: m[currentMap].getMonsters()){
            if(mon.isAlive()){

                mon.takeTurn(m[currentMap]);
            }
        }
        /*
        for (Monster mon : m.getMonsters()){
            if(mon.getCurrentHealth()<0){
                m.getTiles()[mon.getPos().getY()][mon.getPos().getX()].setOccupant(null);
                m.removeMonster(mon);
            }
        }*/

    }
}
