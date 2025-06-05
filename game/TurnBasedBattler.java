import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TurnBasedBattler extends JPanel implements ActionListener { // worry about keylistener if wanted later
  Mob mc;
  Mob enemy;
  int buttonHeight = 120;
  int bottomMargin = 0; // if we want a gap between bottom of button and bottom of frame
  private JButton[] abilityButtons = new JButton[4];
  private BufferedImage tile;

  private void loadTile() {
    String tilePath;
    switch (MapHandler.getCurrentLevelNum()) {
      case 0:
        tilePath = "/images/Grass.png";
        break;
      case 1:
        tilePath = "/images/Sand.png";
        break;
      default:
        tilePath = "/images/Grass.png"; // Default to first tile
        break;
    }
    try {
      tile = ImageIO.read(getClass().getResource(tilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public TurnBasedBattler(Mob mc, Mob enemy) {
    setLayout(null); // disables auto layout so you giet more control?
    // addKeyListener(this);

    setPreferredSize(new Dimension(800, 600));

    this.mc = mc;
    this.enemy = enemy;

    int numButtons = abilityButtons.length;

    loadTile();

    // ability attacks in mc.getheroabilities
    for (int i = 0; i < mc.getMobAbilities().length; i++) {
      // display the buttons using the component resized for resize
      abilityButtons[i] = new JButton(mc.getMobAbilities()[i].getAbilityName());
      abilityButtons[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // do mob attack for given button prob with parameters forWorking with
          // Typing-related Events mob, ability, and
          // enemy if damage
          repaint();
        }
      });
      add(abilityButtons[i]);
    }
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int buttonWidth = getWidth() / numButtons;
        int y = getHeight() - buttonHeight - bottomMargin;

        for (int i = 0; i < numButtons; i++) {
          abilityButtons[i].setBounds(i * buttonWidth, y, buttonWidth, buttonHeight);
        }
        repaint();
      }
    });

    /*
     * not sure, the buttons for sure refresh every time its used obv its way more
     * efficient
     * Timer timer = new Timer(16, this); // ~60 FPS
     * timer.start();
     */

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); // it clears the screen

    // draw background
    if (tile == null) {
      return;
    }
    for (int x = 0; x < this.getWidth(); x += tile.getWidth()) {
      for (int y = 0; y < this.getHeight(); y += tile.getHeight()) {
        ((Graphics2D) g).drawImage(tile, x, y, null);
      }
    }

    mc.draw((Graphics2D) g, this.getWidth(), this.getHeight());
    enemy.draw((Graphics2D) g, this.getWidth(), this.getHeight());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  public static void main(String[] args) {
    // just for testing
    new TurnBasedBattler(new Lihaar(100), new Rat(50));
  }
}
