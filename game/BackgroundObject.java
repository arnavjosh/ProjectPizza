import java.awt.*;

public abstract class BackgroundObject
{
    protected double x, y;
    protected double rotation;

    protected BackgroundObject(double x, double y, double rotation)
    {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

//different objects will draw themslevds differently
    public abstract void draw(Graphics2D graphics);

    public double getX(){return x;}
    public double getY(){return y;}
    public double getRotation(){return rotation;}

    public void setX(double x)
    {
        this.x = x;
    }
    public void setY(double y)
    {
        this.y = y;
    }
    public void setRotation(double rotation){
        this.rotation = rotation;
    }
}