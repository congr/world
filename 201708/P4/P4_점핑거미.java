import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 8..
 */
// 2017 08 2차 우주 숲의 점핑 거미 DP^3 으로 Large pass
public class P4_점핑거미 {
    final static int MOD = 1000000007;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P4/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            Line[] lines = new Line[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y1 = sc.nextInt();
                int y2 = sc.nextInt();
                lines[i] = new Line(x, y1, x, y2);
            }

            Arrays.sort(lines); // !!!!! 소팅을 반드시 해야된다

            long[][] D = new long[N][2]; // !!! 범위가 클때는 안전하게 long으로 하자
            D[0][0] = 1; // y2 u
            D[0][1] = 1; // y1 d

            for (int i = 1; i < N; i++) {
                D[i][0] = D[i - 1][0] % MOD + D[i - 1][1] % MOD;
                D[i][1] = D[i - 1][0] % MOD + D[i - 1][1] % MOD;

                for (int j = 0; j < i - 1; j++) { // prev j:start, i:end
                    Line uuHori = new Line(lines[j].getX1(), lines[j].getY2(), lines[i].getX1(), lines[i].getY2());
                    Line udHori = new Line(lines[j].getX1(), lines[j].getY2(), lines[i].getX1(), lines[i].getY1());
                    Line ddHori = new Line(lines[j].getX1(), lines[j].getY1(), lines[i].getX1(), lines[i].getY1());
                    Line duHori = new Line(lines[j].getX1(), lines[j].getY1(), lines[i].getX1(), lines[i].getY2());

                    if (Line.isAllIntersectByHoriLine(lines, j, i, uuHori))
                        D[i][0] += D[j][0] % MOD;

                    if (Line.isAllIntersectByHoriLine(lines, j, i, udHori))
                        D[i][1] += D[j][0] % MOD;

                    if (Line.isAllIntersectByHoriLine(lines, j, i, ddHori))
                        D[i][1] += D[j][1] % MOD;

                    if (Line.isAllIntersectByHoriLine(lines, j, i, duHori))
                        D[i][0] += D[j][1] % MOD;
                }
            }

            // !!! MOD 를 할때 마지막에 꼭 한번더 MOD 해야함, 각각을 Mod해서 더하면 최종값이 MOD보다 커지는 경우가 발생할 수 있다
            //System.out.println(D[N - 1][0] + " " + D[N - 1][1]);
            //System.out.println(((D[N - 1][0] % MOD)  + (D[N - 1][1] % MOD)));

            long result = (D[N - 1][0] + D[N - 1][1]) % MOD;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    // Line2D.Double로 쓰려면 Dummy 로 만들어 소팅한 후 Line2D.Double에 넣거나,
    // 혹은 Line2D.Double 클래스를 상속받고 Comparable을 implement하거나 해야함.
    static class Line extends Line2D.Double implements Comparable<Line2D.Double> {
        Line(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
        }

        @Override
        public int compareTo(Line2D.Double o) {
            double d = this.getX1() - o.getX1(); // 0.1 이 int cast하면 0이 될 수 있다
            if (d > 0) return 1;
            else if (d < 0) return -1;
            return 0;
        }

        // lines 는 모두 세로선, 주어진 hori line이 모두 교차하면 true return, 한번이라도 교차하지 않을 경우 바로 return
        static boolean isAllIntersectByHoriLine(Line[] lines, int start, int end, Line hori) {
            for (int i = start + 1; i < end; i++) {
                if (hori.intersectsLine(lines[i]) == false) return false;
            }

            return true;
        }
    }
}
