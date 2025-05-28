import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Collidable 
{
    protected int x, y;
    private Color color = Color.RED;
    private BufferedImage image;
    protected Rectangle2D collisionBox;
    public  Collidable(int x, int y, String imagePath) 
    {
        this.x = x;
        this.y = y;
        this.image = getImage(imagePath);
    }
    public abstract Rectangle2D.Double createCollisionBox();
    public void draw(Graphics2D g)
    {
        if(image!=null)
        {
            //no imageobserver so we pass in null for that
            g.drawImage(image,x-image.getWidth()/2,y-image.getHeight()/2,image.getWidth(),image.getHeight(),null);
            //draw the collision box
            //g.setColor(Color.BLUE);
            //g.draw(collisionBox);
        }
        else
        {
            g.setColor(color);
            drawAlt(g);
        }
    }
    public abstract void drawAlt(Graphics2D g); 
    public Rectangle2D getCollisionBox() 
    {
        return collisionBox;
    }
    public BufferedImage getImage(String imagePath) 
    {
        try
        {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}