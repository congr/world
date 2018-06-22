
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 5. 19..
 */
public class P5_Tsunami {
    static boolean debug = false;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P5/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();//격자크기
            int K = sc.nextInt();//대피소점의 개수
            int M = sc.nextInt();//해일이 발생하는 건수

            if (debug) System.out.println(N + " " + K + " " + M);

            Point2D[] points = new Point2D[K];
            Vertex[] vertices = new Vertex[K + 1];
            for (int i = 0; i < K; i++) {
                points[i] = new Point2D(sc.nextInt(), sc.nextInt());
                //if (debug) System.out.println(points[i].x + " " + points[i].y);
                vertices[i + 1] = new Vertex(points[i], i + 1); // 번호 매기고 소팅해야함
            }

            Point2D[] hulls = getConvexArray(points);
            if (debug) System.out.println("new points " + Arrays.toString(points));
            if (debug) System.out.println("new hulls " + Arrays.toString(hulls));

            // 해일 발생 - 가장 먼점 이동
            int[][] tsunamiNo = new int[M][2];
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                tsunamiNo[i][0] = u;
                tsunamiNo[i][1] = v;
            }

            long mod = 0;
            int preU = -1, preV = -1;
            for (int i = 0; i < M; i++) {
                int u = Math.min(tsunamiNo[i][0], tsunamiNo[i][1]);
                int v = Math.max(tsunamiNo[i][0], tsunamiNo[i][1]);
                if (u == v) continue;
                if (debug) System.out.println("----------------------");
                if (debug) System.out.println("this " + u + " " + v + "    pre " + preU + " " + preV);

                if (preU == -1) ; // 해일 발생 한 적없을 때
                else {
                    if (u == preU || u == preV) ;
                    else if (v == preU || v == preV) ;
                    else {
                        if (debug) System.out.println(u + " " + v + "not found on previous tsunami ");
                        continue;
                    }
                }

                Point2D upt = vertices[u].point;
                Point2D vpt = vertices[v].point;

                boolean isAdj = isAdjacentHull(hulls, upt, vpt);
                if (debug)
                    System.out.println("isAdj " + vertices[u].num + "" + vertices[u].point + " " + vertices[v].num + "" + vertices[v].point + " " + isAdj);
                if (isAdj) { // 거리가 가장 먼 점을 더한다
                    preU = Math.min(u, v);
                    preV = Math.max(u, v);

                    Point2D[] triple = new Point2D[3];
                    triple[0] = upt;
                    triple[1] = vpt;

                    double maxPeri = -1;
                    Point2D farPt = null;
                    for (int j = 0; j < hulls.length; j++) {
                        if (hulls[j] != upt && hulls[j] != vpt) {
                            triple[2] = hulls[j];

                            //Point2D t1 = new Point2D(0, 0);
                            //Point2D t2 = new Point2D(0, 2);
                            //Point2D t3 = new Point2D(1, 0);
                            //Point2D t1 = new Point2D(1, 3);
                            //Point2D t2 = new Point2D(1, 1);
                            //Point2D t3 = new Point2D(2, 1);
                           // double test = Point2D.getDistanceToSegment(t1, t2, t3);
                            //if (debug) System.out.println(hulls[j] + " test : " + test);

                            double temp = Point2D.getDistanceToSegment(upt, vpt, triple[2]);
                            //double test = Math.min(upt.distanceTo(triple[2]), vpt.distanceTo(triple[2]));
                            //temp = Math.MAX_VALUE(test, temp);
                            //if (debug) System.out.println(hulls[j] + " temp : " + temp + " " + test);
                            //if (debug) System.out.println(hulls[j] + " dist : " + temp);

                            //if (debug)
                            //    System.out.println("temp " + temp + " maxperi " + maxPeri + " x,y [" + triple[2].x() + " " + triple[2].y());

                            if (maxPeri <= temp) {
                                if (debug) System.out.println("update MAX_VALUE " + maxPeri + " " + temp);
                                if (maxPeri == temp) {
                                    if (debug) System.out.println("same dist MAX_VALUE [" + farPt.x + " " + farPt.y);
                                    if (debug) System.out.println("same dist temp [" + triple[2].x + " " + triple[2].y);
                                    boolean update = false;
                                    if (farPt.x() > triple[2].x()) {
                                        update = true;
                                    } else if (farPt.x() == triple[2].x()) {
                                        if (farPt.y() > triple[2].y())
                                            update = true;
                                    }

                                    if (update) {
                                        farPt = triple[2];
                                        maxPeri = temp;
                                    }
                                } else {
                                    if (debug) System.out.println("not same just update " + maxPeri + " " + temp);
                                    farPt = hulls[j];
                                    maxPeri = temp;

                                }
                                if (debug) System.out.println("farPt" + farPt);
                            }
                        }
                    }
                    for (int j = 1; j < vertices.length; j++) {
                        if (vertices[j].point.x == farPt.x && vertices[j].point.y == farPt.y) {
                            if (debug) System.out.println("farthest " + vertices[j].num + " " + farPt);
                            mod += (vertices[j].num /*% 1000000007*/);
                            if (debug) System.out.println("mod " + (vertices[j].num /*% 1000000007*/));
                            break;
                        }
                    }
                }
            }

            String result = String.valueOf((mod % 1000000007));
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static boolean isAdjacentHull(Point2D[] hulls, Point2D upt, Point2D vpt) {
        for (int j = 0; j < hulls.length; j++) {
            if (hulls[j] == upt) {
                int jtest = j - 1;
                if (jtest < 0) jtest = hulls.length - 1;

                if (hulls[jtest] == vpt) { // 인접한 헐 셋트
                    return true;
                }

                jtest = j + 1;
                if (jtest >= hulls.length) jtest = 0;

                if (hulls[jtest] == vpt) { // 인접한 헐 셋트
                    return true;
                }
            } else if (hulls[j] == vpt) {
                int jtest = j - 1;
                if (jtest < 0) jtest = hulls.length - 1;

                if (hulls[jtest] == upt) { // 인접한 헐 셋트
                    return true;
                }

                jtest = j + 1;
                if (jtest >= hulls.length) jtest = 0;

                if (hulls[jtest] == upt) { // 인접한 헐 셋트
                    return true;
                }
            }
        }
        return false;
    }

    static class Vertex {
        Point2D point;
        int num;
        boolean isHull;

        Vertex(Point2D point, int num) {
            this.point = point;
            this.num = num;
        }

    }

    // 입력된 점중에서 convex 경계점만 리턴한다.
    // 입력된 점들이 모두 convex hull인가를 확인할 수도 있음 - pop 할 때 바로 리턴
    public static Point2D[] getConvexArray(Point2D[] points) {
        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) a[i] = points[i];
        Arrays.sort(a); // points input이 이미 소팅된 상태라면 다시 소팅할 필요 없다
        Arrays.sort(a, 1, n, a[0].polarOrder());

        Stack<Point2D> hull = new Stack<>();
        hull.push(a[0]);                            // a[0] is first extreme point

        // find index k1 of first point not equal to a[0]
        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!a[0].equals(a[k1])) break;
        if (k1 == n) return null;                   // all points equal

        // find index k2 of first point not collinear with a[0] and a[k1]
        int k2;
        for (k2 = k1 + 1; k2 < n; k2++)
            if (Point2D.ccw(a[0], a[k1], a[k2]) != 0) break;
        hull.push(a[k2 - 1]);                       // a[k2-1] is second extreme point

        // Graham scan; note that a[n-1] is extreme point different from a[0]
        for (int i = k2; i < n; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, a[i]) <= 0) {
                top = hull.pop();
                // return null; convex 가 아닐때 바로 리턴.
            }
            hull.push(top);
            hull.push(a[i]);
        }

        return stackToArrayLIFO(hull);
    }

    // stack to array in LIFO order
    static Point2D[] stackToArrayLIFO(Stack<Point2D> st) {
        Point2D[] result = new Point2D[st.size()];
        int top = 0;
        while (!st.isEmpty()) result[top++] = st.pop();
        return result;
    }

    static class Point2D implements Comparable<Point2D> {

        /**
         * Compares two points by x-coordinate.
         */
        public static final Comparator<Point2D> X_ORDER = new XOrder();

        /**
         * Compares two points by y-coordinate.
         */
        public static final Comparator<Point2D> Y_ORDER = new YOrder();

        /**
         * Compares two points by polar radius.
         */
        public static final Comparator<Point2D> R_ORDER = new ROrder();

        private double x;    // x coordinate
        private double y;    // y coordinate

        /**
         * Initializes a new point (x, y).
         *
         * @param x the x-coordinate
         * @param y the y-coordinate
         * @throws IllegalArgumentException if either {@code x} or {@code y}
         *                                  is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY} or
         *                                  {@code Double.NEGATIVE_INFINITY}
         */
        public Point2D(double x, double y) {
            if (Double.isInfinite(x) || Double.isInfinite(y))
                throw new IllegalArgumentException("Coordinates must be finite");
            if (Double.isNaN(x) || Double.isNaN(y))
                throw new IllegalArgumentException("Coordinates cannot be NaN");
            if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
            else this.x = x;

            if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
            else this.y = y;
        }

        /**
         * Returns the x-coordinate.
         *
         * @return the x-coordinate
         */
        public double x() {
            return x;
        }

        /**
         * Returns the y-coordinate.
         *
         * @return the y-coordinate
         */
        public double y() {
            return y;
        }

        /**
         * Returns the polar radius of this point.
         *
         * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
         */
        public double r() {
            return Math.sqrt(x * x + y * y);
        }

        /**
         * Returns the angle of this point in polar coordinates.
         *
         * @return the angle (in radians) of this point in polar coordiantes (between –&pi;/2 and &pi;/2)
         */
        public double theta() {
            return Math.atan2(y, x);
        }

        /**
         * Returns the angle between this point and that point.
         *
         * @return the angle in radians (between –&pi; and &pi;) between this point and that point (0 if equal)
         */
        private double angleTo(Point2D that) {
            double dx = that.x - this.x;
            double dy = that.y - this.y;
            return Math.atan2(dy, dx);
        }

        /**
         * Returns true if a→b→c is a counterclockwise turn.
         *
         * @param a first point
         * @param b second point
         * @param c third point
         * @return { -1, 0, +1 } if a→b→c is a { clockwise, collinear; counterclocwise } turn.
         */
        public static int ccw(Point2D a, Point2D b, Point2D c) {
            double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
            if (area2 < 0) return -1;
            else if (area2 > 0) return +1;
            else return 0;
        }

        /**
         * Returns twice the signed area of the triangle a-b-c.
         *
         * @param a first point
         * @param b second point
         * @param c third point
         * @return twice the signed area of the triangle a-b-c
         */
        public static double area2(Point2D a, Point2D b, Point2D c) {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        }

        // 벡터의 외적을 이용한 볼록 다각형의 면적
        // extreme point에서 뻗어가는 대각선을 모든 점에 긋고 삼각형의 넓이를 구하는 방법과 평행사변형을 구해서 /2 하는 방법
        // 현재 평행사변형을 이용
        public static double polygonArea(Point2D[] points) {
            // double sum = 0;
            // for (int i = 2; i < points.length; i++) {
            //    double area = area2(points[0], points[i - 1], points[i]);
            //    area = Math.abs(area) / 2;
            //    sum += area;
            //}
            // return sum;

            double area = 0.0d;
            int n = points.length;
            if (n <= 2) return 0;
            for (int i = 0; i < n; i++) {
                int i1 = (i + 1) % n;
                area += (points[i].y + points[i1].y) * (points[i1].x - points[i].x) /*/ 2.0*/;
            }
            return area * 0.5;
        }

        public static double polygonArea(Stack<Point2D> stack) {
            int n = stack.size();
            if (n <= 2) return 0;
            Point2D[] points = new Point2D[n];
            int top = 0;
            while (!stack.isEmpty()) points[top++] = stack.pop();

            return polygonArea(points);
        }

        // 다각형의 둘레 길이를 계산
        public static double polygonPerimeter(Point2D[] points) {
            int n = points.length;
            double sum = 0.0d;
            for (int i = 0; i < n; i++) {
                sum = sum + points[i].distanceTo(points[(i + 1) % n]);
            }
            return sum;
        }

        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
        }

        // 선분과의 거리 AB 에서 P까지의 거리
        public static double distToLine(Point2D A, Point2D B, Point2D P) {

            System.out.println(A + " " + B + " " + P);
            double lineLen = A.distanceTo(B);
            if (lineLen == 0) return 0; // A == B

            //double prj = ((P.x - A.x) * (B.x - A.x) + (P.y - A.y) * (B.y - A.y)) / lineLen;
            double aang = angleA(P, A, B, lineLen);
            double bang = angleA(P, B, A, lineLen);

            System.out.println("aang " + aang + " bang " + bang + " line " + lineLen);
            double dist = 0;
            if (aang > lineLen)
                dist = A.distanceTo(P);
            else if (bang > lineLen)
                dist = B.distanceTo(P);
            else if (aang >= 0 && aang <= lineLen && bang >= 0 && bang <= lineLen) // 직선 수선발 가능
                dist = Math.abs(-1 * (P.x - A.x) * (B.y - A.y) + (P.y - A.y) * (B.x - A.x)) / lineLen;

            //if (aang >= 0 && aang <= lineLen && bang >= 0 && bang <= lineLen) // <PAB, <PBA 둘다 예각이면, 수선발로 직선의 수직거리
            //    dist = Math.abs(-1 * (P.x - A.x) * (B.y - A.y) + (P.y - A.y) * (B.x - A.x)) / lineLen;
            //else if (aang < 0) // <PAB가 둔각이면 A가까운 쪽 점으로 A거리
            //    dist = A.distanceTo(P);
            //else if (bang < 0)
            //    dist = B.distanceTo(P);
            ////if (prj < 0)
            //    return A.distanceTo(P);
            //else if (prj > lineLen)
            //    dist = Math.min(A.distanceTo(P), B.distanceTo(P));
            //else { // 수선으로 길이
            //    dist = Math.abs(-1 * (P.x - A.x) * (B.y - A.y) + (P.y - A.y) * (B.x - A.x)) / lineLen; // A에서 가까운 점
            //}
            //System.out.println("dist " + dist);
            return dist;
        }

        //public static double getDistanceToSegment(Point2D ss, Point2D se, Point2D p) {
        //    return getDistanceToSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
        //}

        public static double getDistanceToSegment(Point2D s1, Point2D s2, Point2D p) {
            // ax + by + c = 0
            if (s2.x == s1.x) {
                return Math.abs(s1.x - p.x);
            }

            double a = s2.y - s1.y;
            double b = s1.x - s2.x;
            double c = s2.x * s1.y - s1.x * s2.y;

            double dist = Math.abs(a * p.x + b * p.y + c);
            dist = dist / (a * a + b * b);
            //System.out.println("dist " + dist);

            return dist;

            //return getDistanceToSegment(s1.x, s1.y, s2.x, s2.y, p.x, p.y);
        }

        public static double gety0(double a, double b, double c, double x) {
            return (-c - a * x) / b;
        }

        public static double getx0(double a, double b, double c, double y) {
            return (-c - b * y) / a;
        }

        /**
         * Returns distance to segment
         *
         * @param sx1 segment x coord 1
         * @param sy1 segment y coord 1
         * @param sx2 segment x coord 2
         * @param sy2 segment y coord 2
         * @param px  point x coord
         * @param py  point y coord
         * @return distance to segment
         */
        public static double getDistanceToSegment(double sx1, double sy1, double sx2, double sy2, double px, double py) {
            Point2D closestPoint = getClosestPointOnSegment(sx1, sy1, sx2, sy2, px, py);
            return getDistance(closestPoint.x, closestPoint.y, px, py);
        }

        /**
         * Returns distance between two 2D points
         *
         * @param point1 first point
         * @param point2 second point
         * @return distance between points
         */
        public static double getDistance(Point2D point1, Point2D point2) {
            return getDistance(point1.x, point1.y, point2.x, point2.y);
        }


        /**
         * Returns distance between two sets of coords
         *
         * @param x1 first x coord
         * @param y1 first y coord
         * @param x2 second x coord
         * @param y2 second y coord
         * @return distance between sets of coords
         */
        public static double getDistance(double x1, double y1, double x2, double y2) {
            // using long to avoid possible overflows when multiplying
            double dx = x2 - x1;
            double dy = y2 - y1;

            // return Math.hypot(x2 - x1, y2 - y1); // Extremely slow
            // return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // 20 times faster than hypot
            return Math.sqrt(dx * dx + dy * dy); // 10 times faster then previous line
        }

        /**
         * Returns closest point on segment to point
         *
         * @param ss segment start point
         * @param se segment end point
         * @param p  point to found closest point on segment
         * @return closest point on segment to p
         */
        public static Point2D getClosestPointOnSegment(Point2D ss, Point2D se, Point2D p) {
            return getClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
        }

        /**
         * Returns closest point on segment to point
         *
         * @param sx1 segment x coord 1
         * @param sy1 segment y coord 1
         * @param sx2 segment x coord 2
         * @param sy2 segment y coord 2
         * @param px  point x coord
         * @param py  point y coord
         * @return closets point on segment to point
         */
        public static Point2D getClosestPointOnSegment(double sx1, double sy1, double sx2, double sy2, double px, double py) {
            double xDelta = sx2 - sx1;
            double yDelta = sy2 - sy1;

            if ((xDelta == 0) && (yDelta == 0)) {
                throw new IllegalArgumentException("Segment start equals segment end");
            }

            double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

            final Point2D closestPoint;
            if (u < 0) {
                closestPoint = new Point2D(sx1, sy1);
            } else if (u > 1) {
                closestPoint = new Point2D(sx2, sy2);
            } else {
                closestPoint = new Point2D(Math.round(sx1 + u * xDelta), Math.round(sy1 + u * yDelta));
            }

            //System.out.println("closestPoint " + closestPoint);
            return closestPoint;
        }

        static double angleA(Point2D P, Point2D A, Point2D B, double distAB) {
            return ((P.x - A.x) * (B.x - A.x) + (P.y - A.y) * (B.y - A.y)) / distAB;
        }


        /**
         * Returns the Euclidean distance between this point and that point.
         *
         * @param that the other point
         * @return the Euclidean distance between this point and that point
         */
        public double distanceTo(Point2D that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * Returns the square of the Euclidean distance between this point and that point.
         *
         * @param that the other point
         * @return the square of the Euclidean distance between this point and that point
         */
        public double distanceSquaredTo(Point2D that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return dx * dx + dy * dy;
        }

        /**
         * Compares two points by y-coordinate, breaking ties by x-coordinate.
         * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
         * if and only if either {@code y0 < y1} or if {@code y0 == y1} and {@code x0 < x1}.
         *
         * @param that the other point
         * @return the value {@code 0} if this string is equal to the argument
         * string (precisely when {@code equals()} returns {@code true});
         * a negative integer if this point is less than the argument
         * point; and a positive integer if this point is greater than the
         * argument point
         */
        public int compareTo(Point2D that) {
            if (this.y < that.y) return -1;
            if (this.y > that.y) return +1;
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
            return 0;
        }

        /**
         * Compares two points by polar angle (between 0 and 2&pi;) with respect to this point.
         *
         * @return the comparator
         */
        public Comparator<Point2D> polarOrder() {
            return new PolarOrder();
        }

        /**
         * Compares two points by atan2() angle (between –&pi; and &pi;) with respect to this point.
         *
         * @return the comparator
         */
        public Comparator<Point2D> atan2Order() {
            return new Atan2Order();
        }

        /**
         * Compares two points by distance to this point.
         *
         * @return the comparator
         */
        public Comparator<Point2D> distanceToOrder() {
            return new DistanceToOrder();
        }

        // compare points according to their x-coordinate
        private static class XOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.x < q.x) return -1;
                if (p.x > q.x) return +1;
                return 0;
            }
        }

        // compare points according to their y-coordinate
        private static class YOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.y < q.y) return -1;
                if (p.y > q.y) return +1;
                return 0;
            }
        }

        // compare points according to their polar radius
        private static class ROrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                double delta = (p.x * p.x + p.y * p.y) - (q.x * q.x + q.y * q.y);
                if (delta < 0) return -1;
                if (delta > 0) return +1;
                return 0;
            }
        }

        // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
        private class Atan2Order implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double angle1 = angleTo(q1);
                double angle2 = angleTo(q2);
                if (angle1 < angle2) return -1;
                else if (angle1 > angle2) return +1;
                else return 0;
            }
        }

        // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
        private class PolarOrder implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double dx1 = q1.x - x;
                double dy1 = q1.y - y;
                double dx2 = q2.x - x;
                double dy2 = q2.y - y;

                if (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
                else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
                else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                    if (dx1 >= 0 && dx2 < 0) return -1;
                    else if (dx2 >= 0 && dx1 < 0) return +1;
                    else return 0;
                } else return -ccw(Point2D.this, q1, q2);     // both above or below

                // Note: ccw() recomputes dx1, dy1, dx2, and dy2
            }
        }

        // compare points according to their distance to this point
        private class DistanceToOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                double dist1 = distanceSquaredTo(p);
                double dist2 = distanceSquaredTo(q);
                if (dist1 < dist2) return -1;
                else if (dist1 > dist2) return +1;
                else return 0;
            }
        }


        /**
         * Compares this point to the specified point.
         *
         * @param other the other point
         * @return {@code true} if this point equals {@code other};
         * {@code false} otherwise
         */
        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (other == null) return false;
            if (other.getClass() != this.getClass()) return false;
            Point2D that = (Point2D) other;
            return this.x == that.x && this.y == that.y;
        }

        /**
         * Return a string representation of this point.
         *
         * @return a string representation of this point in the format (x, y)
         */
        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }

        /**
         * Returns an integer hash code for this point.
         *
         * @return an integer hash code for this point
         */
        @Override
        public int hashCode() {
            int hashX = ((Double) x).hashCode();
            int hashY = ((Double) y).hashCode();
            return 31 * hashX + hashY;
        }
    }
}

