import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 19..
 */
// 트리 간선에 가중치가 있다 지름은?
public class BOJ1967_트리의지름 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 노드 N개

        EdgeWeightedGraphLCA g = new EdgeWeightedGraphLCA(N + 1);
        for (int i = 0; i < N - 1; i++) {
            g.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        // 트리 지름 계산
        g.dijkstra(1); // 루트가 문제에서 1로 정의됨
        int x = g.getFarthestVertexIndex();

        g.dijkstra(x); // 루트에서 가장 먼점에서 시작해서, 이점에서 가장 먼 것이 트리 지름이 된다
        int y = g.getFarthestVertexIndex();
        System.out.println(g.distance[y]);
    }

    /*
        Dijkstra, LCA
        g.dijkstra(start), dfs(start, start) -> lca(u, v)
        getDist(u, v) - lca를 이용한 정점간 거리
    */
    static class EdgeWeightedGraphLCA { // int or double?
        ArrayList<Edge>[] adjList;
        long[] distance; // weight type
        int V; // Vertex Cnt
        int L, timer;
        int[] tin, tout;
        int[][] p;

        EdgeWeightedGraphLCA(int V) {
            this.V = V;
            distance = new long[V];

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();

            p = new int[V * 2][18];
            tin = new int[V * 2];
            tout = new int[V * 2];
            L = (int) log2(V) + 1;
        }

        // 참고 : priority - 가중치(0이상) 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        // dijkstra는 root에서 모든 정점을 거치면서 최단 거리(cost)를 저정하고 마지막 정점에는 최종 최단거리 값이 저장되므로 dist[v-1]을 리턴함
        long dijkstra(int start) {
            PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.w > o2.w) return 1;
                else return -1;
            });

            Arrays.fill(distance, Long.MAX_VALUE);              // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다
            distance[start] = 0;                                // root
            pq.add(new Edge(start, 0));

            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();                         // retrieve and remove
                int here = hereE.v;
                long hereCost = hereE.w;

                if (distance[here] < hereCost) continue;        // here 점까지 온적이 있었는데, 그때 cost가 더 낮았다면 큐에서 꺼내기만하고 패스 다음진행

                for (Edge thereE : adjList[here]) {
                    int there = thereE.v;
                    long cost = thereE.w + hereCost;          // cost를 비교해서 방문을 할지 말지를 결정하는 단계
                    if (distance[there] > cost) {               // bfs에서는 처음 발견하면 큐에 넣었지만,
                        distance[there] = cost;                 // dij는 [Here까지의 cost + There 까지 cost]가 기존 there보다 작으면 dist를 갱신하고 pq에 넣는다
                        pq.add(new Edge(there, cost));          // ** new Edge(v, w) : 기존 입력된 w자체를 바꾸면 안된다. new해서 큐에 넣자!
                    }
                }
            }

            return distance[V - 1];                             // distance[V-1]에 0부터 마지막점까지의 cost가 저장되어 있음.
        }

        int getFarthestVertexIndex() {
            int v = 0;
            long d = 0;
            for (int i = 1; i < V; i++) {
                if (d < distance[i]) {
                    d = distance[i];
                    v = i;
                }
            }
            return v;
        }

        // lca를 구하기 위해 dfs를 한번 수행하여 tin - tout을 기록한다
        void dfs(int v, int parent) {
            tin[v] = ++timer;
            p[v][0] = parent;
            for (int i = 1; i <= L; i++) p[v][i] = p[p[v][i - 1]][i - 1];

            for (Edge to : adjList[v]) {
                if (to.v != parent)
                    dfs(to.v, v);
            }
            tout[v] = ++timer;
        }

        boolean upper(int u, int v) {
            return (tin[u] <= tin[v] && tout[u] >= tout[v]);
        }

        // dfs 를 미리 호출해야 한다
        int lca(int u, int v) {
            if (upper(u, v)) return u;
            if (upper(v, u)) return v;
            for (int i = L; i >= 0; i--) {
                if (!upper(p[u][i], v)) {
                    u = p[u][i];
                }
            }
            return p[u][0];
        }

        // u - v 거리
        long getDist(int u, int v) {
            int lca = lca(u, v);
            return distance[u] + distance[v] - 2 * distance[lca];
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
        public void addEdge(int u, int v, long w) {
            adjList[u].add(new Edge(v, w));
            adjList[v].add(new Edge(u, w)); // if directed graph, remove one.
        }

        class Edge {
            int v;
            long w;

            Edge(int v, long w) {
                this.v = v; // to vertex
                this.w = w; // weight
            }

            @Override
            public String toString() {
                return v + "(" + w + ")";
            }
        }
    }

    public static double logb(double a, double b) {
        return Math.log(a) / Math.log(b);
    }

    public static double log2(double a) {
        return logb(a, 2);
    }
}
