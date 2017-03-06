import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

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
            int N = Integer.parseInt(NM[0]); // 선수의 수
            int M = Integer.parseInt(NM[1]); // 남은 경기의 수

            // 현재까지 승리
            int[] victories = new int[N];
            String[] V = br.readLine().split(" ");
            for (int i = 0; i < N; i++) victories[i] = Integer.parseInt(V[i]);

            for (int i = 0; i < M; i++) { // 경기를 할 두 선수 쌍
                String[] Ms = br.readLine().split(" ");
                int a = Integer.parseInt(Ms[0]);
                int b = Integer.parseInt(Ms[1]);
            }
        }

        br.close();
        out.close();
    }

    // network flow
    static class FordFulkerson { // int or double?
        ArrayList<Edge>[] adjList;
        int[] distance; // weight type
        int V; // Vertex Cnt

        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V) {
            this.V = V;

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
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
