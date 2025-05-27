import java.awt.*;
import java.awt.geom.AffineTransform;

public class Compass
{
    private static final int COMP_SIZE = 100;

    public void draw(Graphics2D graphics, double headingRadians)
    {
        int x = GamePanel.dimX - COMP_SIZE - 20;
        int y = 20;
        Graphics2D copyOfGraphics = (Graphics2D)graphics.create();
        copyOfGraphics.translate(x+COMP_SIZE/2,y+COMP_SIZE/2);
        copyOfGraphics.rotate(headingRadians);

        //draws the compass
        copyOfGraphics.setColor(Color.DARK_GRAY);
        copyOfGraphics.fillOval(-COMP_SIZE / 2, -COMP_SIZE / 2, COMP_SIZE, COMP_SIZE);
        copyOfGraphics.setColor(Color.WHITE);
        copyOfGraphics.drawOval(-COMP_SIZE / 2, -COMP_SIZE / 2, COMP_SIZE, COMP_SIZE);

        //draws the needles
        //setstroke so the needles are thick
        copyOfGraphics.setStroke(new BasicStroke(4));
        int needleLength = COMP_SIZE / 2 - 10;
        copyOfGraphics.setColor(Color.RED);
        copyOfGraphics.drawLine(0, 0, 0, -needleLength); // North
        copyOfGraphics.setColor(Color.BLUE);
        copyOfGraphics.drawLine(0, 0, 0, needleLength);  // South


        copyOfGraphics.setTransform(new AffineTransform()); //resets transform so that letters wont get rotated
        copyOfGraphics.setFont(new Font("Arial", Font.BOLD, 14));
        copyOfGraphics.setColor(Color.WHITE);
        //north
        copyOfGraphics.drawString("N", x + COMP_SIZE / 2 - 5, y + 12);
        //south
        copyOfGraphics.drawString("S", x + COMP_SIZE / 2 - 5, y + COMP_SIZE - 4);
        //east
        copyOfGraphics.drawString("E", x + COMP_SIZE - 12, y + COMP_SIZE / 2 + 4);
        //west
        copyOfGraphics.drawString("W", x + 4, y + COMP_SIZE / 2 + 4);
        copyOfGraphics.dispose();
    }
}