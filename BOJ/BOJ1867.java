import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 7..
 */
// 이분그래프의 최대 매칭 개수 = minimum vertex cover
public class BOJ1867 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt(); // 격자 칸 개수
        int K = in.nextInt(); // 돌멩이 개수
        Bipartite bi = new Bipartite(N + 1, N + 1);
        for (int i = 0; i < K; i++) {
            int y = in.nextInt();
            int x = in.nextInt();
            bi.adjMatrix[y][x] = true;
        }
        
        System.out.println(bi.bipartiteMatch());
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
