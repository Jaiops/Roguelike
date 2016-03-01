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

    private class Room{

        int sizeX;
        int sizeY;
        int posY;
        int posX;
        protected Room(int minX, int minY, int maxX, int maxY){
            Random r = new Random();

            sizeX = r.nextInt(5)+1;
            sizeY = r.nextInt(5)+1;
            System.out.println(maxY + " "+minY);
            posY = r.nextInt(maxY)+minY;
            posX = r.nextInt(maxX)+minX;
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public int getPosY() {
            return posY;
        }

        public int getPosX() {
            return posX;
        }
    }
    public void generateRoomsMap(){
        tiles = new Tile[50][50];
        for(int i = 0 ; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(true);
            }
        }
        Room[][] r = new Room[3][3];
        for(int i = 0 ; i<3; i++) {
            for(int j = 0 ; j<3; j++) {
                r[i][j] = new Room(i*(tiles.length/3),j*(tiles.length/3),(tiles.length/3),(tiles.length/3));
            }
        }
        FieldOfView fov = new FieldOfView();
        for(int i = 0 ; i<3; i++) {
            for(int j = 0 ; j<3; j++) {
                for(int y = r[i][j].getPosY() ; y<r[i][j].getSizeY()+r[i][j].getPosY(); y++) {
                    for(int x = r[i][j].getPosX() ; x<r[i][j].getSizeY()+r[i][j].getPosX(); x++) {
                        tiles[y][x] = new Tile(false);
                    }
                }
                if(i>0){
                    Position p1 = new Position(r[i][j].getPosX(),r[i][j].getPosY());
                    Position p2 = new Position(r[i-1][j].getPosX(),r[i-1][j].getPosY());
                    ArrayList<Position> line=fov.calculateLine(p1, p2);
                    for(Position p : line ){
                        tiles[p.getY()][p.getX()] = new Tile(false);
                    }
                }
                if(j>0){
                    Position p1 = new Position(r[i][j].getPosX(),r[i][j].getPosY());
                    Position p2 = new Position(r[i][j-1].getPosX(),r[i][j-1].getPosY());
                    ArrayList<Position> line=fov.calculateLine(p1, p2);
                    for(Position p : line ){
                        tiles[p.getY()][p.getX()] = new Tile(false);
                    }
                }
            }
        }
        printMap();

    }
    private void printMap(){
        for(int i = 0 ; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].isBlocking()){
                    System.out.print("#");
                }else{
                    System.out.print(".");                }
            }
            System.out.println();
        }
    }
    public Position getFreePosition(){
        Random r = new Random();
        while(true){
            Position p = new Position(r.nextInt(tiles.length),
                    r.nextInt(tiles.length));
            if(!tiles[p.getY()][p.getX()].isBlocking()){
                return p;
            }
        }
    }
    private int[][] generateRoom(){
        return null;
    }
}
