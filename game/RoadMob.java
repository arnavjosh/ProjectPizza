// sahnoteusnthua,nthuas

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RoadMob extends Collidable {
  private int width = 300, height = 200;

  private String[] paths = {
      "/images/crazy-ugly-rat.png",
  };
  private int randomIndex = (int) (Math.random() * paths.length);

  public RoadMob(int x, int y) {
    super(x, y, 300, 200,  "/images/crazy-ugly-rat.png");
    setPath(paths[randomIndex]);
    collisionBox = createCollisionBox();
  }

  public Rectangle2D.Double createCollisionBox() {
      return new Rectangle2D.Double(x - width / 2.0, y - height / 2.0, width, height);
  }

  @Override
  public void drawAlt(Graphics2D g) {
    g.fillRect(x - width / 2, y - height / 2, width, height);
  }
}
