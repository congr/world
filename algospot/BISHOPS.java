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
            
        }
        
        class Sol {
            int dx[] = new int[]{-1, 1, -1, 1}; // 대각선만 이동 가능
            int dy[] = new int[]{-1, -1, 1, 1};
            
            int placeBishops() {
                        
            }
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
