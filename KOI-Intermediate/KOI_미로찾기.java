import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 12. 8..
 */
public class KOI_미로찾기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        int sy = 0, sx = 0;
        char[][] A = new char[N][M];
        for (int i = 0; i < N; i++) {
            String str = sc.next();
            A[i] = str.toCharArray();
            if (str.contains("S")) {
                sy = i;
                sx = str.indexOf('S');
            }
        }

        int dist = bfs(A, sy, sx, N, M);
        System.out.println(dist);
    }

    static int bfs(char[][] A, int sy, int sx, int N, int M) {
        Queue<Cell> q = new LinkedList<>();
        q.add(new Cell(sy, sx, 0));                         // 시작 지점

        while (!q.isEmpty()) {
            Cell here = q.remove();

            for (int i = 0; i < 4; i++) {
                int nx = here.x + dx[i], ny = here.y + dy[i];
                if (insideGrid(ny, nx, N, M)) {
                    if (A[ny][nx] == '.') {                     // 뚫린 곳
                        A[ny][nx] = '*';                        // 다음에 카운트 하지 않기 위해 '.' 이 아닌 값으로 채움
                        q.add(new Cell(ny, nx, here.d + 1)); // here에서 4가지 방향 중 . 이라면 q에 추가
                    } else if (A[ny][nx] == 'G')                // 종료 지점
                        return here.d + 1;
                }
            }
        }

        return -1;
    }

    static class Cell {
        int y, x, d;

        Cell(int y, int x, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }
    }

    static int dx[] = new int[]{-1, 0, 1, 0};
    static int dy[] = new int[]{0, -1, 0, 1};

    static boolean insideGrid(int y, int x, int Y, int X) {
        if ((x >= 0 && y >= 0) && (x < X && y < Y)) return true;
        else return false;
    }
}
