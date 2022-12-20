import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree
{
    private Node2d root;
    private int counter;
    // construct an empty set of points 
    public KdTree()
    {   counter = 0;    
        root = null;
    }                               
    
    // is the set empty? 
    public boolean isEmpty()
    {
        return (counter == 0)?true:false;
    }
    // number of points in the set
    public int size()
    {
        return counter;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        
        if(root == null)
        {
            root = new Node2d(p);
            root.isVertical = true;
            counter++;
            return;
        }
        
        Node2d aux = root;
        
        while(true)
        {
            if(aux.isVertical)
            {
                // To check the X coordinate.
                if(aux.point.x() > p.x())
                {
                    //Take left child;
                    if(aux.left == null)
                    {
                        connectNode(aux, new Node2d(p,false), true);
                        counter++;

                        return;
                    }
                         aux = aux.left; 
                }
                else
                {
                    if(aux.right == null)
                    {
                        connectNode(aux, new Node2d(p,false), false);
                        counter++;                        
                        return;
                    }
                         aux = aux.right; 

                }
                
            }
            else // is horizontal
            {
                    // To check the Y coordinate.
                if (aux.point.y() > p.y())
                {
                    // Take left child;
                    if (aux.left == null)
                    {
                        connectNode(aux, new Node2d(p,true), true);
                        counter++;                        
                        return;
                    }
                    aux = aux.left;
                } 
                else
                {
                    if (aux.right == null)
                    {
                        connectNode(aux, new Node2d(p, true), false);
                        counter++;                        
                        return;
                    }
                    aux = aux.right;

                }

            }
        
        }
        

        
    }
    // does the set contain point p?               
    // public boolean contains(Point2D p)
    // {
    //     return points.contains(p);
    // }
    // draw all points to standard draw 
    public void draw()
    {
        _drawPoint(root);
    
    }
    // // all points that are inside the rectangle (or on the boundary)                         
    // public Iterable<Point2D> range(RectHV rect)
    // {
    //     SET<Point2D> pointsRec = new SET<Point2D>();
    //     for (Point2D point2d : points)
    //     {
    //         if(rect.contains(point2d))
    //             pointsRec.add(point2d);            
    //     }
    //     return pointsRec;
    // } 
    // // a nearest neighbor in the set to point p; null if the set is empty 
    // public Point2D nearest(Point2D p)
    // {
    //     if(points.isEmpty())
    //         return null;
    //     Point2D near = new Point2D(0, 0);
    //     //double distance = p.distanceTo(points.max());
    //     double distance = Double.POSITIVE_INFINITY;
    //     double dAux;

    //     for (Point2D point2d : points)
    //     {
    //         dAux = p.distanceTo(point2d); 
    //         if( dAux < distance)
    //         {
    //             near = point2d;
    //             distance = dAux;
    //         }
    //     }
    //     return near;
    // }


    private class Node2d
    {
        public Point2D point;
        public boolean isVertical;
        public Node2d left;
        public Node2d right;
        public Node2d father;
        
        Node2d(Point2D point)
        {
            this.point = point;
            left  = null;
            right = null;
            father = null;
        }
        Node2d(Point2D point, boolean isVertical)
        {
            this.isVertical = isVertical;
            this.point = point;
            left  = null;
            right = null;
            father = null;
        }
    }

    private void connectNode(Node2d nFather, Node2d child, boolean sideLeft) // To connect a node to its father
    {
        // sideLeft--> If this is true, this node must connect in the left child, otherwise in the right child.

        if(sideLeft)
        {
            nFather.left = child;
            child.father = nFather; // To know who is the father of the new node inserted in the tree
        }
        else
        {
            nFather.right = child;
            child.father = nFather; // To know who is the father of the new node inserted in the tree
        }

    }
    private Node2d getGrandFather(Node2d son)
    {
        Node2d grand = son.father;
        
        for (int i = 0; i < 2; i++)
        {
            if(grand==null)
                return null;
            grand = grand.father;
            
        }
        return grand;
    }
    private void _drawPoint(Node2d next)
    {
        if(next==null)
            return;
        
        
        
        if(next == root)
        {
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.setPenRadius(0.02); 
            next.point.draw();
            StdDraw.setPenRadius(0.005); 
            StdDraw.line(next.point.x(), 0.0, next.point.x(), 1.0); //Because it is the first line it must draw from coordinate y = 0.0 to y = 1.0 pass for the same coordinate x.

        }
        else
        {

            if(next.isVertical)
            {
                StdDraw.setPenRadius(0.02); 
                StdDraw.setPenColor(StdDraw.BOOK_RED);
                next.point.draw();
                StdDraw.setPenRadius(0.005);
                StdDraw.line(next.point.x(), next.point.y(), next.point.x(), next.father.point.y()); // Draw line from next.point to father's y coordinate.
                if(next.father.point.y() < next.point.y())
                {
                    // It means that the line goes downwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is above of the current point.

                    Node2d grandFather = getGrandFather(next);
                    if(grandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 1.0); //To conect with the border y 1.0
                    }
                    else
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), grandFather.point.y()); //To conect with his grandfather.
                }
                else
                {
                    // It means that the line goes upwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is below of the current point.

                    Node2d grandFather = getGrandFather(next);
                    if(grandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 0.0); //To conect with the border y 1.0
                    }
                    else
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), grandFather.point.y()); //To conect with his grandfather.

                }


            }    
            else
            {
                StdDraw.setPenRadius(0.02); 
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                next.point.draw();
                StdDraw.setPenRadius(0.005);
                StdDraw.line(next.point.x(), next.point.y(), next.father.point.x(), next.point.y()); // Draw line from next.point to father's x coordinate.
                if(next.father.point.x() > next.point.x())
                {
                    // It means that the line goes rightwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is in the left of the current point.

                    Node2d grandFather = getGrandFather(next);
                    if(grandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), 0.0, next.point.y()); //To conect with the border x 0.0
                    }
                    else
                        StdDraw.line(next.point.x(), next.point.y(), grandFather.point.x(), next.point.y()); //To conect with his grandfather.
                }
                else
                {
                    // It means that the line goes upwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is below of the current point.
                
                    Node2d grandFather = getGrandFather(next);
                    if(grandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), 1.0, next.point.y()); //To conect with the border x 1.0
                    }
                    else
                        StdDraw.line(next.point.x(), next.point.y(), grandFather.point.x(), next.point.y()); // To conect with his grandfather.
                }
            }
        }
    
   
        _drawPoint(next.left);
        _drawPoint(next.right);
        
        //TODO: 1 EJECUTAR PRIMERO EL PROGRAMA YA QUE SE ENCUENTRA CORRIENDO Y VER QUE SE DIBUJE BIEN LAS LINEAS Y        
        
        
     
    }
    public static void main(String[] args)
    {
        KdTree arbol = new KdTree();
        
        // arbol.insert(new Point2D(0.5, 0.5));
        // arbol.insert(new Point2D(0.25, 0.5));
        // arbol.insert(new Point2D(0.47, 0.77));

        arbol.insert(new Point2D(0.7, 0.2));
        arbol.insert(new Point2D(0.5, 0.4));
        arbol.insert(new Point2D(0.2, 0.3));
        arbol.insert(new Point2D(0.4, 0.7));
        arbol.insert(new Point2D(0.9, 0.6));
        arbol.draw();


    }
}