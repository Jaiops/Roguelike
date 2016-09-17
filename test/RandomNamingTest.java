import game.RandomNaming;
import org.junit.Test;


/**
 * Created by Johan on 2016-07-28.
 */

public class RandomNamingTest {
    RandomNaming rn = new RandomNaming();

    @Test
    public void shouldGiveRandomName() {
        for (int i = 0; i<10;i++) {
            System.out.println(rn.getRandomName());
        }
    }
}
