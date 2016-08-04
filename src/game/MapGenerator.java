package game;

import items.ItemFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Johan on 2016-05-29.
 */
public class MapGenerator {

    Tile tiles[][];
    public static Tile[][] generateMap(int map[][]){
        Tile[][] tiles = new Tile[map.length][map[0].length];
        for(int i = 0 ; i<map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == 0){
                    tiles[i][j] = new Tile(false);
                }else{
                    tiles[i][j] = new Tile(true);
                }
            }
        }
        return tiles;
    }

    private static class Room{

        int sizeX;
        int sizeY;
        int posY;
        int posX;
        protected Room(int minX, int minY, int maxX, int maxY){
            Random r = new Random();

            sizeX = r.nextInt(5)+3;
            sizeY = r.nextInt(5)+3;
            posY = r.nextInt(maxY-(sizeY+minY+2))+minY+1;
            posX = r.nextInt(maxX-(sizeX+minX+2))+minX+1;
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

        @Override
        public String toString() {
            return "Room{" +
                    "sizeX=" + sizeX +
                    ", sizeY=" + sizeY +
                    ", posY=" + posY +
                    ", posX=" + posX +
                    '}';
        }
    }
    public static Tile[][] generateRoomsMap(){
        Tile tiles[][] = new Tile[50][50];
        for(int i = 0 ; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(true);
            }
        }
        Room[][] r = new Room[3][3];
        for(int i = 0 ; i<3; i++) {
            for(int j = 0 ; j<3; j++) {
                r[i][j] = new Room(i*(tiles.length/3),j*(tiles.length/3),(i+1)*(tiles.length/3),(j+1)*(tiles.length/3));
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
        printMap(tiles);
        return tiles;
    }

    private static void printMap(Tile[][] tiles){
        for(int i = 0 ; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].isBlocking()){
                    System.err.print("#");
                }else{
                    System.err.print(".");                }
            }
            System.err.println();
        }
    }
    public static Map[] generateMultipleMap(int layers){

        Map[] m = new Map[layers+1];
        //GENERATE MAPS
        for(int i = 0; i<layers+1;i++){
            m[i] = new Map(i,generateRoomsMap());

        }

        //BUILD STAIRCASES
        for(int i = 0; i<layers;i++){
            //m[i] = new Map(generateRoomsMap());
            if(i < layers){
                for(int j = 0; j < 3;j++){

                    Position pos = m[i].getFreePosition();
                    Position pos2 = m[i+1].getFreePosition();

                    m[i].getTiles()[pos.getY()][pos.getX()] = new Staircase(m[i+1],pos2,true);

                    //Position pos = m[i-1].getFreePosition();
                    m[i+1].getTiles()[pos2.getY()][pos2.getX()] = new Staircase(m[i],pos,false);

                }

            }
        }
        Random r = new Random();
        //ADD MONSTERS
        for(int i = 0; i<layers+1;i++){


            for(int j = r.nextInt(5)+5; j>0;j--) {

                int x = r.nextInt(3);
                switch (x){
                    case 0:
                        m[i].addMonster(MonsterFactory.createGoblin(m[i].getFreePosition()));
                        break;
                    case 1:
                        m[i].addMonster(MonsterFactory.createSpeed(m[i].getFreePosition()));
                        break;
                    case 2:
                        m[i].addMonster(MonsterFactory.createOrc(m[i].getFreePosition()));
                        break;
                }
            }
        }

        //ADD ITEMS
        for(int i = 0; i<layers+1;i++){


            for(int j = r.nextInt(5); j>0;j--) {

                int x = r.nextInt(7);
                switch (x){
                    case 0:
                        m[i].addItem(ItemFactory.chainmail(),m[i].getFreePosition());
                        break;
                    case 1:
                        m[i].addItem(ItemFactory.greatsword(), m[i].getFreePosition());
                        break;
                    case 2:
                        m[i].addItem(ItemFactory.speedPotion(), m[i].getFreePosition());
                        break;
                    case 3:
                        m[i].addItem(ItemFactory.healingSalve(), m[i].getFreePosition());
                        break;
                    case 4:
                        m[i].addItem(ItemFactory.healthPotion(), m[i].getFreePosition());
                        break;
                    case 5:
                        m[i].addItem(ItemFactory.randomWeapon(), m[i].getFreePosition());
                        break;
                    case 6:
                        m[i].addItem(ItemFactory.randomArmor(), m[i].getFreePosition());
                        break;


                }
            }
        }
        m[layers].addMonster(MonsterFactory.createDevil(m[layers].getFreePosition()));
        return m;
    }
}
