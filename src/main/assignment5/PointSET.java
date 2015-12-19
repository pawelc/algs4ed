/**
 * PointSET.
 */
public class PointSET {

   private RedBlackBST<Point2D, Point2D> bst = new RedBlackBST<Point2D, Point2D>();

   public PointSET() {
   }
   /**
    * @return is the set empty?
    */
   public boolean isEmpty() {
       return bst.isEmpty();
   }

   /**
    * @return number of points in the set
    */
   public int size() {
       return bst.size();
   }
   /**
    * add the point p to the set (if it is not already in the set).
    * @param p point
    */
   public void insert(Point2D p) {
       bst.put(p, p);
   }

   /**
    * @param p point
    * @return does the set contain the point p
    */
   public boolean contains(Point2D p) {
       return bst.contains(p);
   }

   /**
    * draw all of the points to standard draw.
    */
   public void draw() {
       for (Point2D p : bst.keys()) {
           p.draw();
       }
   }

   /**
    * @param rect rectangle
    * @return all points in the set that are inside the rectangle
    */
   public Iterable<Point2D> range(RectHV rect) {
       Queue<Point2D> q = new Queue<Point2D>();
       for (Point2D p : bst.keys(new Point2D(rect.xmin(), rect.ymin()),
               new Point2D(rect.xmax(), rect.ymax()))) {
           if (rect.contains(p)) {
               q.enqueue(p);
           }
       }
       return q;
   }

   /**
    * @param p point
    * @return a nearest neighbor in the set to p; null if set is empty
    */
   public Point2D nearest(Point2D p) {
       Point2D closest = null;
       double closestD = Double.MAX_VALUE;
       for (Point2D p2 : bst.keys()) {
           if (closest == null || p2.distanceTo(p) < closestD) {
               closest = p2;
               closestD = p2.distanceTo(p);
           }
       }
       return closest;
   }
}
