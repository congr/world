import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 30..
 */
public class BOJ11048_이동하기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] A = new int[N + 1][M + 1];
        int[][] D = new int[N + 1][M + 1];
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                A[i][j] = sc.nextInt();
            }
        }
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                D[i][j] = Math.max (D[i-1][j-1], Math.max(D[i-1][j], D[i][j-1])) + A[i][j]; 
            }
        }

        System.out.println(D[N][M]);
    }
    
    /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] A = new int[N][M];
        int[][] D = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = sc.nextInt();

                // get the most candies previously
                int candy = 0;
                if (insideTable(i, j - 1, N, M))
                    candy = Math.max(candy, D[i][j - 1]);
                if (insideTable(i - 1, j, N, M))
                    candy = Math.max(candy, D[i - 1][j]);
                if (insideTable(i - 1, j - 1, N, M))
                    candy = Math.max(candy, D[i - 1][j - 1]);

                D[i][j] = A[i][j] + candy; // 현재칸의 사탕 + 이전에 모은 가장 많은 사탕
            }
        }

        System.out.println(D[N - 1][M - 1]);
    }

    static boolean insideTable(int i, int j, int N, int M) {
        if (i >= 0 && i < N && j >= 0 && j < M) return true;
        else return false;
    }*/
}
