import java.awt.*;

public class Lihaar extends Mob {
  public Lihaar(int hp) {
    name = "Lihaar";
    this.hp = hp;
  }

  public void draw(Graphics g, int pwidth, int pheight) {
    g.setColor(Color.BLACK);
    g.fillOval((int) (pwidth * .15), (int) (pheight * .6), 60, 120);
  }

  public Ability[] getMobAbilities() {
    Ability[] abs = new Ability[4];
    for (int i = 0; i < abs.length; i++) {
      abs[i] = new Ability("abilitytest " + (i + 1), 20);
    }

    abs[0] = new Ability("Pizza Cutter Slice", 20);
    abs[1] = new Ability("Eat a Pizza Slice", 0, 15);
    abs[2] = new Ability("Cheese Eye Squirt", 40);
    abs[3] = new Ability("something idk", 35, 10);
    return abs;
  }
}
