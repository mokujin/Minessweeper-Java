package Geometry;

import Utilities.Math.VectorMath;
import java.util.ArrayList;

/**
 *
 * @author Денис
 */
public class Polygon
{
    Vector2 _temp1;
    Vector2 _temp2;
    Vector2 _temp3;
    Vector2 _res;

    private ArrayList<Vector2> points;

    public Polygon()
    {
        this.points = new ArrayList<Vector2>();
    }
    public Polygon(ArrayList<Vector2> points)
    {
        this.points = new ArrayList<Vector2>();
        this.points.addAll(points);
    }
    public void Add(Vector2 point)
    {
        points.add(point);
    }

    public int NextIndex(int index)
    {
        if (index == points.size() - 1)
            return 0;
        return index + 1;
    }
    public int PreviousIndex(int index)
    {
        if (index == 0)
            return points.size() - 1;
        return index - 1;
    }

    public Vector2 GetEdge(int index)
    {
        int nextIndex = NextIndex(index);
        _temp2 = points.get(nextIndex);
        _temp3 = points.get(index);
        _temp1  = VectorMath.Subtract(_temp2, _temp3);
        return _temp1;
    }
    public Vector2 GetEdgeNormal(int index)
    {
        _temp1 = GetEdge(index);

        _temp2.x = -_temp1.x;
        _temp2.y = _temp1.y;

        _temp3 = VectorMath.Normalize(_temp2);

        return _temp3;
    }
    public boolean IsConvex()
    {
        boolean isPositive = false;
        for (int i = 0; i < points.size(); ++i)
        {
            int lower = (i == 0) ? (points.size() - 1) : (i - 1);
            int middle = i;
            int upper = (i == points.size() - 1) ? (0) : (i + 1);

            float dx0 = points.get(middle).x - points.get(lower).x;
            float dy0 = points.get(middle).y - points.get(lower).y;
            float dx1 = points.get(upper).x - points.get(middle).x;
            float dy1 = points.get(upper).y - points.get(middle).y;

            float cross = dx0 * dy1 - dx1 * dy0;
            // Cross product should have same sign
            // for each vertex if poly is convex.
            boolean newIsP = (cross >= 0) ? true : false;
            if (i == 0)
            {
                isPositive = newIsP;
            }
            else if (isPositive != newIsP)
            {
                return false;
            }
        }
        return true;
    }
    public boolean PointIn(Vector2 point)
    {
        int i;
        float dist;

        for (i = 0; i < points.size(); i++)
        {
            dist = VectorMath.Dot(GetEdgeNormal(i), point) - VectorMath.Dot(GetEdgeNormal(i),points.get(i));
            if (dist > 0)
            {
                return false;
            }
        }
        return true;
    }
    /// <summary>
    /// Translates the vertices with the specified vector.
    /// </summary>
    /// <param name="vector">The vector.</param>
    public void Translate(Vector2 vector)
    {
        for (int i = 0; i < points.size(); i++)
            points.set(i, VectorMath.Add(points.get(i), vector));
    }

    /// <summary>
    /// Scales the vertices with the specified vector.
    /// </summary>
    /// <param name="value">The Value.</param>
    public void Scale(Vector2 value)
    {
        for (int i = 0; i < points.size(); i++)
            points.set(i,VectorMath.Multiply(points.get(i), value));
    }

    /// <summary>
    /// Rotate the vertices with the defined value in radians.
    /// </summary>
    /// <param name="value">The amount to rotate by in radians.</param>
//    public void Rotate(float value)
//    {
//        Matrix rotationMatrix;
//        Matrix.CreateRotationZ(value, out rotationMatrix);
//        for (int i = 0; i < points.size(); i++)
//            points.get(i) = Vector2.Transform(points.get(i), rotationMatrix);
//    }
    public static Polygon CreateRegularPolygon(float xRadius, float yRadius, int numberOfEdges)
    {
        Polygon vertices = new Polygon();

        float stepSize = (float)(Math.PI*2 / numberOfEdges);

        vertices.Add(new Vector2(xRadius, 0));
        for (int i = 1; i < numberOfEdges; i++)
            vertices.Add(new Vector2(xRadius * (float)Math.cos(stepSize * i), -yRadius * (float)Math.sin(stepSize * i)));

        return vertices;
    }
    public static Polygon CreateSimpleRectangle(float width, float height)
    {
        Polygon vertices = new Polygon();
        vertices.Add(new Vector2(-width * .5f, -height * .5f));
        vertices.Add(new Vector2(-width * .5f, height * .5f));
        vertices.Add(new Vector2(width * .5f, height * .5f));
        vertices.Add(new Vector2(width * .5f, -height * .5f));

        return vertices;
    }
    public float GetSignedArea()
    {
        float area = 0;

        for (int i = 0; i < points.size(); i++)
        {
            int nextIndex = NextIndex(i);
            area += (points.get(i).x * points.get(nextIndex).y - points.get(nextIndex).x * points.get(i).y);
        }
        area /= 2;

        return area;
    }
    public float GetArea()
    {
        return Math.abs(GetSignedArea());
    }
    public Vector2 GetCentroid()
    {
        float area = GetArea();
        Vector2 centroid = new Vector2();
        float factor = 0;

        for (int i = 0; i < points.size(); i++)
        {
            int nextIndex = NextIndex(i);
            factor = -(points.get(i).x*points.get(nextIndex).y - points.get(nextIndex).x*points.get(i).y);
            centroid.x += (points.get(i).x + points.get(nextIndex).x) * factor;
            centroid.y += (points.get(i).y + points.get(nextIndex).y) * factor;
        }

        area *= 6.0f;
        factor = 1 / area;
        centroid.x *= factor;
        centroid.y *= factor;

        return centroid;
    }
}
