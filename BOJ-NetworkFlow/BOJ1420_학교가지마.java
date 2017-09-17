import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 5..
 */
// min-cut : vertex in-out -> min vertex cut
public class BOJ1420_학교가지마 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt(); // 세로
        int M = in.nextInt(); // 가로
        
        FordFulkerson ff = new FordFulkerson(N * M * 2); // v 개수 - in-out 분할하므로 2배수 필요

        char[][] map = new char[N][M];
        for (int i = 0; i < N; i++) {
            String row = in.next();
            map[i] = row.toCharArray();
        }
        
        class Sol {
            int cell(int y, int x) {
                return x * 2 + y * 2 * M; // M - x개수, y = 0일 경우 0, 2, 4, 6, 8, y = 1일 경우 10, 12 ...
            }
            
            boolean insideGrid(int y, int x, int Y, int X) {
                if ((x >= 0 && y >= 0) && (x < X && y < Y)) return true;
                else return false;
            }
            
            int source = -1, sink = -1;
            
            // y, x 현재 지점에서 상하좌우 나가는 간선 연결
            // 현 지점에서 나가는 간선이 필요한 경우 K와 .이다. H는 sink이라서 나가는 간선은 없다 (K는 source, .은 빈칸)
            void setOutCapa(int y, int x) {
                char here = map[y][x];
                if (here == 'K') source = cell(y, x);          // source
                else if (here == 'H') {                             // sink - 나가는 간선 없음 return
                    sink = cell(y, x);
                    return;
                } else if (here == '.') {                           // min-vertex-cut을 위해 in-out 간선간 연결 - capa는 1
                    ff.setCapa(cell(y, x), cell(y, x) + 1, 1);// u -> u' 간선을 미리 연결
                }
                
                // 상하좌우 빈칸이나 싱크를 찾아서 capa setWithoutDup
                int dx[] = new int[]{-1, 0, 1, 0};
                int dy[] = new int[]{0, -1, 0, 1};
                
                for (int i = 0; i < 4; i++) {
                    int px = x + dx[i];                             // point to that position
                    int py = y + dy[i];
                    
                    // if same position or out of grid, skip it
                    if ((px == x && py == y) || insideGrid(py, px, N, M) == false) continue;
                    
                    char there = map[py][px];                       // out v : num[i][j] + 1, in v : num[i][j]
                    if (there == 'K' || there == '#') continue;     // 소스로 들어오는 간선이 없어야 함
                    
                    if (here == 'K')                                // source일 경우 out v가 따로 없어서 + 1 하면 안됨
                        ff.setCapa(cell(y, x), cell(py, px), 987654321);        // capa를 무한대로 세팅함, 그래야 in-out 간선이 끊어짐
                    else
                        ff.setCapa(cell(y, x) + 1, cell(py, px), 987654321); // here -> there 로 간선 연결
                }
            }
        }
        
        Sol sol = new Sol();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '#') continue;
                sol.setOutCapa(i, j); // 현 지점에서 나가는 간선의 capa를 세팅
            }
        }
        
        int result = ff.networkFlow(sol.source, sol.sink);
        if (result >= 987654321) result = -1;
        System.out.println(result);
    }
    
    
    /* FordFulkerson ff = new FordFulkerson(V); Vcnt = V + source + sink
        * ff.setCapa(u, v, c);
        * maxFlow = ff.networkFlow(source, sink);
        * capa를 미리 설정해주면 maxFlow를 계산함 */
    static class FordFulkerson {
        int V;          // Vertex Cnt
        int[][] capa;   // capa[u][v] : u -> v 로 보낼 수 있는 용량
        int[][] flow;   // flow[u][v] : u -> v 로 흘러가는 유량 (반대는 음수)
        
        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V) {
            this.V = V;
            
            capa = new int[V][V];
            flow = new int[V][V];   // init to 0
        }
        
        void setCapa(int u, int v, int c) {
            capa[u][v] = c; // u -> v 로 가는 간선이 여러개 일 수 있음
        }
        
        // 미리 정해진 capa에 따라 flow[u][v]를 계산하고 총 유량 totalFlow을 반환한다
        int networkFlow(int source, int sink) {
            // *** flow 초기화
            for (int i = 0; i < flow.length; i++) Arrays.fill(flow[i], 0);
            
            int totalFlow = 0;
            
            while (true) { // BFS
                int[] parent = new int[V];
                Arrays.fill(parent, -1);                            // should init to -1
                
                Queue<Integer> queue = new LinkedList<>();
                parent[source] = source;                            // root
                queue.add(source);
                
                while (!queue.isEmpty() && parent[sink] == -1) {    // 아직 sink까지 연결안되었다면
                    int here = queue.poll();                        // retrieve and remove
                    
                    for (int there = 0; there < V; there++) {       // 잔여 용량이 있는 간선을 따라 탐색한다
                        if (capa[here][there] - flow[here][there] > 0 && parent[there] == -1) { // capa(u,v) - flow(u,v) 는 잔여용량
                            queue.add(there);
                            parent[there] = here;
                        }
                    }
                }
                
                if (parent[sink] == -1) break;                      // 증가 경로가 없으면 종료
                
                // 증가 경로를 통해 유량을 얼마나 보낼지 결정
                int amount = 987654321;
                for (int p = sink; p != source; p = parent[p]) {
                    amount = Math.min(capa[parent[p]][p] - flow[parent[p]][p], amount);
                }
                
                // 증가 경로를 통해 유량을 보냄
                for (int p = sink; p != source; p = parent[p]) {
                    flow[parent[p]][p] += amount;
                    flow[p][parent[p]] -= amount;
                }
                
                totalFlow += amount;                                // totalFlow에 보낸 유량을 계속 더함
            }
            
            return totalFlow;
        }
    }
    
}
