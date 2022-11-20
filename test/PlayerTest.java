import bqz.model.Player;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {
    @Test
    public void getNameTest() {
        Player p = new Player("Stam");
        Assert.assertEquals("Stam", p.getName());
    }

    @Test
    public void pointsTest() {
        Player p = new Player("Stam");
        p.addPoints(100);
        p.removePoints(50);
        p.addPoints(200);
        Assert.assertEquals(250, p.getPoints());
    }
}
