import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;

public class RoadSegment
{
    public enum Type
    {
        STRAIGHT,
        CURVE_LEFT,
        CURVE_RIGHT,
        T,
        CHECKPOINT
    }

    private static final int ROAD_WIDTH = 150;
    private static final int ROAD_LENGTH = 150;

    public Type roadType;
    private int x,y;
    private double rotation;

    //shape class lets us make rectangles and stuff and also has a contains method so we can chekc if the car is on the road
    private Shape roadShape;

    //Affine transfrom lets us rotate the thing
    private AffineTransform optimusPrime;

    //its named optimus prime cuase its a transformer get it

    public RoadSegment(int x, int y, double rotation, Type roadType)
    {
        this.x = x;
        this.y = y;
        this.roadType = roadType;
        this.rotation = rotation;
        this.roadShape = makeRoadShape();
        optimusPrime = createTransformer();
    }

    private Shape makeRoadShape()
    {
        switch(roadType)
        {
            case STRAIGHT:
                return new Rectangle2D.Double(-ROAD_WIDTH / 2.0, -ROAD_LENGTH, ROAD_WIDTH, ROAD_LENGTH);
            case CURVE_LEFT:
                return new Arc2D.Double(-ROAD_WIDTH - ROAD_WIDTH / 2.0, -ROAD_WIDTH, ROAD_WIDTH * 2, ROAD_WIDTH * 2, 0, 90, Arc2D.PIE);
            case CURVE_RIGHT:
                return new Arc2D.Double(- ROAD_WIDTH / 2.0, -ROAD_WIDTH, ROAD_WIDTH * 2, ROAD_WIDTH * 2, 90, 90, Arc2D.PIE);
            case T:
            //found this thing called area on stack which lets u combine a bunch of tiny shapes? idk its pretty cool
                Area tShape = new Area(new Rectangle2D.Double(-ROAD_WIDTH,-ROAD_WIDTH/4.0,ROAD_WIDTH*2, ROAD_WIDTH/2.0));
                tShape.add(new Area(new Rectangle2D.Double(-ROAD_WIDTH/4.0,-ROAD_WIDTH, ROAD_WIDTH/2.0,ROAD_WIDTH)));
                return tShape;
            case CHECKPOINT:
                double radius = 100;
                return new Ellipse2D.Double(-radius,-radius,radius*2,radius*2);
        }
        return null;
    }

    private AffineTransform createTransformer()
    {
        AffineTransform bumblebee = new AffineTransform();

        bumblebee.translate(x,y);
        bumblebee.rotate(rotation);
        return bumblebee;
    }

    public boolean contains(double checkX, double checkY)
    {
        try{
            AffineTransform inverse = optimusPrime.createInverse();
            //undoes all the movement to convert overall point into local shape point
            Point2D localVersion = inverse.transform(new Point2D.Double(checkX, checkY), null);
            //applies the inverse transformation to the overall point to make the local localVersion
            return roadShape.contains(localVersion);
        }
        //i was told that this might blow up a computer if we dont check for excpetions (not all transforms can be inverted or something)
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void draw(Graphics2D graphics)
    {
        AffineTransform saveTransform = graphics.getTransform();

        //puts the graphics to center the current roadsegment
        graphics.transform(optimusPrime);
        graphics.setColor(getColor());
        graphics.fill(roadShape);
        graphics.setColor(Color.RED);
        double dotRadius = 14;
        graphics.fill(new Ellipse2D.Double(-dotRadius / 2.0, -dotRadius / 2.0, dotRadius, dotRadius));
        graphics.setTransform(saveTransform);
    }
    private Color getColor()
    {
        switch(roadType){
            case STRAIGHT, CURVE_LEFT, CURVE_RIGHT:
                return Color.DARK_GRAY;
            case T:
                return Color.LIGHT_GRAY;
            case CHECKPOINT:
                return Color.YELLOW;
        }
        return Color.BLACK;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getRotation(){return rotation;}
    public void setX(int x)
    {
        this.x = x;
        updateTransform();
    }
    public void setY(int y)
    {
        this.y = y;
        updateTransform();
    }
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
        updateTransform();
    }
    private void updateTransform()
    {
        optimusPrime = createTransformer();
    }
    public static int getWidth()
    {
        return ROAD_WIDTH;
    }
    public static int getLength()
    {
        return ROAD_LENGTH;
    }
}