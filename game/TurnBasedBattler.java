import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TurnBasedBattler extends JPanel implements ActionListener { // worry about keylistener if wanted later
  Mob mc;
  Mob enemy;

  private JButton[] abilityButtons = new JButton[4];

  public TurnBasedBattler(Mob mc, Mob enemy) {

    setLayout(null); // disables auto layout so you giet more control?
    // addKeyListener(this);

    setPreferredSize(new Dimension(800, 600));

    this.mc = mc;
    this.enemy = enemy;
    // attackButton.setBounds(x, y, width, height);

    // ability attacks in mc.getheroabilities
    for (int i = 0; i < mc.getMobAbilities().length; i++) {
      // display the buttons i guess
      abilityButtons[i] = new JButton(mc.getMobAbilities()[i].getAbilityName());

      abilityButtons[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // do mob attack for given button prob with parameters forWorking with
          // Typing-related Events mob, ability, and
          // enemy if damage
          repaint();
        }
      });

    }

    /*
     * not sure, the buttons for sure refresh every time its used obv its way more
     * efficient
     * Timer timer = new Timer(16, this); // ~60 FPS
     * timer.start();
     */

  }

  public void paintComponent(Graphics g) {
    // super.paintComponent(abilityPanel); -- it clears the screen

    mc.draw(g);
    enemy.draw(g);
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
