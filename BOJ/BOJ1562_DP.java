import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 2..
 */
/*
https://www.acmicpc.net/problem/1562
    계단수 문제, 쉬운 계단수와 차이는 모든 수가 한번 등장해야 한다는 조건
    "0부터 9까지 모든 한 자리수가 자리수로 등장하면서" => 0~9가 모두 등장해야 한다는 조건
    D[i][j][s] = 길이가 i, 마지막수 j, 등장한 수 s
    d[i + 1][j + 1][s | (1 << (j + 1))] += d[i][j][s];
    d[i + 1][j - 1][s | (1 << (j - 1))] += d[i][j][s];
*/

public class BOJ1562_DP {
    public static long mod = 1000000000L;

    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        long[][][] d = new long[N + 1][10][1 << 10];
        for (int i = 1; i <= 9; i++) {
            d[1][i][1 << i] = 1L;
        }

        for (int i = 1; i <= N - 1; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k < (1 << 10); k++) {
                    if (d[i][j][k] == 0) continue;
                    if ((k & (1 << j)) == 0) continue; // k 상태에 j가 없다면

                    if (j + 1 <= 9) { // 9보다 큰 수는 없다
                        d[i + 1][j + 1][k | (1 << (j + 1))] += d[i][j][k];
                        d[i + 1][j + 1][k | (1 << (j + 1))] %= mod; // 계속 mod를 해야되는구나.
                    }

                    if (j - 1 >= 0) { // 0보다 작은 수는 없다
                        d[i + 1][j - 1][k | (1 << (j - 1))] += d[i][j][k];
                        d[i + 1][j - 1][k | (1 << (j - 1))] %= mod;
                    }
                }
            }
        }

        long ans = 0;
        for (int j = 0; j < 10; j++) {
            ans += d[N][j][(1 << 10) - 1]; // N길이에 j로 끝나는 수중에 j가 첫등장인 경우의 가지수
            ans %= mod;
        }
        System.out.println(ans);
    }
}
