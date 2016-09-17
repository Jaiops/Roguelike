package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private boolean isPlaying;
    private Character c;
    private Map[] m;

    //eric
    private HashMap entities;

    private float turn;
    public static int currentMap;
    private InventoryScreen is;

    public Game() {


        currentMap = 0;
        m = MapGenerator.generateMultipleMap(4);

        c = new Character(m[currentMap].getFreePosition(), "Player", 1000);
        m[0].getTiles()[c.getPos().getY()][c.getPos().getX()].setOccupant(c);

        c.setSprite(1, 31);
        c.setSpeed(1.0);
        c.setAlignment(Alignment.GOOD);
        c.setDamage(10);
        isPlaying = true;
        is = null;
        turn = 0;

        //eric
        entities = new HashMap<Position, Character>();
        entities.put(c.getPos(), c);
        for (Monster mon : m[currentMap].getMonsters()) {

            entities.put(mon.getPos(), mon);
        }

    }

    public Character getC() {
        return c;
    }

    public void setIs() {
        this.is = null;
    }

    public static int getCurrentMapLevel() {
        return currentMap;
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

    public void getAction(KeyEvent e) {


        if (is != null) {
            is.getAction(e);
        } else {
            double lastTurn = c.getTurn();

            switch (e.getKeyCode()) {
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
                    if (t.getClass() == Staircase.class) {

                        Staircase stair = (Staircase) t;
                        currentMap = stair.m.index;
                        stair.use(c);
                        for (Monster mon : m[currentMap].getMonsters()) {

                            mon.setTurn(c.getTurn());
                        }
                    }
                    break;
                case KeyEvent.VK_G:
                    try {
                        c.getInventory().add(m[currentMap].getTiles()[c.getPos().getY()][c.getPos().getX()].getItems().get(0));
                        m[currentMap].getTiles()[c.getPos().getY()][c.getPos().getX()].getItems().remove(0);

                    } catch (IndexOutOfBoundsException e1) {
                        MessageLogger.getInstance().logMessage("Nothing to pickup");
                    }

                    break;
                case KeyEvent.VK_I:
                    is = new InventoryScreen(c, m[currentMap], this);
                    break;
                default:
                    break;


            }
            double currentTurn = c.getTurn();
            ArrayList<Buff> outlastedBuffs = new ArrayList<>();

            for (Buff b : c.getBuffs()) {
                if (b.getTurns() > 0) {
                    b.decrementTurn(currentTurn - lastTurn);
                    while (b.getTurns() <= b.getNextTick()) {

                        b.update(c);
                        b.setNextTick();
                    }
                } else {
                    outlastedBuffs.add(b);
                }

            }
            c.removeBuff(outlastedBuffs);

        }
        for (Monster mon : m[currentMap].getMonsters()) {
            if (mon.isAlive()) {
                double lastTurn = mon.getTurn();
                ArrayList<Buff> outlastedBuffs = new ArrayList<>();

                while (mon.getTurn() < c.getTurn() && c.isAlive()) {

                    mon.takeTurn(m[currentMap]);
                }
                double currentTurn = mon.getTurn();

                for (Buff b : mon.getBuffs()) {
                    if (b.getTurns() > 0) {
                        b.decrementTurn(currentTurn - lastTurn);
                        while (b.getTurns() <= b.getNextTick()) {

                            b.update(mon);
                            b.setNextTick();
                        }
                    } else {
                        outlastedBuffs.add(b);
                    }
                }
                mon.removeBuff(outlastedBuffs);
            }
        }


    }
}
