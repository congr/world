import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
public class AProblem05_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int result = solve(A, N, 10000);
        System.out.println(result);
    }

    static int solve(int[] A, int N, int K) {
        int[] D = new int[K + 1];
        // !!! 중복없이 할 때는 불필요
        //for (int i = 0; i < N; i++) {
        //    D[A[i]] = 1;
        //}

        D[0] = 1; // 0을 만드는 방법 1
        for (int n = 0; n < N; n++) {
            for (int k = K; k >= A[n]; k--) { // 동전을 중복하지 않고
                if (k - A[n] >= 0 && D[k - A[n]] > 0) // !!! 중복하지 않게 할때 조건
                    D[k] += D[k - A[n]];
            }
        }

        //System.out.println(Arrays.toString(D));

        // 처음으로 못만들어낸 숫자가 발견되면 그 수를 리턴
        for (int k = 1; k <= K; k++) {
            if (D[k] == 0) return k;
        }

        // 모든 수를 다 만들었다면 -1 리턴
        return -1;
    }
}
