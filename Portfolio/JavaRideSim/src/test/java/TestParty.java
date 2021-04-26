import org.junit.Assert;
import org.junit.Test;

public class TestParty {

    @Test
    public void testConstruct() {
        Party p1 = new Party(4);
        Assert.assertEquals(p1.getNumPeople(), 4);
        Assert.assertEquals(p1.getWeight(), 137*p1.getNumPeople(), 0.0001);

        Party p2 = new Party(9, 400.56);
        Assert.assertEquals(p2.getNumPeople(), 9);
        Assert.assertEquals(p2.getWeight(), 400.56, 0.0001);

        Party p3 = new Party(3, 250.6);
        Assert.assertEquals(p3.getNumPeople(), 3);
        Assert.assertEquals(p3.getWeight(), 250.6, 0.0001);

    }

    @Test
    public void testIllegalNumPeople() {
        try {
            Party p1 = new Party(0);
            Party p2 = new Party(-5);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //Error caught, success
        }
    }

    @Test
    public void testIllegalWeight() {
        try {
            Party p1 = new Party(2, 0);
            Party p2 = new Party(5, -12.8);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //error caught, success
        }
    }

}
