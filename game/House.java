import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class House {
    private int x, y, width = 300, height = 200;
    private Color color = Color.RED;
    private BufferedImage houseImage;
    private Rectangle2D.Double collisionBox;
    AffineTransform inverse;
    public House(int x, int y) {
        this.x = x;
        this.y = y;
        collisionBox = new Rectangle2D.Double(x-width/2, y-height/2, width, height);
        AffineTransform normal = new AffineTransform();
        normal.translate(x,y);
        try{
            inverse = normal.createInverse();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        try{
            houseImage = ImageIO.read(getClass().getResource("/images/House.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g) {
        if(houseImage!=null)
        {
            //no imageobserver so we pass in null for that
            g.drawImage(houseImage,x-width/2,y-height/2,width,height,null);
        }
        else{
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
        //draws the collision box
        g.setColor(Color.BLUE);
        g.draw(collisionBox);
    }
    public Rectangle2D.Double getCollisionBox() {
        return collisionBox;
    }
    public AffineTransform getInverseTransform() {
        return inverse;
    }

}
