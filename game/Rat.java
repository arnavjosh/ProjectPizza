import java.awt.*;

public class Rat extends Mob {
  public Rat(int hp) {
    name = "RadioShacktive Rat";
    this.hp = hp;
  }

  public void draw(Graphics g) { // for now only one rat, when theres more more logic is needed to make sure they
                                 // go where they supposed to be
    g.setColor(Color.DARK_GRAY);
    g.fillOval(650, 100, 60, 30);
  }

  public Ability[] getMobAbilities() {
    return null;
  }
}
