import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cutececil on 2017. 2. 28..
 */
public class DRUNKEN {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        String[] VE = br.readLine().split(" ");
        int V = Integer.parseInt(VE[0]);
        int E = Integer.parseInt(VE[1]);

        EdgeWeightedGraphM g = new EdgeWeightedGraphM(V + 1); // vertex starts at 1
        String[] Vs = br.readLine().split(" ");
        int k = g.START; // vertex starts off 1
        for (String s : Vs) g.delay[k++] = Integer.parseInt(s);


        for (int i = 0; i < E; ++i) {
            String[] UVW = br.readLine().split(" ");
            int u = Integer.parseInt(UVW[0]);
            int v = Integer.parseInt(UVW[1]);
            int w = Integer.parseInt(UVW[2]);
            g.addEdge(u, v, w);
        }

        // 어떤 경유점을 지나느냐에 따라 최단거리 값이 영향이 있다면 floyd를 이용
        // 아무 정점도 경유하지 않은 최단거리에 경우할 수 있는 정점을 하나씩 추가해가며 최단거리 갱신
        g.floydShortestPath();

        //tc
        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            String[] SE = br.readLine().split(" ");
            int s = Integer.parseInt(SE[0]);
            int e = Integer.parseInt(SE[1]);
            out.println(g.W[s][e]);
        }

        br.close();
        out.close();
    }

    static class EdgeWeightedGraphM {
        final static int START = 1;
        int[][] adjMatrix;
        int[] delay;
        int[][] W;
        int V;
        int E;
        ArrayList<Pair<Integer, Integer>> order;

        EdgeWeightedGraphM(int v) {
            this.V = v;
            order = new ArrayList<>(V);
            delay = new int[V];
            W = new int[V][V]; // 최소 예상 시간으로 구하고자 하는 답

            adjMatrix = new int[V][V];
            for (int[] a : adjMatrix) Arrays.fill(a, 987654321); // 초기값을 무한대로 세팅
        }

        // EdgeWeightedGraph with Matrix
        void floydShortestPath() {
            // 각 정점마다 delay되는 소요시간을 기준으로 가장 작은 시간부터 정렬
            order.add(new Pair<>(0, 0));
            for (int i = START; i < delay.length; i++) order.add(new Pair<>(delay[i], i));
            order.sort((a, b) -> a.getKey().compareTo(b.getKey())); // natural order by key, 1,2,3

            // 우선 정점을 거치지 않은 최단 거리를 구한다
            for (int i = START; i < V; i++) {
                for (int j = START; j < V; j++) {
                    if (i == j) W[i][j] = 0; // i->j
                    else W[i][j] = adjMatrix[i][j];
                }
            }

            // 각 정점마다 소요시간을 고려한 최단거리를 계산한다
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

        // undirected graph - with weight
        void addEdge(int u, int v, int w) {
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }

        void printGraph() {
            for (int i = START; i < V; i++) {
                for (int j = START; j < V; j++) System.out.print(adjMatrix[i][j]);
                System.out.println();
            }
        }
    }

}
