import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 24..
 */
public class BOJ11726_2NTile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int[] D = new int[N + 3];

        D[1] = 1;
        D[2] = 2;
        for (int i = 3; i <= N; i++) {
            D[i] = (D[i - 2] + D[i - 1]) % 10007;
        }

        //System.out.println(Arrays.toString(D));
        System.out.println(D[N] % 10007);

        //Arrays.fill(D, -1);
        //System.out.println(solve(N) % 10007);
    }

    // Bottom-up recursive
    static int[] D = new int[1001];

    static int solve(int n) {
        if (n <= 1) return 1;
        if (n == 2) return 2;

        if (D[n] != -1)
            return D[n];

        D[n] = (solve(n - 2) + solve(n - 1)) % 10007;
        return D[n];
    }
}