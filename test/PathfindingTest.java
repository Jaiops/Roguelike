import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Johan on 2016-02-25.
 */
public class PathfindingTest {


    @Test
    public void testGetPathNoPath() throws Exception {
        Map m = new Map();
        int[][] map = { {1,1,1,1},
                        {1,0,0,1},
                        {1,1,1,1},
                        {1,0,0,1},
                        {1,1,1,1}

        };
        m.generateMap(map);
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
        m.generateMap(map);
        Pathfinding p = new Pathfinding();
        ArrayList<Position> list = p.getPath(m,new Position(4,1),new Position(4,7));
        assertEquals(12,list.size());
    }
}