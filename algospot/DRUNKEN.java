import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by cutececil on 2017. 2. 28..
 */
public class DRUNKEN {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        //int tc = Integer.parseInt(br.readLine());
        //while (tc-- > 0) {
            String[] VE = br.readLine().split(" ");
            int V = Integer.parseInt(VE[0]);
            int E = Integer.parseInt(VE[1]);

            Graph g = new Graph(V + 1); // vertex starts from 1
            String[] Vs = br.readLine().split(" ");
            int k = 0;
            for (String s : Vs) {
                g.delay[k++] = Integer.parseInt(s);
            }

            for (int i = 0; i < E; ++i) {
                String[] UVW = br.readLine().split(" ");
                int u = Integer.parseInt(UVW[0]);
                int v = Integer.parseInt(UVW[1]);
                int w = Integer.parseInt(UVW[2]);
                g.addEdge(u, v, w);
            }

            //tc
            int T = Integer.parseInt(br.readLine());
            for (int i = 0; i < T; i++) {
                String[] SE = br.readLine().split(" ");
                int s = Integer.parseInt(SE[0]);
                int e = Integer.parseInt(SE[1]);
                System.out.println(g.W[s][e]);
            }

        //}

        br.close();
        out.close();
    }


    static class Graph {
        final static int START = 1;
        int[][] adjMatrix;
        int[] delay;
        int[][] W;
        int V;
        int E;
        ArrayList<Pair<Integer, Integer>> order;

        Graph(int v) {
            this.V = v;
            adjMatrix = new int[V][V];
            W = new int[V][V];
            delay = new int[V];
            order = new ArrayList<>(V);
        }

        void solve() {
            for (int a : delay) {
                order.add(new Pair<>(delay[a], a));
            }

            Collections.sort(order, (a, b) -> a.getKey().compareTo(b.getKey())); // what sort?

            for (int i = START; i < V; i++) {
                for (int j = START; j < V; j++) {
                    if (i == j) W[i][j] = 0; // i->j
                    else W[i][j] = adjMatrix[i][j];
                }
            }

            int ret = 987654321;
            for (int k = START; k < V; k++) {
                // k번째로 예상 시간이 적게 걸리는 정점 w까지를 지나서 얻을 수 있는 최단 거리
                int w = order.get(k).getValue();
                for (int i = START; i < V; i++) {
                    for (int j = START; j < V; j++) {
                        adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][w] + adjMatrix[w][j]);
                        W[i][j] = Math.min(adjMatrix[i][w] + delay[w] + adjMatrix[w][j], W[i][j]);
                    }
                }
            }
        }

        void addEdge(int u, int v, int w) {
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }

        void printGraph() {
            for (int i = START; i < V; i++) {
                for (int j = START; j < V; j++) {
                    System.out.print(adjMatrix[i][j]);
                }
                System.out.println();
            }
        }
    }

}
