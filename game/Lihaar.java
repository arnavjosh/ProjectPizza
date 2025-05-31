import java.awt.*;

public class Lihaar extends Mob {
  public Lihaar(int hp) {
    name = "Lihaar";
    this.hp = hp;
  }

  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillOval(50, 500, 60, 30);
  }

  public Ability[] getMobAbilities() {
    return new Ability[4];
  }
}
