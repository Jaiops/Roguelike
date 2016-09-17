package game;

import items.Item;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Tile {
    private boolean seen;
    private CopyOnWriteArrayList<Item> items;
    private Character occupant;
    private Boolean blocking;
    private Position spritePosition;

    public Tile(Boolean blocking, Position spritePosition) {
        this.spritePosition = spritePosition;
        items = new CopyOnWriteArrayList<>();
        this.blocking = blocking;
        this.seen = false;
        occupant = null;
    }

    public Position getSpritePosition() {
        return spritePosition;
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
//        if(blocking || occupant!=null){
//            return true;
//        }
        return blocking;
    }
}
