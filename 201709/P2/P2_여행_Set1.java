import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 22..
 */
public class P2_여행_Set1 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P2/Set1.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt(); // 도시 개수
            int R = sc.nextInt(); // 여행 일수
            int M = sc.nextInt(); // 간선 개수
            
            EdgeWeightedGraph g = new EdgeWeightedGraph(N + 1, R + 1);
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt(), v = sc.nextInt();
                int k = sc.nextInt(); // 교통 수단 개수
                int[] costs = new int[k];
                for (int j = 0; j < k; j++) {
                    costs[j] = sc.nextInt();
                }
                g.addEdge(u, v, costs);
            }
            
            int result = g.bfs(1, R - 1);
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    // Digraph
    static class EdgeWeightedGraph { // int or double?
        ArrayList<Edge>[] adjList;
        int[][] visited;
        int V; // Vertex Cnt
        
        EdgeWeightedGraph(int V, int R) {
            this.V = V;
            visited = new int[V][R];
            for (int i = 0; i < V; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
            
            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }
        
        // 참고 : priority - 가중치(0이상) 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        // dijkstra는 root에서 모든 정점을 거치면서 최단 거리(cost)를 저정하고 마지막 정점에는 최종 최단거리 값이 저장되므로 dist[v-1]을 리턴함
        int bfs(int start, int R) {
            int minCost = Integer.MAX_VALUE;
            Queue<Edge> pq = new LinkedList<>();
            pq.add(new Edge(start, 0, -1));
            
            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();                         // retrieve and remove
                int here = hereE.v;
                int hereCost = hereE.c;
                
                System.out.println("here " + here + " day " + hereE.d + " hereCost " + hereCost);
                if (hereE.d == R && here == start) {
                    minCost = Math.min(minCost, hereCost);
                    //System.out.println("Rday hereCost " + hereCost);
                }
                
                if (hereE.d > R) break; // 여행일이 지나면 더 갈 필요없다
                
                for (Edge thereE : adjList[here]) {
                    int there = thereE.v;
                    int thereCost = thereE.getCost(hereE.d + 1); // 매일 cost가 다르므로 어떤 cost를 쓸지 찾아온다
                    int newCost = thereCost + hereCost;
                    if (thereCost > 0 && (visited[there][hereE.d + 1] > newCost)) {
                        visited[there][hereE.d + 1] = newCost;
                        pq.add(new Edge(there, newCost, hereE.d + 1));          // ** new Edge(v, w) : 기존 입력된 w자체를 바꾸면 안된다. new해서 큐에 넣자!
                    }
                }
            }
            
            if (minCost == Integer.MAX_VALUE) minCost = -1;
            return minCost;
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
        public void addEdge(int u, int v, int[] w) {
            adjList[u].add(new Edge(v, w));
        }
        
        class Edge {
            int v, d;
            int[] w;
            int c;
            
            Edge(int v, int[] w) {
                this.v = v; // to vertex
                this.w = w; // weight []
            }
            
            Edge(int v, int c, int d) {
                this.v = v; // to vertex
                this.c = c; // cost sum
                this.d = d;
            }
            
            int getCost(int r) {
                if (w == null) return 0;
                else
                    return w[r % w.length];
            }
            
            @Override
            public String toString() {
                return v + "(" + w + ")";
            }
        }
    }
}
