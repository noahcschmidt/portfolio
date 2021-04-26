public class Party {
    private static final double AVG_WEIGHT = 137.00;

    private int numPeople;
    private double weight;

    public Party(int numPeople, double weight) {
        if(numPeople < 0 || weight < 0 ){
            throw new IllegalArgumentException();
        }
        this.numPeople = numPeople;
        this.weight = weight;
    }

    public Party(int numPeople) {
        if(numPeople<0) {
            throw new IllegalArgumentException();
        }
        this.numPeople = numPeople;
        weight = AVG_WEIGHT*numPeople;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }
}



