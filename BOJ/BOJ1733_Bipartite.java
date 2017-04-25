import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 25..
 */
// https://gist.github.com/Baekjoon/689e5e3340bc7abe6b7e
public class BOJ1733_Bipartite {
    public static void main(String[] args) { // class Solution
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        BipartiteList bi = new BipartiteList(1000000);
        for (int i = 0; i < N; i++) {
            int left = sc.nextInt();
            int right = sc.nextInt();
            bi.addEdge(i, left - 1);
            bi.addEdge(i, right - 1);
        }
        int ans = bi.flow();
        if (ans != N) {
            System.out.println(-1);
        } else {
            for (int i = 0; i < N; i++) {
                System.out.println(bi.matchL[i] + 1);
            }
        }
    }
}

// bipartite - dfs - adjList (boj)
// Maximum flow 와 거의 동일한데 주로 이분그래프는 DFS로 한다
// 차이는 - 소스와 싱크 간선을 연결하지 않는다
//      - 반대 간선 추가 하지 않는다
//      - capa와 flow는 무조건 1이다
class BipartiteList {
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