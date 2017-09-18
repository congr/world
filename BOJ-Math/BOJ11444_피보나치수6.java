import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 19..
 */
// 첫째 줄에 n번째 피보나치 수를 1,000,000,007으로 나눈 나머지를 출력한다.
// 주기를 알수 없다 행렬곱셈이나 분할정복으로 해야한다
public class BOJ11444_피보나치수6 {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long N = sc.nextLong();

        long[][] A = {{1, 1}, {1, 0}};
        long[][] R = muliply(A, N, 2);

        System.out.println(R[0][1]);
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
}
