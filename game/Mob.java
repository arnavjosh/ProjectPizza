import java.awt.*;

public abstract class Mob {
  protected int hp;
  protected String name;

  public abstract void draw(Graphics g);

  public abstract Ability[] getMobAbilities();

}
