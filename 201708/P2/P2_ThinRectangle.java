import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 17..
 */
// 2017 8월 2차
// 모든 점을 비교할 필요없이 x축 인접, y축 인접한 점만 비교하면 된다 Set2까지 패스
public class P2_ThinRectangle {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P2/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            RectPoint[] rp = new RectPoint[N];

            for (int i = 0; i < N; i++) {
                rp[i] = new RectPoint(sc.nextInt(), sc.nextInt());
            }

            double ans = 0;
            Arrays.sort(rp, (o1, o2) -> o1.x - o2.x);
            double[] xmins = solve(rp);

            Arrays.sort(rp, (o1, o2) -> o1.y - o2.y);
            double[] ymins = solve(rp);

            // thin이 같다면 가장 작은 peri를 출력
            if (xmins[0] > ymins[0]) ans = ymins[1];
            else if (xmins[0] < ymins[0]) ans = xmins[1];
            else ans = Math.min(xmins[1], ymins[1]);

            long result = (long) ans;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static double[] solve(RectPoint[] rp) {
        double minThin = Double.MAX_VALUE;
        double minPeri = Double.MAX_VALUE;

        RectPoint prev = rp[0];
        for (int i = 1; i < rp.length; i++) {
            double thin = prev.calcThin(rp[i]);

            if (minThin > thin) {
                minThin = thin;
                minPeri = prev.calcPeri(rp[i]);
            } else if (minThin == thin) {
                minPeri = Math.min(minPeri, prev.calcPeri(rp[i]));
            }

            //System.out.println("thin " + thin + " peri " + prev.calcPeri(rp[i]));
            prev = rp[i];
        }

        return new double[]{minThin, minPeri};
    }

    static class RectPoint {
        int x, y;

        RectPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[x " + x + " y " + y + "]";
        }

        double calcThin(RectPoint that) {
            double dx = Math.abs(this.x - that.x);
            double dy = Math.abs(this.y - that.y);
            double thin = Math.min(dx, dy) / Math.max(dx, dy);
            return thin;
        }

        double calcPeri(RectPoint that) {
            double dx = Math.abs(this.x - that.x);
            double dy = Math.abs(this.y - that.y);
            double peri = dx * 2 + dy * 2;
            return peri;
        }
    }
}
