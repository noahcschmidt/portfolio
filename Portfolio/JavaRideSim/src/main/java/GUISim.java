import javax.sound.midi.Track;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.*;
import java.util.Timer;

public class GUISim extends Observable {

    //Ride Components
    private Station station;
    private ArrayList<TrackPiece> track;

    private Queue<Party> line;

    //GUI Components
    private JFrame window;
    private Container linePane;     //right side, holds line info
    private Container stationPane;  //middle, holds empty cars, buttons
    private Container trackPane;     //left side, holds ride info and track

    //left
    private ArrayList<JPanel> trackBoxes;
    private ArrayList<JLabel> trackLabels;

    //middle
    private ArrayList<JPanel> carBoxes;
    private ArrayList<JLabel> carLabels;

    private JButton deployButton;
    private JButton addPassButton;

    //right
    private JLabel nextRiderLabel;

    private JLabel timeEstimation;

    private JButton addButton;
    private JTextField partyNumberField;
    private JTextField weightField;

    //other
    private Timer timer;
    private TimerTask tick;

    //Number of track pieces, including station
    private static final int NUM_TRACKS = 12;

    public GUISim() {
        //Ride
        station = new Station();
        station.addCar(new Car(1));
        station.addCar(new Car(2));
        station.addCar(new Car(3));
        station.addCar(new Car(4));

        track = new ArrayList<>();

        for(int i = 0; i <= NUM_TRACKS-1; i++) {
            TrackPiece t = new TrackPiece(30, 0);
            if (track.isEmpty()) {
                station.setNextTrack(t);
            }
            else {
                track.get(i - 1).setNextTrack(t);
            }
            track.add(t);
        }
        track.get(NUM_TRACKS-1).setNextTrack(station);

        //GUI
        makeWindow();
        makeLeftSide();
        makeCenter();
        makeRight();
        window.setVisible(true);

        timer = new Timer();
        tick = new Tick();
        timer.schedule(tick, 1000);

        Observer ob = new SimObserver();
        this.addObserver(ob);

        setChanged();
        notifyObservers();
    }

