import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
// N*M크기의 행렬 A와 M*K크기의 행렬 B가 주어졌을 때, 두 행렬을 곱하는 프로그램을 작성하시오.
// N * M, M * K 를 곱하면, N * K 행렬이 된다 (M은 반드시 같아야 행렬 곱셈이 가능하다)
public class BOJ2740_행렬곱셈 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // N * M 행렬
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] A = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                A[i][j] = sc.nextInt();
        }

        // M * K 행렬
        M = sc.nextInt();
        int K = sc.nextInt();
        int[][] B = new int[M][K];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < K; j++)
                B[i][j] = sc.nextInt();
        }

        int[][] C = new int[N][K];
        for (int n = 0; n < N; n++) {
            for (int k = 0; k < K; k++) {
                for (int m = 0; m < M; m++) {
                    C[n][k] += A[n][m] * B[m][k];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}
