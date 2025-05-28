import java.awt.*;
import java.awt.geom.AffineTransform;

public class Compass
{
    private static final int COMP_SIZE = 100;
    public void draw(Graphics2D graphics, double headingRadians, double targetX, double targetY, double carX, double carY)
    {
        int x = GamePanel.dimX - COMP_SIZE - 20;
        int y = 20;
        Graphics2D copyOfGraphics = (Graphics2D)graphics.create();
        copyOfGraphics.translate(x+COMP_SIZE/2,y+COMP_SIZE/2);
        copyOfGraphics.setColor(Color.DARK_GRAY);
        copyOfGraphics.fillOval(-COMP_SIZE / 2, -COMP_SIZE / 2, COMP_SIZE, COMP_SIZE);
        copyOfGraphics.setColor(Color.WHITE);
        copyOfGraphics.drawOval(-COMP_SIZE / 2, -COMP_SIZE / 2, COMP_SIZE, COMP_SIZE);
        
        int needleLength = COMP_SIZE / 2 - 10;
        
        double dx = targetX - carX;
        double dy = targetY - carY;
        double targetAngle = Math.atan2(dx, -dy);
        double relativeAngle = targetAngle - headingRadians;
        int arrowLength = needleLength - 10;
        int endX = (int)(Math.sin(relativeAngle) * arrowLength);
        int endY = (int)(-Math.cos(relativeAngle) * arrowLength);
        copyOfGraphics.setColor(Color.YELLOW);
        copyOfGraphics.setStroke(new BasicStroke(3));
        copyOfGraphics.drawLine(0, 0, endX, endY);
        copyOfGraphics.rotate(headingRadians);

        //draws the compass

        //draws the needles
        //setstroke so the needles are thick
        copyOfGraphics.setStroke(new BasicStroke(4));
        copyOfGraphics.setColor(Color.RED);
        copyOfGraphics.drawLine(0, 0, 0, -needleLength); // North
        copyOfGraphics.setColor(Color.BLUE);
        copyOfGraphics.drawLine(0, 0, 0, needleLength);
    }
}