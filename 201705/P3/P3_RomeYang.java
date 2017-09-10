import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 5. 19..
 */
// 막대기
public class P3_RomeYang {

    static final int[] VALUES = /*{10, 9, 5, 4, 1};//*/{1, 4, 5, 9, 10}; // N
    static final int[] WEIGHTS = /*{2, 3, 2, 3, 1};//*/{1, 3, 2, 3, 2}; // K
    static int KofN[] = new int[]{0, 1, 0, 0, 3, 2, 0, 0, 0, 3, 2};

    static boolean debug = true;
    static final int MAX_VALUE = 10001;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P3/temp.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        int[][] D = new int[MAX_VALUE + 1][MAX_VALUE + 1];
        precalc(MAX_VALUE, MAX_VALUE, D);

        if (debug) {
            for (int i = 0; i < MAX_VALUE; i++) {
                System.out.print(i + "| ");
                for (int j = 0; j < MAX_VALUE; j++) {
                    System.out.print(D[i][j] + " ");
                }
            }
        }

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            if (debug) System.out.println("==[N, K] " + N + " " + K);
            if (debug) System.out.println("D => " + D[N][K]);

            String result = D[N][K] > 0 ? "O" : "X";
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static void precalc(int N, int K, int[][] D) {
        for (int i = 1; i < N; i++) {
            D[i][i] = 1;
        }
        //for (int i = 0; i < 5; i++) {
        //    D[VALUES[i]][WEIGHTS[i]] = 1;
        //}

        D[4][3] = 1;
        D[5][2] = 1;
        D[5][4] = 1;
        D[6][3] = 1;
        D[6][5] = 1;
        D[7][4] = 1;
        D[7][6] = 1;
        D[8][5] = 1;
        D[8][6] = 1;
        D[8][7] = 1;
        D[9][3] = 1;
        D[9][5] = 1;
        D[9][6] = 1;
        D[9][7] = 1;
        D[9][8] = 1;
        D[10][2] = 1;
        D[10][4] = 1;
        D[10][6] = 1;
        D[10][7] = 1;
        D[10][8] = 1;
        D[10][9] = 1;

        for (int i = 11; i < N; i++) {
            for (int j = 4; j < K; j++) {
                //D[i][j] = D[i - 1][j - 1] + D[i - 4][j - 3] + D[i - 5][j - 2] + D[i - 9][j - 3] + D[i - 10][j - 2];
                if (D[i][j] == 0) D[i][j] += D[i - 1][j - 1];
                if (D[i][j] == 0) D[i][j] += D[i - 4][j - 3];
                if (D[i][j] == 0) D[i][j] += D[i - 5][j - 2];
                if (D[i][j] == 0) D[i][j] += D[i - 9][j - 3];
                if (D[i][j] == 0) D[i][j] += D[i - 10][j - 2];
            }
        }
    }
}
