import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MapHandler
{
    //TODO: write levels class
    //TODO write up background objects
    private ArrayList<Level> levels;
    private int currentLevelNum = 0;
    private Level currentLevel;
    private BufferedImage grassTile;

    public MapHandler()
    {
        levels = new ArrayList<Level>();
        loadLevels();
        currentLevel = levels.get(currentLevelNum);
        loadGrassTile();
    }
    

    private void loadGrassTile() {
        try {
            grassTile = ImageIO.read(getClass().getResource("/images/Grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setLevel(int levelNum)
    {
        if(levelNum >= 0 && levelNum < levels.size())
        {
            currentLevelNum = levelNum;
            currentLevel = levels.get(currentLevelNum);
        }
        else
        {
            System.out.println("Invalid level number: " + levelNum);
        }
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
        testLevel.addDominos(-25, 84);

        Level level2 = new Level();
        level2.addUp(20);
        level2.addRoad(new RoadSegment(0,0,0,RoadSegment.Type.CHECKPOINT));


        //adds a loop level

        Level loopLevel = new Level();
        loopLevel.addUp(5);           // Facing ↑
        loopLevel.addUpLeft();
        loopLevel.addLeft(5);         // ←
        loopLevel.addLeftDown();      // ← ↓ (now facing ↓)
        loopLevel.addDown(5);         // ↓
        loopLevel.addDownRight();     // ↓ → (now facing →)
        loopLevel.addRight(5);        // →
        loopLevel.addRightUp();       // → ↑ (now facing ↑)

        Level level0 = new Level();
        level0.addUp(5);
        level0.addUpRight();       // ↑ →
        level0.addRight(5);        // →
        level0.addRightDown();     // → ↓ (now facing ↓)
        level0.addDown(2);         // ↓
        level0.addDownLeft();      // ↓ ← (now facing ←)
        level0.addLeft(5);         // ←
        level0.addLeftUp();        // ← ↑ (now facing ↑)
        level0.addUp(5);           // ↑
        level0.addUpRight();       // ↑ →
        level0.addRight(5);        // →
        level0.addRightDown();     // → ↓ (now facing ↓)
        level0.addDown(2);         // ↓
        level0.addDownLeft();      // ↓ ← (now facing ←)
        level0.addLeft(5);         // ←
        level0.addLeftUp();        // ← ↑ (now facing ↑)
        level0.addUp(5);           // ↑
        level0.addUpRight();       // ↑ →
        level0.addRight(5);        // →
        level0.addRightDown();     // → ↓ (now facing ↓)
        level0.addDown(2);         // ↓
        level0.addDownLeft();      // ↓ ← (now facing ←)
        level0.addLeft(5);         // ←
        level0.addLeftUp();        // ← ↑ (now facing ↑)
        level0.addUp(5);           // ↑
        level0.addUpLeft();
        level0.addLeft(5);         // ←
        level0.addLeftDown();      // ← ↓ (now facing ↓)
        level0.addDown(5);         // ↓
        level0.addDownRight();     // ↓ → (now facing →)
        level0.addRight(5);        // →
        level0.addRightUp();       // → ↑ (now facing ↑)
        level0.addUp(5);           // ↑
        level0.addUpRight();       // ↑ →
        level0.addRight(5);        // →
        level0.addRightDown();     // → ↓ (now facing ↓)
        level0.addDown(2);         // ↓
        level0.addDownLeft();      // ↓ ← (now facing ←)
        level0.addLeft(5);         // ←
        level0.addLeftUp();        // ← ↑ (now facing ↑)



        level0.addDominos(415, 500);
        level0.addHouse(675, -238);
        level0.addCheckpointHouse(1000, -238); // This is the checkpoint house
        //subtract 450 from y for each level up
        level0.addHouse(675, -688);
        level0.addHouse(1000, -688);
        level0.addHouse(675, -1136);
        level0.addHouse(1000, -1136);
        level0.addTreeCluster(550,200);
        level0.addTreeCluster(785,438);
        level0.addTreeCluster(743,605);
        level0.addTreeCluster(581,800);
        levels.add(level0);
    }
    public void draw(Graphics2D gameGraphics)
    {
        currentLevel.draw(gameGraphics);
    }

    public void drawRoads(Graphics2D gameGraphics)
    {
        currentLevel.drawRoads(gameGraphics);
    }

    public void drawCollidables(Graphics2D gameGraphics)
    {
        currentLevel.drawCollidables(gameGraphics);
    }

    public void drawBackground(Graphics2D gameGraphics, Car car)
    {
        if (grassTile == null) return;

        int tileW = grassTile.getWidth();
        int tileH = grassTile.getHeight();

        // Get current screen dimensions (in case user resized/fullscreened)
        int screenW = GamePanel.dimX + 100;
        int screenH = GamePanel.dimX + 100;

        // Get top-left world coordinates based on car position
        double worldLeft = car.getX() - screenW / 2.0;
        double worldTop = car.getY() - screenH / 2.0;

        // Align to nearest tile so tiles scroll smoothly
        int startX = (int) (Math.floor(worldLeft / tileW) * tileW);
        int startY = (int) (Math.floor(worldTop / tileH) * tileH);

        int endX = (int) (worldLeft + screenW) + tileW;
        int endY = (int) (worldTop + screenH) + tileH;

        for (int x = startX; x < endX; x += tileW) {
            for (int y = startY; y < endY; y += tileH) {
                gameGraphics.drawImage(grassTile, x, y, null);
            }
        }



        currentLevel.drawBackgroundObjects(gameGraphics);
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

    public double getCheckpointX()
    {
        for(RoadSegment road : currentLevel.getRoadSegments())
        {
            if(road.roadType == RoadSegment.Type.CHECKPOINT)
            {
                return (int) road.getX();
            }
        }
        return 0;
    }
    public double getCheckpointY()
    {
        for(RoadSegment road : currentLevel.getRoadSegments())
        {
            if(road.roadType == RoadSegment.Type.CHECKPOINT)
            {
                return (int) road.getY();
            }
        }
        return 0;
    }
}
