import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
  private Timer timer;
  private MapHandler mapHandler;
  private ProjectPizza game;
  private Compass compass;
  private Car car;

  public static int dimX = 800;
  public static int dimY = 600;

  public static int centerX = dimX / 2;
  public static int centerY = dimY / 2;

  private int startTime;
  private int pauseStartTime = 0;
  private int totalPausedTime = 0;
  private int effectiveElapsed = 0;

  private JButton instructionsButton, creditsButton;
  private boolean showPauseMenu = false;
  private boolean isPaused = false;
  private boolean firstInput = false;
  private JButton resumeButton, restartButton, quitButton;

  private double animationFrame = 0;
  private boolean isAnimating = false;

  public GamePanel(ProjectPizza game) {
    this.game = game;
    setPreferredSize(new Dimension(800, 600));
    setBackground(Color.BLACK);
    // stack overflow said setfocusable will make key inputs focus better? I think
    // this will solve problems we have
    setFocusable(true);
    addKeyListener(this);
    mapHandler = new MapHandler();

    // adds car which is in the middle of screen
    car = new Car(centerX, centerY, this);
    compass = new Compass();

    // allows us to do an event every 16 ms (again i found this out on stackoverflow
    // and it seems to make it work a lot better)
    // https://stackoverflow.com/questions/36387853/java-timer-swing-exactly-60-fps
    // 16 ms is 60 fps
    timer = new Timer(16, this);
    timer.start();

    resumeButton = new JButton("Resume");
    restartButton = new JButton("Restart");
    quitButton = new JButton("Quit");

    resumeButton.setBounds(centerX - 75, centerY - 60, 150, 40);
    restartButton.setBounds(centerX - 75, centerY, 150, 40);
    quitButton.setBounds(centerX - 75, centerY + 60, 150, 40);

    resumeButton.addActionListener(e -> resumeGame());
    restartButton.addActionListener(e -> resetGame());
    quitButton.addActionListener(e -> System.exit(0));

    resumeButton.setVisible(false);
    restartButton.setVisible(false);
    quitButton.setVisible(false);

    // Absolute positioning
    setLayout(null);
    add(resumeButton);
    add(restartButton);
    add(quitButton);

    instructionsButton = new JButton("Instructions");
    creditsButton = new JButton("Credits");

    instructionsButton.setBounds(centerX - 75, centerY + 120, 150, 40);
    creditsButton.setBounds(centerX - 75, centerY + 180, 150, 40);

    instructionsButton.addActionListener(
        e -> JOptionPane.showMessageDialog(this, getInstructions(), "Instructions", JOptionPane.INFORMATION_MESSAGE));

    creditsButton.addActionListener(
        e -> JOptionPane.showMessageDialog(this, getCredits(), "Credits", JOptionPane.INFORMATION_MESSAGE));

    instructionsButton.setVisible(false);
    creditsButton.setVisible(false);

    add(instructionsButton);
    add(creditsButton);

  }

  public void finishLevel() {
    game.addPoints(1000 / (int) ((int) (System.currentTimeMillis() - startTime) / 1000.0));
    mapHandler.incrementLevel();
    firstInput = false;
    totalPausedTime = 0;
    pauseStartTime = 0;
    animationFrame = 0;
    isAnimating = true;
  }

  public void levelEndScreen() {
    // jOptionPane for this
    String info;
    info = "Level " + mapHandler.getCurrentLevelNum() + " completed!\n";
    info += "Time taken: " + effectiveElapsed / 1000.0 + " seconds\n";
    info += "Collisions: " + car.getCollisions() + "\n";
    info += "Points earned: " + (1000 / (int) ((int) (System.currentTimeMillis() - startTime) / 1000.0)) + "\n";
    JOptionPane.showMessageDialog(this, info, "Level Completed", JOptionPane.INFORMATION_MESSAGE);
  }

  protected void paintComponent(Graphics pizzaGraphic) {
    if (isAnimating) {
      if ((int) animationFrame > 10) {
        isAnimating = false;
        firstInput = false;
        levelEndScreen();
        return;
      }
      BufferedImage animationImage;
      try {
        animationImage = ImageIO.read(getClass().getResource("/Anmation/sprite_0" + ((int) animationFrame) + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
        return; // If the image fails to load, just return
      }
      // draws animation but preserves the dimensions of the panel
      pizzaGraphic.setColor(Color.BLACK);
      pizzaGraphic.fillRect(0, 0, getWidth(), getHeight());
      int imageWidth = animationImage.getWidth();
      int imageHeight = animationImage.getHeight();
      int x = (getWidth() - imageWidth) / 2;
      int y = (getHeight() - imageHeight) / 2;
      pizzaGraphic.drawImage(animationImage, x, y, imageWidth, imageHeight, null);
      animationFrame += 0.2;
      return; // Skip the rest of the painting if animating
    }
    super.paintComponent(pizzaGraphic);
    dimX = getWidth();
    dimY = getHeight();
    centerX = dimX / 2;
    centerY = dimY / 2;
    resumeButton.setBounds(centerX - 75, centerY - 60, 150, 40);
    restartButton.setBounds(centerX - 75, centerY, 150, 40);
    quitButton.setBounds(centerX - 75, centerY + 60, 150, 40);
    instructionsButton.setBounds(centerX - 75, centerY + 120, 150, 40);
    creditsButton.setBounds(centerX - 75, centerY + 180, 150, 40);
    pizzaGraphic.setColor(Color.GREEN);
    pizzaGraphic.fillRect(0, 0, getWidth(), getHeight());
    // casts a copy of the graphic to a graphic 2d which lets us transform it with
    // the affine thingy
    Graphics2D pizza2d = (Graphics2D) pizzaGraphic.create();

    // moves origin from corner to the sigma center

    pizza2d.translate(centerX, centerY);
    pizza2d.rotate(-car.getHeading());
    pizza2d.translate(-car.getX(), -car.getY());
    mapHandler.drawBackground(pizza2d, car);
    mapHandler.drawRoads(pizza2d);
    Graphics2D carGraphics = (Graphics2D) pizzaGraphic.create();
    car.draw(carGraphics);
    mapHandler.drawCollidables(pizza2d);
    // draws thick yellow boundary around the screen if car is off road
    if (!car.isOnRoad()) {
      carGraphics.setColor(Color.YELLOW);
      carGraphics.setStroke(new BasicStroke(25));
      carGraphics.drawRect(0, 0, dimX, dimY);
    }

    pizza2d.dispose();

    carGraphics.dispose();
    pizzaGraphic.setColor(Color.YELLOW);
    pizzaGraphic.setFont(new Font("Serif", Font.BOLD, 25));
    pizzaGraphic.drawString("Speed: " + ((int) (car.getSpeed() * 100) / 100.0), 20, 30);

    int currentTime = (int) System.currentTimeMillis();
    // sigma boy ternary
    effectiveElapsed = isPaused ? pauseStartTime - startTime - totalPausedTime
        : currentTime - startTime - totalPausedTime;

    if (!firstInput) {
      effectiveElapsed = 0;
    }

    // prints the dimensions of the screen
    pizzaGraphic.drawString("Dimensions: " + dimX + "x" + dimY, 20, 50);
    System.out.println("Dimensions: " + dimX + "x" + dimY);

    pizzaGraphic.drawString("Time: " + (effectiveElapsed / 1000.0) + " seconds", 20, 70);

    pizzaGraphic.drawString("Collisions: " + car.getCollisions(), 20, 110);
    compass.draw((Graphics2D) pizzaGraphic.create(), car.getHeading(), mapHandler.getCheckpointX(),
        mapHandler.getCheckpointY(), car.getX(), car.getY());

    // have to get rid of the graphics 2d according to stack

    if (showPauseMenu) {
      Graphics2D g2 = (Graphics2D) pizzaGraphic.create();
      g2.setColor(new Color(0, 0, 0, 150)); // translucent dark overlay
      g2.fillRect(0, 0, getWidth(), getHeight());

      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Arial", Font.BOLD, 36));
      drawCenteredString(g2, "Game Paused", getWidth(), centerY - 100);
      g2.dispose();
    }
  }

  public void StartTurnBased() {
    finishLevel();
    isAnimating = false;

    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

    TurnBasedBattler battlerPanel = new TurnBasedBattler(new Lihaar(100), new Rat(50));

    frame.setContentPane(battlerPanel);
    frame.revalidate();
    frame.repaint();
  }

  public void actionPerformed(ActionEvent e) {
    // car.update(map);
    car.update(mapHandler);
    repaint();
  }

  public void keyPressed(KeyEvent e) {
    if (!firstInput) {
      firstInput = true;
      startTime = (int) System.currentTimeMillis();
    }
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
      car.setAccelerating(true);
    if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
      car.setTurningLeft(true);
    if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
      car.setTurningRight(true);
    if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
      car.setBraking(true);
    if (code == KeyEvent.VK_R) {
      car.reset();
    }
    if (code == KeyEvent.VK_T) {
      System.out.println("X: " + car.getX() + ", Y: " + car.getY());
    }
    if (code == KeyEvent.VK_P) {
      if (!showPauseMenu) {
        pauseGame();
      } else {
        resumeGame();
      }
    }
  }

  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
      car.setAccelerating(false);
    if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
      car.setTurningLeft(false);
    if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
      car.setTurningRight(false);
    if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
      car.setBraking(false);
  }

  public void keyTyped(KeyEvent e) {
    // we dont use typing i fear
  }

  public void pauseGame() {
    isPaused = true;
    showPauseMenu = true;

    pauseStartTime = (int) System.currentTimeMillis();

    setPauseButtonsVisible(isPaused);

    repaint();
  }

  public void resumeGame() {
    isPaused = false;
    showPauseMenu = false;

    totalPausedTime += System.currentTimeMillis() - pauseStartTime;

    setPauseButtonsVisible(false);

    // request focus back to the game panel (this mehtod is so cool)
    this.requestFocus();
    repaint();
  }

  public void resetGame() {
    isPaused = false;
    showPauseMenu = false;

    car.reset();
    car.setCollisions(0);
    startTime = (int) System.currentTimeMillis();
    totalPausedTime = 0;
    pauseStartTime = 0;

    setPauseButtonsVisible(false);

    this.requestFocus();
    repaint();
  }

  private void setPauseButtonsVisible(boolean visible) {
    resumeButton.setVisible(visible);
    restartButton.setVisible(visible);
    quitButton.setVisible(visible);
    instructionsButton.setVisible(visible);
    creditsButton.setVisible(visible);
  }

  private void drawCenteredString(Graphics2D g2, String text, int panelWidth, int y) {
    // fontmetrics lets you get the width of the string in pixels
    FontMetrics metrics = g2.getFontMetrics(g2.getFont());
    int textWidth = metrics.stringWidth(text);
    int x = (panelWidth - textWidth) / 2;
    g2.drawString(text, x, y);
  }

  public String getInstructions() {
    return game.getInstructions();
  }

  public String getCredits() {
    return game.getCredits();
  }

  public boolean paused() {
    return isPaused;
  }
}
