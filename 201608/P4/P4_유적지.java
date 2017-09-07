import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by cutececil on 2017. 9. 7..
 */
// 2016 08 2차 유적지 (직선의 방정식, convex hull)
// 두 그룹의 convex hull으로 나누었을 때 가장 작은 둘레 길이 합을 출력
// 핵식은 두그룹으로 어떤 기준에서 나눌 것인가이다. 가장 작은 두 그룹의 둘레가 되도록 나눠야 한다
// 결국 모든 그룹을 N^2으로 모든 점을 다 한번씩 그루핑해서 최소값을 찾는다 (Set3 1분정도에 통과, 제공된 솔루션 CPP도 1분정도 소요)
// 입력 N <= 200 (Set3)
public class P4_유적지 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P4/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            Point2D[] pt = new Point2D[N];
            for (int i = 0; i < N; i++) {
                pt[i] = new Point2D(sc.nextInt(), sc.nextInt());
            }

            Arrays.sort(pt, Point2D.R_ORDER); // 소팅을 polar order로 꼭 해야함

            double minSum = Double.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                for (int j = i; j < N; j++) {
                    double sum = group(pt, i, j, N);
                    minSum = Math.min(minSum, sum);
                }
            }

            String result = String.format("%.5f", minSum);
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static double group(Point2D[] pt, int s1, int e1, int N) {
        int cnt1 = e1 - s1 + 1; // 1그룹개수
        int cnt2 = N - cnt1;

        Point2D[] pt1 = new Point2D[cnt1];
        System.arraycopy(pt, s1, pt1, 0, cnt1);

        Point2D[] pt2 = new Point2D[cnt2];
        for (int i = 0, j = e1 + 1; i < cnt2; i++, j++) {
            pt2[i] = pt[j % N];
        }

        double sum = calcPeri(pt1) + calcPeri(pt2);
        return sum;
    }

    static double calcPeri(Point2D[] org) {
        if (org.length <= 1) return 0;
        else if (org.length == 2) return org[0].distanceTo(org[1]) * 2;

        Stack st = getConvexStack(org);
        double peri = Point2D.polygonPerimeter(st);
        return peri;
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
}
