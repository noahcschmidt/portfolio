import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestCar {
    @Test
    public void TestConstructorIndexOnly() {
        Car c1 = new Car(4);
        Car c2 = new Car(0);
        try {
            Car c3 = new Car(-3);
            Assert.fail("Expected Illegal Argument Exception.");
        } catch(IllegalArgumentException e) {
            //Exception caught, proceeding
        }

        Assert.assertEquals(c1.getNumber(), 4);
        Assert.assertEquals(c2.getNumber(), 0);
        //TODO
    }

    @Test
    public void TestConstructor() {
        Party p1 = new Party(2);
        Party p2 = new Party(1, 200);
        List<Party> list1 = new LinkedList<>();
        List<Party> list2 = new LinkedList<>();
        list1.add(p1);
        list1.add(p2);
        list2.add(p1);

        Car c1 = new Car(3, list1);
        Assert.assertEquals(c1.getNumber(), 3);
        Assert.assertEquals(c1.getWeight(), (137*2) + 200 + Car.BASE_WEIGHT, 0.0001);

        Car c2 = new Car(5, list2);
        Assert.assertEquals(c2.getNumber(), 5);
        Assert.assertEquals(c2.getWeight(), 137*2 + Car.BASE_WEIGHT, 0.0001);
    }

    @Test
    public void TestAddParty() throws Exception {
        Party p1 = new Party(4);
        Party p2 = new Party(1,200);

        Car c = new Car(6);
        Assert.assertEquals(0, c.getNumPeople());

        c.addParty(p1);
        Assert.assertEquals(4, c.getNumPeople());

        c.addParty(p2);
        Assert.assertEquals(5, c.getNumPeople());

        c.addParty(new Party(7));
        Assert.assertEquals(12, c.getNumPeople());

        try {
            c.addParty(new Party(1));
            Assert.fail("Too many people in the car.");
        } catch(IllegalArgumentException e) {
            //Exception caught, proceeding
        }

    }

    @Test
    public void TestWeightModified() throws Exception {
        Party p1 = new Party(2);
        Party p2 = new Party(4);

        Car c = new Car(5);
        Assert.assertEquals(Car.BASE_WEIGHT,c.getWeight(),.0001);
        c.addParty(p1);
        Assert.assertEquals(Car.BASE_WEIGHT + 137*2, c.getWeight(),.0001);
        c.addParty(p2);
        Assert.assertEquals(Car.BASE_WEIGHT + 137*2 + 137*4, c.getWeight(),.0001);
    }
}
