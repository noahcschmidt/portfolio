import java.util.TimerTask;

public class TrackPiece {
    private double length;
    private double difficulty;
    private TrackPiece nextTrack;
    private Car car;
    private int timeLeft;

    public TrackPiece(double length, double difficulty, TrackPiece nextTrack) {
        if(length <=0 ){
            throw new IllegalArgumentException("Length must be greater than zero");
        }
        this.length = length;
        this.difficulty = difficulty;
        this.nextTrack = nextTrack;
        this.car = null;
        this.timeLeft = 0;
    }

    public TrackPiece (double length, double difficulty) {
        this(length, difficulty, null);
    }

    public double getLength(){
        return length;
    }

    public double getDifficulty(){
        return difficulty;
    }

    public TrackPiece getNextTrack() {
        return nextTrack;
    }
    public void setNextTrack(TrackPiece nextTrack){
        this.nextTrack = nextTrack;
    }

    public Car getCar(){
        return car;
    }
    public void setCar(Car car){
        this.car = car;
        calcTime();
    }

    public int getTime() {
        return timeLeft;
    }
    public void setTime(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void timeDec() {
        timeLeft--;
        if (timeLeft <= 0) {
            nextTrack.setCar(car);
            nextTrack.calcTime();
            this.car = null;
        }
    }

    public void calcTime() {
        if (car != null) {
            timeLeft = (int) ((100 / car.getWeight()) * 10 + difficulty * 10 + length / 2);
        }
    }
}
