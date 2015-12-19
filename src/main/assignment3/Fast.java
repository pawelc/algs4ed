import java.util.Arrays;
import java.util.HashSet;

/**
 * Brute force.
 * 
 * @author pawelc
 * 
 */
public class Fast {

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
        Point[] pointsForSlopeSorting = new Point[n];
        Point[] pointsInLine = new Point[n];
        Arrays.sort(points);

        // so we know which points we already classified to lines and which
        // slopes we have seen
        HashSet<String> lineAndSlopeQualified = new HashSet<String>();

        for (int i = 0; i < points.length; i++) {
            if (points.length - i < 4) {
                break;
            }
            System.arraycopy(points, i, pointsForSlopeSorting, 0, points.length
                    - i);
            Arrays.sort(pointsForSlopeSorting, 0, points.length - i,
                    points[i].SLOPE_ORDER);
            double slope = points[i].slopeTo(pointsForSlopeSorting[1]);
            int inLine = 0;
            pointsInLine[inLine++] = pointsForSlopeSorting[0];
            pointsInLine[inLine++] = pointsForSlopeSorting[1];

            for (int j = 2; j < points.length - i; j++) {
                double currentSlope = points[i]
                        .slopeTo(pointsForSlopeSorting[j]);
                boolean isInLine = currentSlope != Double.NEGATIVE_INFINITY
                        && (currentSlope == slope);
                boolean onAlreadySeenLine = lineAndSlopeQualified.contains(currentSlope
                        + pointsForSlopeSorting[j].toString());
                if (isInLine && !onAlreadySeenLine) {
                    pointsInLine[inLine++] = pointsForSlopeSorting[j];
                }
                if (!isInLine || j == points.length - i - 1
                        || onAlreadySeenLine) {
                    if (inLine >= 4) {
                        StringBuilder pointsInLineSb = new StringBuilder();
                        for (int l = 0; l < inLine - 1; l++) {
                            pointsInLineSb.append(pointsInLine[l]).append(" -> ");
                            lineAndSlopeQualified.add(slope + pointsInLine[l].toString());
                        }
                        pointsInLine[0].drawTo(pointsInLine[inLine - 1]);
                        pointsInLineSb.append(pointsInLine[inLine - 1]);
                        lineAndSlopeQualified.add(slope + pointsInLine[inLine - 1].toString());
                        StdOut.println(pointsInLineSb.toString());
                    }
                    // start new series but leave origin
                    inLine = 1;
                    slope = currentSlope;
                    pointsInLine[inLine++] = pointsForSlopeSorting[j];
                }
            }
        }
        // display to screen all at once
        StdDraw.show(0);
    }

}
