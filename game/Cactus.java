import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Cactus extends Collidable {
  private static final String CACTUS_IMAGE = "images/Cactus.png";
  private static final int CACTUS_WIDTH = 50;
  private static final int CACTUS_HEIGHT = 50;

  public Cactus(int x, int y) {
    super(x, y, CACTUS_IMAGE);
    collisionBox = createCollisionBox();
  }

  @Override
  public Rectangle2D.Double createCollisionBox() {
    return new Rectangle2D.Double(x - CACTUS_WIDTH / 2.0, y - CACTUS_HEIGHT / 2.0, CACTUS_WIDTH, CACTUS_HEIGHT);
  }

  @Override
  public void drawAlt(Graphics2D g) {
    g.setColor(Color.GREEN);
    g.fillRect((int) (x - CACTUS_WIDTH / 2.0), (int) (y - CACTUS_HEIGHT / 2.0), CACTUS_WIDTH, CACTUS_HEIGHT);
  }

  public void draw(Graphics2D g) {
    g = (Graphics2D) g.create();
    // draws image with the size of the cactus width and height
    g.drawImage(getImage(), x - CACTUS_WIDTH / 2, y - CACTUS_HEIGHT / 2, CACTUS_WIDTH, CACTUS_HEIGHT, null);
  }
}
