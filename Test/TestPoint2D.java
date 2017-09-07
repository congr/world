import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class TestPoint2D {

    public static void main(String[] args) throws Exception {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        int option = 2; // 0: Random, 1: system.in, 2: file
        Point2D[] points;
        if (option == 0) {  // generate random points
            int n = 6;
            points = new Point2D[n];
            for (int i = 0; i < n; i++) {
                int x = StdRandom.uniform(10);
                int y = StdRandom.uniform(10);
                points[i] = new Point2D(x, y);
                points[i].draw();
            }
        } else if (option == 1) {
            int n = StdIn.readInt();
            points = new Point2D[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point2D(StdIn.readInt(), StdIn.readInt());
                points[i].draw();
            }
        } else {
            Scanner sc = new Scanner(new File("Test/sample.in"));
            int n = sc.nextInt();
            points = new Point2D[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point2D(sc.nextInt(), sc.nextInt());
                points[i].draw();
            }
        }

        Arrays.sort(points); // Compares two points by y-coordinate, breaking ties by x-coordinate.

        System.out.println("Count of Points: " + points.length);
        System.out.println(Arrays.toString(points));
        for (Point2D p : points) {
            System.out.println((int) p.x() + " " + (int) p.y());
        }

        // draw point in red
        Point2D p = points[0];
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();

        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        Arrays.sort(points, p.polarOrder());
        for (int i = 0; i < points.length; i++) {
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(100);
        }

        GrahamScan graham = new GrahamScan(points);
        StdOut.println("\nCount of Convex hull points: " + graham.hull.size());
        for (Point2D po : graham.hull()) {
            StdOut.print(po + ", ");
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.02);
            po.draw();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }
}

/**
 * The {@code GrahamScan} data type provides methods for computing the
 * convex hull of a setWithoutDup of <em>n</em> points in the plane.
 * <p>
 * The implementation uses the Graham-Scan convex hull algorithm.
 * It runs in O(<em>n</em> log <em>n</em>) time in the worst case
 * and uses O(<em>n</em>) extra memory.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/99scientific">Section 9.9</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
class GrahamScan {
    Stack<Point2D> hull = new Stack<Point2D>();

    /**
     * Computes the convex hull of the specified array of points.
     *
     * @param points the array of points
     * @throws IllegalArgumentException if {@code points} is {@code null}
     * @throws IllegalArgumentException if any entry in {@code points[]} is {@code null}
     * @throws IllegalArgumentException if {@code points.length} is {@code 0}
     */
    public GrahamScan(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException("argument is null");
        if (points.length == 0) throw new IllegalArgumentException("array is of length 0");

        // defensive copy
        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points[" + i + "] is null");
            a[i] = points[i];
        }

        // preprocess so that a[0] has lowest y-coordinate; break ties by x-coordinate
        // a[0] is an extreme point of the convex hull
        // (alternatively, could do easily in linear time)
        Arrays.sort(a);

        // sort by polar angle with respect to base point a[0],
        // breaking ties by distance to a[0]
        Arrays.sort(a, 1, n, a[0].polarOrder());

        hull.push(a[0]);       // a[0] is first extreme point

        // find index k1 of first point not equal to a[0]
        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!a[0].equals(a[k1])) break;
        if (k1 == n) return;        // all points equal

        // find index k2 of first point not collinear with a[0] and a[k1]
        int k2;
        for (k2 = k1 + 1; k2 < n; k2++)
            if (Point2D.ccw(a[0], a[k1], a[k2]) != 0) break;
        hull.push(a[k2 - 1]);    // a[k2-1] is second extreme point

        // Graham scan; note that a[n-1] is extreme point different from a[0]
        for (int i = k2; i < n; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, a[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(a[i]);
        }

        //assert isConvex();
    }

    /**
     * Returns the extreme points on the convex hull in counterclockwise order.
     *
     * @return the extreme points on the convex hull in counterclockwise order
     */
    public Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<Point2D>();
        for (Point2D p : hull) s.push(p);
        return s;
    }

    // ccw가 아닌 cw 방향의 결과
    public Point2D[] hullArrayCW() {
        Point2D[] array = new Point2D[hull.size()];
        int i = 0;
        for (Point2D p : hull) {
            array[i] = p;
            i++;
        }
        return array;
    }

    // check that boundary of hull is strictly convex
    private boolean isConvex() {
        int n = hull.size();
        if (n <= 2) return true;

        Point2D[] points = new Point2D[n];
        int k = 0;
        for (Point2D p : hull()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point2D.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return hull.size();
    }

    /**
     * Unit tests the {@code GrahamScan} data type.
     * Reads in an integer {@code n} and {@code n} points (specified by
     * their <em>x</em>- and <em>y</em>-coordinates) from standard input;
     * computes their convex hull; and prints out the points on the
     * convex hull to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point2D(x, y);
        }
        GrahamScan graham = new GrahamScan(points);
        for (Point2D p : graham.hull())
            StdOut.println(p);
    }

}


