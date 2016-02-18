import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-18.
 */
public class FieldOfView {
    public FieldOfView(){

    }
    public ArrayList<Position> calculateFov(Map m, Position p){
        ArrayList<Position> results = new ArrayList<>();
        int x = p.getX()-5;
        int y = p.getY()-5;
        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        int highX = p.getX()+6;
        int highY = p.getY()+6;

        if(highX > m.getTiles().length){
            highX = m.getTiles().length;
        }
        if(highY > m.getTiles().length){
            highY = m.getTiles().length;
        }
        for (int i = x; i<highX; i++){
            for (int j = y; j<highY;j++){
                if(i == x || j == y || j == highY-1|| i == highX-1){
                    ArrayList<Position> line = calculateLine(p,new Position(i,j));
                    int index = 0;
                    while (true){
                        Position pos = line.get(index);
                        if(!results.contains(pos)){
                            results.add(pos);
                        }
                        if(m.getTiles()[pos.getY()][pos.getX()].isBlocking()){
                            break;
                        }
                        if(index == line.size()-1){
                            break;
                        }
                        index++;
                    }
                }
            }
        }
        return results;

    }
    public ArrayList<Position> calculateLine(Position p1, Position p2){
        ArrayList<Position> result = new ArrayList<>();
        int dx = Math.abs(p2.getX() - p1.getX());
        int dy = Math.abs(p2.getY() - p1.getY());

        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        int dx2 = (dx << 1);
        int dy2 = (dy <<1);

        int ix = 0;
        if(p1.getX() <p2.getX()){
            ix =1;
        }else{
            ix = -1;
        }
        int iy = 0;
        if(p1.getY() <p2.getY()){
            iy =1;
        }else{
            iy = -1;
        }

        int d = 0;
        if(dy <= dx){
            while(true){
                result.add(new Position(x1,y1));
                if(x1 == x2){
                    break;
                }
                x1+= ix;
                d += dy2;
                if(d > dx){
                    y1 +=iy;
                    d-=dx2;
                }
            }
        }else{
            while(true){
                result.add(new Position(x1,y1));
                if(y1 == y2){
                    break;
                }
                y1+= iy;
                d += dx2;
                if(d > dy){
                    x1 +=ix;
                    d-=dy2;
                }
            }
        }

        return result;
    }
}
