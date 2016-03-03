import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Tile {
    private boolean seen;
    private CopyOnWriteArrayList<Item> items;
    private Character occupant;
    private Boolean blocking;

    public Tile(Boolean blocking) {
        items = new CopyOnWriteArrayList<>();
        this.blocking = blocking;
        this.seen = false;
        occupant = null;
    }

    public void setSeen(){
        seen = true;
    }
    public boolean isSeen(){
       return this.seen;
    }
    public void addItem(Item i){
        items.add(i);
    }

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }

    public Character getOccupant() {
        return occupant;
    }

    public boolean hasOccupant(){
        return occupant != null;
    }
    public void setOccupant(Character occupant) {
        this.occupant = occupant;
    }

    public Boolean isBlocking() {
       /* if(!blocking && occupant!=null){
            return true;
        }*/
        return blocking;
    }
}
