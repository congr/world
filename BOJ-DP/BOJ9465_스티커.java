import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 10..
 */
public class BOJ9465_스티커 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int[][] D = new int[N][2];
            int[][] A = new int[N][2];
            
            for (int i = 0; i < N; i++) {
                A[i][0] = sc.nextInt();
            }
            for (int i = 0; i < N; i++) {
                A[i][1] = sc.nextInt();
            }
            
            D[0][0] = A[0][0];
            D[0][1] = A[0][1];
            D[1][0] = D[0][1] + A[1][0];
            D[1][1] = D[0][0] + A[1][1];
            
            for (int i = 2; i < N; i++) {
                D[i][0] = Math.max(Math.max(D[i - 2][0], D[i - 2][1]), D[i - 1][1]) + A[i][0];
                D[i][1] = Math.max(Math.max(D[i - 2][0], D[i - 2][1]), D[i - 1][0]) + A[i][1];
            }
            
            System.out.println(Math.max(D[N-1][0], D[N-1][1]));
        }
    }
}
