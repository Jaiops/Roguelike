import java.awt.*;

/**
 * Created by Johan on 2016-02-16.
 */

public class Character {
    Position pos;
    int maxHealth;
    int currentHealth;
    Alignment alignment;
    String name;

    public Character(Position pos,String name) {
        this.pos = pos;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAlignment(Alignment al){
        this.alignment = al;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        if(currentHealth>maxHealth){
            currentHealth = maxHealth;
        }
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void move(int x, int y,Map map){
        Tile[][] tiles = map.getTiles();
        int newX = pos.getX() +x;
        int newY = pos.getY()+y;
        if(tiles[newY][newX].hasOccupant()){
            System.out.println("Attack"+tiles[newY][newX].getOccupant().getName());
        }
        else if(!tiles[newY][newX].isBlocking()){
            tiles[pos.getY()][pos.getX()].setOccupant(null);
            this.pos = new Position(newX,newY);
            tiles[newY][newX].setOccupant(this);
        }

    }
    public Position getPos() {
        return pos;
    }
}
