import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 2..
 */
/*
 https://www.acmicpc.net/problem/1102
 발전소 - 상태 다이나믹,, 비트 - 외판원 순회와 비슷 방식
 D[i] = 발전소의 상태를 i 로 만드는데 필요한 최소비용
 i 는 이진수로 나타낸 상태 1, 0
 D[i | (1<<k)] = D[i] + A[j][K]
 j는 i에서 켜져있는 발전소 k는 i에서 켜져있는 발전소
*/
public class BOJ1102_DP {
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

        // YNNYNYY 켜진곳은 Y, 꺼진곳은 N
        String info = sc.next();
        int start = 0;
        for (int i = N - 1; i >= 0; i--) { // 뒤에서 부터
            start *= 2;
            if (info.charAt(i) == 'Y')
                start += 1;
        }

        int p = sc.nextInt();
        int[] d = new int[1 << N];
        Arrays.fill(d, -1);
        d[start] = 0;
        for (int i = 0; i < (1 << N); i++) {
            if (d[i] == -1) continue;

            for (int j = 0; j < N; j++) {
                if ((i & (1 << j)) != 0) { //j가 켜져있다면
                    for (int k = 0; k < N; k++) {
                        if ((i & (1 << k)) == 0) // k가 '꺼져' 있다면
                            if (d[i | (1 << k)] == -1 || d[i | (1 << k)] > d[i] + a[j][k])
                                d[i | (1 << k)] = d[i] + a[j][k];
                    }
                }
            }
        }

        int ans = -1;
        for (int i = 0; i < (1 << N); i++) {
            if (d[i] == -1) continue;
            int cnt = 0;
            for (int j = 0; j < N; j++) {
                if ((i & (1 << j)) != 0) cnt += 1;
            }
            if (cnt >= p)
                if (ans == -1 || ans > d[i])
                    ans = d[i];
        }

        System.out.println(ans);
    }
}
