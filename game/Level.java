import java.awt.*;
import java.util.ArrayList;

//this class is lowkey straightforward for now
public class Level
{
    private ArrayList<RoadSegment> roads;
    private ArrayList<Collidable> collidables;

    private int newX, newY;

    private static int roadWidth = RoadSegment.getWidth();
    private static int  roadLength = RoadSegment.getLength();

    public Level()
    {
        roads = new ArrayList<>();
        collidables = new ArrayList<>();
        newX = GamePanel.dimX/2;
        newY = GamePanel.dimY/2 + roadLength/2;
    }
    public void addUp()
    {
        addRoad(new RoadSegment(newX,newY,0,RoadSegment.Type.STRAIGHT));
        newY-=roadLength;
    }
    
    public void addUp(int n)
    {
        for (int i = 0; i < n; i++)
        {
            addUp();
        }
    }
    public void addDown()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(180),RoadSegment.Type.STRAIGHT));
        newY+=roadLength;
    }
    public void addDown(int n)
    {
        for (int i = 0; i < n; i++)
        {
            addDown();
        }
    }
    public void addLeft()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-90),RoadSegment.Type.STRAIGHT));
        newX-=roadLength;
    }
    public void addLeft(int n)
    {
        for (int i = 0; i < n; i++)
        {
            addLeft();
        }
    }
    public void addRight()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(90),RoadSegment.Type.STRAIGHT));
        newX+=roadLength;
    }
    public void addRight(int n)
    {
        for (int i = 0; i < n; i++)
        {
            addRight();
        }
    }
    public void addUpRight()
    {
        addRoad(new RoadSegment(newX,newY,0,RoadSegment.Type.CURVE_RIGHT));
        newX +=roadWidth/2.0;
        newY -=roadWidth/2.0;
    }
    public void addUpLeft()
    {
        addRoad(new RoadSegment(newX,newY,0,RoadSegment.Type.CURVE_LEFT));
        newX -= roadWidth/2.0;
        newY -= roadWidth/2.0;
    }
    public void addLeftDown()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-90),RoadSegment.Type.CURVE_LEFT));
        newY+=roadWidth/2.0;
        newX-=roadWidth/2.0;
    }
    public void addRightDown()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(90),RoadSegment.Type.CURVE_RIGHT));
        newY+=roadWidth/2.0;
        newX+=roadWidth/2.0;
    }
    public void addDownRight()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-180),RoadSegment.Type.CURVE_LEFT));
        newY+=roadWidth/2.0;
        newX+=roadWidth/2.0;
    }
    public void addDownLeft()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-180),RoadSegment.Type.CURVE_RIGHT));
        newY+=roadWidth/2.0;
        newX-=roadWidth/2.0;
    }
    public void addRightUp()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(90),RoadSegment.Type.CURVE_LEFT));
        newX+=roadWidth/2.0;
        newY-=roadWidth/2.0;
    }
    public void addLeftUp()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-90),RoadSegment.Type.CURVE_RIGHT));
        newX-=roadWidth/2.0;
        newY-=roadWidth/2.0;
    }

    public void addRoad(RoadSegment road)
    {
        roads.add(road);
    }
    public void addCollidable(Collidable object)
    {
        collidables.add(object);
    }
    public void addHouse(int x,int y)
    {
        collidables.add(new House(x,y));
    }
    public void addTree(int x, int y)
    {
        collidables.add(new Tree(x,y));
    }
    public void draw(Graphics2D graphics)
    {
        for(RoadSegment road : roads)
        {
            road.draw(graphics);
        }
        for(Collidable object : collidables){
            object.draw(graphics);
        }
    }
    public ArrayList<RoadSegment> getRoadSegments(){
        return roads;
    }
    public ArrayList<Collidable> getCollidables()
    {
        return collidables;
    }
}