import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 2..
 */
//https://www.acmicpc.net/problem/2098
// 외판원 순회 문제로 N <= 16, 상태 다이나믹을 이용
public class BOJ2098_외판원순회 {
    public static int MAX = 987654321;

    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] a = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = sc.nextInt();
            }
        }

        int[][] d = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                d[i][j] = MAX;
            }
        }

        d[1 << 0][0] = 0; // d[1][0]

        for (int i = 0; i < (1 << N); i++) {
            for (int j = 1; j < N; j++) {
                if ((i & (1 << j)) != 0) {// i에 j가 포함되어 있다면
                    for (int k = 0; k < N; k++) {
                        if (k != j && (i & (1 << k)) != 0 && a[k][j] != 0)
                            d[i][j] = Math.min(d[i][j], d[i - (1 << j)][k] + a[k][j]);
                    }
                }
            }
        }

        // 처음 경로 비용과 비교
        int ans = MAX;
        for (int i = 1; i < N; i++) {
            if (a[i][0] != 0)
                ans = Math.min(ans, d[(1 << N) - 1][i] + a[i][0]);
        }

        System.out.println(ans);
    }
}
