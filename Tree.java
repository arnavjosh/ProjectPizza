import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class Tree extends BackgroundObject
{
    private static final double TREE_RADIUS = 20;

    public Tree(double x, double y)
    {
        super (x,y,0);
    }

    public void draw(Graphics2D graphics)
    {
        //safe teh position it was at
        AffineTransform save = graphics.getTransform();

        //transfomr to put the thingy in the middle so you can put it in
        graphics.translate(x,y);

        graphics.setColor(new Color(34,139,39)); //color for leaves

        graphics.fill(new Ellipse2D.Double(-TREE_RADIUS,-TREE_RADIUS, TREE_RADIUS*2,TREE_RADIUS*2));

        //go back to the old rotation and transformation
        graphics.setTransform(save);
    }
}