import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 17..
 */
// Dijkstra, LCA, RMQ
public class P4_독재왕국 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P4/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int Q = sc.nextInt();

            EdgeWeightedGraphLCA g = new EdgeWeightedGraphLCA(N + 1);
            for (int i = 1; i < N; i++) g.addEdge(i + 1, sc.nextInt(), sc.nextInt());

            //g.printGraph();
            g.dijkstra(1);
            g.dfs(1, 1);

            ArrayList<Integer> leaves = getLeafNodes(g);
            int M = leaves.size();
            for (int i = 0; i < M; i++) {
                g.addEdge(1, leaves.get(i), sc.nextInt()); // 루트에 M개 leaf노드를 간선 연결
            }

            int[][] queries = new int[Q][3];
            for (int i = 0; i < Q; i++) {
                queries[i][0] = sc.nextInt(); // u
                queries[i][1] = sc.nextInt(); // v
            }

            long pSum = 0, aSum = 0;
            for (int i = 0; i < Q; i++) {
                int u = queries[i][0], v = queries[i][1];
                long dist = g.getDist(u, v);
                queries[i][2] = (int) dist;
                pSum += dist;
            }

            g.dijkstra(1);
            //g.dfs(1, 1); // 사이클이 생겨서 stack overflow

            for (int i = 0; i < Q; i++) {
                int u = queries[i][0], v = queries[i][1];
                long dist = queries[i][2]; // prev sum
                aSum += Math.min(dist, g.distance[u] + g.distance[v]); // 추가 간선 연결 후에는 lca는 루트라서 이전값과 루트를 통한 값중 작은 값
            }

            String result = pSum + " " + aSum;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static ArrayList<Integer> getLeafNodes(EdgeWeightedGraphLCA g) {
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 2; i < g.adjList.length; i++) { // 1번 루트를 제외하고 연결이 1개인 점이 leaf node
            if (g.adjList[i].size() == 1)
                al.add(i);
        }
        return al;
    }

    /*
        Dijkstra, LCA
        g.dijkstra(start), dfs(start, start) -> lca(u, v)
        g.getDist(u, v) - lca를 이용한 두정점간 거리
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
