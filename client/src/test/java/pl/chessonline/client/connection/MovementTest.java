package pl.chessonline.client.connection;

import org.junit.Assert;
import org.junit.Test;

public class MovementTest {
    @Test
    public void testToString() {
        int from = 10;
        int to = 60;
        Movement movement = new Movement(from, to);
        Assert.assertNotNull(movement);

        Assert.assertEquals("1060", movement.toString());
    }
}
