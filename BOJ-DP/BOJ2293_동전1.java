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
        Arrays.fill(D, -1);

        for (int i = 1; i < N; i++) {
            int V = A[i]; // 동전 액면가
            for (int j = 0; j <= K; j++) { // K 는 총합, j는 서브 합
                if (j - V >= 0)
                    D[j] += D[j - V]; // j를 만드는 방법 + j-v를 만드는 방법
            }
        }

        //System.out.println(Arrays.toString(D));
        System.out.println(D[K]);
    }
}
