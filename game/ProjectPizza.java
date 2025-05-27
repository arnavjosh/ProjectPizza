import javax.swing.JFrame;
import java.awt.BorderLayout;

public class ProjectPizza implements JavaArcade
{
    boolean isRunning;
    GamePanel gamePanel;
    GameStats display;
    int points;
    String highScore;

    public ProjectPizza() {
        isRunning = true;
        points = 0;
        highScore = "0";
        gamePanel = new GamePanel(this);
        display = new GameStats(this);

        JFrame frame = new JFrame("Project Pizza");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(display, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean running()
    {
      return isRunning;
    }

    public void startGame()
    {
      isRunning = true;
      points = 0;
      display.update(points);
    }

    public void pauseGame()
    {
      isRunning = false;
    }

    public void stopGame()
    {
      isRunning = false;
      display.gameOver(points);
      points = 0;
    }
    
    public String getGameName() {
        return "Project Pizza";
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String score) {
        highScore = score;
    }

    public int getPoints()
    {
      return points;
    }

    public void addPoints(int pts)
    {
      points+=pts;
      if (display != null)
      {
        display.update(points);
      }
    }

    public void setDisplay(GameStats d)
    {
      display = d;
    }

    public String getInstructions()
    {
      return "figure it out its not that hard";
    }

    public String getCredits()
    {
      return "Authors: AJ and ET";
    }

    public static void main(String[] args) {
        new ProjectPizza();
    }
}