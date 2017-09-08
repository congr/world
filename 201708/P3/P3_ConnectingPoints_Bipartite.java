import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 18..
 */
public class P3_ConnectingPoints_Bipartite {

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201708/P3/sample.in"; // path from root
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
            int L = sc.nextInt();

            int[] Ns = new int[N];
            int[] Ms = new int[M];
            int[] Ls = new int[L];

            for (int i = 0; i < L; i++) { // 빨강
                Ls[i] = sc.nextInt();
            }

            for (int i = 0; i < M; i++) { // 파랑
                Ms[i] = sc.nextInt();
            }

            for (int i = 0; i < N; i++) { // 보라
                Ns[i] = sc.nextInt();
            }

            // N - L (보라 - 빨강)
            BipartiteList bi = new BipartiteList(1000000);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < L; j++) {
                    //bi.addEdge(Ns[i], Ls[j]);
                    bi.addEdge(Ls[j], Ns[i]);
                }
            }
            int NL = bi.flow();

            // N - M (보라 - 파랑
            BipartiteList bi2 = new BipartiteList(1000000);
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    bi2.addEdge(Ns[j], Ms[i]);
                    //bi2.addEdge(Ms[i], Ns[j]);
                }
            }
            int NM = bi2.flow();
            System.out.println("NL " + NL + " NM " + NM );

            int result = NL + NM;

            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class Bipartite {
        int[] aMatch, bMatch;
        int N, M;
        boolean[][] adjMatrix;
        boolean[] visited;

        Bipartite(int n, int m) {
            this.N = n;
            this.M = m;
            adjMatrix = new boolean[N][M];
            aMatch = new int[N];
            bMatch = new int[M];

            Arrays.fill(aMatch, -1); // -1 초기화 : 연결 안 된 상태
            Arrays.fill(bMatch, -1);
        }

        int bipartiteMatch() {
            int size = 0;
            for (int i = 0; i < N; i++) {
                visited = new boolean[N];
                if (dfs(i)) // i에서 시작하는 증가경로를 찾는다
                    ++size;
            }
            return size;
        }

        boolean dfs(int a) {
            if (visited[a]) return false; // 이미 매칭되었으면 리턴

            visited[a] = true; // 방문 시작 시점

            for (int b = 0; b < M; b++) {
                if (adjMatrix[a][b]) {
                    if (bMatch[b] == -1 || dfs(bMatch[b])) { // 증가경로가 발견되면 a, b 매치시킴
                        aMatch[a] = b;
                        bMatch[b] = a;
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
