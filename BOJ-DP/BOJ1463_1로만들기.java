import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 1..
 */
/* 정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.
    X가 3으로 나누어 떨어지면, 3으로 나눈다.
    X가 2로 나누어 떨어지면, 2로 나눈다.
    1을 뺀다.
*/
public class BOJ1463_1로만들기 {
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
