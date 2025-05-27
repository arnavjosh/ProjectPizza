import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Car {
  private double x;
  private double y;
  private double headingRadians;
  private double speed;
  private double acceleration = 0.1;
  private double maxSpeed = 5.0;
  private double friction = 0.99;
  private double steeringAngle = 0;
  private final double maxSteeringAngle = Math.toRadians(10);
  private double steeringSpeed = Math.toRadians(3);
  private final double wheelBase = 20;
    private BufferedImage carImage;

  private boolean accelerating;
  private boolean turningLeft;
  private boolean turningRight;
  private boolean braking;

  private GamePanel panel;

//for testing
  public String currentSegment;
  
  public Car(double startX, double startY, GamePanel panel) {
    x = startX;
    y = startY;
    this.panel = panel;
    headingRadians = 0;

    try{
        carImage = ImageIO.read(getClass().getResource("/images/Car.png"));
    } catch (IOException e)
    {
        e.printStackTrace();
    }
  }

  public void update(MapHandler mapHandler) {
    for (House house : mapHandler.getCurrentHouses()) {
        if (getBounds().intersects(house.getBounds())) {
            //undoes any movment
            x -= speed * Math.sin(headingRadians);
            y += speed * Math.cos(headingRadians);
            speed = -speed*0.2;
        }
    }

    if (turningLeft) {
      steeringAngle = Math.max(steeringAngle - steeringSpeed, -maxSteeringAngle);
    } else if (turningRight) {
      steeringAngle = Math.min(steeringAngle + steeringSpeed, maxSteeringAngle);
    } else{
      if(steeringAngle>0)
        steeringAngle = Math.max(0,steeringAngle-steeringSpeed);
      else if(steeringAngle<0)
        steeringAngle = Math.min(0,steeringAngle+steeringSpeed);
    }

    if (accelerating) {
      speed = Math.min(speed+acceleration, maxSpeed);
    } else if (braking) {
      speed = Math.max(speed-acceleration, -maxSpeed/2.0);
    } else {
      //hit song friction by band imagine dragons
      speed*=friction;
    }

    if (speed!= 0 && Math.abs(steeringAngle) > 0.001)
    {
      //you can use sin to figure out the radius of the turn based on the angle you turn by
      double turnRadius = wheelBase / Math.abs(Math.sin(steeringAngle));
      double angularVelocity = speed/turnRadius;
      headingRadians += angularVelocity * Math.signum(steeringAngle);
    }

    x += speed * Math.sin(headingRadians);
    y -= speed * Math.cos(headingRadians);


    //handles slowing down car if not on road (changes speed sigma boy)
    
    if (!mapHandler.isRoadAt(x, y)) {
      speed *= friction;
    }
    RoadSegment segment = mapHandler.getSegmentAt(x, y);
    if (segment != null && segment.roadType == RoadSegment.Type.CHECKPOINT) {
        speed = 0;
        headingRadians = 0;
        x = GamePanel.dimX/2;
        y = GamePanel.dimY/2;
        panel.finishLevel();
    }

    
  }

  public Rectangle2D getBounds() {
    int carWidth = 40;
    int carLength = 80;
    return new Rectangle2D.Double(
      x - carWidth / 2.0,
      y - carLength / 2.0,
      carWidth,
      carLength
    );
  }

  //this method draws teh car at each frame and rotates the  
  public void draw(Graphics2D g2d, int centerX, int centerY) {
    int carWidth = 40;
    int carLength = 80;
    int bumperHeight = 10;

    

    if(carImage!=null)
    {
        //no imageobserver so we pass in null for that
        g2d.drawImage(carImage, centerX - carWidth / 2, centerY - carLength / 2, carWidth, carLength, null);
    }
    else{
      g2d.setColor(Color.RED);
      g2d.drawImage(carImage, centerX - carWidth / 2, centerY - carLength / 2, carWidth, carLength, null);


      g2d.setColor(Color.BLACK);
      g2d.fillRect(centerX, centerY, carWidth, bumperHeight);
    }

    g2d.setColor(Color.YELLOW);
    g2d.drawRect(centerX - carWidth / 2, centerY - carLength / 2, carWidth, carLength);
  } 

  //setters and getters :D

  public void setAccelerating(boolean accelerating) {
    this.accelerating = accelerating;
  }

  public void setTurningLeft(boolean turningLeft) {
    this.turningLeft = turningLeft;
  }

  public void setTurningRight(boolean turningRight) {
    this.turningRight = turningRight;
  }

  public void setBraking(boolean braking)
  {
    this.braking = braking;
  }

    public double getSpeed()
    {
        return speed;
    }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getHeading() {
    return headingRadians;
  }
} 
