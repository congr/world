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
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P3/sample.in"; // path from root
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
            System.out.println(Arrays.toString(pts));

            Arrays.sort(pts); //x -> y 
//            System.out.println("sorted at first");
//            System.out.println(Arrays.toString(pts));

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    graphM.addEdge(i, j, pts[i].distanceSquaredTo(pts[j]));
                }
            }
//            graphM.printGraph(graphM.adjMatrix);

//            graphM.floydWarshall();
//            graphM.printGraph(graphM.distTo);

            double bestArea = Double.MAX_VALUE;
            // 0 혹은 INF 제외하고 ArrayList에 넣고 convex hull 인지 확인.
            for (int i = 0; i < N; i++) { // pts[i]
                ArrayList<Pair<Double, Point2D>> pairs = new ArrayList<>();
                for (int j = 0; j < N; j++) {
                    if (graphM.adjMatrix[i][j] != 0 && graphM.adjMatrix[i][j] != Double.MAX_VALUE)
                        pairs.add(new Pair(graphM.adjMatrix[i][j], pts[j]));
                }
//                System.out.print(pts[i] + " 거리순 소팅전 - ");
//                System.out.println(Arrays.toString(pairs.toArray()));
                //pairs.sort((d1, d2) -> d1.getKey().compareTo(d2.getKey()));
                System.out.print(pts[i] + " 거리순 소팅후 - ");
                System.out.println(Arrays.toString(pairs.toArray()));

                int n = pairs.size();
                Point2D[] points = new Point2D[n + 1];
                points[0] = pts[i];
                for (int j = 0; j < n; j++) {
                    points[j + 1] = pairs.get(j).getValue();
                }

                // 근접점 4개 넣고 convex hull 체크, 다음 근접점 하나씩 더 넣어가며 convex hull이 될때 까지.
                // convex hull이 되면 넓이 계산, 최소 넓이 업데이트
                for (int k = 4; k < n + 1; k++) {
                    GrahamScan gs = new GrahamScan(Arrays.copyOf(points, k));
                    if (gs.size() >= 4) {
                        // 넓이

                        Point2D[] ha = gs.hullArrayCW(); // cw 방향
                        double area = Point2D.polygonArea(ha);
                        bestArea = Math.min(area, bestArea);
                        System.out.println("ha----");
                        System.out.println(Arrays.toString(ha));
                        System.out.println("area: " + area + " best: " + bestArea);
                        //break;
                    }
                }
            }

            String result = String.format("%.1f", bestArea); // 100.25 -> 100.3
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

            distTo = new double[V][V];
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

        void printGraph(double[][] array) {
            System.out.println("print array");
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) System.out.print(array[i][j] + " ");
                System.out.println();
            }
        }
    }
}
