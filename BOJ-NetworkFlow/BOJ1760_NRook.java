import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 26..
 */
public class BOJ1760_NRook {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int x = sc.nextInt();
                arr[i][j] = x;
            }
        }

        int[][] rmap = new int[N][M];
        int[][] cmap = new int[N][M];
        int ri = 0;
        int ci = 0;

        // 행 row 레벨에서 번호를 매김
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] != 2) {
                    if (j == 0 || arr[i][j - 1] == 2) ri++;// 이전이 벽돌이라면 ++
                    rmap[i][j] = ri;
                }
            }
        }

        // 열 col 레벨에서 번호를 매김 :: 열을 바깥 루프로 함
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (arr[i][j] != 2) {
                    if (i == 0 || arr[i - 1][j] == 2) ci++; // 이전(윗칸)이 없었거나 벽돌이라면 ++
                    cmap[i][j] = ci;
                }
            }
        }

        BipartiteList bi = new BipartiteList(Math.max(ri, ci) + 1);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (rmap[i][j] > 0 && cmap[i][j] > 0 && arr[i][j] == 0) { // 1부터 번호를 매김
                    bi.addEdge(rmap[i][j], cmap[i][j]);
                    //bi.addEdge(cmap[i][j], rmap[i][j]);
                }
            }
        }

        //printArrays(rmap, N, M);
        //printArrays(cmap, N, M);

        System.out.println(bi.flow());
    }

    static void printArrays(int[][] arr, int n, int m) {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // bipartite - dfs - adjList (boj)
    // Maximum flow 와 거의 동일한데 주로 이분그래프는 DFS로 한다
    // 차이는 - 소스와 싱크 간선을 연결하지 않는다
    //      - 반대 간선 추가 하지 않는다
    //      - capa와 flow는 무조건 1이다
    static class BipartiteList {
        int n;
        int source, sink;

        ArrayList<Integer>[] graph;
        int[] check;
        int[] matchR;
        int[] matchL;
        int step;

        BipartiteList(int n) {
            this.n = n;

            graph = new ArrayList[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

            check = new int[n];
            matchL = new int[n];
            matchR = new int[n];

            Arrays.fill(matchL, -1);
            Arrays.fill(matchR, -1);
        }

        void addEdge(int u, int v) {
            graph[u].add(v);
        }

        boolean dfs(int x) {
            if (x == -1) return true;

            for (int next : graph[x]) {
                if (check[next] == step) continue;
                check[next] = step;
                if (dfs(matchR[next])) {
                    matchR[next] = x;
                    matchL[x] = next;
                    return true;
                }
            }
            return false;
        }

        int flow() {
            int ans = 0;
            for (int i = 0; i < n; i++) {
                step = i + 1;
                if (dfs(i)) {
                    ans += 1;
                }
            }
            return ans;
        }
    }

}
