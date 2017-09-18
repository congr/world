import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 19..
 */
/*
    피보나치 수는 0과 1로 시작한다. 0번째 피보나치 수는 0이고, 1번째 피보나치 수는 1이다. 그 다음 2번째 부터는 바로 앞 두 피보나치 수의 합이 된다.
    이를 식으로 써보면 Fn = Fn-1 + Fn-2 (n>=2)가 된다.
    n=17일때 까지 피보나치 수를 써보면 다음과 같다.
    0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597
    n이 주어졌을 때, n번째 피보나치 수를 구하는 프로그램을 작성하시오.
    n은 1,000,000,000,000,000,000보다 작거나 같은 자연수이다.
    첫째 줄에 n번째 피보나치 수를 1,000,000으로 나눈 나머지를 출력한다.
*/
// 피사노 주기를 이용한 피보나치
// 피사노 주기란 피보나치 수를 K로 나눈 나머지는 항상 주기를 갖게 된다는 것이다
// 피보나치 수를 3으로 나누었을 때, 주기의 길이는 8이다
public class BOJ2749_피보나치수3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long N = sc.nextLong();

        int M = 1000000;    // 나눌수
        //int K = 6;          // 10^6 이므로 K는 6
        //int P = 15 * (int) Math.pow(10, K - 1); // 주기는 15 * 10^(k-1)
        int P = 15 * M / 10;// M/10 은 M^(k-1) 과 같다

        long[] D = new long[P + 1];
        fibonacci(D, P, M);
        System.out.println(D[(int) (N % P)] % M); // D[N] % M = D[N % P] % M
    }

    // 주기는 1500000 - recursive는 stack overflow
    static long fibonacci(long[] D, int n, int m) {
        D[0] = 0;
        D[1] = 1;
        for (int i = 2; i <= n; i++) {
            D[i] = D[i - 1] + D[i - 2];
            D[i] %= m;
        }
        return D[n];
    }
}
