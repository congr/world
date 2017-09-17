import javafx.util.Pair;

import java.util.*;

/**
 * Created by cutececil on 2017. 4. 10..
 */
public class BOJ11407_책구매하기3 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        MCMF mcmf = new MCMF(N + M + 2, N + M, N + M + 1);
        
        for (int i = 0; i < N; i++) {       // 사람
            int c = sc.nextInt();
            mcmf.addEdgeToSink(M + i, c, 0);
        }
        
        for (int i = 0; i < M; i++) {       // 서점
            int c = sc.nextInt();
            mcmf.addEdgeFromSource(i, c, 0);
        }
        
        for (int i = 0; i < M; i++) {       // 서점
            for (int j = 0; j < N; j++) {   // 사람
                int c = sc.nextInt();
                mcmf.addEdge(i, j + M, c, 0); // 서점 -> 사람 capa
            }
        }
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int cost = sc.nextInt();
                mcmf.setCost(i, j + M, cost);       // 서점 -> 사람 cost : capa를 array에 버퍼링하는 방법이나 setCost를 구현하는 방법
            }
        }
        
        System.out.println(mcmf.flow().getKey());
        System.out.println(mcmf.flow().getValue());
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
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
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
        boolean spfa() {
            Arrays.fill(check, false);
            Arrays.fill(distance, 987654321);
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
            
            if (distance[sink] == 987654321) return false;
            
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

