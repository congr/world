import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 1..
 */
public class BOJ1463 {
    static int dp[] = new int[1000000 + 1];

    public static void main(String[] args) { // class solution
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        Arrays.fill(dp, -1);
        System.out.println(solve(N));
    }

    static int solve(int n) {
        if (n == 1) return 0;
        if (dp[n] != -1) return dp[n];

        int result = solve(n - 1) + 1;
        if (n % 2 == 0) result = Math.min(result, solve(n / 2) + 1);
        if (n % 3 == 0) result = Math.min(result, solve(n / 3) + 1);
        dp[n] = result;
        return result;
    }
}
