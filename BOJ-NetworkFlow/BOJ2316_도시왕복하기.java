import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 4..
 */
// 도시 왕복하기
public class BOJ2316_도시왕복하기 {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt(); // 도시의 수
        int P = in.nextInt(); // 연결된 도로 수
        
        FordFulkerson ff = new FordFulkerson(N * 2); // N 원래 도시수 + N-2 분리하는 도시수 (source:1번, sink:2번 도시)
        
        // 정점을 분리 u -> u'
        for (int i = 3; i <= N; i++) { // 1,2 제외
            ff.setCapa(i * 2 - 2, i * 2 - 1, 1);
        }
        
        // 도시 하나를 두개의 Vertex로 나눠서 들어가는 간선/나가는 간선을 분리 (한번만 방문이 허용되므로)
        for (int i = 0; i < P; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            
            //u -> v ==> u' -> v
            if (u >= 3) u = u * 2 - 1;
            if (v >= 3) v = v * 2 - 2;
            
            ff.setCapa(v, u, 1);
            ff.setCapa(u, v, 1); // u'->v
        }
        
        System.out.println(ff.networkFlow(1, 2));
    }
    
    static int solve() {
        int result = 0;
        
        return result;
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
