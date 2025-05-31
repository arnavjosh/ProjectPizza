import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Collidable extends GraphicObject
{
    private Color color = Color.RED;
    protected Rectangle2D collisionBox;
    public  Collidable(int x, int y, String imagePath) 
    {
        super(x, y, imagePath);
    }
    public abstract Rectangle2D.Double createCollisionBox();
    public void draw(Graphics2D g)
    {
        if(getImage()!=null)
        {
            //no imageobserver so we pass in null for that
            g.drawImage(getImage(),x-getImage().getWidth()/2,y-getImage().getHeight()/2,getImage().getWidth(),getImage().getHeight(),null);
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
}