/**
 * KdTree.
 */
public class KdTree {

   private Node root;
   private int size;

   public KdTree() {
   }
   /**
    * @return is the set empty?
    */
   public boolean isEmpty() {
       return root == null;
   }

   /**
    * @return number of points in the set
    */
   public int size() {
       return size;
   }
   /**
    * add the point p to the set (if it is not already in the set).
    * @param p point
    */
   public void insert(Point2D p) {
       if (root == null) {
           size++;
           root = new Node();
           root.p = p;
           root.rect = new RectHV(p.x(), 0, p.x(), 1);
       } else if (p.equals(root.p)) {
           return;
       } else if (p.x() > root.p.x()) {
           root.rt = insert(root.rt, p, false, root.p.x(), 1, 0, 1);
       } else {
           root.lb = insert(root.lb, p, false, 0, root.p.x(), 0, 1);
       }
   }

   private Node insert(Node n, Point2D p, boolean xCompare, double xmin, double xmax, double ymin, double ymax) {
       if (n == null) {
           size++;
           Node node = new Node();
           node.p = p;
           if (xCompare) {
               node.rect = new RectHV(p.x(), ymin, p.x(), ymax);
           } else {
               node.rect = new RectHV(xmin, p.y(), xmax, p.y());
           }
           return node;
       } else {
           if (xCompare) {
               if (p.x() > n.p.x()) {
                   n.rt = insert(n.rt, p, !xCompare, n.p.x(), xmax, ymin, ymax);
               } else {
                   if (p.equals(n.p)) {
                       return n;
                   }
                   n.lb = insert(n.lb, p, !xCompare, xmin, n.p.x(), ymin, ymax);
               }
           } else {
               if (p.y() > n.p.y()) {
                   n.rt = insert(n.rt, p, !xCompare, xmin, xmax, n.p.y(), ymax);
               } else {
                   if (p.equals(n.p)) {
                       return n;
                   }
                   n.lb = insert(n.lb, p, !xCompare, xmin, xmax, ymin, n.p.y());
               }
           }
           return n;
       }
   }

   /**
    * @param p point
    * @return does the set contain the point p
    */
   public boolean contains(Point2D p) {
       if (root == null) {
           return false;
       } else if (root.p.x() == p.x() && root.p.y() == p.y()) {
           return true;
       } else if (p.x() > root.p.x()) {
           return contains(root.rt, p, false);
       } else {
           return contains(root.lb, p, false);
       }
   }

   private boolean contains(Node n, Point2D p, boolean xCompare) {
       if (n == null) {
           return false;
       } else {
           if (n.p.x() == p.x() && n.p.y() == p.y()) {
               return true;
           }
           if (xCompare) {
               if (p.x() > n.p.x()) {
                   return contains(n.rt, p, !xCompare);
               } else {
                   return contains(n.lb, p, !xCompare);
               }
           } else {
               if (p.y() > n.p.y()) {
                   return contains(n.rt, p, !xCompare);
               } else {
                   return contains(n.lb, p, !xCompare);
               }
           }
       }
   }

   /**
    * draw all of the points to standard draw.
    */
   public void draw() {
       draw(root);
   }

   private void draw(Node n) {
       if (n != null) {
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.setPenRadius(.01);
           n.p.draw();
           StdDraw.setPenRadius();
           if (n.rect.xmin() == n.rect.xmax()) {
               StdDraw.setPenColor(StdDraw.RED);
           } else {
               StdDraw.setPenColor(StdDraw.BLUE);
           }
           n.rect.draw();
           draw(n.lb);
           draw(n.rt);
       }
   }

   /**
    * @param rect rectangle
    * @return all points in the set that are inside the rectangle
    */
   public Iterable<Point2D> range(RectHV rect) {
       Queue<Point2D> q = new Queue<Point2D>();
       range(rect, root, q, true);
       return q;
   }

   private void range(RectHV rect, Node n, Queue<Point2D> q, boolean xCompare) {
       if (n == null) {
           return;
       }
       if (rect.contains(n.p)) {
           q.enqueue(n.p);
       }
       if (xCompare) {
           if (n.p.x() >= rect.xmin()) {
               range(rect, n.lb, q, !xCompare);
           }
           if (n.p.x() <= rect.xmax()) {
               range(rect, n.rt, q, !xCompare);
           }
       } else {
           if (n.p.y() >= rect.ymin()) {
               range(rect, n.lb, q, !xCompare);
           }
           if (n.p.y() <= rect.ymax()) {
               range(rect, n.rt, q, !xCompare);
           }
       }
   }
/**
    * @param p point
    * @return a nearest neighbor in the set to p; null if set is empty
    */
   public Point2D nearest(Point2D p) {
       double minD = root.p.distanceTo(p);
       Candidate cand = new Candidate();
       cand.minD = minD;
       cand.closestSoFar = root.p;
       nearest(root, p, cand, true);
       return cand.closestSoFar;
   }

   /**
    * Value holder.
    */
   private class Candidate {
       private Point2D closestSoFar;
       private double minD;
   }

   private void nearest(Node n, Point2D p, Candidate cand, boolean xCompare) {
       if (n == null) {
           return;
       }
       double cD = n.p.distanceTo(p);
       if (cD < cand.minD) {
           cand.minD = cD;
           cand.closestSoFar = n.p;
       }
       if ((xCompare && p.x() < n.p.x())
               || (!xCompare && p.y() < n.p.y())) {
           nearest(n.lb, p, cand, !xCompare);
           if (cand.minD > n.rect.distanceTo(p)) {
               nearest(n.rt, p, cand, !xCompare);
           }
       } else {
           nearest(n.rt, p, cand, !xCompare);
           if (cand.minD > n.rect.distanceTo(p)) {
               nearest(n.lb, p, cand, !xCompare);
           }
       }
   }

/**
    * Node.
    */
   private static class Node {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
   }
}
