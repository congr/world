import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by cutececil on 2017. 1. 31..
 */
public class Dictionary {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] input = new String[N];
            for (int i = 0; i < N; i++) {
                input[i] = br.readLine();
            }

            Graph g = new Graph(26); // alphabet cnt

            makeGraph(g, input);
            ArrayList<Integer> order = topologicalSort(g);
            System.out.println(order.toString());
        }
    }

    static void makeGraph(Graph g, String[] input){
        for (int j =1; j<input.length;++j) {
            int i = j-1;
            int len = Math.min(input[i].length(), input[j].length());

            for (int k=0; k<len;++k) {
                if (input[i].charAt(k) != input[j].charAt(k)) {
                    int a = input[i].charAt(k) - 'a';
                    int b = input[j].charAt(k) - 'a';
                    g.adjMatrix[a][b] = true;
                    break;
                }
            }
        }
    }

    static ArrayList<Integer> topologicalSort(Graph g) {
        g.dfsAll();

        Collections.reverse(g.order);

        int n = g.V;
        for (int i =0; i<n; ++i) {
            for (int j =0; j<n; ++j) {
                if (g.adjMatrix[g.order.get(j)][g.order.get(i)])
                    return new ArrayList<>();
            }
        }

        return g.order;
    }

    static class Graph {
        boolean[][] adjMatrix;
        boolean[] visited;
        int V;
        int E;
        ArrayList<Integer> order;

        Graph(int V) {
            adjMatrix = new boolean[V][V];
            visited = new boolean[V];

            order = new ArrayList<>(V);
        }

        void addEdge(int v, int w) {
            if (!adjMatrix[v][w]) E++;
            adjMatrix[v][w] = true;
            adjMatrix[w][v] = true;
        }

        void dfs(int here) {
            visited[here] = true;

            for (int there = 0; there < V; there++) {
                if (adjMatrix[here][there] && !visited[there]) {
                    dfs(there);
                }
            }

            order.add(here);
        }

        void dfsAll() {
            for (int i = 0; i < V; i++) {
                if (visited[i] == false)
                    dfs(i);
            }

        }
    }
}
