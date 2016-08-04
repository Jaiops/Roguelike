package game;

import items.Item;

import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */

public class Character {

    //STATS
    private int damage;
    private int defense;
    private int maxHealth;
    private int currentHealth;
    private double speed;
    private ArrayList<Buff> buffs;

    //SHIT
    private Position pos;
    private Alignment alignment;
    private String name;
    private boolean alive;
    private Position spritePosition;
    private double turn;

    //ITEMS AND EQUIPMENT
    private ArrayList<Item> inventory;
    private Item armor = null;
    private Item mainHand = null;


    public Character(Position pos,String name,int maxHealth) {

        this.pos = pos;
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.alive = true;
        inventory = new ArrayList<>();
        buffs = new ArrayList<>();
        turn = 0;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public Item getMainHand() {
        return mainHand;
    }

    public void setMainHand(Item mainHand) {
        this.mainHand = mainHand;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public double getTurn() {
        return turn;
    }
    public void increaseTurn(double inc){
        turn += inc;
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
        modifyHealth(-(damage-defense));

        if(currentHealth<0){
            alive = false;
        }
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        if(currentHealth>maxHealth){
            this.currentHealth = maxHealth;
        }
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
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

    public void moveOrAttack(int x, int y, Map map){

        Tile[][] tiles = map.getTiles();
        int newX = pos.getX() +x;
        int newY = pos.getY()+y;
        Character occupant;
        if(tiles[newY][newX].hasOccupant()){
            attack(map, tiles, newX, newY);
        }
        else if(!tiles[newY][newX].isBlocking()){
            move(tiles, newX, newY);
        }
    }

    private void move(Tile[][] tiles, int newX, int newY) {
        tiles[pos.getY()][pos.getX()].setOccupant(null);
        this.pos = new Position(newX,newY);
        tiles[newY][newX].setOccupant(this);
        increaseTurn(1.0*speed);
        if(tiles[newY][newX].getItems().size() >0){
            for(Item i : tiles[newY][newX].getItems()){

                MessageLogger.getInstance().logMessage("You See "+i.name);
            }
        }
    }

    private void attack(Map map, Tile[][] tiles, int newX, int newY) {
        Character occupant;
        occupant = tiles[newY][newX].getOccupant();
        if(occupant.getAlignment()!= this.getAlignment()){
            MessageLogger.getInstance().logMessage(this.getName() + " Attacks " + occupant.getName() +
                    " for " + this.damage + " damage");
            occupant.takeDamage(this.getDamage());
            if(mainHand != null){
                increaseTurn(mainHand.itemSpeed*speed);
            }
            else{
                increaseTurn(1.0 * speed);
            }
            if (occupant.getCurrentHealth() <= 0) {
                map.kill(occupant);
            }
        }
        else{
            increaseTurn(1);
        }


    }

    public void modifyDamage(int damage){
        this.damage +=  damage;
    }
    public void modifyDefense(int defense){
        this.defense +=  defense;
    }
    public void modifyHealth(int health){
        this.currentHealth += health;
    }
    public void modifySpeed(double speed){
        this.speed += speed;
    }



    public Position getSprite() {
        return spritePosition;
    }

    public Position getPos() {
        return pos;
    }

    public void setSprite(int x, int y) {
        spritePosition = new Position(x,y);
    }

    public void addBuff(Buff buff) {
        buff.startEffect(this);
        buffs.add(buff);
    }
    public void removeBuff(ArrayList<Buff> buff) {
        for(Buff b : buff){
            b.endEffect(this);
        }
        buffs.removeAll(buff);
    }

    public void removeInventoryItem(Item item) {
        inventory.remove(item);
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }
}
