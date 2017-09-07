import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 8..
 */
public class P5_피난처 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P5/input002.txt"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int source = N + M;
            int sink = N + M + 1;
            FordFulkerson ff = new FordFulkerson(2 + N + M); // source + sink

            // N명 사람
            Place[] person = new Place[N];
            for (int i = 0; i < N; i++) {
                person[i] = new Place(sc.nextInt(), sc.nextInt());
                ff.setCapa(source, i, 1);
            }

            // M개 대피소
            Place[] shelter = new Place[M];
            for (int i = 0; i < M; i++) {
                shelter[i] = new Place(sc.nextInt(), sc.nextInt());
            }

            // M개 대피소 capa
            int[] capa = new int[M];
            for (int i = 0; i < M; i++) {
                capa[i] = sc.nextInt();
                ff.setCapa(i + N, sink, capa[i]);
            }

            int L = sc.nextInt(); // 거리 한계

            // 사람 - 대피소 간 거리 한계 안에 있으면 간선 연결
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int d = getDist(person[i], shelter[j]);
                    if (d <= L) ff.setCapa(i, N + j, 1); // capa 1 - 사람은 한명만 이동한다
                }
            }

            int flow = ff.networkFlow(N + M, N + M + 1); // source -> sink 로 얼마를 흘려보낼 수 있나

            int result = flow == N ? 1 : 0;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static int getDist(Place p, Place s) {
        return Math.abs(p.x - s.x) + Math.abs(p.y - s.y);
    }

    static class Place {
        int x, y;

        Place(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /* FordFulkerson ff = new FordFulkerson(V); Vcnt = V + source + sink
        * ff.setCapa(u, v, c);
        * maxFlow = ff.networkFlow(source, sink);
        * capa를 미리 설정해주면 maxFlow를 계산함 */
    static class FordFulkerson {
        int V;          // Vertex Cnt
        int[][] capa;   // capa[u][v] : u -> v 로 보낼 수 있는 용량
        int[][] flow;   // flow[u][v] : u -> v 로 흘러가는 유량 (반대는 음수)

        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V) {
            this.V = V;

            capa = new int[V][V];
            flow = new int[V][V];   // init to 0
        }

        void setCapa(int u, int v, int c) {
            capa[u][v] += c; // u -> v 로 가는 간선이 여러개 일 수 있음
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
