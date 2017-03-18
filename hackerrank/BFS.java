import java.util.*;

/**
 * Created by cutececil on 2017. 3. 18..
 */
public class BFS {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int tc = in.nextInt();
        while (tc-- > 0) {
            int V = in.nextInt();
            int E = in.nextInt();
            Graph g = new Graph(V + 1); // starts from 1

            for (int i = 0; i < E; i++) g.addEdge(in.nextInt(), in.nextInt());

            int S = in.nextInt();
            int distance[] = g.bfs(S);
            for (int i = 1; i < V + 1; i++) {
                if (i != S) System.out.print((distance[i] == -1 ? -1 : distance[i] * 6) + " ");
            }
            System.out.println();
        }
    }

    static class Graph {
        ArrayList<Integer>[] adjList;
        boolean[] visited;
        int V;

        Graph(int V) {
            this.V = V;
            visited = new boolean[V];
            adjList = (ArrayList<Integer>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }

        int[] bfs(int start) {
            int[] distance = new int[adjList.length];
            int[] parent = new int[adjList.length];
            Arrays.fill(distance, -1);
            Arrays.fill(parent, -1);

            Queue<Integer> queue = new LinkedList<>();
            // root
            distance[start] = 0;
            parent[start] = start;
            queue.add(start);

            while (!queue.isEmpty()) {
                int here = queue.poll(); // retrieve and remove

                for (int there : adjList[here]) {

                    // 처음 발견된 정점이면 큐에 넣는다
                    if (distance[there] == -1) {
                        distance[there] = distance[here] + 1; // 여기까지 온 거리에 +1
                        parent[there] = here;
                        queue.add(there);
                    }
                }
            }
            return distance;
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
            adjList[v].add(u);
        }
    }
}
