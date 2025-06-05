import java.awt.*;

public abstract class Mob {
  protected int hp;
  protected String name;
  protected Image image;

  public abstract void draw(Graphics2D g, int panelWidth, int panelHeight);

  public abstract Ability[] getMobAbilities();

}
