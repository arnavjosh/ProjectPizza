import java.awt.*;
import java.awt.geom.Rectangle2D;

public class House extends Collidable {
    private int width = 300, height = 200;
    private Orientation orientation;
    public enum Orientation {
        NORTH, SOUTH, EAST, WEST
    }
    private String[] paths = {
        "/images/House1.png",
        "/images/House2.png"
    };
    private int randomIndex = (int) (Math.random() * paths.length);

    public House(int x, int y, Orientation orientation) {
        super(x, y);
        setPath(paths[randomIndex]);
        this.orientation = orientation;
        collisionBox = createCollisionBox();
    }
    public Rectangle2D.Double createCollisionBox() {
        if (orientation == Orientation.NORTH || orientation == Orientation.SOUTH) {
            return new Rectangle2D.Double(x - width / 2.0, y - height / 2.0, width, height);
        }else{
            return new Rectangle2D.Double(x - height / 2.0, y - width / 2.0, height, width);
        }
    }

    public void drawAlt(Graphics2D g) {
        g.fillRect(x - width / 2, y - height / 2, width, height);
    }

    public void draw(Graphics2D g) 
    {
        g= (Graphics2D) g.create();
        if(orientation == Orientation.NORTH)
        {
            g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(), getImage().getHeight(), null);
        }
        else if(orientation == Orientation.SOUTH)
        {
            g.rotate(Math.PI, x, y);
            g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(), getImage().getHeight(), null);
            
        }
        else if(orientation == Orientation.EAST)
        {
            g.rotate(Math.PI / 2, x, y);
            g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(), getImage().getHeight(), null);
        }
        else if(orientation == Orientation.WEST)
        {
            g.rotate(-Math.PI/2, x, y); 
            g.drawImage(getImage(), x - getImage().getWidth() / 2, y - getImage().getHeight() / 2, getImage().getWidth(), getImage().getHeight(), null);
        }
    }
}
