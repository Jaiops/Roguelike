package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    private boolean isPlaying;
    private Character c;
    private Map[] m;
    private float turn;
    public int currentMap;
    private InventoryScreen is;
    public Game(){


        currentMap = 0;
        m = MapGenerator.generateMultipleMap(4);

        c = new Character(m[currentMap].getFreePosition(),"Player",1000);
        m[0].getTiles()[c.getPos().getY()][c.getPos().getX()].setOccupant(c);

        c.setSprite(1,31);
        c.setSpeed(1.0);
        c.setAlignment(Alignment.GOOD);
        c.setDamage(10);
        isPlaying = true;
        is = null;
        turn = 0;
    }

    public Character getC() {
        return c;
    }

    public void setIs() {
        this.is = null;
    }

    public InventoryScreen getIs() {
        return is;
    }

    public Map getM() {
        return m[currentMap];
    }

    public float getTurn() {
        return turn;
    }

    public void getAction(KeyEvent e){


        if(is != null){
            is.getAction(e);
        }
        else{
            double lastTurn = c.getTurn();

            switch (e.getKeyCode()){
                case KeyEvent.VK_NUMPAD4:
                    c.moveOrAttack(-1, 0, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD8:
                    c.moveOrAttack(0, -1, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD2:
                    c.moveOrAttack(0, 1, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD6:
                    c.moveOrAttack(1, 0, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD7:
                    c.moveOrAttack(-1, -1, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD9:
                    c.moveOrAttack(1, -1, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD1:
                    c.moveOrAttack(-1, 1, m[currentMap]);
                    break;
                case KeyEvent.VK_NUMPAD3:
                    c.moveOrAttack(1, 1, m[currentMap]);
                    break;
                case KeyEvent.VK_Z:
                    Position p = c.getPos();
                    Tile t = m[currentMap].getTiles()[p.getY()][p.getX()];
                    if(t.getClass() == Staircase.class){

                        Staircase stair = (Staircase)t;
                        currentMap = stair.m.index;
                        stair.use(c);
                        for(Monster mon : m[currentMap].getMonsters()){
                            mon.setTurn(c.getTurn());
                        }
                    }
                    break;
                case KeyEvent.VK_A:
//                    Item i = new Potion();
//                    m[currentMap].getTiles()[3][3].addItem(i);
                    break;
                case KeyEvent.VK_G:
                    c.getInventory().add(m[currentMap].getTiles()[c.getPos().getY()][c.getPos().getX()].getItems().get(0));
                    m[currentMap].getTiles()[c.getPos().getY()][c.getPos().getX()].getItems().remove(0);
                    break;
                case KeyEvent.VK_I:
                    is = new InventoryScreen(c,m[currentMap],this);
                    break;
//                case KeyEvent.VK_D:
//                    Monster mon = new Monster(new Position(4,5),new StandardEvilAi(),"Goblin",10);
//                    mon.setAlignment(Alignment.EVIL);
//                    mon.setDamage(5);
//                    m[currentMap].addMonster(mon);
//                    m[currentMap].getTiles()[5][4].setOccupant(mon);
//                    break;
//                case KeyEvent.VK_S:
//                    Monster rat = new Monster(new Position(7,5),new StandardGoodAi(),"rat",10);
//                    rat.setAlignment(Alignment.GOOD);
//                    rat.setDamage(5);
//                    m[currentMap].addMonster(rat);
//                    m[currentMap].getTiles()[5][7].setOccupant(rat);
//                    break;


            }
            double currentTurn = c.getTurn();
            ArrayList<Buff> outlastedBuffs = new ArrayList<>();

            for(Buff b : c.getBuffs()){
                if(b.getTurns()>0){
                    b.decrementTurn(currentTurn - lastTurn);
                    while(b.getTurns() <= b.getNextTick()){

                        b.update(c);
                        b.setNextTick();
                    }
                }else{
                    outlastedBuffs.add(b);
                }

            }
            c.removeBuff(outlastedBuffs);

        }
        for(Monster mon: m[currentMap].getMonsters()){
            if(mon.isAlive()){
                double lastTurn = mon.getTurn();
                ArrayList<Buff> outlastedBuffs = new ArrayList<>();

                while (mon.getTurn()< c.getTurn() && c.isAlive()){

                    mon.takeTurn(m[currentMap]);
                }
                double currentTurn = mon.getTurn();

                for(Buff b : mon.getBuffs()){
                    if(b.getTurns()>0){
                        b.decrementTurn(currentTurn - lastTurn);
                        while(b.getTurns() <= b.getNextTick()){

                            b.update(mon);
                            b.setNextTick();
                        }
                    }else{
                        outlastedBuffs.add(b);
                    }
                }
                mon.removeBuff(outlastedBuffs);
            }
        }


    }
}
