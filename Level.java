import java.awt.*;
import java.util.ArrayList;

//this class is lowkey straightforward for now
public class Level
{
    private ArrayList<RoadSegment> roads;
    private ArrayList<BackgroundObject> backgroundObjects;
    private ArrayList<House> houses;

    private int newX, newY;

    private static int W, L;

    public Level()
    {
        roads = new ArrayList<>();
        backgroundObjects = new ArrayList<>();
        houses = new ArrayList<>();
        W = RoadSegment.getWidth();
        L = RoadSegment.getLength();

        newX = GamePanel.dimX/2;
        newY = GamePanel.dimY/2 + L/2;
    }
    public void addUp()
    {
        addRoad(new RoadSegment(newX,newY,0,RoadSegment.Type.STRAIGHT));
        newY-=L;
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
        newY+=L;
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
        newX-=L;
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
        newX+=L;
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
        newX +=W/2.0;
        newY -=W/2.0;
    }
    public void addUpLeft()
    {
        addRoad(new RoadSegment(newX,newY,0,RoadSegment.Type.CURVE_LEFT));
        newX -= W/2.0;
        newY -= W/2.0;
    }
    public void addLeftDown()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-90),RoadSegment.Type.CURVE_LEFT));
        newY+=W/2.0;
        newX-=W/2.0;
    }
    public void addRightDown()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(90),RoadSegment.Type.CURVE_RIGHT));
        newY+=W/2.0;
        newX+=W/2.0;
    }
    public void addDownRight()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-180),RoadSegment.Type.CURVE_LEFT));
        newY+=W/2.0;
        newX+=W/2.0;
    }
    public void addDownLeft()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-180),RoadSegment.Type.CURVE_RIGHT));
        newY+=W/2.0;
        newX-=W/2.0;
    }
    public void addRightUp()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(90),RoadSegment.Type.CURVE_LEFT));
        newX+=W/2.0;
        newY-=W/2.0;
    }
    public void addLeftUp()
    {
        addRoad(new RoadSegment(newX,newY,Math.toRadians(-90),RoadSegment.Type.CURVE_RIGHT));
        newX-=W/2.0;
        newY-=W/2.0;
    }

    public void addRoad(RoadSegment road)
    {
        roads.add(road);
    }
    public void addBackgroundObject(BackgroundObject object)
    {
        backgroundObjects.add(object);
    }
    public void addHouse(int x,int y)
    {
        houses.add(new House(x,y));
    }
    public void draw(Graphics2D graphics)
    {
        for(BackgroundObject object : backgroundObjects){
            object.draw(graphics);
        }

        for(RoadSegment road : roads)
        {
            road.draw(graphics);
        }

        for(House house : houses)
        {
            house.draw(graphics);
        }
    }
    public ArrayList<RoadSegment> getRoadSegments(){
        return roads;
    }
    public ArrayList<BackgroundObject> getBackgroundObjects()
    {
        return backgroundObjects;
    }
    public ArrayList<House> getHouses() {
        return houses;
    }
}