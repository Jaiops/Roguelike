package game;

import items.Item;
import items.ItemFactory;

import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */

public class Character {
    //eric
    private int experience;
    private int experiencePerlevel = 100;
    private int level = 1;
    private int expyeild = 36;
    private int regenrate = 20;
    private int regenamount = 1;


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


    public Character(Position pos, String name, int maxHealth) {

        this.pos = pos;
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.alive = true;
        inventory = new ArrayList<>();
        buffs = new ArrayList<>();
        turn = 0;
        //eric
        inventory.add(ItemFactory.healthPotion());


    }

    public int getDefense() {
        return defense;
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

    //eric

    public int getLevel() {
        return this.level;
    }

    public int getExperience() {
        return experience;
    }

    public int getExperienceNeeded() {
        return experiencePerlevel * level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExpYield() {
        return level * expyeild;
    }

    public void setExpYield(int xp) {
        System.out.println("Gained " + xp + " XP");
        this.experience += xp;
        if (this.experience > experiencePerlevel * level - 1) {
            experience = experience - experiencePerlevel * level;
            this.level++;
            maxHealth += 150;
            modifyHealth(maxHealth);
            modifyDamage(5);
            modifyDefense(2);

            MessageLogger.getInstance().logMessage("Leveled up to level " + level +
                    "and gained: 150 hp, 5 dmg and 2 def ," +
                    "for next level: " + experiencePerlevel * level);
            //notify level up
        }

    }

    public double getTurn() {
        return turn;
    }

    public void increaseTurn(double inc) {
        turn += inc;
        if (alignment == Alignment.GOOD) {
            if (turn % regenrate == 0) {
                modifyHealth(regenamount);
            }
        }
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

    public void setAlignment(Alignment al) {
        this.alignment = al;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage) {
        modifyHealth(-(damage - defense));

        if (currentHealth < 0) {
            alive = false;
        }
    }

    //Depricated
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        if (currentHealth > maxHealth) {
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

    public void moveOrAttack(int x, int y, Map map) {

        Tile[][] tiles = map.getTiles();
        int newX = pos.getX() + x;
        int newY = pos.getY() + y;
        if (tiles[newY][newX].hasOccupant()) {

            attack(map, tiles, newX, newY);
        } else if (!tiles[newY][newX].isBlocking()) {
            move(tiles, newX, newY);
        }
    }

    private void move(Tile[][] tiles, int newX, int newY) {
        tiles[pos.getY()][pos.getX()].setOccupant(null);
        this.pos = new Position(newX, newY);
        tiles[newY][newX].setOccupant(this);
        increaseTurn(1.0 * speed);
        if (tiles[newY][newX].getItems().size() > 0) {
            for (Item i : tiles[newY][newX].getItems()) {

                MessageLogger.getInstance().logMessage("You See " + i.name);
            }
        }
    }

    private void attack(Map map, Tile[][] tiles, int newX, int newY) {
        Character occupant;
        occupant = tiles[newY][newX].getOccupant();
        if (occupant.getAlignment() != this.getAlignment()) {
            MessageLogger.getInstance().logMessage(this.getName() + " Attacks " + occupant.getName() +
                    " for " + this.damage + " damage");
            occupant.takeDamage(this.getDamage());
            if (mainHand != null) {
                increaseTurn(mainHand.itemSpeed * speed);
            } else {
                increaseTurn(1.0 * speed);
            }
            if (occupant.getCurrentHealth() <= 0) {
                if (this.alignment == Alignment.GOOD) {
                    setExpYield(occupant.getExpYield());
                }
                map.kill(occupant);
            }
        } else {
            increaseTurn(1);
        }


    }

    public void modifyDamage(int damage) {
        this.damage += damage;
    }

    public void modifyDefense(int defense) {
        this.defense += defense;
    }

    public void modifyHealth(int health) {
        if (this.currentHealth + health > maxHealth) {
            this.currentHealth = maxHealth;
        } else {
            this.currentHealth += health;
        }
    }

    public void modifySpeed(double speed) {
        this.speed += speed;
    }

    //eric
    public void modifyRegenAmount(int amount) {
        this.regenamount += amount;
    }

    //eric
    public void modifyRegenRate(int rate) {
        this.regenrate -= rate;
    }


    public Position getSprite() {
        return spritePosition;
    }

    public Position getPos() {
        return pos;
    }

    public void setSprite(int x, int y) {
        spritePosition = new Position(x, y);
    }

    public void addBuff(Buff buff) {
        buff.startEffect(this);
        buffs.add(buff);
    }

    public void removeBuff(ArrayList<Buff> buff) {
        for (Buff b : buff) {
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

    public void equip(Item i) {
        switch (i.slot) {
            case ARMOR:
                if (getArmor() != null || getArmor() == i) {
                    unequip(getArmor());
                }
                setArmor(i);
                MessageLogger.getInstance().logMessage(
                        "Equipped " + i.name
                );
                break;
            case MAIN_HAND:
                if (getMainHand() != null || getMainHand() == i) {
                    unequip(getMainHand());
                }
                setMainHand(i);
                MessageLogger.getInstance().logMessage(
                        "Wielded " + i.name
                );

                break;
            case NULL:
                MessageLogger.getInstance().logMessage(
                        "Used " + i.name
                );
                removeInventoryItem(i);
                break;

        }
        modifyDamage(i.damage);
        modifyDefense(i.defense);
        modifyHealth(i.health);
        modifySpeed(i.globalSpeed);
        if (i.buff != null) {

            addBuff(i.buff);
        }

    }

    public void unequip(Item i) {

        modifyDamage(-i.damage);
        modifyDefense(-i.defense);
        modifyHealth(-i.health);
        modifySpeed(-i.globalSpeed);
    }
}
