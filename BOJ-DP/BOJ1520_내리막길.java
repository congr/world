import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 7. 12..
 */
public class BOJ1520_내리막길 {
    static int[][] A;
    static int[][] D;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int M = sc.nextInt(); // 세로 크기
        int N = sc.nextInt(); // 가로 크기

        A = new int[M][N];
        D = new int[M][N];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        for (int i = 0; i < M; i++) {
            Arrays.fill(D[i], -1); // !!! -1 로 초기화
        }

        int result = go(0, 0, M, N);
        System.out.println(result);

        for (int i = 0; i < M; i++) {
            System.out.println(Arrays.toString(D[i]));
        }
    }

    static int dx[] = new int[]{-1, 0, 1, 0};
    static int dy[] = new int[]{0, -1, 0, 1};

    static int go(int y, int x, int M, int N) {
        if (y == M - 1 && x == N - 1) return 1; // 마지막 도착지에 도달하면

        if (D[y][x] > 0) return D[y][x];
        if (D[y][x] == -1) D[y][x] = 0; // !!! 이번 칸이 과거에 한번도 오지 않은 칸이라면 0으로 세팅하고 더 진행해 본다

        for (int i = 0; i < 4; i++) {
            int cx = x + dx[i];
            int cy = y + dy[i];
            if (insideGrid(cy, cx, M, N) && A[y][x] > A[cy][cx]) {
                if (D[cy][cx] == -1) // !!! 다음 칸이 한번도 안거쳐온 곳이면 더 진행해 본다
                    D[y][x] += go(cy, cx, M, N);
                else                 // !!! 다음 칸이 이미 진행했었던 칸이라면 더하기만 한다
                    D[y][x] += D[cy][cx];
            }
        }

        return D[y][x];
    }

    static boolean insideGrid(int y, int x, int my, int mx) {
        if ((x >= 0 && y >= 0) && (x < mx && y < my)) return true;
        else return false;
    }
}
