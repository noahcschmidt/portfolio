import java.util.ArrayList;

public class Station extends TrackPiece {
    private ArrayList<Car> cars;

    public Station(TrackPiece nextTrack) {
        super(1, 0, nextTrack);
        cars = new ArrayList<>();
    }

    public Station() {
        super(1,0,null);
        cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public Car removeNextCar() {
        return cars.remove(cars.size()-1);
    }

    public Car getCar(int i) {
        return cars.get(i);
    }

    public int getNumCars() {
        return cars.size();
    }

    @Override
    public void setCar(Car car) {
        car.clear();
        addCar(car);

    }

}
