import java.util.Scanner;

public class BOJ1932_숫자삼각형 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // Line count
        int[][] A = new int[N][N + 1];
        int[][] D = new int[N][N + 1];

        for (int r = 0; r < N; r++) {
            for (int i = 0; i <= r; i++) {
                A[r][i] = sc.nextInt();
            }
        }

        D[0][0] = A[0][0];
        for (int r = 1; r < N; r++) {
            D[r][0] = D[r - 1][0] + A[r][0]; // !!! A[r-1] 이 아니고 A[r]
            for (int i = 1; i <= r; i++) {
                D[r][i] = Math.max(D[r - 1][i - 1], D[r - 1][i]) + A[r][i];
            }
            //System.out.println(Arrays.toString(D[r]));
        }

        int ans = 0;
        for (int i = 0; i <= N; i++) {
            ans = Math.max(ans, D[N - 1][i]);
            //System.out.println(D[N - 1][i]);
        }

        System.out.println(ans);
    }
}
