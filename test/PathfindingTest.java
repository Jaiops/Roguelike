import game.Map;
import game.MapGenerator;
import game.Pathfinding;
import game.Position;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Johan on 2016-02-25.
 */
public class PathfindingTest {

    /* DEFUNCT until generateMap() is fixed
    @Test
    public void testGetPathNoPath() throws Exception {
        Map m = new Map();
        int[][] map = { {1,1,1,1},
                        {1,0,0,1},
                        {1,1,1,1},
                        {1,0,0,1},
                        {1,1,1,1}

        };
        m = new Map(0,MapGenerator.generateMap(map));
        Pathfinding p = new Pathfinding();

        assertEquals(null,p.getPath(m,new Position(1,1),new Position(4,2)));
    }
    @Test
    public void testGetPath() throws Exception {
        Map m = new Map();
        int[][] map = { {1,1,1,1,1,1},
                        {1,0,0,0,0,1},
                        {1,0,1,1,1,1},
                        {1,0,0,0,0,1},
                        {1,1,1,1,0,1},
                        {1,0,0,0,0,1},
                        {1,0,1,1,1,1},
                        {1,0,0,0,0,1},
                        {1,1,1,1,1,1}

        };
        m = new Map(0,MapGenerator.generateMap(map));
        Pathfinding p = new Pathfinding();
        ArrayList<Position> list = p.getPath(m,new Position(4,1),new Position(4,7));
        assertEquals(12,list.size());
    }*/
}