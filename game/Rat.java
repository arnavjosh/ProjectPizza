import java.awt.*;

public class Rat extends Mob {
  public Rat(int hp) {
    name = "RadioShacktive Rat";
    this.hp = hp;
  }

  public void draw(Graphics g, int pwidth, int pheight) { // for now only one rat, when theres more more logic is needed
                                                          // // to make sure they
    // go where they supposed to be
    g.setColor(Color.RED);
    g.fillOval((int) (pwidth * .75), (int) (pheight * .15), 60, 120);
  }

  public Ability[] getMobAbilities() {
    return null;
  }
}
