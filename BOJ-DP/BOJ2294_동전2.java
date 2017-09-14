import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 11..
 */
/*
* 일반적인 동전 문제 - K합을 만드는 최소 동전 개수
*
* n가지 종류의 동전이 있다. 각각의 동전이 나타내는 가치는 다르다.
* 이 동전들을 적당히 사용해서, 그 가치의 합이 k원이 되도록 하고 싶다.
* 그러면서 동전의 개수가 최소가 되도록 하려고 한다. (각각의 동전은 몇개라도 사용할 수 있다.)
* */
public class BOJ2294_동전2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int[] D = new int[K + 1];
        Arrays.fill(D, 987654321);

        D[0] = 0; // 최소 동전 개수
        for (int n = 0; n < N; n++) { // 동전 액면가
            for (int k = 0; k <= K; k++) { // K 는 총합, k는 서브 합
                if (k - A[n] >= 0)
                    D[k] = Math.min(D[k], D[k - A[n]] + 1); // A[n] 동전 1개 + k - A[n]를 만드는 동전 최소 개수
            }
        }

        //System.out.println(Arrays.toString(D));
        if (D[K] == 987654321) System.out.println("-1");
        else System.out.println(D[K]);
    }
}
