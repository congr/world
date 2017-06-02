import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 2..
 */
public class BOJ10844_쉬운계단수 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int L = 10; // 0 ~ 9
        int N = sc.nextInt();
        int[][] D = new int[N + 1][L];

        Arrays.fill(D[1], 1);
        D[1][0] = 0;
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < L; j++) {
                if (j > 0 && j < 9)
                    D[i][j] = (D[i - 1][j - 1] + D[i - 1][j + 1]) % 1000000000;
                else if (j == 0)
                    D[i][j] = D[i - 1][j + 1] % 1000000000;
                else if (j == 9)
                    D[i][j] = D[i - 1][j - 1] % 1000000000;
            }
        }

        //System.out.println(Arrays.toString(D[N]));
        long cnt = 0;
        for (int i = 0; i < L; i++) {
            cnt = (cnt + D[N][i]); // !여기가 문제 - int로 하면 더하다가 overflow도 발생할 수 있음 -> 차라리 long 하자
        }
        System.out.println(cnt % 1000000000);
    }
}
