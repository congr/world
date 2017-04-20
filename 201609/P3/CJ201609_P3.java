import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 20..
 */
// 볼록 사각형이 최소가 되도록 4개점을 뽑고, 그 넓이를 구하라.
public class CJ201609_P3 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P5/input005.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();

            EdgeWeightedGraphM graphM = new EdgeWeightedGraphM(N);
            Point2D[] pts = new Point2D[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                pts[i] = new Point2D(x, y);
            }
            Arrays.sort(pts); //x -> y 
            System.out.println(Arrays.toString(pts));

            for (int i = 1; i < N; i++) {
                Point2D pre = pts[i - 1];
                graphM.addEdge(i, i - 1, pre.distanceSquaredTo(pts[i]));
            }

            graphM.floydWarshall();

            // 0 혹은 INF 제외하고 ArrayList에 넣고 CCW 돌려서 최소값을 기록해라
            for (int i = 0; i < N; i++) { // pts[i]
                ArrayList<Pair<Double, Point2D>> pairs = new ArrayList<>();
                for (int j = 0; j < N; j++) {
                    pairs.add(new Pair(graphM.distTo[i][j], pts[j]));
                }

                pairs.sort((d1,d2) -> d1.getKey().compareTo(d2.getKey()));
                // ccw
            }

            int result = 0;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class EdgeWeightedGraphM {
        double[][] adjMatrix;
        double[][] distTo; // 거리 결과
        int V;

        EdgeWeightedGraphM(int v) {
            this.V = v;

            adjMatrix = new double[V][V];
            for (double[] a : adjMatrix) Arrays.fill(a, Double.POSITIVE_INFINITY); // 초기값을 무한대로 세팅
        }

        // 간선에만 weight가 있고 정점에는 없는 경우
        void floydWarshall() {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i == j) distTo[i][j] = 0;
                    else distTo[i][j] = adjMatrix[i][j];
                }
            }

            for (int i = 0; i < V; i++) { // intermediate v
                for (int u = 0; u < V; u++) { // start v
                    for (int v = 0; v < V; v++) { // targe v
                        if (distTo[u][v] > distTo[u][i] + distTo[i][v]) {
                            distTo[u][v] = distTo[u][i] + distTo[i][v];
                        }
                    }
                }
            }
        }

        // undirected graph - with weight
        void addEdge(int u, int v, double w) {
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }

        void printGraph() {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) System.out.print(adjMatrix[i][j]);
                System.out.println();
            }
        }
    }
}
