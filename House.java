import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class House {
    private int x, y, width = 300, height = 200;
    private Color color = Color.RED;
    private BufferedImage houseImage;

    public House(int x, int y) {
        this.x = x-width/2;
        this.y = y-height/2;

        try{
            houseImage = ImageIO.read(getClass().getResource("House.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g) {
        if(houseImage!=null)
        {
            //no imageobserver so we pass in null for that
            g.drawImage(houseImage,x,y,width,height,null);
        }
        else{
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
        g.setColor(Color.YELLOW);
        g.draw(getBounds());
    }
    //for collisions with car
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}
