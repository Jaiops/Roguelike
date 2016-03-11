import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */

public class Character {

    private int damage;
    private Position pos;
    private int maxHealth;
    private int currentHealth;
    private Alignment alignment;
    private String name;
    private boolean alive;
    private ArrayList<Item> inventory;

    public Character(Position pos,String name,int maxHealth) {

        this.pos = pos;
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.alive = true;
        inventory = new ArrayList<>();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlignment(Alignment al){
        this.alignment = al;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage){
        this.currentHealth -= damage;
        if(currentHealth<0){
            alive = false;
        }
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
            System.out.println(this.getName() + " Attacks " + occupant.getName()+
                    " for "+this.damage+" damage");
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
