import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 15..
 */
public class P1_가계도 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P1/sample.in"; // path from root
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
            TreeGraph g = new TreeGraph(N + 1);
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
            
            int result = g.dfs(root);
            System.out.println(result);
            wr.write(result + "\n");
        }
        sc.close();
        wr.close();
    }
    
    // unweighted directed graph
    static class TreeGraph {
        int V;
        ArrayList<Integer>[] adjList;
        ArrayList<Integer> order;
        boolean[] visited;
        
        TreeGraph(int V) {
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
        
        int dfs(int here) {
            visited[here] = true; // 방문 시작 시점
            
            int h = 1; // 루트가 1 높이
            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
                if (visited[there] == false) {
                    h = Math.max(h, dfs(there) + 1);
                }
            }
            
            return h;
            // 방문 종료 시점 order list
            //order.add(here);
        }
    }
}

