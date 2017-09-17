import javafx.util.Pair;

import java.util.*;

/**
 * Created by cutececil on 2017. 4. 12..
 */
// MCMF - 열혈강호6 (최소 비용이 아니고 -> 최대 비용)
// https://www.acmicpc.net/problem/11408
public class BOJ11409_열혈강호6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        MCMF mcmf = new MCMF(N + M + 2, N + M, N + M + 1);
        for (int i = 0; i < N; i++) {
            // source - 사람 edge
            mcmf.addEdgeFromSource(i, 1, 0);

            // 사람 - 일 edge
            int A = sc.nextInt(); // 일의 개수
            for (int j = 0; j < A; j++) {
                int work = sc.nextInt(); // i 사람이 할 수 있는 일 번호
                int cost = sc.nextInt(); // i 사람이 일을 하고 받을 월급
                mcmf.addEdge(i, work + N - 1, 1, -cost); // 한사람은 한가지 일만 할 수 있다 - 최대비용을 찾는 문제
            }
        }

        // 일 - sink edge
        for (int i = 0; i < M; i++) {
            mcmf.addEdgeToSink(i + N, 1, 0);
        }

        Pair<Integer, Integer> result = mcmf.flow();
        System.out.println(result.getKey());
        System.out.println(result.getValue() * -1); // 음수 비용에 * -1 을 해서 최대비용으로 출력
    }

    static class MCMF { // BOJ
        class Edge {
            int to, capacity;
            int cost;
            Edge rev;

            Edge(int to, int capacity, int cost) {
                this.to = to;
                this.capacity = capacity;
                this.cost = cost;
            }
        }

        static final int INF = 987654321;
        int n, source, sink;
        ArrayList<Edge>[] graph;
        boolean[] check;
        int[] distance;
        Pair<Integer, Integer>[] from;

        MCMF(int n, int source, int sink) {
            this.n = n;
            this.source = source;
            this.sink = sink;

            check = new boolean[n];
            distance = new int[n];
            graph = new ArrayList[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
            from = new Pair[n];
        }

        void setCost(int u, int v, int cost) {
            for (Edge edge : graph[u]) {
                if (edge.to == v) {
                    edge.cost = cost;
                    edge.rev.cost = -cost;
                    break;
                }
            }
        }

        void addEdge(int u, int v, int capa, int cost) {
            Edge ori = new Edge(v, capa, cost);
            Edge rev = new Edge(u, 0, -cost); // -cost
            ori.rev = rev;
            rev.rev = ori;
            graph[u].add(ori);
            graph[v].add(rev);
        }

        void addEdgeFromSource(int v, int capa, int cost) {
            addEdge(source, v, capa, cost);
        }

        void addEdgeToSink(int u, int capa, int cost) {
            addEdge(u, sink, capa, cost);
        }

        // shortest path faster algorithm
        // shortest path faster algorithm
        boolean spfa() {
            Arrays.fill(check, false);
            Arrays.fill(distance, INF);
            Arrays.fill(from, new Pair<>(-1, -1));

            distance[source] = 0;
            Queue<Integer> q = new LinkedList<>();
            q.add(source);

            while (!q.isEmpty()) {
                int x = q.remove();
                check[x] = false;
                for (int i = 0; i < graph[x].size(); i++) {
                    Edge edge = graph[x].get(i);
                    int y = edge.to;
                    if (edge.capacity > 0 && distance[x] + edge.cost < distance[y]) {
                        distance[y] = distance[x] + edge.cost;
                        from[y] = new Pair(x, i);
                        if (!check[y]) {
                            check[y] = true;
                            q.add(y);
                        }
                    }
                }
            }

            if (distance[sink] == INF) return false;

            int x = sink;
            int c = graph[from[x].getKey()].get(from[x].getValue()).capacity;
            while (from[x].getKey() != -1) {
                if (c > graph[from[x].getKey()].get(from[x].getValue()).capacity) {
                    c = graph[from[x].getKey()].get(from[x].getValue()).capacity;
                }
                x = from[x].getKey();
            }

            x = sink;
            while (from[x].getKey() != -1) {
                Edge edge = graph[from[x].getKey()].get(from[x].getValue());
                edge.capacity -= c;
                edge.rev.capacity += c;
                x = from[x].getKey();
            }

            totalFlow += c;
            totalCost += c * distance[sink];
            return true;
        }

        boolean bellman() {
            Arrays.fill(check, false);
            Arrays.fill(distance, INF);
            Arrays.fill(from, new Pair<>(-1, -1));

            distance[source] = 0;

            for (int step = 0; step < n - 1; step++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < graph[i].size(); j++) {
                        Edge edge = graph[i].get(j);
                        if (edge.capacity == 0) continue;
                        int x = i;
                        int y = edge.to;
                        if (distance[x] == INF) continue;
                        if (distance[y] > distance[x] + edge.cost) {
                            distance[y] = distance[x] + edge.cost;
                            from[y] = new Pair<>(i, j);
                        }
                    }
                }
            }

            if (distance[sink] == INF) return false;

            int x = sink;
            int c = graph[from[x].getKey()].get(from[x].getValue()).capacity;
            while (from[x].getKey() != -1) {
                if (c > graph[from[x].getKey()].get(from[x].getValue()).capacity) {
                    c = graph[from[x].getKey()].get(from[x].getValue()).capacity;
                }
                x = from[x].getKey();
            }

            x = sink;
            while (from[x].getKey() != -1) {
                Edge edge = graph[from[x].getKey()].get(from[x].getValue());
                edge.capacity -= c;
                edge.rev.capacity += c;
                x = from[x].getKey();
            }

            totalFlow += c;
            totalCost += c * distance[sink];
            return true;
        }

        int totalFlow;
        int totalCost;

        Pair<Integer, Integer> flow() {
            while (spfa()) ;
            return new Pair<>(totalFlow, totalCost);
        }
    }
}
