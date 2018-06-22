import java.util.Arrays;
import java.util.Scanner;

public class BOJ1937_욕심쟁이판다 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] A = new int[N][N];
        int[][] D = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //D[i][j] = Math.max(1, go(A, D, i, j)); // !!! max 불필요,
                max = Math.max(max, go(A, D, i, j));
            }
        }

        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(D[i]));
        }

        System.out.println(max);
    }

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static int go(int[][] A, int[][] D, int y, int x) {
        if (D[y][x] > 0) return D[y][x];

        D[y][x] = 1; // !!! 1일차부터 시작
        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k], ny = y + dy[k];

            if (ny >= 0 && nx >= 0 && ny < A.length && nx < A.length && A[y][x] < A[ny][nx]) {
                D[y][x] = Math.max(D[y][x], go(A, D, ny, nx) + 1); // !!! 여기서 D[][] 배열이 계속 채워짐, main 에서 다시 채울 필요 없음.
            }
        }

        return D[y][x];
    }
}
