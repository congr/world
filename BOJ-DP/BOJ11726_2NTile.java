import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 24..
 */
// 2×n 크기의 직사각형을 1×2, 2×1 타일로 채우는 방법의 수를 구하는 프로그램을 작성하시오.
public class BOJ11726_2NTile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int[] D = new int[N + 3]; // D[2] = 2를 쓸려면 0이 입력되었을 때 + 3 되어야 함

        D[1] = 1;
        D[2] = 2;
        for (int i = 3; i <= N; i++) {
            D[i] = (D[i - 2] + D[i - 1]) % 10007;
        }

        System.out.println(D[N]);

        //System.out.println(solve(N));
    }

    // Bottom-up recursive
    static int[] D = new int[1001];

    static int solve(int n) {
        if (n <= 1) return 1;
        if (n == 2) return 2;

        if (D[n] > 0) // n 이 3보다 크다면 채우는 방법이 반드시 0보다는 큰 값이 존재한다
            return D[n];

        D[n] = (solve(n - 2) + solve(n - 1)) % 10007;
        return D[n];
    }
}