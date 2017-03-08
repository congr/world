import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by cutececil on 2017. 2. 28..
 */
public class FIRETRUCKS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] info = br.readLine().split(" ");
            int V = Integer.parseInt(info[0]); // 모든 장소의 수
            int E = Integer.parseInt(info[1]); // 도로수
            int N = Integer.parseInt(info[2]); // 화재장소 수
            int M = Integer.parseInt(info[3]); // 소방서 수

            EdgeWeightedGraph g = new EdgeWeightedGraph(V + 1); // 루트지점이 추가될 예정이므로 + 1
            for (int i = 0; i < E; ++i) {
                String[] AB = br.readLine().split(" ");
                int A = Integer.parseInt(AB[0]);
                int B = Integer.parseInt(AB[1]);
                int W = Integer.parseInt(AB[2]);
                g.addEdge(A, B, W);
            }

            // 화재장소
            ArrayList<Integer> firePlaces = new ArrayList<>();
            String[] Ns = br.readLine().split(" ");
            for (String s : Ns) firePlaces.add(Integer.parseInt(s));

            // 소방서
            String[] Ms = br.readLine().split(" ");
            for (String s : Ms) g.addEdge(Integer.parseInt(s), 0, 0); // 가중치가 0인 간선을 임의로 생성한 0번 루트에서 소방서로 연결

            // 임의로 0 루트에서 소방서로 간선을 연결하면 모든 소방서에서 모든 화재장소까지 최단거리를 알수 있다.
            // 루트에서 소방서까지는 가는 시간(가중치)이 0이므로 시간 출력값에 영향이 없다
            g.dijkstra(0);

            int result = 0;
            for (int n : firePlaces) result += g.distance[n];
            out.println(result);
        }

        br.close();
        out.close();
    }

    static class EdgeWeightedGraph { // int or double?
        ArrayList<Edge>[] adjList;
        int[] distance; // weight type
        int V; // Vertex Cnt

        EdgeWeightedGraph(int V) {
            this.V = V;
            distance = new int[V];
            Arrays.fill(distance, Integer.MAX_VALUE);            // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }

        // 참고 : priority - 가중치(0이상) 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        // dijkstra는 root에서 모든 정점을 거치면서 최단 거리(cost)를 저정하고 마지막 정점에는 최종 최단거리 값이 저장되므로 dist[v-1]을 리턴함
        int dijkstra(int start) {
            PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.w > o2.w) return 1;
                else return -1;
            });

            distance[start] = 0;                                // root
            pq.add(new Edge(start, 0));

            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();                         // retrieve and remove
                int here = hereE.v;
                int hereCost = hereE.w;

                if (distance[here] < hereCost) continue;        // here 점까지 온적이 있었는데, 그때 cost가 더 낮았다면 큐에서 꺼내기만하고 패스 다음진행

                for (Edge thereE : adjList[here]) {
                    int there = thereE.v;
                    int cost = thereE.w + hereCost;             // cost를 비교해서 방문을 할지 말지를 결정하는 단계
                    if (distance[there] > cost) {               // bfs에서는 처음 발견하면 큐에 넣었지만,
                        distance[there] = cost;                 // dij는 [Here까지의 cost + There 까지 cost]가 기존 there보다 작으면 dist를 갱신하고 pq에 넣는다
                        pq.add(new Edge(there, cost));          // ** new Edge(v, w) : 기존 입력된 w자체를 바꾸면 안된다. new해서 큐에 넣자!
                    }
                }
            }

            return distance[V - 1];                             // distance[V-1]에 0부터 마지막점까지의 cost가 저장되어 있음.
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

        // undirected graph - add Edge
        public void addEdge(int u, int v, int w) {
            adjList[u].add(new Edge(v, w));
            adjList[v].add(new Edge(u, w)); // if directed graph, remove one.
        }

        class Edge {
            int v;
            int w;

            Edge(int v, int w) {
                this.v = v; // to vertex
                this.w = w; // weight
            }

            @Override
            public String toString() {
                return v + "(" + w + ")";
            }
        }
    }
}
