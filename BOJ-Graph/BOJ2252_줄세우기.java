import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 12..
 */
public class BOJ2252_줄세우기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 사람수
        int M = sc.nextInt(); // 비교 횟수

        DirectedGraph g = new DirectedGraph(N + 1);
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            g.addEdge(u, v);
        }

        ArrayList<Integer> order = g.topologicalSort();
        for (Integer o : order) {
            System.out.print(o + " ");
        }
    }

    // unweighted directed graph
    static class DirectedGraph {
        int V;
        ArrayList<Integer>[] adjList;
        ArrayList<Integer> order;
        boolean[] visited;

        DirectedGraph(int V) {
            this.V = V;
            adjList = new ArrayList[V];

            for (int i = 1; i < V; i++) {
                adjList[i] = new ArrayList<>();
            }

            visited = new boolean[V];
            order = new ArrayList<>();
        }

        void addEdge(int u, int v) {
            adjList[v].add(u);
        }

        void dfs(int here) {
            visited[here] = true; // 방문 시작 시점

            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
                if (visited[there] == false) {
                    dfs(there);
                }
            }

            // 방문 종료 시점 order list
            order.add(here);
        }

        ArrayList<Integer> topologicalSort() {
            for (int i = 1; i < V; ++i) {
                if (visited[i] == false) dfs(i);
            }

            //Collections.reverse(order);
            return order;
        }
    }
}
