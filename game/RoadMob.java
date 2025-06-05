// sahnoteusnthua,nthuas

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RoadMob extends Collidable {
  private int width = 300, height = 200;
  private Orientation orientation;

  public enum Orientation {
    NORTH, SOUTH, EAST, WEST
  }

  private String[] paths = {
      "/images/crazy-ugly-rat.png",
  };
  private int randomIndex = (int) (Math.random() * paths.length);

  public RoadMob(int x, int y, Orientation orientation) {
    super(x, y, 30, 20,  "/images/crazy-ugly-rat.png");
    setPath(paths[randomIndex]);
    this.orientation = orientation;
    collisionBox = createCollisionBox();
  }

  public Rectangle2D.Double createCollisionBox() {
    if (orientation == Orientation.NORTH || orientation == Orientation.SOUTH) {
      return new Rectangle2D.Double(x - width / 2.0, y - height / 2.0, width, height);
    } else {
      return new Rectangle2D.Double(x - height / 2.0, y - width / 2.0, height, width);
    }
  }

  @Override
  public void drawAlt(Graphics2D g) {
    g.fillRect(x - width / 2, y - height / 2, width, height);
  }

  @Override
  public void draw(Graphics2D g) {
    g = (Graphics2D) g.create();
    g.setColor(Color.BLUE);
    g.draw(collisionBox);
    if (orientation == Orientation.NORTH) {
      g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(),
          getImage().getHeight(), null);
    } else if (orientation == Orientation.SOUTH) {
      g.rotate(Math.PI, x, y); // Rotate for south orientation
      g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(),
          getImage().getHeight(), null);

    } else if (orientation == Orientation.EAST) {
      g.rotate(Math.PI / 2, x, y); // Rotate for east orientation
      g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(),
          getImage().getHeight(), null);
    } else if (orientation == Orientation.WEST) {
      g.rotate(-Math.PI / 2, x, y); // Rotate for west orientation
      g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(),
          getImage().getHeight(), null);
    }
  }
}
