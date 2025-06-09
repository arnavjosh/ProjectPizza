import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Rat extends Mob {
  private int maxHp;
  private String[] paths = {
      "/images/ratturnbased.png",
  };

  public Rat(int hp) {
    name = "RadioShacktive Rat";
    maxHp = hp;
    this.hp = hp;

    try {
      image = ImageIO.read(getClass().getResource(paths[0]));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getHealth() {
    return hp;
  }

  public void draw(Graphics2D g, int panelWidth, int panelHeight) {
    if (image == null)
      return;

    g.drawImage(image, (int) (panelWidth * .7), (int) (panelHeight * .175), panelWidth / 6, panelHeight / 6, null);

    double healthBarLength = (int) (panelWidth / 6);
    double healthBarHeight = (int) (panelHeight * .075);

    g.setColor(Color.GREEN);
    g.fillRect((int) (panelWidth * .7), (int) (panelHeight * .095), (int) (healthBarLength * hp / maxHp),
        (int) healthBarHeight);

    g.setColor(Color.BLACK);
    g.drawRect((int) (panelWidth * .7), (int) (panelHeight * .095), (int) (healthBarLength * hp / maxHp),
        (int) healthBarHeight);
  }

  public Ability[] getMobAbilities() {
    Ability[] abs = new Ability[4];
    for (int i = 0; i < abs.length; i++) {
      abs[i] = new Ability("abilitytest" + (i + 1), 5);
    }

    return abs;
  }

  public void damage(int dmg) {
    hp -= dmg;
  }
}
