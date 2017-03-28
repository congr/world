import javafx.util.Pair;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 29..
 */
public class CJ201608_P1 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P1/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);

        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int source = M + N;
            int sink = M + N + 1;
            FordFulkerson ff = new FordFulkerson(M + N + 2);

            Point[] persons = new Point[N];
            for (int i = 0; i < N; i++) {
                persons[i] = new Point(sc.nextInt(), sc.nextInt());
                ff.capa[source][i] = 1;
            }

            Point[] shelters = new Point[M];
            for (int i = 0; i < M; i++) {
                shelters[i] = new Point(sc.nextInt(), sc.nextInt());
            }

            int[] candidates = new int[M]; // how many to save in a shelter := capacity of shelters
            for (int i = 0; i < M; i++) {
                candidates[i] = sc.nextInt();
                ff.capa[N + i][sink] = candidates[i];
            }

            int reachableDist = sc.nextInt();// reachable distance between person and shelter

            // person - shelter capa
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int d = distance(persons[i], shelters[j]);
                    if (d <= reachableDist) // can save to shelters[j]
                        ff.capa[i][N + j] = 1;
                }
            }

            int maxFlow = ff.networkFlow(source, sink);// is it possible to save all the people?

            int result = 0;
            if (maxFlow == N) result = 1;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int distance(Point a, Point b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.abs(dx) + Math.abs(dy);
    }

    static class FordFulkerson { // int or double?
        int V;          // Vertex Cnt
        int[][] capa;   // capa[u][v] : u -> v 로 보낼 수 있는 용량
        int[][] flow;   // flow[u][v] : u -> v 로 흘러가는 유량 (반대는 음수)

        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V) {
            this.V = V;

            capa = new int[V][V];
            flow = new int[V][V];   // init to 0
        }

        // 미리 정해진 capa에 따라 flow[u][v]를 계산하고 총 유량 totalFlow을 반환한다
        int networkFlow(int source, int sink) {
            // *** flow 초기화
            for (int i = 0; i < flow.length; i++) Arrays.fill(flow[i], 0);

            int totalFlow = 0;

            while (true) { // BFS
                int[] parent = new int[V];
                Arrays.fill(parent, -1);                            // should init to -1

                Queue<Integer> queue = new LinkedList<>();
                parent[source] = source;                            // root
                queue.add(source);

                while (!queue.isEmpty() && parent[sink] == -1) {    // 아직 sink까지 연결안되었다면
                    int here = queue.poll();                        // retrieve and remove

                    for (int there = 0; there < V; there++) {       // 잔여 용량이 있는 간선을 따라 탐색한다
                        if (capa[here][there] - flow[here][there] > 0 && parent[there] == -1) { // capa(u,v) - flow(u,v) 는 잔여용량
                            queue.add(there);
                            parent[there] = here;
                        }
                    }
                }

                if (parent[sink] == -1) break;                      // 증가 경로가 없으면 종료

                // 증가 경로를 통해 유량을 얼마나 보낼지 결정
                int amount = 987654321;
                for (int p = sink; p != source; p = parent[p]) {
                    amount = Math.min(capa[parent[p]][p] - flow[parent[p]][p], amount);
                }

                // 증가 경로를 통해 유량을 보냄
                for (int p = sink; p != source; p = parent[p]) {
                    flow[parent[p]][p] += amount;
                    flow[p][parent[p]] -= amount;
                }

                totalFlow += amount;                                // totalFlow에 보낸 유량을 계속 더함
            }

            return totalFlow;
        }
    }
}
