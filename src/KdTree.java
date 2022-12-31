
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree
{
    private Node2d root;
    private int counter;
    private SET<Point2D> pointsRec;
        
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
    public boolean contains(Point2D p)
    {
        if(root == null)
            return false; //Because the tree is empty.
        
        Node2d auxP = root;
        
        do
        {
            if(auxP.point.compareTo(p) == 0) // TODO: revisar si hace bien la comparacion. que x == x2 y == y2
                return true;
            
            if(auxP.isVertical)
            {
                if(p.x() < auxP.point.x())
                {
                    if(auxP.left == null)
                        return false;
                    auxP = auxP.left;                                              
                }
                else
                {
                    if(auxP.right == null)
                        return false;
                    auxP = auxP.right;
                }
            }    
            else
            {
                if( p.y() < auxP.point.y())
                {
                    if(auxP.left == null)
                        return false;
                    auxP = auxP.left;  
                }
                else
                { 
                    if(auxP.right == null)
                        return false;
                    auxP = auxP.right;

                }

            }
        } 
        while (true);
    }
    // draw all points to standard draw 
    public void draw()
    {
        _drawPoint(root);
    
    }
    // // all points that are inside the rectangle (or on the boundary)                         
    public Iterable<Point2D> range(RectHV rect)
    {
        pointsRec = new SET<Point2D>();
        
        if(this.isEmpty())
            return pointsRec;
        
        StdDraw.setPenColor(StdDraw.GREEN);
        rect.draw();
        Stack<Node2d> stkNodes =  new Stack<Node2d>(); // To search whithin right child.
        Node2d auxP = root;

        while (true)
        {
            if(auxP.isVertical)
            {
                if((rect.xmin()<= auxP.point.x()) && (rect.xmax() >= auxP.point.x()))
                {
                    // Enter here because it is necessary to search within right child.

                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                    
                    if(auxP.right != null)
                        stkNodes.push(auxP.right); 
                    if(auxP.left != null)
                        auxP = auxP.left;
                    else if(auxP.left == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop(); 

                }
                else if(rect.xmin()<= auxP.point.x())
                {
                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                    
                    if(auxP.left != null)
                        auxP = auxP.left;
                    else if(auxP.left == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop();                    
                }
                else
                {
                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                
                    if(auxP.right != null)
                        auxP = auxP.right;
                    else if(auxP.right == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop();     
                }
            }
            else
            {   // is horizontal
                if((rect.ymin()<= auxP.point.y()) && (rect.ymax() >= auxP.point.y()))
                {
                    // Enter here because it is necessary to search within right child.
                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                    
                    if(auxP.right != null)
                        stkNodes.push(auxP.right); 
                    if(auxP.left != null)
                        auxP = auxP.left;
                    else if(auxP.left == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop();
                }
                else if(rect.ymin()<= auxP.point.y())
                {
                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                    
                    if(auxP.left != null)
                        auxP = auxP.left;
                    else if(auxP.left == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop();                    
                }
                else
                {
                    if(rect.contains(auxP.point))
                        pointsRec.add(auxP.point);
                
                    if(auxP.right != null)
                        auxP = auxP.right;
                    else if(auxP.right == null && stkNodes.isEmpty())
                        return pointsRec;
                    else
                        auxP = stkNodes.pop();     
                }                
            }            
        }
    } 
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
            StdDraw.setPenRadius(0.013); 
            next.point.draw();
            StdDraw.setPenRadius(0.005); 
            StdDraw.line(next.point.x(), 0.0, next.point.x(), 1.0); //Because it is the first line it must draw from coordinate y = 0.0 to y = 1.0 pass for the same coordinate x.

        }
        else
        {
            if(next.isVertical)
            {
                StdDraw.setPenRadius(0.013); 
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
                StdDraw.setPenRadius(0.013); 
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
        // arbol.insert(new Point2D(0.2, 0.1));
        // arbol.insert(new Point2D(0.2, 0.0));
        arbol.insert(new Point2D(0.4, 0.7));
        arbol.insert(new Point2D(0.9, 0.6));
        arbol.insert(new Point2D(0.6, 0.4));
        arbol.draw();
        var prueba = arbol.range(new RectHV(.1, .1, .3, .9));
        arbol.contains(new Point2D(.6, .4));

    }
}
