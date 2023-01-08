
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
                        _connectNode(aux, new Node2d(p,false), true);
                        counter++;

                        return;
                    }
                         aux = aux.left; 
                }
                else
                {
                    if(aux.right == null)
                    {
                        _connectNode(aux, new Node2d(p,false), false);
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
                        _connectNode(aux, new Node2d(p,true), true);
                        counter++;                        
                        return;
                    }
                    aux = aux.left;
                } 
                else
                {
                    if (aux.right == null)
                    {
                        _connectNode(aux, new Node2d(p, true), false);
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
            if(auxP.point.compareTo(p) == 0) 
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
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p)
    {
        if(root == null)
            return null;
        if(this.size() == 1)
            return root.point;

        Node2d auxP = root;
        Node2d nearP = root;
        double disToLineRoot = 0.0; //--> To store distance nearest point of the father line.
        double disNear;//---------------> To store the distance the nearest point to the query point.
        double disN2;//-----------------> To store the distance the nearest point, but the other side of the tree.
        boolean left = true;
        
        StdDraw.setPenColor(StdDraw.GREEN);
        p.draw();
        
        nearP = auxP;
        disNear = p.distanceTo(auxP.point);

        if(auxP.point.x() > p.x() && auxP.left != null)
        {
            auxP = auxP.left;
            left = true;
        }
        else
        {
            auxP = auxP.right;
            left = false;
        }
        
        nearP = _checkSideTree(auxP, p, disNear);
        disNear = p.distanceTo(nearP.point);
        disToLineRoot = p.distanceTo(new Point2D(root.point.x(), p.y()));
                        
        if(disNear >= disToLineRoot && left ) // Because of the way of inserting nodes into the tree is necessary to check the right son differently than the left son.
        {
            auxP = root.right;
            auxP = _checkSideTree(auxP, p, disNear);
            disN2 = p.distanceTo(auxP.point);
            return (disNear <= disN2)?nearP.point:auxP.point;                
        }
        else if (disNear > disToLineRoot && left == false)
        {
            auxP = root.left;
            auxP = _checkSideTree(auxP, p, disNear);
            disN2 = p.distanceTo(auxP.point);
            return (disNear <= disN2)?nearP.point:auxP.point;              
        }
        else
            return nearP.point;
    }


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

    private void    _connectNode(Node2d nFather, Node2d child, boolean sideLeft) // To connect a node to its father
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
    private Node2d  _getGrandFather(Node2d son)
    {
        Node2d greatGrand = son.father;
        
        for (int i = 0; i < 2; i++)
        {
            if(greatGrand==null)
                return null;
            greatGrand = greatGrand.father;
            
        }
        return greatGrand;
    }
    private void    _drawPoint(Node2d next)
    {
        if(next==null)
            return;
        
        if(next == root)
        {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.013); 
            next.point.draw();
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.setPenRadius(0.005); 
            StdDraw.line(next.point.x(), 0.0, next.point.x(), 1.0); //Because it is the first line it must draw from coordinate y = 0.0 to y = 1.0 pass for the same coordinate x.

        }
        else
        {
            if(next.isVertical)
            {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.013); 
                next.point.draw();
                StdDraw.setPenColor(StdDraw.BOOK_RED);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(next.point.x(), next.point.y(), next.point.x(), next.father.point.y()); // Draw line from next.point to father's y coordinate.
                if(next.father.point.y() <= next.point.y())
                {
                    // It means that the line goes downwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is above of the current point.

                    Node2d greatGrandFather = _getGrandFather(next);
                    if(greatGrandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 1.0); //To conect with the border y 1.0
                    }
                    else
                    {
                        if(greatGrandFather.point.y()<= next.father.point.y())// The greatGrandFather is down to the father.
                            StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 1.0); //To conect with the border y 1.0
                        else
                            StdDraw.line(next.point.x(), next.point.y(), next.point.x(), greatGrandFather.point.y()); //To conect with his grandfather. the point is between his father and his GGfather.
                    }
                        
                }
                else
                {
                    // It means that the line goes upwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is below of the current point.

                    Node2d greatGrandFather = _getGrandFather(next);
                    if(greatGrandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 0.0); //To conect with the border y 0.0
                    }
                    else
                    {
                        if(greatGrandFather.point.y()>= next.father.point.y())// The greatGrandFather is righ to the father.
                            StdDraw.line(next.point.x(), next.point.y(), next.point.x(), 0.0); //To conect with the border y 0.0
                        else
                            StdDraw.line(next.point.x(), next.point.y(), next.point.x(), greatGrandFather.point.y()); //To conect with his great grand father.

                    }   
                }
            }    
            else
            {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.013); 
                next.point.draw();
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(next.point.x(), next.point.y(), next.father.point.x(), next.point.y()); // Draw line from next.point to father's x coordinate.
                if(next.father.point.x() > next.point.x())
                {
                    // It means that the line goes rightwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is in the left of the current point.

                    Node2d greatGrandFather = _getGrandFather(next); // Three levels up in the kdtree,
                    if(greatGrandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), 0.0, next.point.y()); //To conect with the border x 0.0
                    }
                    else
                    {
                        if(greatGrandFather.point.x()>= next.father.point.x())// The greatGrandFather is righ to the father.
                            StdDraw.line(next.point.x(), next.point.y(), 0.0, next.point.y()); //To conect with the border x 0.0
                        else
                            StdDraw.line(next.point.x(), next.point.y(), greatGrandFather.point.x(), next.point.y()); //To conect with his grandfather. the point is between his father and his GGfather.
                    }
                }
                else
                {
                    // It means that the line goes leftwards from this point to his father point.
                    // Because of that is necessary to know if his grandfather point is below of the current point.
                
                    Node2d greatGrandFather = _getGrandFather(next);
                    if(greatGrandFather == null)
                    {
                        StdDraw.line(next.point.x(), next.point.y(), 1.0, next.point.y()); //To conect with the border x 1.0
                    }
                    else
                    {
                        if(greatGrandFather.point.x()<= next.father.point.x())// The greatGrandFather is left to the father.
                            StdDraw.line(next.point.x(), next.point.y(), 1.0, next.point.y()); //To conect with the border x 1.0
                        else
                            StdDraw.line(next.point.x(), next.point.y(), greatGrandFather.point.x(), next.point.y()); //To conect with his grandfather. the point is between his father and his GGfather.
                            

                    }
                        
                }
            }
        }
       
        _drawPoint(next.left);
        _drawPoint(next.right);       
        
    }
    private Node2d _checkSideTree(Node2d auxP, Point2D p, double disNear)
    {     
        Node2d nearP = root;
        double disToPoint = Double.POSITIVE_INFINITY;//-> To store the distance between the query point and the current point stored within the Kd tree.
        Stack<Node2d> stkNodes =  new Stack<Node2d>();//  To search whithin right child.

        while(true)
        {   
            if(auxP.isVertical)
            {
                // To check the X coordinate.
                disToPoint = p.distanceTo(auxP.point);
                if(disToPoint < disNear)
                { 
                    nearP = auxP;
                    disNear = disToPoint;
                }
    
                if(p.x() <= auxP.point.x())//To know if the query point is to the left of the father node.
                {
                    // The query point is to the left of the father node.

                    if ( ( (p.x() + disNear) >= auxP.point.x() ) && (auxP.left != null) )
                    {
                        stkNodes.push(auxP.right);
                        auxP = auxP.left;                        
                    }
                    else if ( ((p.x() + disNear) >= auxP.point.x()) && (auxP.right != null) )
                        auxP = auxP.right;
                    else if (auxP.left != null)
                        auxP = auxP.left;
                    else if ( !stkNodes.isEmpty() )
                        auxP = stkNodes.pop();
                    else
                        return nearP;    

                }
                else
                {
                    // The query point is to the right of the father node.

                    if ( ( (p.x() - disNear) < auxP.point.x() ) && (auxP.right != null) )
                    {
                        stkNodes.push(auxP.left);
                        auxP = auxP.right;                        
                    }
                    else if ( ((p.x() - disNear) <= auxP.point.x()) && (auxP.left != null) )
                        auxP = auxP.left;
                    else if (auxP.right != null)
                        auxP = auxP.right;
                    else if ( !stkNodes.isEmpty() )
                        auxP = stkNodes.pop();
                    else
                        return nearP;
                }                
            }
            else // is horizontal
            {
                // To check the Y coordinate.

                disToPoint = p.distanceTo(auxP.point);
                if (disToPoint < disNear)
                {
                    nearP = auxP;
                    disNear = disToPoint;
                }

                if (p.y() < auxP.point.y())// To know if the query point is to the left of the father node.
                {
                    // The query point is below of the father node.

                    if (((p.y() + disNear) >= auxP.point.y()) && (auxP.left != null)) {
                        stkNodes.push(auxP.right);
                        auxP = auxP.left;
                    } else if ((p.y() + disNear) >= auxP.point.y() && auxP.right != null )
                        auxP = auxP.right;
                    else if (auxP.left != null)
                        auxP = auxP.left;
                    else if (!stkNodes.isEmpty())
                        auxP = stkNodes.pop();
                    else
                        return nearP;

                }
                else
                {
                    // The query point is above of the father node.

                    if (((p.y() - disNear) <= auxP.point.y()) && (auxP.right != null)) {
                        stkNodes.push(auxP.left);
                        auxP = auxP.right;
                    } else if ((p.y() - disNear) <= auxP.point.y())
                        auxP = auxP.left;
                    else if (auxP.right != null)
                        auxP = auxP.right;
                    else if (!stkNodes.isEmpty())
                        auxP = stkNodes.pop();
                    else
                        return nearP;
                }

            }
        }    
        
    }

       public static void main(String[] args)
    {
        KdTree arbol = new KdTree();
        
        arbol.insert(new Point2D(0.7, 0.2));
        
        arbol.insert(new Point2D(0.5, 0.5));
        arbol.insert(new Point2D(0.25, 0.5));
        arbol.insert(new Point2D(0.47, 0.77));

        arbol.insert(new Point2D(0.5, 0.4));
        arbol.insert(new Point2D(0.2, 0.3));
        arbol.insert(new Point2D(0.2, 0.1));
        arbol.insert(new Point2D(0.2, 0.0));
        arbol.insert(new Point2D(0.4, 0.7));
        arbol.insert(new Point2D(0.9, 0.6));
        arbol.insert(new Point2D(0.6, 0.4));        
        arbol.insert(new Point2D(0.3, 0.5)); 
        arbol.insert(new Point2D(0.81, 0.385));
        arbol.insert(new Point2D(0.4, 0.35));
        arbol.draw();
      //  var prueba = arbol.range(new RectHV(.1, .1, .3, .9));
        arbol.contains(new Point2D(.6, .4));
        // Point2D p = new Point2D(.6, .3); depurado y funciona bien, busca primero izq y luego der, p 
        // Point2D p = new Point2D(.685, .385); depurado y funiciona bien. el punto del lado derecho es el mas cercano.
        // Point2D p = new Point2D(.85, .7); punto mas cercano del lado derecho.
        // Point2D p = new Point2D(.95, .25);
        // Point2D p = new Point2D(.1, .1);   

    }
}
