import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 15..
 */
public class P5_해일 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P5/02input.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt(); // N * N 격자
            int K = sc.nextInt(); // 점개수
            int M = sc.nextInt(); // 해일 발생수
            //System.out.println(N + " " + K + " " + M);

            LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            Point2D[] all = new Point2D[K];
            for (int i = 0; i < K; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                all[i] = new Point2D(x, y);
                map.put((double) x + "," + (double) y, i + 1);
            }
            //System.out.println("all " + Arrays.toString(all));

            // convex hull을 구한다
            Point2D[] hull = getConvexArray(all);
            LinkedHashSet<String> set = new LinkedHashSet<>(); // hull 을 HashSet에 저장해서 바로 검색하도록
            LinkedHashMap<Point2D, Integer> idxMap = new LinkedHashMap<>();
            for (int i = 0; i < hull.length; i++) {
                idxMap.put(hull[i], i);
                set.add(hull[i].x() + "," + hull[i].y());
            }
            //System.out.println("hull " + set);

            long sum = 0;
            int p = 0, q = 0; // 이전 해일
            for (int i = 0; i < M; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();

                //System.out.println("t " + a + " " + b);
                int u = Math.min(a, b), v = Math.max(a, b);
                int s = 0; // 대피소

                if (p == 0 && q == 0) { // 첫 해일
                    if (isHullPoint(all[u - 1], all[v - 1], set, hull, idxMap)) {
                        //System.out.println("yes hull");
                        s = getFarthestPoint(all[u - 1], all[v - 1], hull, map);
                        p = u;
                        q = v;
                    } else { // hull점이 아니므로 허위보고
                        s = 0;
                    }
                } else {
                    if (u == p || u == q || v == p || v == q) { // 이전 해일과 같은 번호가 하나는 있어야한다
                        if (isHullPoint(all[u - 1], all[v - 1], set, hull, idxMap)) {
                            //System.out.println("yes hull");
                            s = getFarthestPoint(all[u - 1], all[v - 1], hull, map);
                            p = u;
                            q = v;
                        } else { // hull점이 아니므로 허위보고
                            s = 0;
                        }
                    }
                }
                sum += s % 1000000007;
            }

            long result = sum % 1000000007;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    // hull점들 중에서 p-q를 잇는 직선에서 가장 먼 점을 리턴
    private static int getFarthestPoint(Point2D p, Point2D q, Point2D[] hull, LinkedHashMap<String, Integer> map) {
        //System.out.println("getFarthestPoint " + p + " " + q);
        double maxd = 0;
        ArrayList<Pair<Double, Point2D>> al = new ArrayList<>();
        //ArrayList<Point2D> al = new ArrayList<>(); // 가장 먼점이 여러개이라면 x기준 소트, y기준 소트
        for (int i = 0; i < hull.length; i++) {
            if (p != hull[i] && q != hull[i]) {
                double d = Point2D.getDistanceToSegment(p, q, hull[i]);
                if (maxd < d) {
                    maxd = d;
                }

                al.add(new Pair<>(d, hull[i]));
            }
        }

        Collections.sort(al, (a, b) -> {

            double d1 = a.getKey(), d2 = b.getKey();
            int x1 = (int) a.getValue().x(), y1 = (int) a.getValue().y();
            int x2 = (int) b.getValue().x(), y2 = (int) b.getValue().y();

            if (d2 < d1) return -1;
            if (d2 > d1) return +1;
            if (x1 < x2) return -1;
            if (x1 > x2) return +1;
            if (y1 < y2) return -1;
            if (y1 > y2) return +1;
            return 0;
        });

        Point2D m = al.get(0).getValue();

        Integer ind = map.getOrDefault(m.x() + "," + m.y(), 0);
        //System.out.println("far point " + m + " " + ind);
        return ind;
    }

    // p, q 점이 둘다 hull 점인가
    static boolean isHullPoint(Point2D p, Point2D q, LinkedHashSet<String> set, Point2D[] hull, LinkedHashMap<Point2D,Integer> idxMap) {
        if (set.contains(p.x() + "," + p.y()) && set.contains(q.x() + "," + q.y())) {
            int pi = idxMap.getOrDefault(p, 0);
            int qi = idxMap.getOrDefault(q, 0);

            //int pi = -1, qi = -1;
            //for (int i = 0; i < hull.length; i++) {
            //    Point2D m = hull[i];
            //    if (p.x() == m.x() && p.y() == m.y()) pi = i;
            //    if (q.x() == m.x() && q.y() == m.y()) qi = i;
            //    if (pi > -1 && qi > -1) break;
            //}

            int d = Math.abs(pi - qi);
            if (d == 1 || d == hull.length - 1) return true;

        }

        return false;
    }

    // Array 순서를 cw로 리턴함
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
                //if (hull.isEmpty())
                //    //System.out.println("emp");
                //    return null; //convex 가 아닐때 바로 리턴.
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
}
