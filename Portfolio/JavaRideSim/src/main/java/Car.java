import java.util.LinkedList;
import java.util.List;

public class Car {
    public static final int SEATS = 12;
    public static final int BASE_WEIGHT = 300;

    private double weight;
    private double speed;
    private int number;
    private List<Party> parties;

    public Car(int number, List<Party> parties) {
        if (number < 0) {
            throw new IllegalArgumentException("Car number cannot be negative.");
        }

        this.speed = 0;
        this.number = number;

        if (parties == null) {
            this.parties = new LinkedList<>();
        }
        else {
            this.parties = parties;
        }

        setPartyWeights();
        checkNumPeople();
    }

    public Car(int number) {
        this(number, new LinkedList<>());
    }

    public double getWeight() {
        return weight;
    }

    public double getSpeed() {
        return speed;
    }

    public int getNumber() {
        return number;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getNumPeople() {
        int total = 0;
        for (Party p : parties) {
            total += p.getNumPeople();
        }
        return total;
    }

    public void addParty(Party p) {
        if(getNumPeople() + p.getNumPeople() <= SEATS) {
            parties.add(p);
        }

        setPartyWeights();
    }

    /**
     * Private helper method, calculates and sets weight based on base weight and weights of parties.
     */
    private void setPartyWeights() {
        weight = BASE_WEIGHT;
        for(Party p : parties) {
            weight += p.getWeight();
        }
    }

    /**
     * Private helper, ensures the list of parties added to a car does not exceed the maximum amount of people.
     */
    private void checkNumPeople() {
        int total = 0;
        for (Party p : parties) {
            total += p.getNumPeople();
        }
        if (total > SEATS) {
            throw new IllegalArgumentException("List of parties cannot exceed number of seats: " + SEATS);
        }
    }

    public void clear() {
        parties = new LinkedList<>();
    }
}