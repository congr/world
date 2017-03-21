import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 3. 6..
 */
public class MATCHFIX {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] NM = br.readLine().split(" ");
            N = Integer.parseInt(NM[0]);    // 선수의 수
            M = Integer.parseInt(NM[1]);    // 남은 경기의 수
            
            // 현재까지 승리
            int[] victories = new int[N];
            String[] V = br.readLine().split(" ");
            for (int i = 0; i < N; i++) victories[i] = Integer.parseInt(V[i]);
            // 현재까지 승수 중 가장 큰 승수 =: 누가 가장 많은 승수로 이기고 있나 => 남은 경기로 0번이 현재 일등을 이길 수 있나?
            int curMaxWins = Arrays.stream(victories).max().getAsInt();
            
            // 경기를 할 두 선수 쌍
            int canWin = 0;
            Pair<Integer, Integer>[] matches = new Pair[M];
            for (int i = 0; i < M; i++) {       // 경기를 할 두 선수 쌍
                String[] M01 = br.readLine().split(" ");
                int a = Integer.parseInt(M01[0]);
                int b = Integer.parseInt(M01[1]);
                matches[i] = new Pair<>(a, b);  // match vertexes
                
                if (a == 0 || b == 0)           // 0 player 0선수가 경기를 하는 한다면 최대 이길 수 있는 경기수는 canWin 인데 최소로 하고자 한다
                    canWin++;                   // 남은 경기에서 0번이 이길 수 있는 최대 승수
            }
            
            if (curMaxWins > canWin) {          // 남은 경기를 0번이 다 이겨도 1등할 수 없다
                System.out.println(-1);
                continue;
            }
            
            FordFulkerson ff = new FordFulkerson(N + M + 1 + 1); // N : players, M: remained matches, 1 : source, 1 : sink
            source = N + M;
            sink = N + M + 1;
            
            for (int i = 0; i < M; i++) {
                ff.addEdge(source, i, 1);   // match vertexes (0,1선수 경기)
                ff.addEdge(i, M + matches[i].getKey(), 1);
                ff.addEdge(i, M + matches[i].getValue(), 1);
            }
            
            boolean found = false;
            int curWins = victories[0];         // 현재 0번 선수의 승수
            for (int totalWin = curWins; totalWin < curWins + canWin; totalWin++) {
                if(ff.canWinWith(totalWin, victories, matches)) {
                    System.out.println(totalWin);
                    found = true;
                    break;
                }
            }
            
            if (!found)
                System.out.println(-1);
            
        }
        
        br.close();
        out.close();
    }
    
    static int M, N;
    static int source;
    static int sink;
    
    // network flow
    static class FordFulkerson { // int or double?
        ArrayList<Edge>[] adjList;
        int V;          // Vertex Cnt
        int[][] capa;   // capa[u][v] : u -> v 로 보낼 수 있는 용량
        int[][] flow;   // flow[u][v] : u -> v 로 흘러가는 유량 (반대는 음수)
        
        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V) {
            this.V = V;
            
            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
            
            capa = new int[V][V];
            flow = new int[V][V];   // init to 0
        }
        
        // 0번 선수가 총 totalWins로 우승할 수 있는지를 반환
        boolean canWinWith(int totalWins, int[] victories, Pair<Integer, Integer>[] matches) {
            Arrays.fill(capa, 0);
            
            for (int i = 0; i < M; i++) {
                capa[source][i] = 1; // source -> match vertexes
                capa[i][matches[i].getKey()] = 1; // 각 경기에서 -> 선수1
                capa[i][matches[i].getValue()] = 1; // 각 경기에서 -> 선수2
            }
            
            // 각 선수에서 싱크로 가능한 최대 승수를 용량으로 하는 간선을 추가
            for (int i = 0; i < N; i++) {
                int maxWin = (i == source ? totalWins : totalWins - 1);
                capa[i + M][sink] = maxWin - victories[i];
            }
            
            // 이때 모든 경기에서
            return networkFlow(source, sink) == M;
        }
        
        // flow[u][v]를 계산하고 총 유량을 반환한다
        int networkFlow(int source, int sink) {
            int totalFlow = 0;
            
            while (true) { // BFS
                int[] parent = new int[adjList.length];
                Arrays.fill(parent, -1); // should init to -1
                
                Queue<Integer> queue = new LinkedList<>();
                // root
                parent[source] = source;
                queue.add(source);
                
                while (!queue.isEmpty() && parent[sink] == -1) {    // 아직 sink까지 연결안되었다면
                    int here = queue.poll();                        // retrieve and remove
                    
                    for (int there = 0; there < V; there++) {
                        if (capa[here][there] - flow[here][there] > 0 && parent[there] == -1) {
                            queue.add(there);
                            parent[there] = here;
                        }
                    }
                }
                
                // 증가 경로가 없으면 종료
                if (parent[sink] == -1) break;
                
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
                
                // totalFlow에 보낸 유량을 계속 더함
                totalFlow += amount;
            }
            
            return totalFlow;
        }
        
        // 포드-풀커슨 알고리즘은 '유령' 간선을 추가해야한다.
        public void addEdge(int u, int v, int c) {
            Edge uv = new Edge();          // u -> v 로 가는 간선
            Edge vu = new Edge();          // v -> u 로 가는 유령 간선
            
            uv.init(v, c, 0, vu);       // u -> v 간선 초기화
            vu.init(u, 0, 0, uv);    // v -> u 유령 간선으로 이 간선의 용량은 0이다
            
            adjList[u].add(uv);
            adjList[v].add(vu);
        }
        
        // Network Flow를 위한 간선 정보
        class Edge {
            int target, capacity, flow;
            Edge reverse; // 역방향 간선 포인터
            
            void init(int t, int c, int f, Edge r) {
                target = t;
                capacity = c;
                flow = f;
                reverse = r;
            }
            
            // 이 간선의 잔여 용량을 계산한다
            int residualCapacity() {
                return capacity - flow;
            }
            
            // 이 간선을 따라 amt만큼의 용량을 보낸다
            void push(int amt) {
                flow += amt;
                reverse.flow -= amt; // 반대방향의 유량도 갱신
            }
            
            @Override
            public String toString() {
                return target + "(" + capacity + "/" + flow + ")";
            }
        }
        
        public void printGraph() {
            int id = 0;
            for (ArrayList list : adjList) {
                System.out.print(id + " -> ");
                for (int i = 0; i < list.size(); ++i) {
                    System.out.print(list.get(i) + " ");
                }
                System.out.println();
                ++id;
            }
        }
    }
}
