import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 19..
 */
// 2017 8월 2차
// N^2 으로 set2 패스 못함
public class P2_Large {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P2/Set1_Pa.in"; // path from root
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

            double minThin = Double.MAX_VALUE;
            double minPeri = Double.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    double xlen = Math.abs(rp[i].x - rp[j].x);
                    double ylen = Math.abs(rp[i].y - rp[j].y);
                    double thin = Math.min(xlen, ylen) / Math.max(xlen, ylen);

                    //System.out.println(rp[i] + " " + rp[j] + " " + xlen + " " + ylen + " " + thin);
                    //System.out.println("minThin " + minThin + " thin " + thin);
                    if (minThin >= thin) {
                        minThin = thin;

                        double perimeter = xlen * 2 + ylen * 2;
                        minPeri = perimeter;
                        //System.out.println("updated minPeri " + minPeri + " perimeter" + perimeter);
                    }
                }
            }

            long result = (long)minPeri;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class RectPoint implements Comparable<RectPoint>{
        int x, y;
        double thin;

        RectPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[x " + x + " y " + y + "]";
        }

        @Override
        public int compareTo(RectPoint o) {
            double thin = getThin(this, o);
            return 0;
        }

        public double getThin(RectPoint i, RectPoint j) {
            double xlen = Math.abs(i.x - j.x);
            double ylen = Math.abs(i.y - j.y);
            double thin = Math.min(xlen, ylen) / Math.max(xlen, ylen);
            return thin;
        }
    }
}
