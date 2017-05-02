import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 2..
 */
//https://www.acmicpc.net/problem/2133
// 2*1, 1*2 타일로 3*N크기의 벽을 채울 수 있는 방법의 가지수
public class BOJ2133_DP {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] D = new int[N + 1][8]; // N <= 30,  상태는 8가지

        D[0][7] = 1; // i열을 채우는 방법은 1가지 밖에 없다. (이해 안됨..)
        for (int i = 1; i <= N; i++) {
            D[i][0] = D[i - 1][7];
            D[i][1] = D[i - 1][5];
            D[i][2] = D[i - 1][6];
            D[i][3] = D[i - 1][4];
            D[i][4] = D[i - 1][3] + D[i - 1][7];
            D[i][5] = D[i - 1][1] + D[i - 1][7];
            D[i][6] = D[i - 1][2];
            D[i][7] = D[i - 1][0] + D[i - 1][4] + D[i - 1][5];
        }
        System.out.println(D[N][7]);
    }
}
