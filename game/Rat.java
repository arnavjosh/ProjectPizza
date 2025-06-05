import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Rat extends Mob {
  private String[] paths = {
      "/images/ratturnbased.png",
  };

  public Rat(int hp) {
    name = "RadioShacktive Rat";
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

    g.drawImage(image, (int) (panelWidth * .7), (int) (panelHeight * .175), panelWidth / 6, panelHeight / 6, null);
  }

  public Ability[] getMobAbilities() {
    return null;
  }
}
