import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Johan on 2016-03-11.
 */
public class InventoryScreen {
    int index;
    Character c;
    Map m;
    Game g;
    public InventoryScreen(Character c, Map m, Game g) {
        index = 0;
        this.c = c;
        this.m = m;
        this.g = g;
    }

    public int getIndex() {
        return index;
    }

    public void getAction(KeyEvent e) {


        switch (e.getKeyCode()) {
            case KeyEvent.VK_NUMPAD8:
                index--;
                if(index<0){
                    index =0;
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                index++;
                if(index>c.getInventory().size()-1){
                    index =c.getInventory().size()-1;
                }
                break;
            case KeyEvent.VK_D:
                m.getTiles()[c.getPos().getY()][c.getPos().getX()].addItem(c.getInventory().get(index));
                c.getInventory().remove(index);
                g.setIs();
                break;
            case KeyEvent.VK_ESCAPE:
                g.setIs();
                break;
        }
    }
}
