import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 14..
 */
// 두번째 도전, 메모이제이션 안하면 시간초과남, 솔루션도 시간초과
// 상하좌우 방향 이동가능하고 이동할 칸의 숫자가 더 작아야 한다 = 내리막길로만 갈 수 있다
// 상하좌우 방향으로 가보는데 이미 가본적 있다면 D[][]를 리턴, 이미 가본적 있는 지는 V[][]에 저장
// Top-down이 이해도 쉽고 수행속도도 더 빠르다 Bottom-up하려면 소팅부터 해야하고 시간초과날 수 있다
public class BOJ1520_내리막길2 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();       // 세로 크기
        int M = sc.nextInt();       // 가로 크기
        int[][] A = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = sc.nextInt();
            }
        }
        
        int[][] D = new int[N][M];
        boolean[][] V = new boolean[N][M]; // isVisited
        int cnt = go(0, 0, A, D, V, N, M);
    
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(D[i]));
        }
        System.out.println(cnt);
    }
    
    static int dx[] = new int[]{-1, 0, 1, 0};
    static int dy[] = new int[]{0, -1, 0, 1};
    
    static int go(int row, int col, int[][] A, int[][] D, boolean[][] V, int N, int M) {
        if (row == N - 1 && col == M - 1) return 1;             // 오른쪽아래칸에 도달하면 1가지 찾음
        
        if (V[row][col]) return D[row][col];                    // 이미 방문했었다면 메모이제이션된 값 리턴
        
        V[row][col] = true;                                     // 이 칸에 방문한 적이 있음
        
        for (int i = 0; i < 4; i++) {                           // 좌상우하 방향으로 다 가본다
            int y = row + dy[i], x = col + dx[i];
            
            if (insideGrid(y, x, N, M) && A[row][col] > A[y][x]) // 좌상우하 칸이 그리드 안이고 내리막이면 가본다
                D[row][col] += go(y, x, A, D, V, N, M);          // 좌상우하를 가서 찾은 방법의 수를 다 더한다
        }
        
        return D[row][col];                                      // row, col에서 가본 경로 수를 리턴
    }
    
    static boolean insideGrid(int y, int x, int my, int mx) {
        if ((x >= 0 && y >= 0) && (x < mx && y < my)) return true;
        else return false;
    }
}
