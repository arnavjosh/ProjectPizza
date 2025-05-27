import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//for ethan: i found this library that'll let us do the tranfroms
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private MapHandler mapHandler;
    private ProjectPizza game;
    private Compass compass;
    private Car car;

    public static final int dimX = 800;
    public static final int dimY = 600;

    public static final int centerX = dimX / 2;
    public static final int centerY = dimY / 2;

    public GamePanel(ProjectPizza game) {
        this.game = game;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        //stack overflow said setfocusable will make key inputs focus better? I think this will solve problems we have
        setFocusable(true);
        addKeyListener(this);
        mapHandler = new MapHandler();

        //adds car which is in the middle of screen
        car = new Car(centerX, centerY, this);
        compass = new Compass();

        //allows us to do an event every 16 ms (again i found this out on stackoverflow and it seems to make it work a lot better)
        //https://stackoverflow.com/questions/36387853/java-timer-swing-exactly-60-fps
        //16 ms is 60 fps
        timer = new Timer(16, this);
        timer.start();
    }

    public void finishLevel()
    {
        game.addPoints(1);
        mapHandler.incrementLevel();
    }

    protected void paintComponent(Graphics pizzaGraphic)
    {
        super.paintComponent(pizzaGraphic);
        pizzaGraphic.setColor(Color.GREEN);
        pizzaGraphic.fillRect(0, 0, getWidth(), getHeight());
        //casts a copy of the graphic to a graphic 2d which lets us transform it with the affine thingy
        Graphics2D pizza2d = (Graphics2D) pizzaGraphic.create();

        //moves origin from corner to the sigma center

        pizza2d.translate(centerX, centerY);
        pizza2d.rotate(-car.getHeading());
        pizza2d.translate(-car.getX(),-car.getY());

        mapHandler.draw(pizza2d);

        pizza2d.dispose();

        Graphics2D carGraphics = (Graphics2D)pizzaGraphic.create();
        car.draw(carGraphics);
        carGraphics.dispose();
        pizzaGraphic.setColor(Color.BLACK);
        pizzaGraphic.setFont(new Font("Arial", Font.BOLD, 16));
        pizzaGraphic.drawString("Speed: " + ((int)(car.getSpeed()*100)/100.0), 20, 30);
        

        compass.draw((Graphics2D)pizzaGraphic.create(),car.getHeading());

        //have to get rid of the graphics 2d according to stack
    }

    public void actionPerformed(ActionEvent e)
    {
        //car.update(map);
        car.update(mapHandler);
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
            car.setAccelerating(true);
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
            car.setTurningLeft(true);
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
            car.setTurningRight(true);
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
            car.setBraking(true);
        if (code == KeyEvent.VK_R){
            mapHandler = new MapHandler();
            car = new Car(dimX/2,dimY/2,this);
        }
        if (code == KeyEvent.VK_T){
            System.out.println("X: "+car.getX()+", Y: "+car.getY());
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

    public void keyTyped(KeyEvent e)
    {
        //we dont use typing i fear
    }
}