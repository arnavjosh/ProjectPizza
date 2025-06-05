import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Collidable extends GraphicObject {
  private Color color = Color.RED;
  protected Rectangle2D collisionBox;

  public Collidable(int x, int y, String imagePath) {
    super(x, y, imagePath);
  }

  public Collidable(int x, int y) {
    super(x, y);
    collisionBox = createCollisionBox();
  }

  public Collidable(int x, int y, int width, int height, String imagePath) {
    super(x, y, (int) width, (int) height, imagePath);
    collisionBox = createCollisionBox();
  }

  public abstract Rectangle2D.Double createCollisionBox();

  public void draw(Graphics2D g) {
    if (getImage() == null)
        return;
    if(width == 0 || height == 0) {
      // no imageobserver so we pass in null for that
      g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(),
          getImage().getHeight(), null);
    }
    else{
        g.drawImage(getImage(), x - width / 2, y - height / 2, width, height, null);
    }
  }

  public abstract void drawAlt(Graphics2D g);

  public Rectangle2D getCollisionBox() {
    return collisionBox;
  }
}
