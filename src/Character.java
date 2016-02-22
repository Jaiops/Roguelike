import java.awt.*;

/**
 * Created by Johan on 2016-02-16.
 */

public class Character {

    int damage;
    Position pos;
    int maxHealth;
    int currentHealth;
    Alignment alignment;
    String name;

    public Character(Position pos,String name,int maxHealth) {
        this.pos = pos;
        this.name = name;
        this.maxHealth = maxHealth;
    }

    public void setPos(Position pos) {
        this.pos = pos;
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

    public void takeDamage(int damage){
        this.currentHealth -= damage;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        if(currentHealth>maxHealth){
            currentHealth = maxHealth;
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void move(int x, int y,Map map){
        Tile[][] tiles = map.getTiles();
        int newX = pos.getX() +x;
        int newY = pos.getY()+y;
        Character occupant;
        if(tiles[newY][newX].hasOccupant()){
            occupant = tiles[newY][newX].getOccupant();
            System.out.println(this.getName() + " Attacks " + occupant.getName());
            occupant.takeDamage(this.getDamage());
            if(occupant.getCurrentHealth()<0){
                map.kill(occupant);
            }
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
