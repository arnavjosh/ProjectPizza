import java.awt.*;

public abstract class Mob {
  protected int hp;
  protected String name;

  public abstract void draw(Graphics g, int pwidth, int pheight);

  public abstract Ability[] getMobAbilities();

}
