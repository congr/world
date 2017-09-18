import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
// 크기가 N*N인 행렬 A가 주어진다. 이 때, A의 B제곱을 구하는 프로그램을 작성하시오. 수가 매우 커질 수 있으니, A^B의 각 원소를 1,000으로 나눈 나머지를 출력한다.
// 첫째 줄에 행렬의 크기 N과 B가 주어진다. (2 ≤ N ≤  5, 1 ≤ B ≤ 100,000,000,000)
// 둘째 줄부터 N개의 줄에 행렬의 각 원소가 주어진다. 행렬의 각 원소는 1,000보다 작거나 같은 자연수 또는 0이다.
//
public class BOJ10830_행렬제곱 {
    static final int MOD = 1000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        long B = sc.nextLong();
        long[][] A = new long[N][N]; // 행렬의 제곱은 정사각 행렬만 가능하다
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt() % MOD; // !!! 입력할 때 부터 %하거나, 혹은 multiply()의 base case중 B = 1일 때 A리턴시에 %를 또 해야한다
            }
        }

        long[][] result = muliply(A, B, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 재귀 divide and conquer - 행렬을 B만큼 제곱하는데 곱셈은 행렬 곱셈인 multiplyMatrix를 사용
    static long[][] muliply(long[][] A, long B, int N) {
        // base case
        if (B == 0) {
            long[][] E = new long[N][N];
            for (int i = 0; i < N; i++) Arrays.fill(E, 1);
            return E;
        } else if (B == 1) {
            return A;
        }

        if (B % 2 == 0) {
            long[][] T = muliply(A, B / 2, N); // 중복연산을 피하기 위해 A ^ (B/2) * A ^ (B/2) 를 하면 같은 연산이 중복된다
            return muliplyMatrix(T, T, N); // T * T
        } else {
            long[][] T = muliply(A, B - 1, N);
            return muliplyMatrix(A, T, N); // A행렬 * T행렬, A와 T행렬은 N개 행과 열을 갖고있음
        }
    }

    // 정사각 행렬 곱셈 - 행렬의 제곱은 N 정사각 행렬만 가능
    static long[][] muliplyMatrix(long[][] A, long[][] B, int N) {
        return muliplyMatrix(A, B, N, N, N);
    }

    // 행렬 곱셈 N * M, M * K => N * K
    static long[][] muliplyMatrix(long[][] A, long[][] B, int N, int M, int K) {
        long[][] C = new long[N][K];

        for (int n = 0; n < N; n++) {
            for (int k = 0; k < K; k++) {
                for (int m = 0; m < M; m++) {
                    C[n][k] += (A[n][m] * B[m][k]) % MOD;
                    C[n][k] %= MOD; // !!! 곱셈 한후 MOD 해도, 모두 다 덧셈한 후에 MOD를 넘어갈 수 있으므로 MOD 한번더 해야함
                }
            }
        }
        return C;
    }

    static long[][] modMatrix(long[][] A, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = A[i][j] % MOD;
            }
        }
        return A;
    }
}
