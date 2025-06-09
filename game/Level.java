import java.awt.*;
import java.math.RoundingMode;
import java.text.spi.CollatorProvider;
import java.util.ArrayList;

//this class is lowkey straightforward for now
public class Level {
  private ArrayList<RoadSegment> roads;
  private ArrayList<Collidable> collidables;
  private ArrayList<GraphicObject> backgroundObjects;

  private int newX, newY;

  private static int roadWidth = RoadSegment.getWidth();
  private static int roadLength = RoadSegment.getLength();

  private int overlap = 1;

  public Level() {
    roads = new ArrayList<>();
    collidables = new ArrayList<>();
    backgroundObjects = new ArrayList<>();
    newX = GamePanel.dimX / 2;
    newY = GamePanel.dimY / 2 + roadLength / 2;
  }

  public void addUp() {
    // with overlap
    addRoad(new RoadSegment(newX, newY, 0, RoadSegment.Type.STRAIGHT));
    newY -= roadLength - overlap;
  }

  public void addUp(int n) {
    for (int i = 0; i < n; i++) {
      addUp();
    }
  }

  public void addDown() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(180), RoadSegment.Type.STRAIGHT));
    newY += roadLength - overlap;
  }

  public void addDown(int n) {
    for (int i = 0; i < n; i++) {
      addDown();
    }
  }

  public void addLeft() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(-90), RoadSegment.Type.STRAIGHT));
    newX -= roadLength - overlap;
  }

  public void addLeft(int n) {
    for (int i = 0; i < n; i++) {
      addLeft();
    }
  }

  public void addRight() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(90), RoadSegment.Type.STRAIGHT));
    newX += roadLength - overlap;
  }

  public void addRight(int n) {
    for (int i = 0; i < n; i++) {
      addRight();
    }
  }

  public void addUpRight() {
    addRoad(new RoadSegment(newX, newY, 0, RoadSegment.Type.CURVE_RIGHT));
    newX += roadWidth / 2.0 - overlap;
    newY -= roadWidth / 2.0;
  }

  public void addUpLeft() {
    addRoad(new RoadSegment(newX, newY, 0, RoadSegment.Type.CURVE_LEFT));
    newX -= roadWidth / 2.0 - overlap;
    newY -= roadWidth / 2.0;
  }

  public void addLeftDown() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(-90), RoadSegment.Type.CURVE_LEFT));
    newY += roadWidth / 2.0 - overlap;
    newX -= roadWidth / 2.0;
  }

  public void addRightDown() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(90), RoadSegment.Type.CURVE_RIGHT));
    newY += roadWidth / 2.0 - overlap;
    newX += roadWidth / 2.0;
  }

  public void addDownRight() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(-180), RoadSegment.Type.CURVE_LEFT));
    newY += roadWidth / 2.0;
    newX += roadWidth / 2.0 - overlap;
  }

  public void addDownLeft() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(-180), RoadSegment.Type.CURVE_RIGHT));
    newY += roadWidth / 2.0;
    newX -= roadWidth / 2.0 - overlap;
  }

  public void addRightUp() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(90), RoadSegment.Type.CURVE_LEFT));
    newX += roadWidth / 2.0;
    newY -= roadWidth / 2.0;
  }

  public void addLeftUp() {
    addRoad(new RoadSegment(newX, newY, Math.toRadians(-90), RoadSegment.Type.CURVE_RIGHT));
    newX -= roadWidth / 2.0 + overlap;
    newY -= roadWidth / 2.0;
  }

  public void addRoad(RoadSegment road) {
    roads.add(road);
  }

  public void addCollidable(Collidable object) {
    collidables.add(object);
  }

  public void addHouse(int x, int y, House.Orientation orientation) {
    collidables.add(new House(x, y, orientation));
    // backgroundObjects.add(new GraphicObject(x,y,"/images/HouseShadow.png"));
  }

  public void addHouseNorth(int x, int y) {
    addHouse(x, y, House.Orientation.NORTH);
  }

  public void addHouseSouth(int x, int y) {
    addHouse(x, y, House.Orientation.SOUTH);
  }

  public void addHouseEast(int x, int y) {
    addHouse(x, y, House.Orientation.EAST);
  }

  public void addHouseWest(int x, int y) {
    addHouse(x, y, House.Orientation.WEST);
  }

  public void addExplosion(int x, int y) {
    backgroundObjects.add(new GraphicObject(x, y, "/images/Explosion.png"));
  }

  public void addCheckpoint(int x, int y, House.Orientation orientation) {
    double rotation;
    if (orientation == House.Orientation.EAST || orientation == House.Orientation.WEST) {
      rotation = Math.toRadians(90);
    } else {
      rotation = 0;
    }
    addRoad(new RoadSegment(x, y, 0, RoadSegment.Type.CHECKPOINT));
    collidables.add(new House(x, y, orientation));
  }

  public void addTurnBased(int x, int y) {
    collidables.add(new RoadMob(x, y));

  }

  public void addTree(int x, int y) {
    collidables.add(new Tree(x, y));
  }

  public void addPlant(int x, int y) {
    backgroundObjects.add(new GraphicObject(x, y, 60, 60, "/images/Plant.png"));
  }

  public void addDominos(int x, int y) {
    collidables.add(new Dominos(x, y));
  }

  public void draw(Graphics2D graphics) {
    for (RoadSegment road : roads) {
      road.draw(graphics);
    }
    for (Collidable object : collidables) {
      object.draw(graphics);
    }
    for (GraphicObject backgroundObject : backgroundObjects) {
      backgroundObject.draw(graphics);
    }
  }

  public void drawRoads(Graphics2D graphics) {
    for (RoadSegment road : roads) {
      road.draw(graphics);
    }
  }

  public void drawCollidables(Graphics2D graphics) {
    for (Collidable object : collidables) {
      object.draw(graphics);
    }
  }

  public void drawBackgroundObjects(Graphics2D graphics) {
    for (GraphicObject backgroundObject : backgroundObjects) {
      backgroundObject.draw(graphics);
    }
  }

  public void addBackgroundObject(GraphicObject object) {
    backgroundObjects.add(object);
  }

  public void addTreeCluster(int x, int y) {
    addTree(x, y);
  }

  public void addCactus(int x, int y) {
    collidables.add(new Cactus(x, y));
  }

  public ArrayList<RoadSegment> getRoadSegments() {
    return roads;
  }

  public ArrayList<Collidable> getCollidables() {
    return collidables;
  }
}
