import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class GraphicObject
{
    protected int x, y;
    private BufferedImage image;
    public  GraphicObject(int x, int y, String imagePath) 
    {
        this.x = x;
        this.y = y;
        this.image = getImage(imagePath);
    }
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