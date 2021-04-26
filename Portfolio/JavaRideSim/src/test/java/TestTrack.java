import org.junit.Assert;
import org.junit.Test;

import javax.sound.midi.Track;

public class TestTrack {
    @Test
    public void test() {
        Station s = new Station();
        TrackPiece t4 = new TrackPiece(20, 1.1);
        TrackPiece t3 = new TrackPiece(25,0, t4);
        TrackPiece t2 = new TrackPiece(20,-0.4, t3);
        TrackPiece t1 = new TrackPiece(28, 0.3, t2);
        s.setNextTrack(t1);

        Assert.assertEquals(t1.getNextTrack(), t2);
        Assert.assertEquals(t2.getNextTrack(), t3);
        Assert.assertEquals(t3.getNextTrack(), t4);
        Assert.assertEquals(t4.getNextTrack(), s);
        Assert.assertEquals(s.getNextTrack(), t1);

    }
}
