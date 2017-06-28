import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 28..
 */
/*
0부터 N까지의 정수 K개를 더해서 그 합이 N이 되는 경우의 수를 구하는 프로그램을 작성하시오.

덧셈의 순서가 바뀐 경우는 다른 경우로 센다(1+2와 2+1은 서로 다른 경우). 또한 한 개의 수를 여러 번 쓸 수도 있다.
 */
public class BOJ2225_합분해 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[][] D = new int[K + 1][N + 1];
        Arrays.fill(D[1], 1); // 1개로 어떤수를 만드는 방법은 오직 하나씩이다 예로 20 1 입력이면 답은 1개임

        for (int k = 2; k <= K; k++) {
            for (int n = 0; n <= N; n++) {
                for (int l = 0; l <= n; l++) {
                    D[k][n] += D[k - 1][n - l];
                    D[k][n] %= 1000000000;
                }
            }
        }

        //for (int i = 1; i <= K; i++) {
        //    for (int j = 0; j <= N; j++) {
        //        System.out.print(D[i][j] + " ");
        //    }
        //    System.out.println();
        //}

        System.out.println(D[K][N]);
    }
}
