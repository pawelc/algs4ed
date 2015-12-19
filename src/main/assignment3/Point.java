import java.util.Comparator;

/**
 * Point.
 * @author pawelc
 *
 */
public class Point implements Comparable<Point> {

    /**
     * compare points by slope.
     */
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(final Point o1, final Point o2) {
            double slopeO1 = copmputeSlope(Point.this, o1);
            double slopeO2 = copmputeSlope(Point.this, o2);
            if (slopeO1 == slopeO2) {
                return 0;
            } else if (slopeO1 < slopeO2) {
                return -1;
            } else {
                return 1;
            }
        };
    }; // YOUR DEFINITION HERE

    /**
     * x coordinate.
     */
    private final int x;
    /**
     * y coordinate.
     */
    private final int y;

    /**
     * create the point (x, y).
     * @param newX coordinate
     * @param newY coordinate
     */
    public Point(final int newX, final int newY) {
        /* DO NOT MODIFY */
        this.x = newX;
        this.y = newY;
    }

    /**
     * plot this point to standard drawing.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing.
     * @param that point
     */
    public void drawTo(final Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * return string representation of this point.
     * @return string
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * unit test.
     * @param args from command line
     */
    public static void main(final String[] args) {
        for (int i = 0; i < 100000; i++) {
            Point p  = new Point(StdRandom.uniform(0, 500), StdRandom.uniform(0, 500));
            Point q  = new Point(StdRandom.uniform(0, 500), StdRandom.uniform(0, 500));
            if (p.SLOPE_ORDER.compare(q, q) != 0) {
                throw new AssertionError("reflexivity test failed: p:" + p + ", q:" + q);
            }
        }
        Point p0 = new Point(11766, 4857);
        Point p1 = new Point(19356, 21153);
        Point p2 = new Point(19390, 21226);
        Point p3 = new Point(19424, 21299);
        Point p4 = new Point(19458, 21372);
        StdOut.println(p0.slopeTo(p1));
        StdOut.println(p0.slopeTo(p2));
        StdOut.println(p0.slopeTo(p3));
        StdOut.println(p0.slopeTo(p4));
    }

    /**
     * is this point lexicographically smaller than that point?
     * @param that point
     * @return compared
     */
    public int compareTo(final Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else {
            if (this.x < that.x) {
                return -1;
            } else if (this.x > that.x) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * the slope between this point and that point.
     * @param that point
     * @return slope
     */
    public double slopeTo(final Point that) {
        return copmputeSlope(this, that);
    }

    /**
     * slope.
     * @param p1 1st point
     * @param p2 2nd point
     * @return slope
     */
    private double copmputeSlope(final Point p1, final Point p2) {
        if (p2.y == p1.y && p2.x != p1.x) {
            return +0.0;
        } else if (p2.y != p1.y && p2.x == p1.x) {
            return Double.POSITIVE_INFINITY;
        } else if (p2.y == p1.y && p2.x == p1.x) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return (double) (p2.y - p1.y) / (p2.x - p1.x);
        }
    }
}
