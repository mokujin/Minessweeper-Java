package Games;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Денис
 */
public class OctMinesSweeper extends MinesSweeper
{
    public OctMinesSweeper()
    {
        super();
    }

    @Override
    public ArrayList<Point> GetSurroundCells(int x, int y)
    {
        ArrayList<Point> p = new ArrayList<Point>();
        if(InRange(x-1,y))
            p.add(new Point(x-1, y));
        if(InRange(x+1,y))
            p.add(new Point(x+1,y));
        if(InRange(x,y-1))
            p.add(new Point(x,y-1));
        if (InRange(x,y+1))
            p.add(new Point(x,y+1));
        
        if(InRange(x-1,y-1))
            p.add(new Point(x-1,y-1));
        if(InRange(x+1,y-1))
            p.add(new Point(x+1,y-1));
        if(InRange(x-1,y+1))
            p.add(new Point(x-1,y+1));
        if(InRange(x+1,y+1))
            p.add(new Point(x+1,y+1));
            
        return p;
    }
}
