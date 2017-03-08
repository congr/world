import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 2. 20..
 */
public class ROUTING {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine().trim());
        while (tc-- > 0) {
            String[] VE = br.readLine().split(" ");
            int V = Integer.parseInt(VE[0]);
            int E = Integer.parseInt(VE[1]);

            EdgeWeightedGraph g = new EdgeWeightedGraph(V);
            for (int i = 0; i < E; ++i) { // 간선의 개수
                String[] UVW = br.readLine().split(" ");
                int u = Integer.parseInt(UVW[0]);
                int v = Integer.parseInt(UVW[1]);
                double w = Math.log(Double.parseDouble(UVW[2])); // log A + log B = log(A * B) = C -> pow (Math.E, C) 는 출력값
                g.addEdge(u, v, w);
            }

            //g.printGraph();                                               // 출력값에 pow 를 해서 입력시 log한 것을 원복시킨다 결과적으로 A*B의 증폭값
            System.out.printf("%.10f\n", Math.pow(Math.E, g.dijkstra(0)));  // distance[v-1] has total cost from 0 vertex(root)
        }

        br.close();
        out.close();
    }

    static class EdgeWeightedGraph {
        ArrayList<Edge>[] adjList;
        double[] distance;
        int V; // Vertex Cnt

        EdgeWeightedGraph(int V) {
            this.V = V;
            distance = new double[V];
            Arrays.fill(distance, Double.MAX_VALUE);            // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }

        // 참고 : priority - 가중치(0이상) 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        // dijkstra는 root에서 모든 정점을 거치면서 최단 거리(cost)를 저정하고 마지막 정점에는 최종 최단거리 값이 저장되므로 dist[v-1]을 리턴함
        double dijkstra(int start) {
            PriorityQueue<Edge> pq = new PriorityQueue<>(V, (o1, o2) -> {
                if (o1.w > o2.w) return 1;
                else return -1;
            });

            distance[start] = 0;                                // root
            pq.add(new Edge(start, 0));                         // new Edge - w를 1로 초기화해서 pow, log를 안쓰고 그냥 가중치를 증폭(multiply)시키는 것도 가능

            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();                         // retrieve and remove
                int here = hereE.v;
                double hereCost = hereE.w;

                if (distance[here] < hereCost) continue;        // here 점까지 온적이 있었는데, 그때 cost가 더 낮았다면 큐에서 꺼내기만하고 패스 다음진행

                for (Edge thereE : adjList[here]) {
                    int there = thereE.v;
                    double cost = thereE.w + hereCost;          // cost를 비교해서 방문을 할지 말지를 결정하는 단계
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
        public void addEdge(int u, int v, double w) {
            adjList[u].add(new Edge(v, w));
            adjList[v].add(new Edge(u, w));
        }

        class Edge {
            int v;
            double w;

            Edge(int v, double w) {
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
