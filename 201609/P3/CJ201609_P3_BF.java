import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by cutececil on 2017. 4. 21..
 */
// 볼록 사각형이 최소가 되도록 4개점을 뽑고, 그 넓이를 구하라.
// 모든 경우의 수 200개중 4개의 점을 찾아서 넓이 비교 200 * 199 * 198 * 197 최적화 필요
public class CJ201609_P3_BF {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P3/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            Point2D[] pts = new Point2D[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                pts[i] = new Point2D(x, y);
            }

            // 1. sort points y - x order
            Arrays.sort(pts);
            //System.out.println(Arrays.toString(pts));
            //Arrays.sort(pts, 1, N, pts[0].polarOrder()); // 여기서 소팅할 필요 없다

            // 2. exhaustive search 200 * 199 * 198 * 197 -> n = 200, 64684950, in time solution
            int cnt = 0;
            double bestArea = Double.MAX_VALUE;
            Point2D[] rect = new Point2D[4];
            for (int i = 0; i < N; i++) {
                rect[0] = pts[i];
                for (int j = i + 1; j < N; j++) {
                    rect[1] = pts[j];
                    for (int k = j + 1; k < N; k++) {
                        rect[2] = pts[k];
                        for (int l = k + 1; l < N; l++) {
                            rect[3] = pts[l];                                   // 사각형 4점을 선택
                            cnt++;

                            Point2D[] ca = getConvexArray(rect);                 // 4점을 convex hull인지 확인하고 ccw순으로 소팅한 점 배열을 넘겨받음
                            if (ca == null || ca.length != rect.length) continue; // 4점중 pop 한경우가 있다면 바로 null 리턴

                            double area = Point2D.polygonArea(ca);
                            //System.out.println("area: " + area + " " + Arrays.toString(ca));
                            if (area == 0) continue;
                            bestArea = Math.min(bestArea, area);
                            // System.out.println("area: " + area + " best: " + bestArea);
                        }
                    }
                }
            }

            String result = String.format("%.1f", bestArea); // 100.25 -> 100.3
            System.out.println(result);
            wr.write(result + "\n");
            //System.out.println(cnt);
        }

        sc.close();
        wr.close();
    }

    public static Stack<Point2D> getConvexStack(Point2D[] points) {
        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) a[i] = points[i];
        Arrays.sort(a);
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
            }
            hull.push(top);
            hull.push(a[i]);
        }
        //return (hull.size() == points.length);
        return hull;
    }

    public static Point2D[] getConvexArray(Point2D[] points) {
        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) a[i] = points[i];
        //Arrays.sort(a); // points input이 이미 소팅된 상태라면 다시 소팅할 필요 없다
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
                return null;
            }
            hull.push(top);
            hull.push(a[i]);
        }

        Point2D[] result = new Point2D[hull.size()];
        int top = 0;
        while (!hull.isEmpty()) result[top++] = hull.pop();
        return result;
    }
}
