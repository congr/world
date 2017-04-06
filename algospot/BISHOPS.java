import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by cutececil on 2017. 4. 6..
 */
// Bipartite
public class BISHOPS {
    public static void main(String[] args) throws Exception { // class main
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());

            char[][] map = new char[N][N];
            for (int i = 0; i < N; ++i) {
                map[i] = br.readLine().toCharArray();
            }

            class Sol {
                int dx[] = new int[]{-1, 1}; // 대각선만 이동 가능
                int dy[] = new int[]{1, 1};
                int id[][][] = new int[2][8][8];

                boolean insideGrid(int y, int x) {
                    if ((x >= 0 && y >= 0) && (x < N && y < N)) return true;
                    else return false;
                }

                int placeBishops() {
                    for (int i = 0; i < 8; i++) {
                        Arrays.fill(id[0][i], -1);
                    }
                    int[] count = new int[2];

                    for (int dir = 0; dir < 2; dir++) {
                        for (int y = 0; y < N; y++) {
                            for (int x = 0; x < N; x++) {
                                if (map[y][x] == '.' && id[dir][y][x] == -1) {
                                    int cy = y, cx = x;
                                    while (insideGrid(cy, cx) && map[cy][cx] == '.') {
                                        id[dir][cy][cx] = count[dir];
                                        cy += dy[dir];
                                        cx += dx[dir];
                                    }
                                    count[dir]++;
                                }
                            }
                        }
                    }

                    // 이분 그래프를 만든다
                    Bipartite bi = new Bipartite(count[0], count[1]);
                    //Arrays.fill(adjMatrix, 0);
                    for (int y = 0; y < N; y++) {
                        for (int x = 0; x < N; x++) {
                            if (map[y][x] == '.') {
                                int i = id[0][y][x];
                                int j = id[1][y][x];
                                bi.adjMatrix[i][j] = true;
                            }
                        }
                    }

                    return bi.bipartiteMatch();
                }
            }

            Sol sol = new Sol();
            System.out.println(sol.placeBishops());
        }

        br.close();
        out.close();
    }

    static class Bipartite {
        int[] aMatch, bMatch;
        int N, M;
        boolean[][] adjMatrix;
        boolean[] visited;

        Bipartite(int n, int m) {
            this.N = n;
            this.M = m;
            adjMatrix = new boolean[N][M];
            aMatch = new int[N];
            bMatch = new int[M];

            Arrays.fill(aMatch, -1); // -1 초기화 : 연결 안 된 상태
            Arrays.fill(bMatch, -1);
        }

        int bipartiteMatch() {
            int size = 0;
            for (int i = 0; i < N; i++) {
                visited = new boolean[N];
                if (dfs(i)) // i에서 시작하는 증가경로를 찾는다
                    ++size;
            }
            return size;
        }

        boolean dfs(int a) {
            if (visited[a]) return false; // 이미 매칭되었으면 리턴

            visited[a] = true; // 방문 시작 시점

            for (int b = 0; b < M; b++) {
                if (adjMatrix[a][b]) {
                    if (bMatch[b] == -1 || dfs(bMatch[b])) { // 증가경로가 발견되면 a, b 매치시킴
                        aMatch[a] = b;
                        bMatch[b] = a;
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
