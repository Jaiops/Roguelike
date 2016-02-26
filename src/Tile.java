import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Tile {
    boolean seen;
    ArrayList<Item> items;
    Character occupant;
    Boolean blocking;

    public Tile(Boolean blocking) {
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

    public ArrayList<Item> getItems() {
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
