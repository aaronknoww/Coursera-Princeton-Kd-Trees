import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET
{
    private SET<Point2D> points;
    
    // construct an empty set of points 
    public PointSET()
    {
        points = new SET<Point2D>();
    }                               
    
    // is the set empty? 
    public boolean isEmpty()
    {
        return points.isEmpty();
    }
    // number of points in the set
    public int size()
    {
        return points.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        points.add(p);
    }
    // does the set contain point p?               
    public boolean contains(Point2D p)
    {
        return points.contains(p);
    }
    // draw all points to standard draw 
    public void draw()
    {
        for (Point2D point2d : points)
        {
            point2d.draw();           
        }
    }
    // all points that are inside the rectangle (or on the boundary)                         
    public Iterable<Point2D> range(RectHV rect)
    {
        SET<Point2D> pointsRec = new SET<Point2D>();
        for (Point2D point2d : points)
        {
            if(rect.contains(point2d))
                pointsRec.add(point2d);            
        }
        return pointsRec;
    } 
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p)
    {
        if(points.isEmpty())
            return null;
        Point2D near = new Point2D(0, 0);
        //double distance = p.distanceTo(points.max());
        double distance = Double.POSITIVE_INFINITY;
        double dAux;

        for (Point2D point2d : points)
        {
            dAux = p.distanceTo(point2d); 
            if( dAux < distance)
            {
                near = point2d;
                distance = dAux;
            }
        }
        return near;
    }
 
    // )                  // unit testing of the methods (optional) 
    
}
