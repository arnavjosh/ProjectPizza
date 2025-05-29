import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class Car {
  private double x;
  private double y;
  
  private int centerX;
  private int centerY;

  private int carWidth = 40;
  private int carLength = 80;

  private double headingRadians;
  private double speed;
  private double acceleration = 0.1;
  private double maxSpeed = 15.0;
  private double friction = 0.97;;
  private double steeringAngle = 0;
  private final double maxSteeringAngle = Math.toRadians(25);
  private double steeringSpeed = Math.toRadians(20);
  private final double wheelBase = 70;

  private double velocityX = 0;
  private double velocityY = 0;

  private double driftFactor = 0.87; // Lower = more drift
  private double gripFactor = 0.1;

  private BufferedImage carImage;

  private boolean accelerating;
  private boolean turningLeft;
  private boolean turningRight;
  private boolean braking;

  private GamePanel panel;

//for testing
  public String currentSegment;
  
  public Car(int startX, int startY, GamePanel panel) {
    x = startX;
    y = startY;
    centerX = startX;
    centerY = startY;
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

    /*
    //gets all of the corners
    ArrayList<Point2D> cornersAndMidsections = new ArrayList<>();
    cornersAndMidsections.add(new Point2D.Double(x - carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians),y-carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians))); //top left
    cornersAndMidsections.add(new Point2D.Double(x + carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians),y+carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians))); //top right
    cornersAndMidsections.add(new Point2D.Double(x - carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians),y-carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians))); //bottom left
    cornersAndMidsections.add(new Point2D.Double(x + carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians),y+carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians))); //bottom right

    int timesToSplit = 2;

    for(int i = 0; i < timesToSplit; i++)
    {
      int sizeBefore = cornersAndMidsections.size();
      for(int j = 0; j < sizeBefore; j++)
      {
        Point2D p1 = cornersAndMidsections.get(j);
        Point2D p2 = cornersAndMidsections.get((j+1)%cornersAndMidsections.size());
        cornersAndMidsections.add((j+1)%cornersAndMidsections.size(),getMidpoint(p1, p2));
      }
    }


    for (Point2D point : cornersAndMidsections) {
      if (mapHandler.isColliding(point)) {
        //reverses movement
        x -= speed * Math.sin(headingRadians);
        y += speed * Math.cos(headingRadians);
        speed = -1*(Math.signum(speed))*(Math.min(Math.abs(speed*0.2), 0.2));
        steeringAngle = 0;
      }
    }
    */

    for(Collidable collidable : mapHandler.getCurrentCollidables())
    {
      if(getBounds().intersects(collidable.getCollisionBox()))
      {
        //reverses movement by a minimum of 0.5
        x -= velocityX;
        y -= velocityY;
        speed = -4*(Math.signum(speed))*(Math.max(Math.abs(speed*0.2), 0.5));
        velocityX = 0;
        velocityY = 0;
        steeringAngle = 0;
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

    double desiredVelocityX = speed * Math.sin(headingRadians);
    double desiredVelocityY = -speed * Math.cos(headingRadians);

    //simulate sliding
    velocityX = velocityX * driftFactor + desiredVelocityX * gripFactor;
    velocityY = velocityY * driftFactor + desiredVelocityY * gripFactor;

    // Apply velocity
    x += velocityX;
    y += velocityY;


    //handles slowing down car if not on road (changes speed sigma boy)
    
    if (!mapHandler.isRoadAt(x, y)) {
      speed *= friction*friction*friction;
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

  private Point2D getMidpoint(Point2D p1, Point2D p2) {
    return new Point2D.Double(
      (p1.getX() + p2.getX()) / 2,
      (p1.getY() + p2.getY()) / 2
    );
  }

  public Path2D getBounds() {
    //applies rotations to points based on headingRadians (insert starstruck emoji)
    double topLeftX = (x - carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians));
    double topLeftY = (y - carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians));
    double topRightX =  (x + carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians));
    double topRightY =  (y + carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians));
    double bottomLeftX =  (x - carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians));
    double bottomLeftY =  (y - carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians));
    double bottomRightX =  (x + carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians));
    double bottomRightY =  (y + carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians));

    Path2D path = new Path2D.Double();
    path.moveTo(topLeftX, topLeftY);
    path.lineTo(topRightX, topRightY);
    path.lineTo(bottomRightX, bottomRightY);
    path.lineTo(bottomLeftX, bottomLeftY);
    path.closePath();
    return path;
  }

  //this method draws teh car at each frame and rotates the  
  public void draw(Graphics2D g2d) {
    int carWidth = 40;
    int carLength = 80;
    int bumperHeight = 10;

    

    if(carImage!=null)
    {
        //no imageobserver so we pass in null for that
        g2d.drawImage(carImage, centerX - carImage.getWidth() / 2, centerY - carImage.getHeight() / 2, carImage.getWidth(),carImage.getHeight(), null);
    }
    else{
      g2d.setColor(Color.RED);
      g2d.drawImage(carImage, centerX - carWidth / 2, centerY - carLength / 2, carImage.getWidth(),carImage.getHeight(), null);


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
