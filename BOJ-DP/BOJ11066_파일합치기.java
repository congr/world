import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 13..
 */
public class BOJ11066_파일합치기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        while (T-- > 0) {
            int N = sc.nextInt();
            int[] A = new int[N];
            int[] S = new int[N + 1];
            for (int i = 0; i < N; i++) {
                A[i] = sc.nextInt();
                S[i + 1] = S[i] + A[i];
            }

            int[][] D = new int[N][N];
            for (int i = 0; i < N; i++) {
                Arrays.fill(D[i], -1);
            }

            int result = go(A, S, D, 0, N - 1);
            System.out.println(result);
        }
    }

    static int go(int[] A, int[] S, int[][] D, int i, int j) {
        if (i == j) return 0;

        if (D[i][j] != -1)
            return D[i][j];

        int min = 987654321;
        for (int k = i; k <= j - 1; k++) {
            int temp = go(A, S, D, i, k) + go(A, S, D, k + 1, j) + S[j + 1] - S[i];
            min = Math.min(min, temp);
        }

        D[i][j] = min;
        return min;
    }
}
