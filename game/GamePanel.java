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
  public int centerX = dimX / 2;
  public int centerY = dimY / 2;

  private long startTime;
  private long pauseStartTime = 0;
  private long totalPausedTime = 0;
  private long effectiveElapsed = 0;

  private int gainedPoints = 0;

  private boolean showPauseMenu = false;
  private boolean isPaused = false;
  private boolean firstInput = false;
  private JButton resumeButton, restartButton, quitButton;
  private JButton instructionsButton, creditsButton;
  private JButton pauseButton;

  private double animationFrame = 0;
  private boolean isAnimating = false;

  public GamePanel(ProjectPizza game) {
    this.game = game;
    setPreferredSize(new Dimension(800, 600));
    setBackground(Color.BLACK);
    // stack overflow said setfocusable will make key inputs focus better? I think
    // this will solve problems we have
    setFocusable(true);
    requestFocusInWindow();
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

    pauseButton = new JButton("Pause Menu");
    resumeButton = new JButton("Resume");
    restartButton = new JButton("Restart");
    quitButton = new JButton("Quit");
    instructionsButton = new JButton("Instructions");
    creditsButton = new JButton("Credits");
    instructionsButton.addActionListener(
        e -> JOptionPane.showMessageDialog(this, getInstructions(), "Instructions", JOptionPane.INFORMATION_MESSAGE));
    creditsButton.addActionListener(
        e -> JOptionPane.showMessageDialog(this, getCredits(), "Credits", JOptionPane.INFORMATION_MESSAGE));
    pauseButton.addActionListener(e -> pauseGame());

    resumeButton.setBounds(centerX - 75, centerY - 60, 150, 40);
    restartButton.setBounds(centerX - 75, centerY, 150, 40);
    quitButton.setBounds(centerX - 75, centerY + 60, 150, 40);
    instructionsButton.setBounds(centerX - 75, centerY + 120, 150, 40);
    creditsButton.setBounds(centerX - 75, centerY + 180, 150, 40);

    // bottom corner
    pauseButton.setBounds(dimX - 150, dimY - 100, 140, 40);

    resumeButton.addActionListener(e -> resumeGame());
    restartButton.addActionListener(e -> resetGame());
    quitButton.addActionListener(e -> System.exit(0));

    resumeButton.setVisible(false);
    restartButton.setVisible(false);
    quitButton.setVisible(false);
    instructionsButton.setVisible(false);
    creditsButton.setVisible(false);
    pauseButton.setVisible(true);

    // Absolute positioning
    setLayout(null);
    add(resumeButton);
    add(restartButton);
    add(quitButton);
    add(instructionsButton);
    add(creditsButton);
    add(pauseButton);
  }

  public void finishLevel() {
    gainedPoints = (int) effectiveElapsed;
    game.addPoints(gainedPoints);
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
    info += "Points earned: " + gainedPoints + "\n";
    JOptionPane.showMessageDialog(this, info, "Level Completed", JOptionPane.INFORMATION_MESSAGE);
  }

  private void animate(Graphics pizzaGraphic) {
    if (isAnimating) {
      if ((int) animationFrame > 10) {
        isAnimating = false;
        firstInput = false;
        levelEndScreen();
        return;
      }

      // gets the image of the animation
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
  }

  protected void paintComponent(Graphics pizzaGraphic) {
    super.paintComponent(pizzaGraphic);
    if (isAnimating) {
      animate(pizzaGraphic);
      return;
    }

    // updates dimension values
    dimX = getWidth();
    dimY = getHeight();
    centerX = dimX / 2;
    centerY = dimY / 2;

    // updates buttonlocations
    resumeButton.setBounds(centerX - 75, centerY - 60, 150, 40);
    restartButton.setBounds(centerX - 75, centerY, 150, 40);
    quitButton.setBounds(centerX - 75, centerY + 60, 150, 40);
    instructionsButton.setBounds(centerX - 75, centerY + 120, 150, 40);
    creditsButton.setBounds(centerX - 75, centerY + 180, 150, 40);

    pauseButton.setBounds(dimX - 150, dimY - 100, 140, 40);

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

    long currentTime = /* (long) ((int) */ System.currentTimeMillis();
    // sigma boy ternary
    effectiveElapsed = isPaused ? pauseStartTime - startTime - totalPausedTime
        : currentTime - startTime - totalPausedTime;

    if (!firstInput) {
      effectiveElapsed = 0;
    }

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

  public void startTurnBased() {
    this.setLayout(null);
    TurnBasedBattler battlerPanel = new TurnBasedBattler(new Lihaar(100), new Rat(100), this);
    battlerPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
    this.add(battlerPanel);
    battlerPanel.requestFocusInWindow();
    battlerPanel.setVisible(true);
    this.setComponentZOrder(battlerPanel, 0);
    this.revalidate();
    this.repaint();

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        battlerPanel.setBounds(0, 0, getWidth(), getHeight());
        battlerPanel.revalidate();
        battlerPanel.repaint();
      }
    });
  }

  public void actionPerformed(ActionEvent e) {
    // car.update(map);
    car.update(mapHandler);
    repaint();
  }

  public void keyPressed(KeyEvent e) {
    if (!firstInput) {
      firstInput = true;
      startTime = /* (long) ((int) */ System.currentTimeMillis();
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

  public void pauseTime() {
    isPaused = true;
    pauseStartTime = System.currentTimeMillis();
  }

  public void resumeTime() {
    isPaused = false;
    totalPausedTime += System.currentTimeMillis() - pauseStartTime;
    pauseStartTime = 0;
    // timer.start();
  }

  public void pauseGame() {
    pauseTime();

    showPauseMenu = true;

    setPauseButtonsVisible(isPaused);

    repaint();
  }

  public void resumeGame() {
    showPauseMenu = false;

    resumeTime();

    setPauseButtonsVisible(false);

    // request focus back to the game panel (this mehtod is so cool)
    this.setFocusable(true);
    this.requestFocusInWindow();

    repaint();
  }

  public void resumeGameTurnBased() { // other custom timer methods don't work on things relating to turn based
    this.setFocusable(true);
    this.requestFocusInWindow();

    timer.stop();
    timer.start();

    repaint();
  }

  public void resetGame() {
    isPaused = false;
    showPauseMenu = false;

    gainedPoints = 0;

    car.reset();
    car.setCollisions(0);
    startTime = /* (long) ((int) */ System.currentTimeMillis();
    totalPausedTime = 0;
    pauseStartTime = 0;
    effectiveElapsed = 0;
    firstInput = false;
    mapHandler.setLevel(0);

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
    pauseButton.setVisible(!visible);
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

  public void gameOver() {
    String message = "Game Over! You scored " + game.getPoints() + " points.";
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    game.stopGame();
    repaint();
  }
}
