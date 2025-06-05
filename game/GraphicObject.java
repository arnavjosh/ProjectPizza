import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GraphicObject
{
    protected int x, y;
    private BufferedImage image;
    protected int width, height;
    public  GraphicObject(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    public GraphicObject(int x, int y, String imagePath) 
    {
        this.x = x;
        this.y = y;
        this.image = getImage(imagePath);
    }

    public GraphicObject(int x, int y, int width, int height, String imagePath) 
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = getImage(imagePath);
    }
    public void setPath(String imagePath) 
    {
        this.image = getImage(imagePath);
    }
    public BufferedImage getImage() 
    {
        return image;
    }
    public void draw(Graphics2D g)
    {
        if(image == null)
        {
            return;
        }
        if(width == 0 || height == 0)
        {
            //draw the image at the center of the x,y coordinates
            g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        }
        else
        {
            //draw the image with specified width and height
            g.drawImage(image, x - width / 2, y - height / 2, width, height, null);
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