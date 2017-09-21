import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 19..
 */
/*
 ‘(‘, ‘)’ 문자로만 이루어진 문자열을 괄호 문자열이라 한다. 올바른 괄호 문자열이란 다음과 같이 정의된다. ()는 올바른 괄호 문자열이다.
 S가 올바른 괄호 문자열이라면, (S)도 올바른 괄호 문자열이다.
 S와 T가 올바른 괄호 문자열이라면, 두 문자열을 이어 붙인 ST도 올바른 괄호 문자열이다.
 (()())()은 올바른 괄호 문자열이지만 (()은 올바른 괄호 문자열이 아니다. 괄호 문자열이 주어졌을 때 올바른 괄호 문자열인지 확인하는 방법은 여러 가지가 있다.
 하지만 우리가 궁금한 것은 길이가 L인 올바른 괄호 문자열의 개수이다.
 길이 L이 주어졌을 때 길이가 L인 서로 다른 올바른 괄호 문자열의 개수를 출력하는 프로그램을 만들어 보자.

 각 테스트 케이스에 대해 길이가 L인 올바른 괄호 문자열의 개수를 1,000,000,007로 나눈 나머지를 출력하시오.
 */

// http://www.geeksforgeeks.org/program-nth-catalan-number/ 참고
// 카탈란 수를 찾는 방법 2가지가 있는데 2nCn 조합을 이용한 방법과, 시그마를 이용한 방법
// 솔루션 https://gist.github.com/Baekjoon/58302ab9793b7e861fa8
public class BOJ10422_괄호 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            // 카탈란 수를 DP를 이용해서 채우고 N/2출력
            long[] D = new long[N + 1]; // !!! mod 할 수가 크다면 long으로 잡는 것이 안전

            D[0] = 1;
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j < i; j++) {
                    D[i] += D[j] * D[i - 1 - j];
                    D[i] %= 1000000007;
                }
            }

            // 짝수일 경우 N/2를 출력하고, 홀수면 0을 출력
            if (N % 2 == 0) System.out.println(D[N / 2]);
            else System.out.println(0);
        }
    }

    // 오답..
    // catalan number = 2nCn / n+1
    static long catalan(int N) {
        long c = (long) binomial(2 * N, N);
        System.out.println(N + " " + c);
        return c / (N + 1);
    }

    // nCk 를 구함 50도 안됨
    static long binomial(int n, int k) {
        if (k > n - k)
            k = n - k;

        long b = 1;
        for (int i = 1, m = n; i <= k; i++, m--)
            b = b * m / i;
        return b;
    }
}
