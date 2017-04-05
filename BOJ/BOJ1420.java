import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 5..
 */
// min-cut : vertex in-out -> min vertex cut
public class BOJ1420 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt(); // 세로
        int M = in.nextInt(); // 가로
        
        FordFulkerson ff = new FordFulkerson(N * M * 2 + 2); // v 넉넉하게...
        char[][] map = new char[N][M];
        int[][] cell = new int[N][M];
        int index = 0;
        for (int i = 0; i < N; i++) {
            String row = in.next();
            map[i] = row.toCharArray();
            for (int j = 0; j < M; j++) {
                cell[i][j] = index + 2;
                index += 2;
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] != 'K' && map[i][j] != 'H' && map[i][j] != '#') // source 와 sink가 아닌 경우, capa 1인
                    ff.setCapa(cell[i][j], cell[i][j] + 1, 1); // u -> u' 간선을 미리 연결
            }
        }
        
        class Sol {
            boolean insideGrid(int y, int x, int Y, int X) {
                if ((x >= 0 && y >= 0) && (x < X && y < Y)) return true;
                else return false;
            }
            
            void set(int y, int x) {
                char here = map[y][x];
                if (here == 'K' || here == '.') {   // 현 지점에서 나가는 간선이 필요한 경우 K: source
                    
                    int dx[] = new int[]{-1, 0, 1, 0};
                    int dy[] = new int[]{0, -1, 0, 1};
                    
                    for (int i = 0; i < 4; i++) { //y
                        
                        int px = x + dx[i]; // point to that position
                        int py = y + dy[i];
                        if ((px == x && py == y) || insideGrid(py, px, N, M) == false)
                            continue; // same position or out of grid
                        
                        char there = map[py][px]; // out v : num[i][j] + 1, in v : num[i][j]
                        if (there == 'H' || there == '.') { // H : sink, . : 빈칸
                            if (here == 'K') // source
                                ff.setCapa(cell[y][x], cell[py][px], 1); // out v : there 의  셀번호 + 1, in : here의 셀번호
                            else if (there == 'H') {
                                ff.setCapa(cell[y][x] + 1, cell[py][px], 1); // out v : there 의  셀번호 + 1, in : here의 셀번호
                            }
                            else {
                                ff.setCapa(cell[y][x] + 1, cell[py][px], 1); // out v : there 의  셀번호 + 1, in : here의 셀번호
                                //ff.setCapa(cell[py][px] + 1, cell[y][x], 1);
                            }
                        }
                    }
                }
            }
        }
        
        int source = -1, sink = -1;
        Sol sol = new Sol();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '#') continue;
                
                sol.set(i, j);
                if (map[i][j] == 'K') // source
                    source = cell[i][j];
                else if (map[i][j] == 'H') // sink
                    sink = cell[i][j];
            }
        }
        
        int result = ff.networkFlow(source, sink);
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
            capa[u][v] += c; // u -> v 로 가는 간선이 여러개 일 수 있음
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
