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

  private int startX;
  private int startY;

  private int carWidth = 50;
  private int carLength = 100;

  private double headingRadians;
  private double speed;
  private double acceleration = 0.2;
  private double maxSpeed = 15.0;
  private double friction = 0.97;;
  private double steeringAngle = 0;
  private final double maxSteeringAngle = Math.toRadians(25);
  private double steeringSpeed = Math.toRadians(2.5);
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

  private boolean onRoad = true;
  private int numCollisions = 0;

  private GamePanel panel;

  // for testing
  public String currentSegment;

  public Car(int startX, int startY, GamePanel panel) {
    x = startX;
    y = startY;
    centerX = startX;
    centerY = startY;
    this.startX = startX;
    this.startY = startY;
    this.panel = panel;
    headingRadians = 0;

    try {
      carImage = ImageIO.read(getClass().getResource("/images/Car.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update(MapHandler mapHandler) {

    centerX = GamePanel.dimX / 2;
    centerY = GamePanel.dimY / 2;
    for (Collidable collidable : mapHandler.getCurrentCollidables()) {
      if (getBounds().intersects(collidable.getCollisionBox())) {

        // moves car away from the center of the collidable by a given distance
        double dx = x - collidable.getCollisionBox().getCenterX();
        double dy = y - collidable.getCollisionBox().getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double moveDistance = 4; // Distance to move away from the collidable
        if (distance > 0) {
          dx /= distance;
          dy /= distance;
        } else {
          dx = 0;
          dy = 0;
        }
        x += dx * moveDistance;
        y += dy * moveDistance;

        speed = -1 * (Math.signum(speed)) * (Math.max(Math.abs(speed * 0.2), 0.5));
        velocityX = 0;
        velocityY = 0;
        steeringAngle = 0;
        //checks if it collided into a roadmob
        if( collidable instanceof RoadMob) {
          panel.StartTurnBased();
        }
        numCollisions++;
        if (numCollisions >= 100) {
          // if you hit too many times, reset the car
          reset();
          numCollisions = 0;
        }

      }
    }

    if (!panel.paused()) {

      if (turningLeft) {
        steeringAngle = Math.max(steeringAngle - steeringSpeed, -maxSteeringAngle);
      } else if (turningRight) {
        steeringAngle = Math.min(steeringAngle + steeringSpeed, maxSteeringAngle);
      } else {
        if (steeringAngle > 0)
          steeringAngle = Math.max(0, steeringAngle - steeringSpeed);
        else if (steeringAngle < 0)
          steeringAngle = Math.min(0, steeringAngle + steeringSpeed);
      }

      if (accelerating) {
        speed = Math.min(speed + acceleration, maxSpeed);
      } else if (braking) {
        speed = Math.max(speed - acceleration, -maxSpeed / 2.0);
      } else {
        // hit song friction by band imagine dragons
        speed *= friction;
      }

      if (speed != 0 && Math.abs(steeringAngle) > 0.001) {
        // you can use sin to figure out the radius of the turn based on the angle you
        // turn by
        double turnRadius = wheelBase / Math.abs(Math.sin(steeringAngle));
        double angularVelocity = speed / turnRadius;
        headingRadians += angularVelocity * Math.signum(steeringAngle);
      }

      double desiredVelocityX = speed * Math.sin(headingRadians);
      double desiredVelocityY = -speed * Math.cos(headingRadians);

      // simulate sliding
      velocityX = velocityX * driftFactor + desiredVelocityX * gripFactor;
      velocityY = velocityY * driftFactor + desiredVelocityY * gripFactor;

      // Apply velocity
      x += velocityX;
      y += velocityY;

    }

    // handles slowing down car if not on road (changes speed sigma boy)

    if (!mapHandler.isRoadAt(x, y)) {
      onRoad = false;
      speed *= friction * friction;
    } else {
      onRoad = true;
    }
    double frontOfCarX = x + carLength / 2 * Math.sin(headingRadians);
    double frontOfCarY = y - carLength / 2 * Math.cos(headingRadians);
    RoadSegment segment = mapHandler.getSegmentAt(frontOfCarX, frontOfCarY);
    if (segment != null && segment.roadType == RoadSegment.Type.CHECKPOINT) {
      nextLevel();
    }
    if (segment != null && segment.roadType == RoadSegment.Type.TURNBASEDTEST) {
      panel.StartTurnBased();
    }
  }

  public void nextLevel() {
    reset();
    panel.finishLevel();

  }

  public Path2D getBounds() {
    // applies rotations to points based on headingRadians (insert starstruck emoji)
    double topLeftX = (x - carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians));
    double topLeftY = (y - carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians));
    double topRightX = (x + carWidth / 2 * Math.cos(headingRadians) - carLength / 2 * Math.sin(headingRadians));
    double topRightY = (y + carWidth / 2 * Math.sin(headingRadians) + carLength / 2 * Math.cos(headingRadians));
    double bottomLeftX = (x - carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians));
    double bottomLeftY = (y - carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians));
    double bottomRightX = (x + carWidth / 2 * Math.cos(headingRadians) + carLength / 2 * Math.sin(headingRadians));
    double bottomRightY = (y + carWidth / 2 * Math.sin(headingRadians) - carLength / 2 * Math.cos(headingRadians));

    Path2D path = new Path2D.Double();
    path.moveTo(topLeftX, topLeftY);
    path.lineTo(topRightX, topRightY);
    path.lineTo(bottomRightX, bottomRightY);
    path.lineTo(bottomLeftX, bottomLeftY);
    path.closePath();
    return path;
  }

  public void reset() {
    speed = 0;
    headingRadians = 0;
    x = startX;
    y = startY;
    accelerating = false;
    turningLeft = false;
    turningRight = false;
    braking = false;
    onRoad = true;
    numCollisions = 0;
    velocityX = 0;
    velocityY = 0;
  }

  // this method draws teh car at each frame and rotates the
  public void draw(Graphics2D g2d) {
    int carWidth = 40;
    int carLength = 80;
    int bumperHeight = 10;

    if (carImage != null) {
      // no imageobserver so we pass in null for that
      g2d.drawImage(carImage, centerX - carImage.getWidth() / 2, centerY - carImage.getHeight() / 2,
          carImage.getWidth(), carImage.getHeight(), null);
    } else {
      g2d.setColor(Color.RED);
      g2d.drawImage(carImage, centerX - carWidth / 2, centerY - carLength / 2, carImage.getWidth(),
          carImage.getHeight(), null);

      g2d.setColor(Color.BLACK);
      g2d.fillRect(centerX, centerY, carWidth, bumperHeight);
    }
  }

  // setters and getters :D

  public void setAccelerating(boolean accelerating) {
    this.accelerating = accelerating;
  }

  public void setTurningLeft(boolean turningLeft) {
    this.turningLeft = turningLeft;
  }

  public void setTurningRight(boolean turningRight) {
    this.turningRight = turningRight;
  }

  public void setBraking(boolean braking) {
    this.braking = braking;
  }

  public void setCollisions(int numCollisions) {
    this.numCollisions = numCollisions;
  }

  public double getSpeed() {
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

  public boolean isOnRoad() {
    return onRoad;
  }

  public int getCollisions() {
    return numCollisions;
  }
}
