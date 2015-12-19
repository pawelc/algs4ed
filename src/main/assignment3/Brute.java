import java.util.Arrays;

/**
 * Brute force.
 *
 * @author pawelc
 *
 */
public class Brute {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slopePQ = points[i].slopeTo(points[j]);
                for (int j2 = j + 1; j2 < points.length; j2++) {
                    double slopePR = points[i].slopeTo(points[j2]);
                    if (slopePQ == slopePR) {
                        for (int k = j2 + 1; k < points.length; k++) {
                            double slopePS = points[i].slopeTo(points[k]);
                            if (slopePS == slopePQ) {
                                points[i].drawTo(points[k]);
                                StdOut.printf(
                                        "%s -> %s -> %s -> %s\n",
                                        points[i], points[j], points[j2], points[k]);
                            }
                        }
                    }
                }
            }
        }
        // display to screen all at once
        StdDraw.show(0);
    }

}
