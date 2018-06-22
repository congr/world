import java.util.Scanner;

public class BOJ1149_RGB거리 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // N집
        int[][] A = new int[N][3];
        for (int i = 0; i < N; i++) {
            A[i][0] = sc.nextInt();
            A[i][1] = sc.nextInt();
            A[i][2] = sc.nextInt();
        }

        int[][] D = new int[N][3];
        D[0][0] = A[0][0];
        D[0][1] = A[0][1];
        D[0][2] = A[0][2];
        for (int i = 1; i < N; i++) {
            D[i][0] = Math.min(D[i - 1][1], D[i - 1][2]) + A[i][0];
            D[i][1] = Math.min(D[i - 1][2], D[i - 1][0]) + A[i][1];
            D[i][2] = Math.min(D[i - 1][0], D[i - 1][1]) + A[i][2];
        }

        System.out.println(Math.min(D[N-1][0], Math.min(D[N-1][1], D[N-1][2])));
    }
}
