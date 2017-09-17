import javafx.util.Pair;

import java.util.*;

/**
 * Created by cutececil on 2017. 4. 9..
 */
// MCMF SPFA
public class BOJ11405_책구매하기 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt(); // 사람수
        int M = in.nextInt(); // 온라인 서점수
        MCMF mcmf = new MCMF(N + M + 2, N + M, N + M + 1);
        
        for (int i = 0; i < N; i++) { // 각 사람이 사려는 책의 개수
            int x = in.nextInt();
            mcmf.addEdgeToSink(M + i, x, 0);
        }
        for (int i = 0; i < M; i++) { // 각 서점이 가지고 있는 책의 개수
            int x = in.nextInt();
            mcmf.addEdgeFromSource(i, x, 0);
        }
        for (int i = 0; i < M; i++) { // 각 서점 i에서 -> j 사람에게 책을 보내는 배송비
            for (int j = 0; j < N; j++) {
                int x = in.nextInt();
                mcmf.addEdge(i, j + M, 100, x); // capa는 최대치로 (책의 개수 최대치는 100)
            }
        }
        
        System.out.println(mcmf.flow().getValue());
    }
}

class MCMF { // BOJ
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
