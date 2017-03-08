import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cutececil on 2017. 2. 7..
 */
public class Gallery {
    static final int UNWATCHED = 0;
    static final int WATCHED = 1;
    static final int INSTALLED = 2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] GH = br.readLine().split(" "); // g: gallery cnt, h: connected edge cnt
            int G = Integer.parseInt(GH[0]);
            int H = Integer.parseInt(GH[1]);

            Graph g = new Graph(G);
            for (int i = 0; i < H; ++i) {
                String[] UV = br.readLine().split(" ");
                int U = Integer.parseInt(UV[0]);
                int V = Integer.parseInt(UV[1]);
                g.addEdge(U, V);
            }
            //g.printGraph();

            int result = g.installCamera();
            out.println(result);
        }

        br.close();
        out.close();
    }

    static class Graph {
        ArrayList<Integer>[] adjList;
        boolean[] visited;
        int V;
        int installed;

        Graph(int V) {
            this.V = V;
            visited = new boolean[V];
            adjList = (ArrayList<Integer>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) {
                adjList[i] = new ArrayList<>(V);
            }
        }

        int dfs(int here) {
            visited[here] = true; // 방문 시작 시점

            int[] children = {0, 0, 0};
            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
//            for (int i = 0; i < adjList[here].size(); ++i) {
                if (visited[there] == false) {
                    int index = dfs(there);
                    ++children[index];
                }
            }

            // 정점 방문을 종료하는 시점
            // UNWATCHED -> INSTALLED -> WATCHED
            if (children[UNWATCHED] > 0) {
                ++installed;
                return INSTALLED;
            }

            if (children[INSTALLED] > 0) {
                return WATCHED;
            }

            // if WATCHED
            return UNWATCHED;
        }

        int installCamera() { // dfsAll()
            installed = 0;
            for (int i = 0; i < V; ++i) {
                if (visited[i] == false && dfs(i) == UNWATCHED)
                    ++installed;
            }

            return installed;
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
            adjList[v].add(u);
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
    }
}
