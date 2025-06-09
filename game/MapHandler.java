import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MapHandler {
  // TODO: write levels class
  // TODO write up background objects
  private ArrayList<Level> levels;
  private static int currentLevelNum = 0;
  private Level currentLevel;
  private BufferedImage grassTile;

  public MapHandler() {
    levels = new ArrayList<Level>();
    loadLevels();
    currentLevel = levels.get(currentLevelNum);
    loadGrassTile();
  }

  private void loadGrassTile() {
    String grassTilePath;
    switch (currentLevelNum) {
      case 0:
        grassTilePath = "/images/Grass.png";
        break;
      case 1:
        grassTilePath = "/images/Sand.png";
        break;
      default:
        grassTilePath = "/images/Grass.png"; // Default to first tile
        break;
    }
    try {
      grassTile = ImageIO.read(getClass().getResource(grassTilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setLevel(int levelNum) {
    if (levelNum >= 0 && levelNum < levels.size()) {
      currentLevelNum = levelNum;
      currentLevel = levels.get(currentLevelNum);
      loadGrassTile();
    } else {
      System.out.println("Invalid level number: " + levelNum);
    }
  }

  private void loadLevels() {
    System.out.println(levels);
    Level level0 = new Level();
    level0.addUp(5);
    level0.addUpRight();
    level0.addRight(5); // →
    level0.addRightDown();
    level0.addDown(2); // ↓
    level0.addDownLeft();
    level0.addLeft(5);
    level0.addLeftUp();
    level0.addUp(5);
    level0.addUpRight();
    level0.addRight(5);
    level0.addRightDown();
    level0.addDown(2);
    level0.addDownLeft();
    level0.addLeft(5);
    level0.addLeftUp();
    level0.addUp(5);
    level0.addUpRight();
    level0.addRight(5);
    level0.addRightDown();
    level0.addDown(2);
    level0.addDownLeft();
    level0.addLeft(5);
    level0.addLeftUp();
    level0.addUp(5);
    level0.addUpLeft();
    level0.addLeft(5);
    level0.addLeftDown();
    level0.addDown(5);
    level0.addDownRight();
    level0.addRight(5);
    level0.addRightUp();
    level0.addUp(5);
    level0.addUpRight();
    level0.addRight(5);
    level0.addRightDown();
    level0.addDown(2);
    level0.addDownLeft();
    level0.addLeft(5);
    level0.addLeftUp();

    level0.addDominos(415, 500);
    level0.addHouseNorth(675, -238); // This is the starting house
    level0.addCheckpoint(1000, -238, House.Orientation.NORTH); // This is the checkpoint house
    level0.addTurnBased(500, -2000);// for testing this is the second time im writing tehsnit
    // subtract 450 from y for each level up
    level0.addHouseNorth(675, -688);
    level0.addHouseNorth(1000, -688);
    level0.addHouseNorth(675, -1136);
    level0.addHouseNorth(1000, -1136);
    level0.addTreeCluster(550, 200);
    level0.addTreeCluster(785, 438);
    level0.addTreeCluster(743, 605);
    level0.addTreeCluster(581, 800);
    level0.addTreeCluster(260, 266);
    level0.addTreeCluster(257, 82);
    level0.addTreeCluster(253, 160);
    level0.addHouseEast(190, -180);
    level0.addHouseEast(190, -530);
    level0.addTreeCluster(213, -774);
    level0.addHouseNorth(-109, -688);
    level0.addTreeCluster(-601, -851);
    level0.addTreeCluster(-553, -793);
    level0.addTreeCluster(-396, -743);
    level0.addTreeCluster(-625, -918);
    level0.addBackgroundObject(new GraphicObject(554, -557, "/images/Explosion.png"));
    level0.addHouseEast(-697, -1194);
    level0.addHouseEast(-697, -1544);

    level0.addTreeCluster(-658, -1758);
    level0.addTreeCluster(-559, -1833);
    level0.addTreeCluster(-491, -1907);
    level0.addTreeCluster(-404, -1941);

    level0.addHouseSouth(-155, -1993);
    level0.addHouseSouth(200, -1993);
    // tree
    level0.addTreeCluster(450, -1993);
    // more houses
    level0.addHouseSouth(750, -1993);
    level0.addHouseSouth(1100, -1993);
    // TREES
    level0.addTreeCluster(1315, -1935);
    level0.addTreeCluster(1330, -1854);
    level0.addTreeCluster(1421, -1787);
    level0.addTreeCluster(1490, -1685);
    level0.addTreeCluster(1451, -1694);

    level0.addHouseWest(1488, -1427);
    level0.addHouseWest(1488, -1077);
    level0.addHouseWest(1488, -727);
    level0.addHouseWest(1488, -377);
    level0.addHouseWest(1488, -27);
    // trees

    level0.addTreeCluster(1307, 142);
    level0.addTreeCluster(1237, 182);
    level0.addTreeCluster(1112, 201);
    level0.addTreeCluster(1029, 174);
    level0.addTreeCluster(931, 117);
    level0.addTreeCluster(828, 115);
    level0.addTreeCluster(731, 134);
    level0.addTreeCluster(628, 120);
    level0.addTreeCluster(703, 210);
    level0.addTreeCluster(774, 316);
    level0.addTreeCluster(897, 316);
    level0.addTreeCluster(1009, 297);
    level0.addTreeCluster(1130, 328);
    level0.addTreeCluster(1268, 271);
    level0.addTreeCluster(1410, 209);
    level0.addTreeCluster(1546, 189);
    level0.addTreeCluster(1691, 80);
    level0.addTreeCluster(1689, -103);
    level0.addTreeCluster(1673, -268);
    level0.addTreeCluster(1664, -448);
    level0.addTreeCluster(1780, -722);
    level0.addTreeCluster(1710, -889);
    level0.addTreeCluster(1702, -1134);
    level0.addTreeCluster(1717, -1310);

    // adds explosion
    level0.addExplosion(1159, -563);
    level0.addExplosion(365, -32);
    level0.addExplosion(470, 276);
    level0.addExplosion(361, -51);
    level0.addExplosion(-298, -831);
    level0.addExplosion(-432, -961);
    level0.addExplosion(-559, -1365);
    level0.addExplosion(-542, -1691);
    level0.addExplosion(1340, -332);
    level0.addExplosion(1354, -899);
    level0.addExplosion(1223, -1243);
    level0.addExplosion(685, -1486);
    level0.addExplosion(297, -1661);
    level0.addExplosion(440, -1852);
    level0.addExplosion(-334, -1818);
    level0.addExplosion(-296, -1679);
    level0.addExplosion(-486, -970);
    level0.addExplosion(-304, -866);
    level0.addExplosion(840, 227);
    level0.addExplosion(1105, 134);
    level0.addExplosion(1211, -1105);
    level0.addExplosion(192, -1328);
    level0.addExplosion(32, -1065);
    level0.addExplosion(-732, -1244);
    level0.addExplosion(-823, -1502);
    level0.addExplosion(-771, -1807);
    level0.addExplosion(-633, -2051);
    level0.addExplosion(249, -395);
    level0.addExplosion(172, 289);
    level0.addExplosion(180, 524);
    level0.addExplosion(301, 744);
    level0.addBackgroundObject(new GraphicObject(100, 100, "/images/Crater.png"));
    levels.add(level0);

    Level level1 = new Level();
    level1.addDominos(415, 500);
    // grid of roads, no comments on lines
    level1.addUp(5);
    level1.addUpRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(5);
    level1.addLeftUp();
    level1.addUp(5);
    level1.addUpRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(5);
    // ADDS GRID on other side
    level1.addLeftUp();
    level1.addUp(5);
    level1.addUpRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(5);
    level1.addLeftUp();
    level1.addUp(5);
    level1.addUpRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(10);
    level1.addLeftDown();
    level1.addDown(2);
    level1.addDownRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(5);
    level1.addLeftUp();
    level1.addUp(2);
    level1.addUpRight();
    level1.addRight(5);
    level1.addRightDown();
    level1.addDown(2);
    level1.addDownLeft();
    level1.addLeft(5);
    level1.addLeftDown();
    level1.addDown(2);
    level1.addDownRight();
    level1.addRight(5);
    level1.addCactus(541, 303);
    level1.addCactus(585, 213);
    level1.addCactus(559, 139);
    level1.addCactus(276, 107);
    level1.addCactus(268, 290);
    level1.addCactus(240, 200);

    level1.addHouseNorth(44, 201);
    level1.addHouseSouth(29, -255);
    level1.addHouseEast(-622, -178);
    level1.addHouseEast(-622, -598);
    level1.addHouseEast(-622, -1018);
    level1.addHouseSouth(-147, -1558);
    level1.addHouseSouth(950, -1558);
    level1.addHouseEast(181, -1638);
    level1.addHouseSouth(583, -2008);
    level1.addHouseSouth(1100, -2008);
    level1.addCheckpoint(1510, -1548, House.Orientation.WEST);
    level1.addHouseWest(1510, -1048);
    level1.addHouseWest(1510, -548);
    level1.addHouseWest(1510, -48);
    level1.addHouseNorth(654, -1092);
    level1.addHouseSouth(893, -686);
    level1.addHouseNorth(636, -211);
    level1.addPlant(776, 262);
    level1.addPlant(948, 259);
    level1.addPlant(1083, 206);
    level1.addPlant(1275, 143);
    level1.addPlant(1357, -235);
    level1.addPlant(1514, -351);
    level1.addPlant(1618, -367);
    level1.addPlant(1705, -557);
    level1.addPlant(1609, -805);
    level1.addPlant(1445, -836);
    level1.addPlant(1020, -1009);
    level1.addPlant(892, -1122);
    level1.addPlant(815, -1237);
    level1.addPlant(668, -1445);
    level1.addPlant(587, -1683);
    level1.addPlant(322, -1443);
    level1.addPlant(107, -1426);
    level1.addPlant(110, -1136);

    level1.addPlant(288, -1130);
    level1.addPlant(-75, -999);
    level1.addPlant(-142, -752);
    level1.addPlant(28, -708);
    level1.addPlant(189, -637);
    level1.addPlant(275, -354);
    level1.addPlant(1297, -1888);
    level1.addPlant(1436, -1835);

    level1.addPlant(847, -1982);
    level1.addPlant(667, -2225);
    level1.addPlant(393, -2163);
    level1.addPlant(341, -1890);
    level1.addPlant(-311, -1389);
    level1.addPlant(-581, -1613);
    level1.addPlant(-663, -1475);
    level1.addPlant(-545, -1288);
    level1.addPlant(-455, -1022);
    level1.addPlant(-475, -865);
    level1.addPlant(-462, -703);
    level1.addPlant(-461, -551);
    level1.addPlant(-472, -380);
    level1.addPlant(-459, -188);
    level1.addPlant(-450, -55);

    level1.addPlant(-331, 124);
    level1.addPlant(-198, 152);
    level1.addPlant(-146, 434);
    level1.addPlant(95, 440);

    levels.add(level1);
  }

  public void draw(Graphics2D gameGraphics) {
    currentLevel.draw(gameGraphics);
  }

  public void drawRoads(Graphics2D gameGraphics) {
    currentLevel.drawRoads(gameGraphics);
  }

  public void drawCollidables(Graphics2D gameGraphics) {
    currentLevel.drawCollidables(gameGraphics);
  }

  public void drawBackground(Graphics2D gameGraphics, Car car) {
    if (grassTile == null)
      return;

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

  public RoadSegment getSegmentAt(double x, double y) {
    for (RoadSegment road : currentLevel.getRoadSegments()) {
      if (road.contains(x, y))
        return road;
    }
    return null;
  }

  public boolean isRoadAt(double x, double y) {
    return getSegmentAt(x, y) != null;
  }

  public ArrayList<Collidable> getCurrentCollidables() {
    return currentLevel.getCollidables();
  }

  public void incrementLevel() {
    // loops level using mod
    currentLevelNum = (currentLevelNum + 1) % levels.size();
    currentLevel = levels.get(currentLevelNum);
    loadGrassTile();
  }

  public double getCheckpointX() {
    for (RoadSegment road : currentLevel.getRoadSegments()) {
      if (road.roadType == RoadSegment.Type.CHECKPOINT) {
        return (int) road.getX();
      }
    }
    return 0;
  }

  public double getCheckpointY() {
    for (RoadSegment road : currentLevel.getRoadSegments()) {
      if (road.roadType == RoadSegment.Type.CHECKPOINT) {
        return (int) road.getY();
      }
    }
    return 0;
  }

  public static int getCurrentLevelNum() {
    return currentLevelNum;
  }
}
