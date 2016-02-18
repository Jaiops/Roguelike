import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class Map {
    Tile[][] tiles;
    ArrayList<Monster> monsters;

    public Map(){
        monsters = new ArrayList<>();
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public void addMonster(Monster m){
        monsters.add(m);
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
}
