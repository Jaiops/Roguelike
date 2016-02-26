import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by Johan on 2016-02-25.
 */
public class Viewport {
    int currentX;
    int currentY;
    int maxX;
    int maxY;
    int sizeX;
    int sizeY;

    public Viewport(int sizeX, int sizeY, Map m) throws Exception {
        if(sizeX>m.getTiles().length){
            throw new Exception("Error ViewportX"+sizeX + "<"+m.getTiles().length);
        }
        else if(sizeY >m.getTiles()[0].length){
            throw new Exception("Error ViewportY"+sizeY + "<"+m.getTiles()[0].length);

        }
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxX = m.getTiles().length;
        this.maxY = m.getTiles()[0].length;
    }
    public void setNewPos(Position pos){
        this.currentX = pos.getX()-Math.round(sizeX / 2);
        this.currentY = pos.getY()-Math.round(sizeY / 2);
        if(currentX < 0){
            currentX = 0;
        }else if(currentX > maxX-sizeX){
            currentX = (maxX-sizeX);
        }

        if(currentY < 0){
            currentY = 0;
        }else if(currentY > maxY-sizeY){
            currentY = (maxY-sizeY);
        }
    }

    public boolean withinViewport(Position p){

        return p.getX() > currentX && p.getX() < currentX + sizeX
                && p.getY() > currentY && p.getY() < currentY + sizeY;
    }
    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
