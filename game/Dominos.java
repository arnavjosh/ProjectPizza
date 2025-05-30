import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Dominos extends Collidable {
    private int width = 400, height = 300;
    public Dominos(int x, int y) {
        super(x, y, "/images/Dominos.png");
        collisionBox = createCollisionBox();
    }
    public Rectangle2D.Double createCollisionBox() {
        return new Rectangle2D.Double(x - width / 2.0, y - height / 2.0, width, height);
    }

    @Override
    public void drawAlt(Graphics2D g) {
        g.fillRect(x - width / 2, y - height / 2, width, height);
    }
}
