import java.util.Arrays;
import java.util.Scanner;

public class BOJ_TEST {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 동전 개수
        int K = sc.nextInt(); // 동전 합
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        //System.out.println(Arrays.toString(A));
        int[] D = new int[K + 1];
        Arrays.fill(D, 987654321);
        D[0] = 0;

        for (int n = 0; n < N; n++) { // 동전 종류
            for (int k = 0; k <= K; k++) { // 동전 합
                if (k - A[n] >= 0)
                    D[k] = Math.min(D[k], D[k - A[n]] + 1);
            }
        }

        if (D[K] == 987654321) System.out.println("-1");
        else System.out.println(D[K]);
    }
}
