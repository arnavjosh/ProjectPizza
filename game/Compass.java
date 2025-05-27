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


        // Reset transform so text is not rotated
        copyOfGraphics.setTransform(new AffineTransform());
        copyOfGraphics.translate(x, y); // Top-left corner of compass

        copyOfGraphics.setFont(new Font("Arial", Font.BOLD, 14));
        copyOfGraphics.setColor(Color.WHITE);
        //north
        copyOfGraphics.drawString("N", COMP_SIZE / 2 - 5, 12);
        //south
        copyOfGraphics.drawString("S", COMP_SIZE / 2 - 5, COMP_SIZE - 4);
        //east
        copyOfGraphics.drawString("E", COMP_SIZE - 12, COMP_SIZE / 2 + 4);
        //west
        copyOfGraphics.drawString("W", 4, COMP_SIZE / 2 + 4);
        copyOfGraphics.dispose();
    }
}