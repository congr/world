import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 19..
 */
public class P4_Spider {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P4/Set3.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            //Dummy[] dummies = new Dummy[N];
            Line[] lines = new Line[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y1 = sc.nextInt();
                int y2 = sc.nextInt();
                //dummies[i] = new Dummy(x, y1, y2);
                lines[i] = new Line(x, y1, x, y2);
            }
            //Arrays.sort(dummies);

            //for (int i = 0; i < N; i++) {
            //    int x = dummies[i].x;
            //    int y1 = dummies[i].y1;
            //    int y2 = dummies[i].y2;
            //    lines[i] = new Line2D.Double(x, y1, x, y2);
            //}

            //Collections.sort(lines, new LineComp());
            Arrays.sort(lines, Comparator.comparingDouble(Line2D.Double::getX1));

            long[][] D = new long[N][2];
            D[0][0] = 1;
            D[0][1] = 1;

            for (int i = 1; i < N; i++) {
                Line2D il = lines[i];
                D[i][0] = (D[i - 1][0] + D[i - 1][1]) % 1000000007;
                D[i][1] = (D[i - 1][0] + D[i - 1][1]) % 1000000007;

                for (int j = 0; j < i - 1; j++) {
                    Line2D jl = lines[j];
                    Line2D horiLine1 = new Line2D.Double(il.getX1(), il.getY2(), jl.getX1(), jl.getY2());
                    Line2D horiLine2 = new Line2D.Double(il.getX1(), il.getY1(), jl.getX1(), jl.getY2());
                    Line2D horiLine3 = new Line2D.Double(il.getX1(), il.getY1(), jl.getX1(), jl.getY1());
                    Line2D horiLine4 = new Line2D.Double(il.getX1(), il.getY2(), jl.getX1(), jl.getY1());

                    if (isAllConnected(i, j, lines, horiLine1))
                        D[i][1] += D[j][1] % 1000000007;

                    if (isAllConnected(i, j, lines, horiLine2))
                        D[i][0] += D[j][1] % 1000000007;

                    if (isAllConnected(i, j, lines, horiLine3))
                        D[i][0] += D[j][0] % 1000000007;

                    if (isAllConnected(i, j, lines, horiLine4))
                        D[i][1] += D[j][0] % 1000000007;
                }
            }

            long result = (D[N - 1][0] + D[N - 1][1]) % 1000000007;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static boolean isAllConnected(int i, int j, Line2D[] lines, Line2D horiLine) {
        for (int k = j; k <= i; k++) { // 모든 사이 나무 k를 통과하는가 통과안하면 i - j 연결할 수 없다
            Line2D kl = lines[k];
            if (kl.intersectsLine(horiLine) == false)
                return false;
        }
        return true;
    }

    static class Dummy implements Comparable<Dummy> {
        public int x, y1, y2;

        Dummy(int x, int y1, int y2) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public int compareTo(Dummy o) {
            return this.x - o.x;
        }
    }

    // Line2D.Double로 쓰려면 Dummy 로 만들어 소팅한 후 Line2D.Double에 넣거나,
    // 혹은 Line2D.Double 클래스를 상속받고 Comparable을 implement하거나 해야함.
    static class Line extends Line2D.Double implements Comparable<Line2D.Double> {
        Line(int x1, int y1, int x2, int y2) {
            super(x1, y1, x2, y2);
        }

        @Override
        public int compareTo(Line2D.Double o) {
            double d = this.getX1() - o.getX1(); // 0.1 이 int cast하면 0이 될 수 있다
            if (d > 0) return 1;
            else if (d < 0) return -1;
            return 0;
        }
    }
}
