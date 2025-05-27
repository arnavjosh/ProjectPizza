import java.awt.*;

public abstract class Collidable {
    private int x, newY;
    private Shape collisionBox;
    public abstract void draw(Graphics2D g);
    public Shape getCollisionBox() {
        return collisionBox;
    }
}
