import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 9..
 */
// 2017 05 3번 로마의 양치기 DP 문제
// 코잼당시 힘들게 해결을 하긴 하였으나 고전.
// 경우의 수를 D에 계속 더하면 overflow로 답이 이상한 결과가 나온다. 출력시 0이면 "X"로 출력하고 왠만하면 overflow안나도록 Mod를 하고 long으로
public class P3_로마의양치기 {
    static final int MAX = 10001;

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201705/P3/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        long[][] D = new long[MAX][MAX];
        D[1][1] = D[4][3] = D[5][2] = D[9][3] = D[10][2] = 1;

        for (int i = 2; i < MAX; i++) {
            for (int j = 1; j < MAX; j++) {
                if (i >= 1 && j >= 1) D[i][j] += D[i - 1][j - 1] % 987654321; // 계속 더하면 int overflow가 발생
                if (i >= 4 && j >= 3) D[i][j] += D[i - 4][j - 3] % 987654321;
                if (i >= 5 && j >= 2) D[i][j] += D[i - 5][j - 2] % 987654321;
                if (i >= 9 && j >= 3) D[i][j] += D[i - 9][j - 3] % 987654321;
                if (i >= 10 && j >= 2) D[i][j] += D[i - 10][j - 2] % 987654321;
            }
        }

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            //System.out.println(D[N][K]);
            String result = D[N][K] == 0 ? "X" : "O"; // 0은 이면 x로 하는 것이 안전, overflow 가 발생해도 된다
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }
}
