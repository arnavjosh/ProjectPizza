import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapHandler
{
    //TODO: write levels class
    //TODO write up background objects
    private ArrayList<Level> levels;
    private int currentLevelNum = 0;
    private Level currentLevel;

    public MapHandler()
    {
        levels = new ArrayList<Level>();
        loadLevels();
        currentLevel = levels.get(currentLevelNum);
    }

    private void loadLevels()
    {
        Level testLevel = new Level();
        testLevel.addUp(5);           // Facing ↑
        testLevel.addUpRight();       // ↑ → (now facing →)
        testLevel.addRight(5);        // →
        testLevel.addRightDown();     // → ↓ (now facing ↓)
        testLevel.addDown(5);         // ↓
        testLevel.addDownLeft();      // ↓ ← (now facing ←)
        testLevel.addLeft(5);         // ←
        testLevel.addLeftUp();        // ← ↑ (now facing ↑)
        testLevel.addUp(2);           // ↑

        // === Branch 1: splits right from the right side of the loop ===
        testLevel.addUpRight();       // ↑ → (now facing →)
        testLevel.addRight(3);        // →
        testLevel.addRightDown();     // → ↓ (now facing ↓)
        testLevel.addDown(3);         // ↓
        testLevel.addDownLeft();      // ↓ ← (now facing ←)
        testLevel.addLeft(3);         // ←
        testLevel.addLeftUp();        // ← ↑ (now facing ↑)
        testLevel.addUpLeft();        // ↑ ← (now facing ←)
        testLevel.addLeft(2);         // ←
        testLevel.addLeftDown();      // ← ↓ (now facing ↓)
        testLevel.addDownRight();     // ↓ → (now facing →)
        testLevel.addRight(1);        // →
        testLevel.addRightUp();       // → ↑ (now facing ↑)

        // === Branch 2: splits left from the left side of the loop ===
        testLevel.addUpLeft();        // ↑ ← (now facing ←)
        testLevel.addLeft(3);         // ←
        testLevel.addLeftDown();      // ← ↓ (now facing ↓)
        testLevel.addDown(3);         // ↓
        testLevel.addDownRight();     // ↓ → (now facing →)
        testLevel.addRight(3);        // →
        testLevel.addRightUp();       // → ↑ (now facing ↑)
        testLevel.addUpRight();       // ↑ → (now facing →)
        testLevel.addRight(2);        // →
        testLevel.addRightDown();     // → ↓ (now facing ↓)
        testLevel.addDownLeft();      // ↓ ← (now facing ←)
        testLevel.addLeftUp();        // ← ↑ (now facing ↑)
        

        testLevel.addTree(700, 200);
        
        testLevel.addHouse(650,-255);
        testLevel.addHouse(995,-255);
        testLevel.addRoad(new RoadSegment(600,-600,0,RoadSegment.Type.CHECKPOINT));

        levels.add(testLevel);

        Level level2 = new Level();
        level2.addUp(20);
        level2.addRoad(new RoadSegment(0,0,0,RoadSegment.Type.CHECKPOINT));

        levels.add(level2);

    }

    public void draw(Graphics2D gameGraphics)
    {
        currentLevel.draw(gameGraphics);
    }

    public RoadSegment getSegmentAt(double x, double y)
    {
        for(RoadSegment road : currentLevel.getRoadSegments())
        {
            if(road.contains(x, y ))
                return road;
        }
        return null;
    }

    public boolean isRoadAt(double x, double y)
    {
        return getSegmentAt(x,y)!=null;
    }

    public ArrayList<Collidable> getCurrentCollidables() {
        return currentLevel.getCollidables();
    }


    public void incrementLevel()
    {
        //loops level using mod
        currentLevelNum = (currentLevelNum+1)%levels.size();
        currentLevel = levels.get(currentLevelNum);
    }
}