    /**
     * Sets up the JFrame object holding all GUI components
     */
    private void makeWindow() {
        window = new JFrame();
        window.setTitle("RIDE SIMULATOR");
        window.setLocation(100,100);
        window.setSize(800, 500);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Creates the Left side of the GUI.
     */
    private void makeLeftSide() {
        trackPane = new Container();

        //create containers for track pieces
        trackBoxes = new ArrayList<>();
        trackLabels = new ArrayList<>();

        trackPane.add(new JLabel("TRACK"));
        for (int i = 0; i < NUM_TRACKS - 1; i++) {
            JPanel temp = new JPanel();
            temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            temp.setPreferredSize(new Dimension(150,20));

            JLabel label = new JLabel();
            temp.add(label);
            trackLabels.add(label);

            trackBoxes.add(temp);
        }

        //Setup layout of pane
        trackPane.setLayout(new BoxLayout(trackPane,BoxLayout.PAGE_AXIS));
        for (int i = 0; i < trackBoxes.size(); i++) {
            trackPane.add(trackBoxes.get(i));
        }

        window.add(trackPane, BorderLayout.WEST);
    }

    /**
     * Creates the Station Area
     */
    private void makeCenter() {
        stationPane = new Container();
        stationPane.setLayout(new BoxLayout(stationPane, BoxLayout.Y_AXIS));

        stationPane.add(new JLabel("STATION"));

        carBoxes = new ArrayList<>();
        carLabels = new ArrayList<>();

        for (int i = 0; i < station.getNumCars(); i++) {
            JPanel temp = new JPanel();
            temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            temp.setPreferredSize(new Dimension(200, 30));
            carBoxes.add(temp);

            JLabel l = new JLabel();
            carLabels.add(l);
            temp.add(l);

            stationPane.add(temp);
        }

        Container space = new Container();
        space.setPreferredSize(new Dimension(200, window.getHeight() - 30*station.getNumCars() - 30));
        stationPane.add(space);

        Container midButtons = new Container();
        midButtons.setLayout(new BoxLayout(midButtons, BoxLayout.X_AXIS));

        deployButton = new JButton("Deploy Car");
        deployButton.addActionListener(new DeployListener());
        addPassButton = new JButton("Add Party");
        addPassButton.addActionListener(new FillCarListener());
        midButtons.add(deployButton);
        midButtons.add(addPassButton);

        stationPane.add(midButtons);


        window.add(stationPane, BorderLayout.CENTER);
    }

    private void makeRight() {
        linePane = new Container();
        linePane.setLayout(new BoxLayout(linePane, BoxLayout.Y_AXIS));

        Container nextRiders = new Container();             //holds boxes for next riders, 1 for now
        nextRiders.setLayout(new BoxLayout(nextRiders, BoxLayout.Y_AXIS));

        line = new LinkedList();

        JPanel temp = new JPanel();
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        temp.setPreferredSize(new Dimension(200, 50));

        nextRiderLabel = new JLabel();
        temp.add(nextRiderLabel);
        nextRiders.add(temp);

        temp = new JPanel();
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        temp.setPreferredSize(new Dimension(200, 50));

        timeEstimation = new JLabel();
        temp.add(timeEstimation);
        nextRiders.add(temp);

        Container riderMiddleSection = new Container();     //holds nextRiders and estimated time
        riderMiddleSection.setLayout(new BoxLayout(riderMiddleSection, BoxLayout.X_AXIS));
        riderMiddleSection.setPreferredSize(new Dimension(300,300));

        Container buttonSection = new Container();          //holds bottom buttons and boxes
        buttonSection.setLayout(new BoxLayout(buttonSection, BoxLayout.X_AXIS));
        buttonSection.setPreferredSize(new Dimension(300,30));


        //Bottom Buttons
        partyNumberField = new JTextField("Num in Party");
        weightField = new JTextField("Weight");
        addButton = new JButton("ADD");
        addButton.addActionListener(new AddPartyListener());
        buttonSection.add(partyNumberField);
        buttonSection.add(weightField);
        buttonSection.add(addButton);

        linePane.add(new JLabel("Next Riders:"));
        linePane.add(nextRiders);
        linePane.add(riderMiddleSection);
        linePane.add(buttonSection);

        window.add(linePane, BorderLayout.EAST);

    }

    /**
     * Observer of this, the ride sim.
     * When setChanged() and notifyObservers()
     * it updates the gui
     */
    private class SimObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            //update track times and cars etc
            for (int i = 0; i < NUM_TRACKS-1; i++) {
                TrackPiece t = track.get(i);
                JLabel l = trackLabels.get(i);
                String c = (t.getCar() == null) ? "_" : ""+t.getCar().getNumber();  //num if there is a car, x if not
                l.setText("Track " + i + ", Car: " + c + " --- " + t.getTime());
            }

            //update station cars / num people
            for (int i = 0; i < station.getNumCars(); i++) {
                Car c = station.getCar(i);
                JLabel l = carLabels.get(i);
                l.setText("Car " + c.getNumber() + " - " + c.getNumPeople() + "/" + Car.SEATS);
            }

            //update next rider
            nextRiderLabel.setText((line.isEmpty()) ? "Queue empty." : "Next Party: " + line.peek().getNumPeople());

            timeEstimation.setText("Estimated Wait Time: " + getTimeEstimation());
        }
    }

    private int getTimeEstimation() {
        int peopleInLine = 0;
        for(Party p : line) {
            peopleInLine += p.getNumPeople();
        }
        int seconds = peopleInLine * (track.size() * 5);
        return seconds/60;
    }

    private class Tick extends TimerTask {
        @Override
        public void run() {
            for(TrackPiece t : track) {
                if (t.getTime() > 0) {
                    t.timeDec();
                    setChanged();
                    notifyObservers();
                }
            }
            tick = new Tick();
            timer.schedule(tick, 1000);
        }
    }

    private class DeployListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //only deploy if first two track sections are empty
            if (track.get(0).getCar() == null && track.get(1).getCar() == null) {
                int i = station.getNumCars();
                carLabels.get(i - 1).setText("---");

                Car c = station.removeNextCar();
                station.getNextTrack().setCar(c);

                setChanged();
                notifyObservers();
            }
        }
    }

    private class AddPartyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int pnum;
            double weight;
            try {
                pnum = Integer.parseInt(partyNumberField.getText());
                weight = Double.parseDouble(weightField.getText());
                Party party = new Party(pnum, weight);
                line.add(party);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            partyNumberField.setText("");
            weightField.setText("");

            setChanged();
            notifyObservers();
        }
    }

    private class FillCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Car nextCar = station.getCar(station.getNumCars()-1);
            int numInCar = nextCar.getNumPeople();
            //if we can fit next party
            if (!line.isEmpty()) {
                if (numInCar + line.peek().getNumPeople() <= Car.SEATS) {
                    nextCar.addParty(line.remove());
                }
            }
            setChanged();
            notifyObservers();
        }
    }
}
