package pl.chessonline.server;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MovementTest {
    @Test
    public void testToString() {
        int from = 8;
        int to = 80;
        Movement movement = new Movement(from, to);
        Assert.assertNotNull(movement);

        Assert.assertEquals("{\"from\": \"8\", \"to\": \"80\"}", movement.toString());
    }


    @Test
    public void testVeritify() {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        int from[] = {-10, 22, -10, 120, 12, 12, 15};
        int to[] = {22, -10, -11, 10, 12, 120, 20};
        boolean vals[] = new boolean[from.length];
        boolean expected[] = {false, false, false, false, false, false, true};
        for (int i = 0; i < from.length; ++i) {
            movements.add(i, new Movement(from[i], to[i]));
        }

        for (int i = 0; i < vals.length; i++) {
            vals[i] = true;
            try {
                movements.get(i).veritify();
            } catch (IllegalArgumentException e) {
                vals[i] = false;
            }
        }

        for (int i = 0; i < expected.length; ++i) {
            Assert.assertEquals(expected[i], vals[i]);
        }

    }
}
