import java.util.Scanner;

/**
 * Created by cutececil on 2017. 5. 31..
 */
public class BOJ11052_Boonga {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] P = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            P[i] = sc.nextInt();
        }

        int[] D = new int[N + 1];
        for (int n = 1; n <= N; n++) {
            for (int k = 1; k <= n; k++) {
                D[n] = Math.max(D[n], D[n - k] + P[k]);
            }
        }

        //System.out.println(Arrays.toString(D));
        System.out.println(D[N]);
    }
}
