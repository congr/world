import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 3. 6..
 */
public class MATCHFIX {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] NM = br.readLine().split(" ");
            int N = Integer.parseInt(NM[0]);    // 선수의 수
            int M = Integer.parseInt(NM[1]);    // 남은 경기의 수

            // 현재까지 승리
            int[] players = new int[N];
            String[] V = br.readLine().split(" ");
            for (int i = 0; i < N; i++) players[i] = Integer.parseInt(V[i]);
            // 현재까지 승수 중 가장 큰 승수 =: 누가 가장 많은 승수로 이기고 있나 => 남은 경기로 0번이 현재 일등을 이길 수 있나?
            int curMaxWins = Arrays.stream(players, 1, N).max().getAsInt();// startInclusive, endExclusive

            // 경기를 할 두 선수 쌍
            int canWin = 0;
            Pair<Integer, Integer>[] matches = new Pair[M];
            for (int i = 0; i < M; i++) {       // 경기를 할 두 선수 쌍
                String[] M01 = br.readLine().split(" ");
                int a = Integer.parseInt(M01[0]);
                int b = Integer.parseInt(M01[1]);
                matches[i] = new Pair<>(a, b);  // match vertexes

                if (a == 0 || b == 0)           // 0 player 0선수가 경기를 하는 한다면 최대 이길 수 있는 경기수는 canWin 인데 최소로 하고자 한다
                    canWin++;                   // 남은 경기에서 0번이 이길 수 있는 최대 승수
            }

            if (curMaxWins >= players[0] + canWin) {          // 0번선수의 현재승수와 남은 경기수가, 다른 선수의 최고 승수보다 작다면 승리할 수 없다
                System.out.println(-1);
                continue;
            }

            // source : N+M번째 vertex, sink : N+M+1번째 vertex
            FordFulkerson ff = new FordFulkerson(N + M + 1 + 1, N + M, N + M + 1); // N : players, M: remained matches, 1 : source, 1 : sink
            ff.initCapa(matches);

            int minWins = -1;
            int curWins = players[0];                                               // 현재 0번 선수의 승수
            for (int totalWin = curWins; totalWin <= curWins + canWin; totalWin++) {// 현재 승수에서 +1을 하면서 모두 매치되는 최소 승수를 구함
                if (totalWin > curMaxWins && ff.canWinWith(totalWin, players, M)) {
                    minWins = totalWin;                                             // 현재까지 totalWin
                    break;
                }
            }

            System.out.println(minWins);                                            // 불가능하면 -1, 가능한 경우 최소 승수
        }

        br.close();
        out.close();
    }

    // network flow
    static class FordFulkerson { // int or double?
        int V;          // Vertex Cnt
        int[][] capa;   // capa[u][v] : u -> v 로 보낼 수 있는 용량
        int[][] flow;   // flow[u][v] : u -> v 로 흘러가는 유량 (반대는 음수)
        int source;
        int sink;

        // 네트워크 유량 문제의 해결 방법을 제시한 것은 포드 풀커슨이지만, 이를 어떤 방법(DFS or BFS)으로 구현하는지 제시한 것은 Edmonds-Karp이다.
        FordFulkerson(int V, int source, int sink) {
            this.V = V;
            this.source = source;
            this.sink = sink;

            capa = new int[V][V];
            flow = new int[V][V];   // init to 0
        }

        // 소스에서 각 경기로, 경기에서 선수로 capa를 1로 준다. 즉 간선을 연결한다
        void initCapa(Pair<Integer, Integer>[] matches) {
            int m = matches.length;
            for (int i = 0; i < m; i++) {
                capa[source][i] = 1;                                        // source -> match vertexes
                capa[i][m + matches[i].getKey()] = 1;                       // 각 경기에서 -> 선수1
                capa[i][m + matches[i].getValue()] = 1;                     // 각 경기에서 -> 선수2
            }
        }

        // 선수들에서 sink로 가는 간선을 추가하고 이 capa를 재조정해가며, 0번 선수가 현재 totalWins로 우승할 수 있는지를 반환
        boolean canWinWith(int totalWins, int[] players, int m) {           // m 남은 경기 수
            for (int i = 0, n = players.length; i < n; i++) {               // 각 선수에서 싱크로 가능한 최대 승수를 용량으로 하는 간선을 추가
                int maxWin = (i == 0 ? totalWins : totalWins - 1);
                capa[i + m][sink] = maxWin - players[i];                    // 각 선수에서 sink로 가는 간선의 capa 지정
            }

            return networkFlow(source, sink) == m;                          // 이때 모든 경기에서 승자를 지정할 수 있나?
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
