import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 11..
 */
public class BOJ2293_동전1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int[] D = new int[K + 1];
        D[0] = 1;

        // 1 + 1 + 2 와 1 + 2 + 1는 같다
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= K; j++) { // K 는 총합, j는 서브 합
                if (j - A[i] >= 0)
                    D[j] += D[j - A[i]];
            }
        }

        // i, j가 헷갈린다면
        //for (int n = 0; n < N; n++) {
        //    for (int k = 0; k <= K; k++) {
        //        if (k - A[n] >= 0)
        //            D[k] += D[k - A[n]];
        //    }
        //}

        // 1 + 1 + 2 는 안됨 1을 한번만 쓴다면
        //for (int n = 0; n < N; n++) {
        //    for (int k = K; k >= A[n]; k--) {
        //        if (k - A[n] >= 0)
        //            D[k] += D[k - A[n]];
        //    }
        //}

        System.out.println(Arrays.toString(D));
        System.out.println(D[K]);
    }
}
