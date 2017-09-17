import java.util.*;

/**
 * Created by cutececil on 2017. 4. 27..
 */
// crucial link 찾는 문제
// MAX_VALUE flow를 돌린 후에 더이상 u->v로 가는 capa가 남은 간선이 없다면 crucial link이다
// flow 돌린 후 dfs로 남은 경로가 있는지 다 확인해서 더이상 capa가 남은 간선이 없는 카운트를 출력한다
// 이를 위해 edge를 별도로 array나 list에 보관해야한다 edge도 from/to를 받도록 수정
public class BOJ5651_CrucialLink {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            
            MaximumFlowListCrucialLink mf = new MaximumFlowListCrucialLink(N + 1, 1, N);
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int c = sc.nextInt();
                mf.addEdge(u, v, c);
            }
            
            int max = mf.crucialLink();
            System.out.println(max);
        }
    }
    
    static class MaximumFlowListCrucialLink {
        class Edge {
            int from;
            int to, capacity;
            Edge rev;
            
            Edge(int from, int to, int capacity) {
                this.from = from;
                this.to = to;
                this.capacity = capacity;
            }
        }
        
        int n, source, sink;
        ArrayList<Edge>[] graph;
        boolean[] check;
        ArrayList<Edge> edges;
        
        MaximumFlowListCrucialLink(int n, int source, int sink) {
            this.n = n;
            this.source = source;
            this.sink = sink;
            graph = new ArrayList[n];
            check = new boolean[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<Edge>();
            }
            
            edges = new ArrayList();
        }
        
        void addEdge(int u, int v, int capa) {
            Edge ori = new Edge(u, v, capa);
            Edge rev = new Edge(v, u, 0);
            ori.rev = rev;
            rev.rev = ori;
            graph[u].add(ori);
            graph[v].add(rev);
            edges.add(ori); // orginal edge를 따로 모은다
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
        
        // augmenting path find
        boolean dfs(int x, int goal) {
            if (x == goal) return true;
            
            check[x] = true;
            for (int i = 0; i < graph[x].size(); i++) {
                int next = graph[x].get(i).to;
                if (!check[next] && graph[x].get(i).capacity > 0) {
                    if (dfs(next, goal)) return true;
                }
            }
            return false;
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
        
        // maximum flow 를 한번 돌리고
        // u->v로 가는 경로가 있는지 간선들을 돌아서, u->v로 남은 capa가 더 없다면 crucial link 이므로 경로가 없는 것을 카운트해서 리턴
        int crucialLink() {
            flow();
            int cnt = 0;
            
            for (Edge e : edges) {
                Arrays.fill(check, false);
                if (!dfs(e.from, e.to)) cnt++;
            }
            return cnt;
        }
    }
}
