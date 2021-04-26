//import org.junit.Assert;
//import org.junit.Test;
//
//public class TestTrackPiece {
//
//    @Test
//    public void testConstruct(){
//        TrackPiece p1 = new TrackPiece(5.00);
//        Assert.assertEquals(p1.getLength(),5.00,0.0001);
//        //Assert.assertEquals(p1.getDifficulty(), 20);
//
//        TrackPiece p2 = new TrackPiece(10.00, 1, 2, 4);
//        Assert.assertEquals(p2.getLength(),10.00, 0.0001);
//        Assert.assertEquals(p2.getDifficulty(), 1, 0.0001);
//        Assert.assertEquals(p2.getNextTrack(),2);
//        Assert.assertEquals(p2.getCar(), 4);
//
//        TrackPiece p3 = new TrackPiece(20.00, 5, 3, 6);
//        Assert.assertEquals(p3.getLength(),20.00,0.0001);
//        Assert.assertEquals(p3.getDifficulty(),5);
//        Assert.assertEquals(p3.getNextTrack(),3);
//
//
//    }
//    @Test
//    public void testIllegalLength(){
//        try{
//            TrackPiece p1 = new TrackPiece(0.00);
//            TrackPiece p2 = new TrackPiece(-5.00);
//            Assert.fail("Expected IllegalArgumentException");
//        }catch (IllegalArgumentException e){
//            //Error caught, success
//        }
//    }
//
//    @Test
//    public void testIllegalDifficulty(){
//        try{
//            TrackPiece p1 = new TrackPiece(1.00, 0);
//            TrackPiece p2 = new TrackPiece(10.00, -10);
//            Assert.fail("Expected IllegalArgumentException");
//        }catch (IllegalArgumentException e){
//            //Error caught, success
//        }
//    }
//
//    @Test
//    public void testIllegalNextTrack(){
//        try {
//            TrackPiece p1 = new TrackPiece(1.00, 1, 0);
//            TrackPiece p2 = new TrackPiece(2.00, 3, -5);
//            Assert.fail("Expected IllegalArgumentException");
//        }catch(IllegalArgumentException e){
//
//        }
//
//    }
//
//    @Test
//    public void testIllegalCar(){
//        try{
//            TrackPiece p1 = new TrackPiece(3.00, 2, 3, 0);
//            TrackPiece p2 = new TrackPiece( 4.00, 9,4, -1);
//            Assert.fail("Expected IllegalArgumentException");
//        }catch(IllegalArgumentException  e){
//
//        }
//    }
//
//}

