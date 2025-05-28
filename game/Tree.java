import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Tree extends Collidable
{
    private static final int TREE_RADIUS = 80;

    public Tree(int x, int y)
    {
        super (x,y, "/images/Tree.png");
        collisionBox = createCollisionBox();
    }
    public Rectangle2D.Double createCollisionBox()
    {
        return new Rectangle2D.Double(x - TREE_RADIUS / 2.0, y - TREE_RADIUS / 2.0, TREE_RADIUS, TREE_RADIUS);
    }
    public void drawAlt(Graphics2D g)
    {
        //dark green
        g.setColor(new Color(34, 139, 34)); // Forest Green
        g.fill(new Ellipse2D.Double(x - TREE_RADIUS / 2.0, y - TREE_RADIUS / 2.0, TREE_RADIUS, TREE_RADIUS));
    }
}
