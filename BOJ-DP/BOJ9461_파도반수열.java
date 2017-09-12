import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 27..
 */
/*
파도반 수열 P(N)은 나선에 있는 정삼각형의 변의 길이이다. P(1)부터 P(10)까지 첫 10개 숫자는 1, 1, 1, 2, 2, 3, 4, 5, 7, 9이다.

N이 주어졌을 때, P(N)을 구하는 프로그램을 작성하시오.
 */
public class BOJ9461_파도반수열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // precalc
        int M = 100;
        long[] D = new long[M + 1];
        D[0] = D[1] = D[2] = 1;

        // D[N] = D[N-1] + D[N-5] 으로 해도 된다 대신 D[3], D[4]도 2로 초기화필요
        //D[3] = D[4] = 2;
        //for (int i = 5; i <= M ; i++) {
        //    D[i] = D[i-1] + D[i-5];
        //}

        // D[N] = D[N-2] + D[N-3]
        for (int i = 3; i <= M; i++) {
            D[i] = D[i - 2] + D[i - 3];
        }
        //System.out.println(Arrays.toString(D));

        // input
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int N = sc.nextInt();
            System.out.println(D[N-1]);
        }
    }
}
