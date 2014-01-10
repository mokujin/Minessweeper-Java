package Geometry;

/**
 *
 * @author Денис
 */
public class Circle
{
    protected float radius;
    protected Vector2 position;
    public Circle()
    {
        setRadius(0);
        setPosition(new Vector2());
    }
    public Circle(float radius,Vector2 position)
    {
        setRadius(radius);
        setPosition(position);
    }
    public void setRadius(float r)
    {
        this.radius = r;
    }
    public void setPosition(Vector2 pos)
    {
        this.position = pos;
    }
    public void setPosition(float x,float y)
    {
        this.position.set(x,y);
    }
    
}
