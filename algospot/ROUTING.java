import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by cutececil on 2017. 2. 20..
 */
public class ROUTING {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());

            for (int i = 0; i < N; ++i) {
                String[] AB = br.readLine().split(" ");
                int A = Integer.parseInt(AB[0]);
                int B = Integer.parseInt(AB[1]);
            }
        }

        br.close();
        out.close();
    }

    static class EdgeWeightedGraph {
        ArrayList<Edge>[] adjList;
        boolean[] visited;
        int V;

        static class Edge {
            int from, to;
            double weight;

            Edge(int from, int to, double weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }
        }

        EdgeWeightedGraph(int V) {
            this.V = V;
            visited = new boolean[V];
            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) {
                adjList[i] = new ArrayList<>(V);
            }
        }

        // priority - 가중치 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        void dijkstra(int start, int[] distance, int[] parent) {
            distance = new int[adjList.length];
            parent = new int[adjList.length];
            Arrays.fill(distance, -1);
            Arrays.fill(parent, -1);

            Queue<Integer> pq = new PriorityQueue<>();
            // root
            distance[start] = 0;
            parent[start] = start;
            pq.add(start);

            while (!pq.isEmpty()) {
                int here = pq.poll(); // retrieve and remove

                for (Edge edge : adjList[here]) {

                    // 처음 발견된 정점이면 큐에 넣는다
//                    if (distance[there] == -1) {
//                        distance[there] = distance[here] + 1; // 여기까지 온 거리에 +1
//                        parent[there] = here;
//                        pq.add(there);
//                    }
                }
            }
        }

        // undirected
        public void addEdge(int u, int v, int w) {
            adjList[u].add(new Edge(u,v,w));
            adjList[v].add(new Edge(v,u,w));
        }

    }

}
