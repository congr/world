import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 20..
 */
public class P3_Small {
    static public int MAX = 10000;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/sample1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int L = sc.nextInt();
            int M = sc.nextInt();
            int N = sc.nextInt();

            int[] Ns = new int[N];
            int[] Ms = new int[M];
            int[] Ls = new int[L];

            for (int i = 0; i < L; i++) { // 빨강
                Ls[i] = sc.nextInt();
            }

            for (int i = 0; i < M; i++) { // 파랑
                Ms[i] = sc.nextInt();
            }

            for (int i = 0; i < N; i++) { // 보라
                Ns[i] = sc.nextInt();
            }

            Arrays.sort(Ls);
            Arrays.sort(Ms);
            Arrays.sort(Ns);

            //for (int j = 0; j < M; j++) {
            //
            //}
            //
            //for (int j = 0; j < L; j++) {
            //    for (int i = j + 1; i < L; i++) {
            //    }
            //}


            int lw = 0, mw = 0;
            if (L > 0) lw = Ls[L - 1] - Ls[0];
            if (M > 0) mw = Ms[M - 1] - Ms[0];
            long result = lw + mw;
            System.out.println("L " + lw + " M " + mw);

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
