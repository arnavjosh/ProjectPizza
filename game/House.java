import java.awt.*;
import java.awt.geom.Rectangle2D;

public class House extends Collidable {
    private int width = 300, height = 200;
    public House(int x, int y) {
        super(x, y, "/images/House.png");
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
