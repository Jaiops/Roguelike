import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Map {
    private Tile[][] tiles;
    private CopyOnWriteArrayList<Monster> monsters;

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
        tiles[4][2] = new Tile(true);
        tiles[4][3] = new Tile(true);
        tiles[4][4] = new Tile(true);


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
    public void generateRoomsMap(){
        Random r = new Random();
        tiles = new Tile[50][50];
        for(int i = 0 ; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(true);
            }
        }
        int x = r.nextInt(5)+1;
        int y = r.nextInt(5)+1;
        int posY = r.nextInt(tiles.length-y);
        int posX = r.nextInt(tiles.length-x);
        for(int i = 0 ; i<y; i++) {
            for(int j = 0 ; j<x; j++) {
                tiles[posY+y][posX+x] = new Tile(false);
            }
        }


    }
    private int[][] generateRoom(){
        return null;
    }
}
