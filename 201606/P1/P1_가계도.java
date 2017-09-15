import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 15..
 */
public class P1_가계도 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P1/input003.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            int[] child = new int[N + 1];
            DirectedGraph g = new DirectedGraph(N + 1);
            for (int i = 0; i < N - 1; i++) {
                int p = sc.nextInt();
                int c = sc.nextInt();
                child[c] = 1;
                g.addEdge(p, c);
            }

            // find root
            int root = -1;
            for (int i = 1; i < child.length; i++) {
                if (child[i] == 0) {
                    root = i;
                    break;
                }
            }

            g.dfs(root, 1);
            int result = g.maxCnt;
            System.out.println(result);
            wr.write(result + "\n");
        }
        sc.close();
        wr.close();
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
            adjList[u].add(v);
        }

        int maxCnt = 0;

        int dfs(int here, int cnt) {
            //System.out.println("here" + here + " " + cnt);
            visited[here] = true; // 방문 시작 시점

            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
                if (visited[there] == false) {
                    maxCnt = Math.max(maxCnt, cnt + 1);
                    dfs(there, cnt + 1);
                }
            }

            return cnt;
            // 방문 종료 시점 order list
            //order.add(here);
        }

    }
}

