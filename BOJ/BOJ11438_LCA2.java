import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 17..
 */
public class BOJ11438_LCA2 {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt(); // 정점의 개수
        EdgeWeightedGraph g = new EdgeWeightedGraph(N + 1);
        
        for (int i = 0; i < N - 1; i++) {
            g.addEdge(sc.nextInt(), sc.nextInt(), 0);
        }
        
        g.dfs(1, 1);
        
        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int lca = g.lca(sc.nextInt(), sc.nextInt());
            System.out.println(lca);
        }
    }
    
    static class EdgeWeightedGraph { // int or double?
        ArrayList<Edge>[] adjList;
        int[] distance; // weight type
        int V; // Vertex Cnt
        int L, timer;
        int[] tin, tout;
        int[][] p;
        
        EdgeWeightedGraph(int V) {
            this.V = V;
            distance = new int[V];
            Arrays.fill(distance, Integer.MAX_VALUE);            // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다
            
            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
            
            p = new int[V * 2][18];
            tin = new int[V * 2];
            tout = new int[V * 2];
            L = (int) log2(V) + 1;
        }
        
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
    
    public static double logb(double a, double b) {
        return Math.log(a) / Math.log(b);
    }
    
    public static double log2(double a) {
        return logb(a, 2);
    }
}
