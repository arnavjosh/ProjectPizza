import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mob {
  protected int hp;
  protected String name;
  protected BufferedImage image;

  public abstract void draw(Graphics2D g, int panelWidth, int panelHeight);

  public abstract Ability[] getMobAbilities();

  public abstract void damage(int dmg);
}
