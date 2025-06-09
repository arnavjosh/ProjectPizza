import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Lihaar extends Mob {
  private int maxHp;
  private String[] paths = {
      "/images/deliverymanturnbased.png",
  };

  public Lihaar(int hp) {
    name = "Lihaar";
    maxHp = hp;
    this.hp = hp;

    try {
      image = ImageIO.read(getClass().getResource(paths[0]));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g, int panelWidth, int panelHeight) {
    if (image == null)
      return;

    int drawWidth = panelWidth / 2;
    int drawHeight = panelHeight / 2;
    int drawX = (panelWidth - drawWidth) / 2;
    int drawY = (panelHeight - drawHeight) / 2;

    g.drawImage(image, (int) (panelWidth * .15), (int) (panelHeight * .65), panelWidth / 6, panelHeight / 6, null);

    double healthBarLength = (int) (panelWidth / 6);
    double healthBarHeight = (int) (panelHeight * .075);

    g.setColor(Color.GREEN);
    g.fillRect((int) (panelWidth * .15), (int) (panelHeight * .57), (int) (healthBarLength * hp / maxHp),
        (int) healthBarHeight);

    g.setColor(Color.BLACK);
    g.drawRect((int) (panelWidth * .15), (int) (panelHeight * .57), (int) (healthBarLength * hp / maxHp),
        (int) healthBarHeight);
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

  public void damage(int dmg) {
    hp -= dmg;
  }
}
