package game;

import items.Item;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Map {
    public int index;
    private Tile[][] tiles;
    private CopyOnWriteArrayList<Monster> monsters;
    private CopyOnWriteArrayList<Item> items;


    public Map(int index,Tile[][] tiles) {
        this.index = index;
        this.tiles = tiles;
        monsters = new CopyOnWriteArrayList<>();
        items = new CopyOnWriteArrayList<>();
    }

    public Map(){
        monsters = new CopyOnWriteArrayList<>();
        items = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<Monster> getMonsters() {
        return monsters;
    }

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public void addMonster(Monster m){
        monsters.add(m);
        tiles[m.getPos().getY()][m.getPos().getX()].setOccupant(m);
    }
    public void addItem(Item i, Position p){
        items.add(i);
        tiles[p.getY()][p.getX()].addItem(i);
    }
    public void removeMonster(Monster m){
        monsters.remove(m);
    }
    public void buildMap(){
        tiles = new Tile[15][15];
        for(int i = 0; i<15;i++){
            for(int j = 0; j<15;j++){

                if(i==0||j==0||i==14||j==14){
                    tiles[i][j] = new Tile(true);
                }else{
                    tiles[i][j] = new Tile(false);
                }
            }
        }
        tiles[4][2] = new Tile(true);
        tiles[4][3] = new Tile(true);
        tiles[4][4] = new Tile(true);


    }
    public void kill(Character c){
        tiles[c.getPos().getY()][c.getPos().getX()].setOccupant(null);
        monsters.remove(c);
    }
    public Position getFreePosition(){
        Random r = new Random();
        while(true){
            Position p = new Position(r.nextInt(tiles.length),
                    r.nextInt(tiles.length));
            if(!tiles[p.getY()][p.getX()].isBlocking() &&!tiles[p.getY()][p.getX()].hasOccupant() ){
                return p;
            }
        }
    }
}
