import java.util.*;

/**
 * Created by cutececil on 2017. 4. 10..
 */
// Maximum Flow
public class BOJ11046 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();               // 사람 수
        int M = sc.nextInt();               // 온라인 서점 수
        
        MaximumFlow mf = new MaximumFlow(N + M + 2, N + M, N + M + 1);
        
        for (int i = 0; i < N; i++) {       // 각 사람이 살려고 하는 책의 개수
            int c = sc.nextInt();
            mf.addEdge(N + M, i, c);    // source -> 사람 capa
        }
        
        for (int i = 0; i < M; i++) {       // 각 서점에서 팔 수 있는 책의 개수
            int c = sc.nextInt();           // + N 필요
            mf.addEdge(N + i, N + M + 1, c); // 서점 -> sink capa
        }
        
        for (int i = 0; i < M; i++) {       // 서점
            for (int j = 0; j < N; j++) {   // 사람
                int c = sc.nextInt();
                mf.addEdge(j, N + i, c); // 사람j - 서점i capa, 서점은 i + N(총사람)
            }
        }
        
        System.out.println(mf.flow());
    }
}

class MaximumFlow {
    class Edge {
        int to, capacity;
        Edge rev;
        
        Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
        }
    }
    
    int n, source, sink;
    ArrayList<Edge>[] graph;
    boolean[] check;
    
    MaximumFlow(int n, int source, int sink) {
        this.n = n;
        this.source = source;
        this.sink = sink;
        graph = new ArrayList[n];
        check = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Edge>();
        }
    }
    
    void addEdge(int u, int v, int capa) {
        Edge ori = new Edge(v, capa);
        Edge rev = new Edge(u, 0);
        ori.rev = rev;
        rev.rev = ori;
        graph[u].add(ori);
        graph[v].add(rev);
    }
    
    int bfs() {
        boolean[] check = new boolean[n];
        int[] from1 = new int[n];
        int[] from2 = new int[n];
        for (int i = 0; i < n; i++) {
            from1[i] = from2[i] = -1;
        }
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        check[source] = true;
        while (!q.isEmpty()) {
            int x = q.remove();
            for (int i = 0; i < graph[x].size(); i++) {
                Edge e = graph[x].get(i);
                if (e.capacity > 0 && !check[e.to]) {
                    q.add(e.to);
                    check[e.to] = true;
                    from1[e.to] = x;
                    from2[e.to] = i;
                }
            }
        }
        if (!check[sink]) {
            return 0;
        }
        int x = sink;
        int c = graph[from1[x]].get(from2[x]).capacity;
        while (from1[x] != -1) {
            if (c > graph[from1[x]].get(from2[x]).capacity) {
                c = graph[from1[x]].get(from2[x]).capacity;
            }
            x = from1[x];
        }
        x = sink;
        while (from1[x] != -1) {
            Edge e = graph[from1[x]].get(from2[x]);
            e.capacity -= c;
            e.rev.capacity += c;
            x = from1[x];
        }
        return c;
    }
    
    int flow() {
        int ans = 0;
        while (true) {
            Arrays.fill(check, false);
            int f = bfs();
            if (f == 0) break;
            ans += f;
        }
        return ans;
    }
}

