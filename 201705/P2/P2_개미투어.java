import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 11..
 */
public class P2_개미투어 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P2/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            int[] P = new int[N];
            for (int i = 0; i < N; i++) P[i] = sc.nextInt();

            Arrays.sort(P);

            // 0 ~ 2 까지 걸쳐야 하므로 P[i + 2] - P[i] 가 가장 큰 구간을 찾는다
            int maxDiff = 0;
            for (int i = 0; i < N - 2; i++) {
                int d = P[i + 2] - P[i];
                maxDiff = Math.max(maxDiff, d);
            }

            double ans = 0;
            if (N == 1) ans = 0; // 1인 경우 0
            else if (N == 2) ans = Math.round((double) (P[1] - P[0]) / 2); // N이 2인 경우 /2
            else ans = Math.round((double) maxDiff / 2);

            int result = (int) ans;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
