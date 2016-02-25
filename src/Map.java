import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Map {
    Tile[][] tiles;
    CopyOnWriteArrayList<Monster> monsters;

    public Map(){
        monsters = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public void addMonster(Monster m){
        monsters.add(m);
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
        tiles[4][2].blocking = true;
        tiles[4][3].blocking = true;
        tiles[4][4].blocking = true;


    }
    public void kill(Character c){
        tiles[c.getPos().getY()][c.getPos().getX()].setOccupant(null);
        monsters.remove(c);
    }
    public void generateMap(int map[][]){
        tiles = new Tile[map.length][map[0].length];
        for(int i = 0 ; i<map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == 0){
                    tiles[i][j] = new Tile(false);
                }else{
                    tiles[i][j] = new Tile(true);
                }
            }
        }
    }
}
